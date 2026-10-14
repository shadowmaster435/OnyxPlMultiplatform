package net.shadowmaster435.onyx.lang.builtin.clazz.iterable

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxNumber
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.generics.OnyxGenericVariance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType
import net.shadowmaster435.onyx.lang.pkg.onyxCollections
import net.shadowmaster435.onyx.lang.util.interfaceField

class OnyxMap(override val held: Map<OnyxClassInstance, OnyxClassInstance>):
    OnyxExternalClassInstance<Map<OnyxClassInstance, OnyxClassInstance>>(desc) {
    val size = held.size

    companion object {
        val desc = anyDesc.extend(
            "Map",
            onyxCollections,
            OnyxClassType.INTERFACE,
            OnyxIterable.desc, null,
            linkedMapOf(
                interfaceField("size", OnyxNumber.descOf(OnyxInt::class)),

                ),
            genericTuples = OnyxGenericTuples(OnyxGenericType(
                anyDesc, OnyxGenericVariance.OUT
            ))
        )
    }
}