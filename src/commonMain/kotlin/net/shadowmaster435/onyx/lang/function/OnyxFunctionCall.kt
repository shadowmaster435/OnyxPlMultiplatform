package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType
import net.shadowmaster435.onyx.lang.impl.OnyxRunnableImpl
import net.shadowmaster435.onyx.lang.util.OnyxDefinitionName
import net.shadowmaster435.onyx.lang.util.expressionTemplate
import net.shadowmaster435.onyx.lang.util.extensionDefinitionName
import net.shadowmaster435.onyx.lang.util.list
import net.shadowmaster435.onyx.util.OnyxParams
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxFunctionCall(
    val thisInst: () -> OnyxClassInstance?,
    val params: () -> OnyxParams,
    val generics: () -> List<OnyxGenericType>,
    val desc: AbstractOnyxFunctionDescriptor
) : OnyxRunnableImpl {

    override fun execute(
        thisInst: OnyxClassInstance?,
        params: OnyxParams
    ): OnyxClassInstance {
        val thisInst = thisInst()
        val params = params()
        val generics = generics()
        desc.tuples.validate(params)
        if (desc.genericTuples.tuples.isNotEmpty()) desc.genericTuples.validate(generics)
        return desc.invoke(thisInst, params, generics)
    }
    companion object {
        fun template(thisInst: OnyxClassInstance?): ParsinatorTemplate {
            return ParsinatorTemplate {
                chain(false) {
                    use(extensionDefinitionName)
                    whitespace(true)
                    use(OnyxGenericTuples.inputParamTemplate)
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