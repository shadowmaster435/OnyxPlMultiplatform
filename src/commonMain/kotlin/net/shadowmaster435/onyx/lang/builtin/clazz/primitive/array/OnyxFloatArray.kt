package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.array

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxByte
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxFloat
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

class OnyxFloatArray(override val held: FloatArray) : OnyxExternalClassInstance<FloatArray>(desc) {


    companion object {
        val desc = anyDesc.extend(
            "FloatArray", onyxPrimitives, OnyxClassType.NORMAL,
            null, linkedMapOf(
                externalFinalField("size", OnyxNumber.descOf(OnyxInt::class)) {
                    OnyxInt((it as OnyxFloatArray).held.size)
                }
            ), operatorOverloads = enumMapOf(
                OnyxOperator.GET.externalOverload(OnyxFloat.desc, OnyxInt.desc) {
                    a, b -> OnyxFloat((a as OnyxFloatArray).held[(b[0] as OnyxInt).held])
                },
                OnyxOperator.SET.externalOverload(OnyxUnit.desc, OnyxInt.desc, OnyxFloat.desc) {
                    a, b ->
                    (a as OnyxFloatArray).held[(b[0] as OnyxInt).held] = ((b[1] as OnyxFloat)).held
                    OnyxUnit
                }
            )
        )
    }
}