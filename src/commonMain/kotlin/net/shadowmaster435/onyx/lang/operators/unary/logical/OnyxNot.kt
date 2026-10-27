package net.shadowmaster435.onyx.lang.operators.unary.logical

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxBoolean
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.operators.OnyxClassProvider
import net.shadowmaster435.onyx.lang.operators.OnyxUnaryOp


class OnyxNot(
    a: OnyxClassProvider,
) : OnyxUnaryOp(
    a
) {
    override val op = OnyxOperator.EXCLAMATION_POINT
    override fun evaluate() = OnyxBoolean(!(a() as? OnyxBoolean ?: throw nab).held)
}