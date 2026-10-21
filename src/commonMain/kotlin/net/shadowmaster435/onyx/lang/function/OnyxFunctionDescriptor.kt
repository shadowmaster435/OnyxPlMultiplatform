package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.ext.substringOrNull
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock
import net.shadowmaster435.onyx.lang.code.OnyxReturn
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType
import net.shadowmaster435.onyx.lang.pkg.OnyxPackage
import net.shadowmaster435.onyx.lang.util.*
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxModifiers
import net.shadowmaster435.parsinator.Parsinator
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxFunctionDescriptor(
    name: String,
    tuples: OnyxTuples,
    val block: OnyxCodeBlock,
    modifiers: List<OnyxModifierType>,
    genericTuples: OnyxGenericTuples,
    returnType: OnyxClassDescriptor = OnyxUnit.desc,
) : AbstractOnyxFunctionDescriptor(name, tuples, modifiers, genericTuples, returnType) {
    override val type = OnyxMemberType.FUNCTION

    override fun invoke(
        thisInst: OnyxClassInstance?,
        params: List<OnyxClassInstance>,
        generics: List<OnyxGenericType>
    ): OnyxClassInstance {
        genericTuples.validate(generics)
        tuples.validate(params)
        return block.execute(thisInst, params).first
    }
    override fun poke() {}
    @Suppress("UNCHECKED_CAST")
    companion object {



        val definitionTemplate = ParsinatorTemplate {
            chain(false) {
                use(OnyxModifierType.template)
                token(false, "fun", excludeFromChains = true)
                use(OnyxGenericTuples.definitionTemplate)
                whitespace(true)
                use(extensionDefinitionName)
                whitespace(true)
                use(OnyxTuples.template)
                whitespace(true)
                branch(true) {
                    chain(true) {
                        chain(true) {
                            token(false, ":", false, true)
                            whitespace(true)
                            use(optionalTypeTemplate)
                        }
                        whitespace(true)
                        token(false, "=", false, true)
                        whitespace(true)
                        use(expressionTemplate)
                    }
                    branch(false) {
                        chain(true) {
                            token(false, ":", false, true)
                            whitespace(true)
                            use(optionalTypeTemplate)
                        }
                        whitespace(true)
                        use(OnyxCodeBlock.template)
                    }
                }
            } makes {
                it as list
                val modifiers = it[0] as OnyxModifiers
                val generics = it[1] as OnyxGenericTuples
                val nameString = it[2] as String
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
                        use(OnyxGenericTuples.paramTemplate) makes { t ->
                            extensionGenericTuples = t as OnyxGenericTuples
                        }
                    }
                    parser.parse(nameString.substring(genericSplitIndex))
                }
                val extensionClass = OnyxPackage.findClassByQName(
                    if (genericSplitIndex < 0) nameString else nameString.substring(0..<genericSplitIndex)
                )
                val tuples = it[4] as OnyxTuples
                var returnType: OnyxClassDescriptor = OnyxUnit.desc
                val block = it.getOrNull(5)?.let { blockList ->
                    blockList as List<*>
                    val isTyped = blockList.getOrNull(0) !is List<*>
                    val obj = blockList.last()
                    val isExpression = obj is OnyxExpression
                    var i = 0
                    if (isTyped) {
                        returnType = (blockList[0] as List<*>)[0] as OnyxClassDescriptor
                        i = 1
                    }
                    if (isExpression)
                        return@let OnyxCodeBlock(listOf(OnyxReturn(obj)), returnType)
                    else
                        return@let obj as OnyxCodeBlock
                }

                val desc = if (modifiers.contains(OnyxModifierType.ABSTRACT)) OnyxVirtualFunctionDescriptor(
                    name, tuples, modifiers, generics, returnType
                ) else OnyxFunctionDescriptor(name, tuples,
                    block ?: throw ImpossibleException("Null code block for non virtual function. this should never happen"),
                    modifiers, generics, returnType
                )
                desc.extensionType = extensionClass
                desc.extensionGenerics = extensionGenericTuples
                desc
            }
        }
        val invokeTemplate = ParsinatorTemplate {
            chain(false) {
                use(accessChainStringTemplate())
                whitespace(true)
                use(OnyxGenericTuples.paramTemplate)
                whitespace(true)
                token(false, "(", false, true)
                whitespace(true)
                repeating(true, true) {
                    branch(true) {
                        chain(false) {
                            token(false, "(", false, true)
                            repeating(true, true) {
                                use(expressionTemplate)
                                trailing {
                                    token(true, ",", excludeFromChains = true)
                                }
                            }
                            token(false, ")", false, true)
                        }
                        use(expressionTemplate)
                    }
                    whitespace(true)
                }
                token(false, ")", false, true)
            } makes {TODO("Function invoke template")}
        }
    }

}