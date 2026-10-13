package net.shadowmaster435.onyx.lang.operators.binary.assignment

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxNumber
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.field.OnyxFieldInstance
import net.shadowmaster435.onyx.lang.operators.OnyxAssignment
import net.shadowmaster435.onyx.lang.operators.OnyxClassProvider

class OnyxXorAssign(
    field: OnyxFieldInstance,
    v: OnyxClassProvider
) : OnyxAssignment(field, v) {
    override val op = OnyxOperator.XOR_ASSIGN
    override fun evaluate(): OnyxClassInstance {
        field.set(
            OnyxNumber.op(10, field.get() as? OnyxNumber<*> ?: throw nan, v() as? OnyxNumber<*> ?: throw nan)
        )
        return OnyxUnit
    }
}