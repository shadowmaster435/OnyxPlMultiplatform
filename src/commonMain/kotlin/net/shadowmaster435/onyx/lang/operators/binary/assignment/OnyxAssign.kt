package net.shadowmaster435.onyx.lang.operators.binary.assignment

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.field.OnyxFieldInstance
import net.shadowmaster435.onyx.lang.operators.OnyxAssignment
import net.shadowmaster435.onyx.lang.operators.OnyxClassProvider

class OnyxAssign(
    field: OnyxFieldInstance,
    v: OnyxClassProvider
) : OnyxAssignment(field, v) {
    override val op = OnyxOperator.ASSIGN
    override fun evaluate(): OnyxClassInstance {
        field.set(v())
        return OnyxUnit
    }
}