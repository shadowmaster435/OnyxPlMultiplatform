package net.shadowmaster435.onyx.lang.clazz

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxBoolean
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxNumber

abstract class OnyxExternalClassInstance<T>(
    desc: OnyxClassDescriptor,
) : OnyxClassInstance(desc) {
    abstract val held: T
    override fun onyxEquals(other: OnyxClassInstance) = OnyxBoolean(other is OnyxNumber<*> && held == other.held)
    override fun toString() = held.toString()
}