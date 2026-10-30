package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.exception.UninitializedException
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock
import net.shadowmaster435.onyx.lang.generics.OnyxUnfinalizedGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxUnfinalizedGenericType
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.lang.util.OnyxDefinitionName
import net.shadowmaster435.onyx.lang.util.expressionTemplate
import net.shadowmaster435.onyx.lang.util.extensionDefinitionName
import net.shadowmaster435.onyx.lang.util.list
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxParams
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxUnfinalizedFunctionCall(
    params: List<OnyxExpression>,
    private val genericsUnfinal: List<OnyxUnfinalizedGenericType>,
    private val descUnfinal: OnyxUnfinalizedFunctionDescriptor
) : OnyxFunctionCall(
    params, genericsUnfinal, descUnfinal
), OnyxFinalizable<OnyxFunctionCall> {

    override fun execute(
        parent: OnyxCodeBlock?,
        thisInst: OnyxClassInstance?,
        params: OnyxParams
    ): OnyxClassInstance {
        throw UninitializedException("Function call is not finalized")
    }

    override fun createFinal() = OnyxFunctionCall(
        params.apply { forEach { it.checkCreateFinal() } },
        buildList { genericsUnfinal.forEach { add(it.checkCreateFinal()) } },
        descUnfinal.checkCreateFinal()
    )

    companion object {
        fun template(thisInst: OnyxClassInstance?): ParsinatorTemplate {
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