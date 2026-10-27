package net.shadowmaster435.onyx.lang.builtin.clazz.iterable

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.code.OnyxNullability
import net.shadowmaster435.onyx.lang.enums.generics.OnyxGenericCovariance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType
import net.shadowmaster435.onyx.lang.pkg.onyxCollections

class OnyxCollection(override val held: Collection<OnyxClassInstance>):
    OnyxExternalClassInstance<Collection<OnyxClassInstance>>(desc), Collection<OnyxClassInstance> {
    override val size = held.size
    override fun isEmpty() = held.isEmpty()
    override fun contains(element: OnyxClassInstance) = held.contains(element)
    override fun iterator() = held.iterator()
    override fun containsAll(elements: Collection<OnyxClassInstance>) = held.containsAll(elements)

    companion object {
        val desc = anyDesc.extend(
            "Collection",
            onyxCollections,
            OnyxClassType.INTERFACE,
            OnyxIterable.desc,
            genericTuples = OnyxGenericTuples(OnyxGenericType(
                "T", anyDesc, OnyxGenericCovariance.OUT, OnyxNullability.INPUT
            ))
        )
    }
}