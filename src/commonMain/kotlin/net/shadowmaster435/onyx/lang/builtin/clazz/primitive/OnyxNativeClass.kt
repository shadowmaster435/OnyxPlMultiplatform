package net.shadowmaster435.onyx.lang.builtin.clazz.primitive

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance

class OnyxNativeClass(override val held: OnyxClassDescriptor) : OnyxExternalClassInstance<OnyxClassDescriptor>(desc) {
    override fun onyxEquals(other: OnyxClassInstance) = OnyxBoolean(other is OnyxNativeClass && other.held == held)
    companion object
}

expect val OnyxNativeClass.Companion.desc: OnyxClassDescriptor