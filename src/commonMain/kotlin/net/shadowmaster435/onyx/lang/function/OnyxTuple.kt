package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.enums.code.OnyxNullability


class OnyxTuple(
    val type: OnyxClassDescriptor,
    val name: String,
    val optional: Boolean,
    val nullability: OnyxNullability,
    val vararg: Boolean = false
) {
    constructor(type: OnyxClassDescriptor, name: String, optional: Boolean, nullable: Boolean, vararg: Boolean = false) : this (
        type, name, optional, if (nullable) OnyxNullability.NULLABLE else OnyxNullability.NON_NULLABLE
    )
    fun wrap() = OnyxTuples(this)

    override fun toString() = "$name: $type"
}