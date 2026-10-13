package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor


class OnyxTuple(val type: OnyxClassDescriptor, val name: String, val optional: Boolean, val nullable: Boolean) {
    override fun toString() = "$name: $type"
}