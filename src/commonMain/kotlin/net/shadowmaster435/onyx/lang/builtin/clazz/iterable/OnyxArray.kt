package net.shadowmaster435.onyx.lang.builtin.clazz.iterable

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.generics.OnyxGenericVariance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType
import net.shadowmaster435.onyx.lang.pkg.onyxCollections

class OnyxArray(override val held: Array<OnyxClassInstance>): OnyxExternalClassInstance<Array<OnyxClassInstance>>(desc) {
    val size = held.size
    fun isEmpty() = held.isEmpty()
    fun contains(element: OnyxClassInstance) = held.contains(element)
    fun iterator() = held.iterator()
    operator fun set(index: Int) {
    }

    companion object {
        val desc = anyDesc.extend(
            "Array",
            onyxCollections,
            OnyxClassType.NORMAL,
            OnyxIterable.desc,
            genericTuples = OnyxGenericTuples(OnyxGenericType(
                anyDesc, OnyxGenericVariance.OUT
            ))
        )
    }
}