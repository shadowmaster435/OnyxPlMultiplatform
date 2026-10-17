package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.array

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxByte
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxNumber
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxShort
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.pkg.onyxPrimitives
import net.shadowmaster435.onyx.lang.util.enumMapOf
import net.shadowmaster435.onyx.lang.util.externalFinalField

class OnyxIntArray(override val held: IntArray) : OnyxExternalClassInstance<IntArray>(desc) {


    companion object {
        val desc = anyDesc.extend(
            "IntArray", onyxPrimitives, OnyxClassType.NORMAL,
            null, linkedMapOf(
                externalFinalField("size", OnyxNumber.descOf(OnyxInt::class)) {
                    OnyxInt((it as OnyxIntArray).held.size)
                }
            ), operatorOverloads = enumMapOf(
                OnyxOperator.GET.externalOverload(OnyxInt.desc, OnyxInt.desc) {
                    a, b -> OnyxInt((a as OnyxIntArray).held[(b[0] as OnyxInt).held])
                },
                OnyxOperator.SET.externalOverload(OnyxUnit.desc, OnyxInt.desc,OnyxInt.desc) {
                    a, b ->
                    (a as OnyxIntArray).held[(b[0] as OnyxInt).held] = ((b[1] as OnyxInt)).held
                    OnyxUnit
                }
            )
        )
    }
}