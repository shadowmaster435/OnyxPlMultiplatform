package net.shadowmaster435.onyx.lang.util

import net.shadowmaster435.onyx.ext.substringOrNull
import net.shadowmaster435.onyx.lang.clazz.OnyxUnfinalizedClassDesc
import net.shadowmaster435.onyx.lang.field.OnyxUnfinalizedFieldDesc
import net.shadowmaster435.onyx.lang.function.OnyxUnfinalizedFunctionDescriptor
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxUnfinalizedGenericTuples
import net.shadowmaster435.onyx.lang.pkg.OnyxPackage
import net.shadowmaster435.onyx.lang.pkg.OnyxPackage.Companion.byQualifiedName
import net.shadowmaster435.parsinator.Parsinator
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate
import net.shadowmaster435.parsinator.util.ParsinatorErrorType

internal typealias list = List<*>

fun accessChainTemplate(make: (Any?) -> Any?) = ParsinatorTemplate {
    repeating(true, false) {
        name(false)
        whitespace(true)
        trailing {
            name(false)
            whitespace(true)
            token(true, ".", false)
            whitespace(true)
        }
    } makes make
}

fun accessChainStringTemplate() = ParsinatorTemplate {
    repeating(true, false) {
        name(false)
        whitespace(true)
        trailing {
            token(true, ".", false)
            whitespace(true)
            name(false)
            whitespace(true)
        }
    } makes {
        var str = ""
        it as List<*>
        for (entry in it) if (entry is String) str += entry
        str
    }
}

fun accessChainStringTemplate(make: (String) -> Any?) = ParsinatorTemplate {
    repeating(true, false) {
        name(false)
        whitespace(true)
        trailing {
            token(true, ".", false)
            whitespace(true)
            name(false)
            whitespace(true)
        }
    } makes {
        var str = ""
        it as List<*>
        for (entry in it) if (entry is String) str += entry
        make(str)
    }
}

val memberTemplate = ParsinatorTemplate {
    branch(false) {
        use(OnyxUnfinalizedFieldDesc.definitionTemplate)
        use(OnyxUnfinalizedFunctionDescriptor.template)
        use(OnyxUnfinalizedClassDesc.template)
    }
}

val extensionDefinitionName = ParsinatorTemplate {
    manual(false) {
        var accum = ""
        var depth = 0
        var started = false
        while (depth > 0 || !started) {
            if (it.char == '<') {
                depth++
                started = true
            }
            if (it.char == '>') depth--
            if (depth == 0 && it.char == '(') break
            accum += it.char
            it.inc()
            if (!it.inbounds(0)) return@manual ParsinatorErrorType.EOF
        }
        held = accum
        null
    } makes {
        val nameString = it as String
        val extensionSplitIndex = nameString.lastIndexOf('.')
        val name = nameString.substringOrNull(
            extensionSplitIndex.let { i ->
                if (i < 0) i
                else i + 1
            }
        ) ?: nameString
        var extensionGenericTuples: OnyxGenericTuples? = null
        val extensionGenericString = (if (extensionSplitIndex < 0) nameString else nameString.substring(0, extensionSplitIndex))
        val genericSplitIndex = extensionGenericString.indexOf('<')
        if (genericSplitIndex >= 0) {
            val parser = Parsinator {
                use(OnyxUnfinalizedGenericTuples.inputParamTemplate) makes { t ->
                    extensionGenericTuples = t as OnyxGenericTuples
                }
            }
            parser.parse(nameString.substring(genericSplitIndex))
        }
        val extensionClass = OnyxPackage.findClassByQName(
            if (genericSplitIndex < 0) nameString else nameString.substring(0..<genericSplitIndex)
        )
        OnyxDefinitionName(name, extensionClass, extensionGenericTuples)
    }
}

val expressionTemplate: ParsinatorTemplate get() = TODO()

fun nameTemplate(optional: Boolean) =
    ParsinatorTemplate {name(optional, nameRegex, numbersRegex)}

val importTemplate = ParsinatorTemplate {
    chain(false) {
        token(false, "import", excludeFromChains = true)
        whitespace(false)
        use(accessChainStringTemplate(::byQualifiedName))
        chain(true) {
            whitespace(true)
            token(true, ".", false, excludeFromChains = true)
            whitespace(true)
            token(true, "*", false)
        } makes {true}
    } makes {TODO("Import Template")}
}
val typeTemplate: ParsinatorTemplate = ParsinatorTemplate {
    chain(false) {
        use(accessChainStringTemplate { it }) makes {it}
        whitespace(true)
        use(OnyxUnfinalizedGenericTuples.inputParamTemplate)
    } makes {TODO("Type Template")}
}

val typeDefinitionTemplate: ParsinatorTemplate = ParsinatorTemplate {
    chain(false) {
        use(nameTemplate(false))
        whitespace(true)
        use(OnyxUnfinalizedGenericTuples.definitionTemplate)
    } makes {TODO("Type Template")}
}
