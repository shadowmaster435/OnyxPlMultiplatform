package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.enums.code.OnyxNullability
import net.shadowmaster435.onyx.lang.enums.jvm.JVMApplicability
import net.shadowmaster435.onyx.lang.impl.jvm.JVMFlaggable
import net.shadowmaster435.onyx.lang.util.expressionTemplate
import net.shadowmaster435.onyx.lang.util.list
import net.shadowmaster435.onyx.lang.util.nameTemplate
import net.shadowmaster435.onyx.lang.util.typeTemplate
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate


class OnyxTuple(
    val type: OnyxClassDescriptor,
    val name: String,
    val nullability: OnyxNullability,
    val vararg: Boolean = false,
    val initExpression: OnyxExpression? = null
) : JVMFlaggable {
    override val applicability = JVMApplicability.PARAMETER

    val nullable get() = nullability.nullable
    val optional get() = initExpression != null

    constructor(
        type: OnyxClassDescriptor,
        name: String,
        nullable: Boolean,
        vararg: Boolean = false,
        initExpression: OnyxExpression? = null
    ) : this (
        type, name,
        if (nullable) OnyxNullability.NULLABLE else OnyxNullability.NON_NULLABLE,
        vararg, initExpression
    )
    fun wrap() = OnyxTuples(this)

    override fun toString() = "$name: $type"

    companion object {
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
                val type = it[2] as OnyxClassDescriptor
                val nullability = it[3] as OnyxNullability
                val initExpression = it.getOrNull(4) as? OnyxExpression
                OnyxTuple(type, name, nullability, vararg, initExpression)
            }
        }
    }


}