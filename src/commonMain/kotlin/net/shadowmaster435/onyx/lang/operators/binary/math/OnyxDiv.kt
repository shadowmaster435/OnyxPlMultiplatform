package net.shadowmaster435.onyx.lang.operators.binary.math

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxNumber
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.operators.OnyxBinaryOp
import net.shadowmaster435.onyx.lang.operators.OnyxClassProvider

class OnyxDiv(
    a: OnyxClassProvider,
    b: OnyxClassProvider,
) : OnyxBinaryOp(
    a, b
) {
    override val op = OnyxOperator.FWD_SLASH
    override fun evaluate() = OnyxNumber.op(3, a() as? OnyxNumber<*> ?: throw nan, b() as? OnyxNumber<*> ?: throw nan)
}