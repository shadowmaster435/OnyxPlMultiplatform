package net.shadowmaster435.onyx.lang.operators.unary.bitwise

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxNumber
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.operators.OnyxClassProvider
import net.shadowmaster435.onyx.lang.operators.OnyxUnaryOp


class OnyxBitInvert(
    a: OnyxClassProvider,
) : OnyxUnaryOp(
    a
) {
    override val op = OnyxOperator.PERCENT
    override fun evaluate() = OnyxNumber.op(11, a() as? OnyxNumber<*> ?: throw nan, nil)
    companion object {
        private val nil = OnyxInt(0)
    }
}