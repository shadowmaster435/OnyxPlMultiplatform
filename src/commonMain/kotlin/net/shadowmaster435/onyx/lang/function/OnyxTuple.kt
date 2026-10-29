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


open class OnyxTuple(
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
    open fun wrap() = OnyxTuples(this)

    override fun toString() = "$name: $type"
}