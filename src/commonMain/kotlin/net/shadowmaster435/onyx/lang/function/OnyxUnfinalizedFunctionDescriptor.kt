package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.exception.ParseException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxUnfinalizedClassTypeRef
import net.shadowmaster435.onyx.lang.code.OnyxReturn
import net.shadowmaster435.onyx.lang.code.OnyxUnfinalizedCodeBlock
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.generics.OnyxUnfinalizedGenericTuples
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.lang.util.OnyxDefinitionName
import net.shadowmaster435.onyx.lang.util.extensionDefinitionName
import net.shadowmaster435.onyx.lang.util.list
import net.shadowmaster435.onyx.lang.util.typeTemplate
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxModifiers
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxUnfinalizedFunctionDescriptor(
    name: String,
    modifiers: List<OnyxModifierType>,
    private val codeBlockUnfinal: OnyxUnfinalizedCodeBlock,
    private val genericTuplesUnfinal: OnyxUnfinalizedGenericTuples,
    private val returnTypeUnfinal: OnyxUnfinalizedClassTypeRef,
    private val tuplesUnfinal: OnyxUnfinalizedTuples
) : OnyxFunctionDescriptor(
    name, tuplesUnfinal, codeBlockUnfinal, modifiers, genericTuplesUnfinal, returnTypeUnfinal
), OnyxFinalizable<OnyxFunctionDescriptor> {
    override fun createFinal() = OnyxFunctionDescriptor(
        name,
        tuplesUnfinal.createFinal(),
        codeBlockUnfinal.createFinal(),
        modifiers,
        genericTuplesUnfinal.createFinal(),
        returnTypeUnfinal.createFinal()
    )

    @Suppress("UNCHECKED_CAST")
    companion object {
        val template = ParsinatorTemplate {
            chain(false) {
                use(OnyxModifierType.template)
                token(false, "fun", excludeFromChains = true)
                use(OnyxUnfinalizedGenericTuples.definitionTemplate)
                whitespace(true)
                use(extensionDefinitionName)
                whitespace(true)
                use(OnyxUnfinalizedTuples.functionTemplate)
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
                        use(net.shadowmaster435.onyx.lang.util.expressionTemplate)
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
                        use(OnyxUnfinalizedCodeBlock.template)
                    }
                }
            } makes {
                it as list
                val modifiers = it[0] as OnyxModifiers
                val generics = it[1] as OnyxUnfinalizedGenericTuples
                val definitionName = it[2] as OnyxDefinitionName
                val name = definitionName.name
                val tuples = it[3] as OnyxUnfinalizedTuples
                var returnType = OnyxUnfinalizedClassTypeRef(OnyxUnit.desc.qualifiedName)
                val block = it.getOrNull(4)?.let { blockList ->
                    blockList as List<*>
                    val obj = blockList.last()
                    val isExpression = obj is OnyxExpression
                    returnType = blockList[0] as OnyxUnfinalizedClassTypeRef
                    return@let if (isExpression)
                        OnyxUnfinalizedCodeBlock(listOf(OnyxReturn(obj)), returnType)
                    else
                        obj as OnyxUnfinalizedCodeBlock
                }

                val desc = if (modifiers.contains(OnyxModifierType.ABSTRACT)) OnyxUnfinalizedVirtualFunctionDescriptor(
                    name, modifiers, generics, returnType, tuples
                ) else OnyxUnfinalizedFunctionDescriptor(name, modifiers,
                    block ?: throw ParseException("Non virtual functions must have a code block defined."),
                    generics, returnType, tuples
                )
                desc.extensionType = definitionName.extensionClass
                desc.extensionGenerics = definitionName.extensionGenerics
                desc
            }
        }
    }
}