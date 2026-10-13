package net.shadowmaster435.onyx.lang.operators.binary.comparison

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxNumber
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.operators.OnyxBinaryOp
import net.shadowmaster435.onyx.lang.operators.OnyxClassProvider

class OnyxGreaterEqual(
    a: OnyxClassProvider,
    b: OnyxClassProvider,
) : OnyxBinaryOp(
    a, b
) {
    override val op = OnyxOperator.GREATER_EQ
    override fun evaluate() = OnyxNumber.op(15, a() as? OnyxNumber<*> ?: throw nan, b() as? OnyxNumber<*> ?: throw nan)
}