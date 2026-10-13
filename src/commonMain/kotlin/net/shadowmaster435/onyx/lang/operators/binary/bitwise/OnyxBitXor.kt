package net.shadowmaster435.onyx.lang.operators.binary.bitwise

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxNumber
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.operators.OnyxBinaryOp
import net.shadowmaster435.onyx.lang.operators.OnyxClassProvider

class OnyxBitXor(
    a: OnyxClassProvider,
    b: OnyxClassProvider,
) : OnyxBinaryOp(
    a, b
) {
    override val op = OnyxOperator.AMPERSAND
    override fun evaluate() = OnyxNumber.op(10, a() as? OnyxNumber<*> ?: throw nan, b() as? OnyxNumber<*> ?: throw nan)
}