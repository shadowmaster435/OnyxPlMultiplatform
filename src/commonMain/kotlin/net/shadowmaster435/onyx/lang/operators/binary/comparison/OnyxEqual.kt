package net.shadowmaster435.onyx.lang.operators.binary.comparison

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxBoolean
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.operators.OnyxBinaryOp
import net.shadowmaster435.onyx.lang.operators.OnyxClassProvider


class OnyxEqual(
    a: OnyxClassProvider,
    b: OnyxClassProvider
) : OnyxBinaryOp(
    a, b
) {
    override val op = OnyxOperator.EQUAL
    override fun evaluate() = a().onyxEquals(b())
}