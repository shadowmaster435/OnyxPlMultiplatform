package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType
import net.shadowmaster435.onyx.lang.generics.OnyxUnfinalizedGenericTuples
import net.shadowmaster435.onyx.lang.impl.OnyxCodeBlockContainer
import net.shadowmaster435.onyx.lang.impl.OnyxRunnableImpl
import net.shadowmaster435.onyx.lang.util.OnyxDefinitionName
import net.shadowmaster435.onyx.lang.util.expressionTemplate
import net.shadowmaster435.onyx.lang.util.extensionDefinitionName
import net.shadowmaster435.onyx.lang.util.list
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxParams
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

open class OnyxFunctionCall(
    val params: List<OnyxExpression>,
    val generics: List<OnyxGenericType>,
    val desc: AbstractOnyxFunctionDescriptor
) : OnyxRunnableImpl, OnyxCodeBlockContainer {

    override fun execute(
        parent: OnyxCodeBlock?,
        thisInst: OnyxClassInstance?,
        params: OnyxParams
    ): OnyxClassInstance {
        desc.tuples.validate(params)
        if (desc.genericTuples.tuples.isNotEmpty()) desc.genericTuples.validate(generics)
        return desc.invoke(thisInst, params, generics)
    }

    override fun forEachBlock(consumer: (block: OnyxCodeBlock) -> Unit) {
        TODO("Not yet implemented")
    }

    companion object {
        fun template(
            thisInst: () -> OnyxClassInstance?,
            params: () -> OnyxParams,
            generics: () -> List<OnyxGenericType>,
            desc: AbstractOnyxFunctionDescriptor
        ): ParsinatorTemplate {
            return ParsinatorTemplate {
                chain(false) {
                    use(extensionDefinitionName)
                    whitespace(true)
                    use(OnyxUnfinalizedGenericTuples.inputParamTemplate)
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
                } makes {
                    it as list
                    val definitionName = it[0] as OnyxDefinitionName
                }
            }
        }
    }
}