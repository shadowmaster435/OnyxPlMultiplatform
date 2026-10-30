package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.exception.UninitializedException
import net.shadowmaster435.onyx.lang.clazz.OnyxUnfinalizedClassTypeRef
import net.shadowmaster435.onyx.lang.enums.code.OnyxNullability
import net.shadowmaster435.onyx.lang.enums.jvm.JVMApplicability
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.lang.util.expressionTemplate
import net.shadowmaster435.onyx.lang.util.list
import net.shadowmaster435.onyx.lang.util.nameTemplate
import net.shadowmaster435.onyx.lang.util.typeTemplate
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate


class OnyxUnfinalizedTuple(
    private val typeUnfinal: OnyxUnfinalizedClassTypeRef,
    name: String,
    nullability: OnyxNullability,
    vararg: Boolean = false,
    initExpression: OnyxExpression? = null
) : OnyxTuple(
    typeUnfinal, name, nullability, vararg, initExpression
), OnyxFinalizable<OnyxTuple> {
    override val applicability = JVMApplicability.PARAMETER

    override fun wrap() = throw uninitialized
    override fun createFinal() = OnyxTuple(
        typeUnfinal.checkCreateFinal(), name, nullability, vararg, initExpression
    )


    companion object {
        private val uninitialized get() = UninitializedException("Generic is not initialized")
        val functionTemplate = ParsinatorTemplate {
            chain(true) {
                branch(false) {
                    token(false, "vararg", true, false) makes {true}
                    exit(0) makes {false}
                }
                whitespace(true)
                use(nameTemplate(false))
                whitespace(true)
                token(false, ":", excludeFromChains = true)
                whitespace(true)
                use(typeTemplate)
                branch(false) {
                    token(false, "?", false) makes {
                        OnyxNullability.NULLABLE
                    }
                    exit(0) makes { OnyxNullability.NON_NULLABLE }
                }
                chain(true) {
                    whitespace(true)
                    token(false, "=", excludeFromChains = true)
                    whitespace(true)
                    use(expressionTemplate)
                }
            } makes {
                it as list
                val vararg = it[0] as Boolean
                val name = it[1] as String
                val type = OnyxUnfinalizedClassTypeRef(it[2] as String)
                val nullability = it[3] as OnyxNullability
                val initExpression = it.getOrNull(4) as? OnyxExpression
                OnyxUnfinalizedTuple(type, name, nullability, vararg, initExpression)
            }
        }
    }


}