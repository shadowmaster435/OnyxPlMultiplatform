package net.shadowmaster435.onyx.lang.builtin.clazz.iterable

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxNumber
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.code.OnyxNullability
import net.shadowmaster435.onyx.lang.enums.generics.OnyxGenericVariance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType
import net.shadowmaster435.onyx.lang.pkg.onyxCollections
import net.shadowmaster435.onyx.lang.util.interfaceField

class OnyxMutableSet(override val held: MutableSet<OnyxClassInstance>, ):
    OnyxExternalClassInstance<MutableSet<OnyxClassInstance>>(desc), MutableSet<OnyxClassInstance> {
    override val size = held.size
    override fun isEmpty() = held.isEmpty()
    override fun contains(element: OnyxClassInstance) = held.contains(element)
    override fun iterator() = held.iterator()
    override fun add(element: OnyxClassInstance) = held.add(element)

    override fun remove(element: OnyxClassInstance) = held.remove(element)

    override fun addAll(elements: Collection<OnyxClassInstance>) = held.addAll(elements)

    override fun removeAll(elements: Collection<OnyxClassInstance>) = held.removeAll(elements.toSet())

    override fun retainAll(elements: Collection<OnyxClassInstance>) = held.retainAll(elements.toSet())

    override fun clear() = held.clear()

    override fun containsAll(elements: Collection<OnyxClassInstance>) = held.containsAll(elements)

    companion object {
        val desc = anyDesc.extend(
            "Set",
            onyxCollections,
            OnyxClassType.INTERFACE,
            OnyxIterable.desc, null,
            linkedMapOf(
                interfaceField("size", OnyxNumber.descOf(OnyxInt::class)),

                ),
            genericTuples = OnyxGenericTuples(OnyxGenericType(
                "T", anyDesc, OnyxGenericVariance.OUT, OnyxNullability.INPUT
            ))
        )
    }
}