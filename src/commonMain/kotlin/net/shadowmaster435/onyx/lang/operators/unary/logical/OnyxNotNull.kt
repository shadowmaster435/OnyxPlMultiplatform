package net.shadowmaster435.onyx.lang.operators.unary.logical

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxBoolean
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxNull
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxNumber
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.operators.OnyxClassProvider
import net.shadowmaster435.onyx.lang.operators.OnyxUnaryOp


class OnyxNotNull(
    a: OnyxClassProvider,
) : OnyxUnaryOp(
    a
) {
    override val op = OnyxOperator.NOT_NULL
    override fun evaluate() = OnyxBoolean(a() !is OnyxNull)
}