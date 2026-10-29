package net.shadowmaster435.onyx.lang.builtin.clazz.primitive

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.function.OnyxTuple
import net.shadowmaster435.onyx.lang.function.OnyxTuples
import net.shadowmaster435.onyx.lang.pkg.onyxPrimitives
import net.shadowmaster435.onyx.lang.util.externalFunc

class OnyxNativeClass(override val held: OnyxClassDescriptor) : OnyxExternalClassInstance<OnyxClassDescriptor>(desc) {
    override fun onyxEquals(other: OnyxClassInstance) = OnyxBoolean(other is OnyxNativeClass && other.held == held)
    companion object
}

expect val OnyxNativeClass.Companion.desc: OnyxClassDescriptor