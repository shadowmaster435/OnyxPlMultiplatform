package net.shadowmaster435.onyx.lang.util

import net.shadowmaster435.onyx.exception.UnknownException
import net.shadowmaster435.onyx.lang.code.OnyxImports
import net.shadowmaster435.onyx.lang.pkg.OnyxPackage
import net.shadowmaster435.onyx.lang.pkg.OnyxPackage.Companion.byQualifiedName
import net.shadowmaster435.onyx.lang.pkg.globalRoot
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

fun accessChainTemplate(make: (Any?) -> Any?) = ParsinatorTemplate {
    repeating(true, false) {
        name(false)
        whitespace(true)
        trailing {
            name(false)
            whitespace(true)
            token(true, ".")
            whitespace(true)
        }
    } makes make
}

fun accessChainStringTemplate(make: (String) -> Any?) = ParsinatorTemplate {
    repeating(true, false) {
        name(false)
        whitespace(true)
        trailing {
            token(true, ".")
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

val expressionTemplate: ParsinatorTemplate get() = TODO()

val importTemplate = ParsinatorTemplate {
    chain(false) {
        token(false, "import")
        whitespace(false)
        use(accessChainStringTemplate(::byQualifiedName))
        chain(true) {
            whitespace(true)
            token(true, ".")
            whitespace(true)
            token(true, "*")
        } makes {true}
    }
}
val typeTemplate = ParsinatorTemplate {
    use(accessChainStringTemplate { it }) makes {
        OnyxPackage.findClassByQName(it as String)
    }
}

