package net.shadowmaster435.onyx.lang.builtin.clazz.primitive

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.jvm.JVMPrimitiveType
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.pkg.onyxPrimitives

object OnyxUnit : OnyxExternalClassInstance<Unit>(Desc.desc) {
    override val held = Unit
    override fun onyxEquals(other: OnyxClassInstance) = OnyxBoolean(other === this)
    override fun onyxHashcode() = OnyxInt(Unit.hashCode())
    object Desc {
        val desc = anyDesc.extend(
            "Unit", onyxPrimitives, OnyxClassType.NORMAL, fields =  linkedMapOf(),  functions =  linkedMapOf(
                OnyxClassDescriptor.hashcodeDesc,
                OnyxClassDescriptor.equalDesc
            ), classes = linkedMapOf()
        ).apply { jvmNameOverride = JVMPrimitiveType.VOID.descriptor }
    }
}