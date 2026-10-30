package net.shadowmaster435.onyx.lang.builtin.clazz.member

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.enums.code.OnyxNullability
import net.shadowmaster435.onyx.lang.enums.generics.OnyxGenericCovariance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.field.OnyxFieldDescriptor
import net.shadowmaster435.onyx.lang.function.AbstractOnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.function.OnyxTuple
import net.shadowmaster435.onyx.lang.function.OnyxTuples
import net.shadowmaster435.onyx.lang.function.OnyxVirtualFunctionDescriptor
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType
import net.shadowmaster435.onyx.lang.pkg.onyxFunctions
import net.shadowmaster435.onyx.lang.util.interfaceField
import net.shadowmaster435.onyx.util.OnyxExpression

object OnyxFunction {
    val baseInterface = anyDesc.extend(
        "Function", onyxFunctions, OnyxClassType.INTERFACE,
        fields = linkedMapOf(interfaceField("parameterCount", OnyxInt.desc))
    )
    private val functions = genDescriptors()

    operator fun get(i: Int) = functions[i]

    private fun genGenerics(amount: Int) = buildList {
        repeat(amount) {
            val ch = ('A' + it).toString()
            add(
                OnyxGenericType(
                    ch,
                    anyDesc,
                    OnyxGenericCovariance.NONE,
                    OnyxNullability.INPUT
                )
            )
        }
    }

    private fun genDescriptors() = Array(GENERIC_AMOUNT) {
        val generics = OnyxGenericTuples(buildList {
            add(OnyxGenericType(
                "T",
                anyDesc,
                OnyxGenericCovariance.NONE,
                OnyxNullability.INPUT
            ))
            addAll(genGenerics(it))
        })
        val invoke = genInvoke(it, generics)
        baseInterface.extend(
            "Function$it", onyxFunctions, OnyxClassType.OPEN,
            fields = linkedMapOf(
                "parameterCount" to OnyxFieldDescriptor(
                    "parameterCount", OnyxInt.desc,
                    OnyxInt.desc, listOf(OnyxModifierType.PUBLIC, OnyxModifierType.META, OnyxModifierType.OVERRIDE),
                    initExpression = OnyxExpression.wrap(OnyxInt(it))
                ),
            ),
            genericTuples = generics,
            functions = LinkedHashMap<String, AbstractOnyxFunctionDescriptor>().apply {
                put("invoke", invoke)
            }
        )
    }

    private fun genInvoke(amount: Int, generics: OnyxGenericTuples): OnyxVirtualFunctionDescriptor {
        val tuples = OnyxTuples(buildList {
            if (amount == 0) return@buildList
            repeat(amount) {
                if (it > 0) {
                    val ch = ('a' + it).toString()
                    add(
                        OnyxTuple(
                            generics.tuples[it + 1],
                            ch,
                            OnyxNullability.INPUT
                        )
                    )
                }
            }
        })
        return OnyxVirtualFunctionDescriptor(
            "invoke",
            tuples,
            listOf(OnyxModifierType.PUBLIC),
            returnType = generics.tuples.first()
        )
    }
    const val GENERIC_AMOUNT = 17
}