package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import net.shadowmaster435.onyx.exception.IllegalOperationException
import net.shadowmaster435.onyx.exception.UnknownOperationException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxBoolean
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxObject
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.function.OnyxTuples
import net.shadowmaster435.onyx.lang.function.OnyxVirtualFunctionDescriptor
import net.shadowmaster435.onyx.lang.pkg.onyxPrimitives
import net.shadowmaster435.onyx.lang.util.externalFunc
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
abstract class OnyxNumber<T: Number>(desc: OnyxClassDescriptor = Companion.desc): OnyxExternalClassInstance<T>(desc) {
    abstract val byteCount: Int
    open val floatingPoint = false
    abstract fun toByte(): Byte
    abstract fun toShort(): Short
    abstract fun toInt(): Int
    abstract fun toLong(): Long
    abstract fun toFloat(): Float
    abstract fun toDouble(): Double//
    override fun onyxHashcode() = OnyxInt(held.hashCode())
    companion object {
        private val extendedModifiers = listOf(OnyxModifierType.PUBLIC, OnyxModifierType.OVERRIDE)
        private val descriptors = hashMapOf<KClass<*>, OnyxClassDescriptor>()

        fun <T: OnyxNumber<*>> descOf(klass: KClass<T>) = descriptors[klass]!!


        val desc = anyDesc.extend(
            "Number", onyxPrimitives, OnyxClassType.OPEN, null,
            linkedMapOf(), linkedMapOf(
                "toByte" to OnyxVirtualFunctionDescriptor("toByte", OnyxTuples.empty, listOf()),
                "toShort" to OnyxVirtualFunctionDescriptor("toShort", OnyxTuples.empty, listOf()),
                "toInt" to OnyxVirtualFunctionDescriptor("toInt", OnyxTuples.empty, listOf()),
                "toLong" to OnyxVirtualFunctionDescriptor("toLong", OnyxTuples.empty, listOf()),
                "toFloat" to OnyxVirtualFunctionDescriptor("toFloat", OnyxTuples.empty, listOf()),
                "toDouble" to OnyxVirtualFunctionDescriptor("toDouble", OnyxTuples.empty, listOf()),
                "hashCode" to OnyxVirtualFunctionDescriptor("hashCode", OnyxTuples.empty, listOf()),
                "isFloatingPoint" to OnyxVirtualFunctionDescriptor("isFloatingPoint", OnyxTuples.empty, listOf()),
                "byteCount" to OnyxVirtualFunctionDescriptor("byteCount", OnyxTuples.empty, listOf()),
            )
        )

        @Suppress("UNCHECKED_CAST")
        internal inline fun <reified T: OnyxNumber<*>> makeOverrideDesc(
            name: String,
            supertypes: List<OnyxClassDescriptor> = listOf(),
            companion: OnyxObject? = null
        ): OnyxClassDescriptor {
            val desc = actuallyMakeOverrideDesc<T>(name, supertypes, companion)
            descriptors[T::class] = desc
            return desc
        }

        fun <T: OnyxNumber<*>> actuallyMakeOverrideDesc(
            name: String,
            supertypes: List<OnyxClassDescriptor> = listOf(),
            companion: OnyxObject? = null
        ) = OnyxClassDescriptor(
            name, onyxPrimitives, listOf(OnyxModifierType.PUBLIC), linkedMapOf(), linkedMapOf(
                externalFunc("toByte", OnyxTuples.empty, extendedModifiers) { thisInst, _ ->
                    OnyxByte((thisInst as T).toByte())
                },
                externalFunc("toShort", OnyxTuples.empty, extendedModifiers) { thisInst, _ ->
                    OnyxShort((thisInst as T).toShort())
                },
                externalFunc("toInt", OnyxTuples.empty, extendedModifiers) { thisInst, _ ->
                    OnyxInt((thisInst as T).toInt())
                },
                externalFunc("toLong", OnyxTuples.empty, extendedModifiers) { thisInst, _ ->
                    OnyxLong((thisInst as T).toLong())
                },
                externalFunc("toFloat", OnyxTuples.empty, extendedModifiers) { thisInst, _ ->
                    OnyxFloat((thisInst as T).toFloat())
                },
                externalFunc("toDouble", OnyxTuples.empty, extendedModifiers) { thisInst, _ ->
                    OnyxDouble((thisInst as T).toDouble())
                },
                externalFunc("isFloatingPoint", OnyxTuples.empty, extendedModifiers) { thisInst, _ ->
                    OnyxBoolean((thisInst as T).floatingPoint)
                },
                externalFunc("byteCount", OnyxTuples.empty, extendedModifiers) { thisInst, _ ->
                    OnyxInt((thisInst as T).byteCount)
                },
            ), linkedMapOf(), OnyxClassType.NORMAL, listOf(desc) + supertypes, companion
        )

        private val unknownOp get() = UnknownOperationException("Unknown operator")
        private val illegalBitwiseOp
            get() = IllegalOperationException("Cannot perform bitwise operations on ints and longs")

        /**
         * Operations are as follows
         *
         * 0 add
         *
         * 1 subtract
         *
         * 2 multiply
         *
         * 3 divide
         *
         * 4 remainder
         *
         * 5 shl
         *
         * 6 shr
         *
         * 7 ushr
         *
         * 8 or
         *
         * 9 and
         *
         * 10 xor
         *
         * 11 invert
         *
         * 12 less than
         *
         * 13 less than or equal
         *
         * 14 greater than
         *
         * 15 greater than or equal
         *
         */
        fun op(op: Int, a: OnyxNumber<*>, b: OnyxNumber<*>): OnyxClassInstance {
            val a = a.held
            val b = b.held

            fun double(): OnyxClassInstance {
                val a = a.toDouble()
                val b = b.toDouble()
                return OnyxDouble(
                    when(op) {
                        0 -> a + b
                        1 -> a - b
                        2 -> a * b
                        3 -> a / b
                        4 -> a % b
                        5, 6, 7, 8, 9, 10, 11 -> throw illegalBitwiseOp
                        12 -> return OnyxBoolean(a < b)
                        13 -> return OnyxBoolean(a <= b)
                        14 -> return OnyxBoolean(a > b)
                        15 -> return OnyxBoolean(a >= b)
                        else -> throw unknownOp
                    }
                )
            }
            fun float(): OnyxClassInstance {
                val a = a.toFloat()
                val b = b.toFloat()
                return OnyxFloat(
                    when(op) {
                        0 -> a + b
                        1 -> a - b
                        2 -> a * b
                        3 -> a / b
                        4 -> a % b
                        5, 6, 7, 8, 9, 10, 11 -> throw illegalBitwiseOp
                        12 -> return OnyxBoolean(a < b)
                        13 -> return OnyxBoolean(a <= b)
                        14 -> return OnyxBoolean(a > b)
                        15 -> return OnyxBoolean(a >= b)
                        else -> throw unknownOp
                    }
                )
            }
            fun int(): OnyxClassInstance {
                val a = a.toInt()
                val b = b.toInt()
                return OnyxInt(
                    when(op) {
                        0 -> a + b
                        1 -> a - b
                        2 -> a * b
                        3 -> a / b
                        4 -> a % b
                        5 -> a shl b
                        6 -> a shr b
                        7 -> a ushr b
                        8 -> a or b
                        9 -> a and b
                        10 -> a xor b
                        11 -> a.inv()
                        12 -> return OnyxBoolean(a < b)
                        13 -> return OnyxBoolean(a <= b)
                        14 -> return OnyxBoolean(a > b)
                        15 -> return OnyxBoolean(a >= b)
                        else -> throw unknownOp
                    }
                )
            }
            fun long(): OnyxClassInstance {
                val a = a.toLong()
                val b = b.toLong()
                return OnyxLong(
                    when(op) {
                        0 -> a + b
                        1 -> a - b
                        2 -> a * b
                        3 -> a / b
                        4 -> a % b
                        5 -> a shl b.toInt()
                        6 -> a shr b.toInt()
                        7 -> a ushr b.toInt()
                        8 -> a or b
                        9 -> a and b
                        10 -> a xor b
                        11 -> a.inv()
                        12 -> return OnyxBoolean(a < b)
                        13 -> return OnyxBoolean(a <= b)
                        14 -> return OnyxBoolean(a > b)
                        15 -> return OnyxBoolean(a >= b)
                        else -> throw unknownOp
                    }
                )
            }
            fun short(): OnyxClassInstance {
                val a = a.toShort()
                val b = b.toShort()
                return OnyxInt(
                    when(op) {
                        0 -> a + b
                        1 -> a - b
                        2 -> a * b
                        3 -> a / b
                        4 -> a % b
                        5, 6, 7, 8, 9, 10, 11 -> throw illegalBitwiseOp
                        12 -> return OnyxBoolean(a < b)
                        13 -> return OnyxBoolean(a <= b)
                        14 -> return OnyxBoolean(a > b)
                        15 -> return OnyxBoolean(a >= b)
                        else -> throw unknownOp
                    }
                )
            }
            fun byte(): OnyxClassInstance {
                val a = a.toByte()
                val b = b.toByte()
                return OnyxInt(
                    when(op) {
                        0 -> a + b
                        1 -> a - b
                        2 -> a * b
                        3 -> a / b
                        4 -> a % b
                        5, 6, 7, 8, 9, 10, 11 -> throw illegalBitwiseOp
                        12 -> return OnyxBoolean(a < b)
                        13 -> return OnyxBoolean(a <= b)
                        14 -> return OnyxBoolean(a > b)
                        15 -> return OnyxBoolean(a >= b)
                        else -> throw unknownOp
                    }
                )
            }
            return when(a) {
                is Double -> double()
                is Float -> when(b) {
                    is Double -> double()
                    else -> float()
                }
                is Long -> when(b) {
                    is Double -> double()
                    is Float -> float()
                    else -> long()
                }
                is Int -> when(b) {
                    is Double -> double()
                    is Float -> float()
                    is Long -> long()
                    else -> int()
                }
                is Short -> when(b) {
                    is Double -> double()
                    is Float -> float()
                    is Long -> long()
                    is Int -> int()
                    else -> short()
                }
                is Byte -> when(b) {
                    is Double -> double()
                    is Float -> float()
                    is Long -> long()
                    is Int -> int()
                    is Short -> short()
                    else -> byte()
                }
                else -> throw RuntimeException("Unknown number class")
            }
        }
    }
}