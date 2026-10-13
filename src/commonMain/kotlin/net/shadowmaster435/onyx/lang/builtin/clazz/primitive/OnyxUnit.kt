package net.shadowmaster435.onyx.lang.builtin.clazz.primitive

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.pkg.onyxPrimitives

object OnyxUnit : OnyxExternalClassInstance<Unit>(Desc.desc) {
    override val held = Unit
    override fun onyxEquals(other: OnyxClassInstance) = OnyxBoolean(other === this)
    override fun onyxHashcode() = OnyxInt(Unit.hashCode())
    object Desc {
        val desc = OnyxClassDescriptor(
            "Unit", onyxPrimitives, linkedMapOf(), linkedMapOf(
                OnyxClassDescriptor.hashcodeDesc,
                OnyxClassDescriptor.equalDesc
            ), linkedMapOf(), OnyxClassType.NORMAL, OnyxAny.desc
        )
    }
}