package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.exception.ParseException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock
import net.shadowmaster435.onyx.lang.code.OnyxReturn
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType
import net.shadowmaster435.onyx.lang.util.*
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxModifiers
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
    @Suppress("UNCHECKED_CAST")
    companion object {
        val template = ParsinatorTemplate {
            chain(false) {
                use(OnyxModifierType.template)
                token(false, "fun", excludeFromChains = true)
                use(OnyxGenericTuples.definitionTemplate)
                whitespace(true)
                use(extensionDefinitionName)
                whitespace(true)
                use(OnyxTuples.functionTemplate)
                whitespace(true)
                branch(true) {
                    chain(true) {
                        branch(false) {
                            chain(true) {
                                token(false, ":", false, true)
                                whitespace(true)
                                use(typeTemplate)
                            }
                            exit(0) makes { OnyxUnit }
                        }
                        whitespace(true)
                        token(false, "=", false, true)
                        whitespace(true)
                        use(expressionTemplate)
                    }
                    branch(false) {
                        branch(false) {
                            chain(true) {
                                token(false, ":", false, true)
                                whitespace(true)
                                use(typeTemplate)
                            }
                            exit(0) makes { OnyxUnit }
                        }
                        whitespace(true)
                        use(OnyxCodeBlock.template)
                    }
                }
            } makes {
                it as list
                val modifiers = it[0] as OnyxModifiers
                val generics = it[1] as OnyxGenericTuples
                val definitionName = it[2] as OnyxDefinitionName
                val name = definitionName.name
                val tuples = it[3] as OnyxTuples
                var returnType: OnyxClassDescriptor = OnyxUnit.desc
                val block = it.getOrNull(4)?.let { blockList ->
                    blockList as List<*>
                    val obj = blockList.last()
                    val isExpression = obj is OnyxExpression
                    returnType = blockList[0] as OnyxClassDescriptor
                    return@let if (isExpression)
                        OnyxCodeBlock(listOf(OnyxReturn(obj)), returnType)
                    else
                        obj as OnyxCodeBlock
                }

                val desc = if (modifiers.contains(OnyxModifierType.ABSTRACT)) OnyxVirtualFunctionDescriptor(
                    name, tuples, modifiers, generics, returnType
                ) else OnyxFunctionDescriptor(name, tuples,
                    block ?: throw ParseException("Non virtual functions must have a code block defined."),
                    modifiers, generics, returnType
                )
                desc.extensionType = definitionName.extensionClass
                desc.extensionGenerics = definitionName.extensionGenerics
                desc
            }
        }
    }

}