package net.shadowmaster435.onyx.lang.operators.binary.other

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.operators.OnyxBinaryOp
import net.shadowmaster435.onyx.lang.operators.OnyxClassProvider

class OnyxCast(
    a: OnyxClassProvider,
    b: OnyxClassProvider,
) : OnyxBinaryOp(
    a, b
) {
    override val op = OnyxOperator.AS

    override fun evaluate(): OnyxClassInstance {
        val a = a()
        if (a.castableTo(b().desc)) return a
        throw ClassCastException("Class ${a.desc} cannot be cast to ${b().desc}")
    }

}