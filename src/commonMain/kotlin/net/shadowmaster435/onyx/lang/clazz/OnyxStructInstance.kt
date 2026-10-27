package net.shadowmaster435.onyx.lang.clazz

import net.shadowmaster435.onyx.lang.impl.clazz.OnyxConstableImpl

class OnyxStructInstance(
    desc: OnyxClassDescriptor,
    private val entries: List<OnyxConstableImpl>
) : OnyxClassInstance(desc) {
    operator fun get(index: Int) = entries[index]
}