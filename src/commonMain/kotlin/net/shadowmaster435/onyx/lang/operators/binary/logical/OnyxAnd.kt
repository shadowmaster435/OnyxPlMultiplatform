package net.shadowmaster435.onyx.lang.operators.binary.logical

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxBoolean
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.operators.OnyxBinaryOp
import net.shadowmaster435.onyx.lang.operators.OnyxClassProvider


class OnyxAnd(
    a: OnyxClassProvider,
    b: OnyxClassProvider
) : OnyxBinaryOp(
    a, b
) {
    override val op = OnyxOperator.PERCENT
    override fun evaluate() = OnyxBoolean((a() as? OnyxBoolean ?: throw nab).held && (b() as? OnyxBoolean ?: throw nab).held)
}