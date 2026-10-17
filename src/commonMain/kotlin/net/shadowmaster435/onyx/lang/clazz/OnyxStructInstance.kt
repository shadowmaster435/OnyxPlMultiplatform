package net.shadowmaster435.onyx.lang.clazz

import net.shadowmaster435.onyx.lang.impl.clazz.OnyxConstableImpl

class OnyxStructInstance(
    desc: OnyxClassDescriptor,
    private val entries: List<OnyxConstableImpl>
) : OnyxClassInstance(desc), OnyxConstableImpl {

    override val byteSize = run {
        var i = 0
        i
    }

    override fun getBytes(): ByteArray {
        TODO("Not yet implemented")
    }


    operator fun get(index: Int) = entries[index]
}