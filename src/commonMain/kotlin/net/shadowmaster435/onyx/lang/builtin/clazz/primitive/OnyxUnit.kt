package net.shadowmaster435.onyx.lang.builtin.clazz.primitive

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.jvm.JVMPrimitiveType
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.pkg.onyxPrimitives


object OnyxUnit : OnyxExternalClassInstance<Unit>(OnyxClassDescriptor(
    "Unit", onyxPrimitives, linkedMapOf(), linkedMapOf(
        "hashCode" to OnyxClassInstance.defaultHashcodeFunction,
        "equals" to OnyxClassInstance.defaultEqualsFunction
    ), linkedMapOf(), OnyxClassType.OBJECT, null
).apply { jvmNameOverride = JVMPrimitiveType.VOID.descriptor }) {
    override val held = Unit

    override fun onyxEquals(other: OnyxClassInstance) = OnyxBoolean(other === this)
    override fun onyxHashcode() = OnyxInt(Unit.hashCode())

}