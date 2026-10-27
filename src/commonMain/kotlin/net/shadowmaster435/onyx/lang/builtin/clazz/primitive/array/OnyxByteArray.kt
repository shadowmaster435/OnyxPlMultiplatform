package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.array

import net.shadowmaster435.onyx.compiler.OnyxCompiledType
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxByte
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxNumber
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.impl.OnyxCompilableImpl
import net.shadowmaster435.onyx.lang.pkg.onyxPrimitives
import net.shadowmaster435.onyx.lang.util.enumMapOf
import net.shadowmaster435.onyx.lang.util.externalFinalField

expect val OnyxByteArray.compileFunc: (any: Any?) -> Any?
class OnyxByteArray(override val held: ByteArray) : OnyxExternalClassInstance<ByteArray>(desc), OnyxCompilableImpl {
    override fun compile(any: Any?)  {
        OnyxCompiledType(compileFunc(any))
    }
    companion object {
        val desc = anyDesc.extend(
            "ByteArray", onyxPrimitives, OnyxClassType.NORMAL,
            null, linkedMapOf(
                externalFinalField("size", OnyxNumber.descOf(OnyxInt::class)) {
                    OnyxInt((it as OnyxByteArray).held.size)
                }
            ), operatorOverloads = enumMapOf(
                OnyxOperator.GET.externalOverload(OnyxByte.desc, OnyxInt.desc) {
                    a, b -> OnyxByte((a as OnyxByteArray).held[(b[0] as OnyxInt).held])
                },
                OnyxOperator.SET.externalOverload(OnyxUnit.desc, OnyxInt.desc, OnyxByte.desc) {
                    a, b ->
                    (a as OnyxByteArray).held[(b[0] as OnyxInt).held] = ((b[1] as OnyxByte)).held
                    OnyxUnit
                }
            )
        )
    }
}