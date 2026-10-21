package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.enums.code.OnyxNullability
import net.shadowmaster435.onyx.lang.util.accessChainStringTemplate
import net.shadowmaster435.onyx.lang.util.expressionTemplate
import net.shadowmaster435.onyx.lang.util.nameRegex
import net.shadowmaster435.onyx.lang.util.nameTemplate
import net.shadowmaster435.onyx.lang.util.numbersRegex
import net.shadowmaster435.onyx.lang.util.typeTemplate
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate


class OnyxTuple(
    val type: OnyxClassDescriptor,
    val name: String,
    val optional: Boolean,
    val nullability: OnyxNullability,
    val vararg: Boolean = false,
    val initExpression: OnyxExpression? = null
) {
    val nullable get() = nullability.nullable

    constructor(
        type: OnyxClassDescriptor,
        name: String,
        optional: Boolean,
        nullable: Boolean,
        vararg: Boolean = false,
        initExpression: OnyxExpression? = null
    ) : this (
        type, name, optional,
        if (nullable) OnyxNullability.NULLABLE else OnyxNullability.NON_NULLABLE,
        vararg, initExpression
    )
    fun wrap() = OnyxTuples(this)

    override fun toString() = "$name: $type"

    companion object {
        val template = ParsinatorTemplate {
            chain(true) {
                use(nameTemplate(false))
                whitespace(true)
                token(false, ":")
                whitespace(true)
                use(typeTemplate)
                chain(true) {
                    whitespace(true)
                    token(false, "=")
                    whitespace(true)
                    use(expressionTemplate)
                }
            }
        }
    }


}