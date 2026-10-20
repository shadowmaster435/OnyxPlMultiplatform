package net.shadowmaster435.onyx.lang.builtin.clazz.iterable

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.code.OnyxNullability
import net.shadowmaster435.onyx.lang.enums.generics.OnyxGenericVariance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType
import net.shadowmaster435.onyx.lang.pkg.onyxCollections

abstract class OnyxIterable : OnyxExternalClassInstance<Collection<OnyxClassInstance>>(desc), Iterable<OnyxClassInstance> {
    override fun iterator() = held.iterator()
    override fun onyxIterator() = iterator()

    companion object {
        val desc = anyDesc.extend(
            "Iterable",
            onyxCollections,
            OnyxClassType.INTERFACE,
            genericTuples = OnyxGenericTuples(OnyxGenericType(
                "T", anyDesc, OnyxGenericVariance.OUT, OnyxNullability.INPUT
            ))
        )
    }
}