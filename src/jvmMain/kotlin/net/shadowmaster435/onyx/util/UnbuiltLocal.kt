package net.shadowmaster435.onyx.util

import net.shadowmaster435.onyx.asm.OnyxClassWriter
import net.shadowmaster435.onyx.asm.OnyxLocalSorter
import net.shadowmaster435.onyx.lang.field.OnyxFieldDescriptor

@JvmInline
internal value class UnbuiltLocal (
    private val v: Triple<Int, OnyxFieldDescriptor, OnyxLocalSorter>
) {
    constructor(
        index: Int,
        field: OnyxFieldDescriptor,
        sorter: OnyxLocalSorter
    ) : this(Triple(index, field, sorter))

    val varIndex get() = v.first
    val field get() = v.second
    val sorter get() = v.third
}