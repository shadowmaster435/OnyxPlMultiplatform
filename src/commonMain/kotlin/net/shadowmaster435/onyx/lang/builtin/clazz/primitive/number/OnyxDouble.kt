package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import net.shadowmaster435.onyx.exception.ParseException
import net.shadowmaster435.onyx.ext.binaryToDouble
import net.shadowmaster435.onyx.ext.binaryToFloat
import net.shadowmaster435.onyx.ext.hexToDouble
import net.shadowmaster435.onyx.ext.hexToFloat
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxDouble(override val held: Double) : OnyxNumber<Double>(makeOverrideDesc<OnyxDouble>("Double")) {
    override val byteCount = 8
    override fun toByte() = held.toInt().toByte()
    override fun toShort() = held.toInt().toShort()
    override fun toInt() = held.toInt()
    override fun toLong() = held.toLong()
    override fun toFloat() = held.toFloat()
    override fun toDouble() = held
    companion object {
        val desc = descOf(OnyxDouble::class)
        val template = ParsinatorTemplate {
            chain(true) {
                branch(false) {
                    token(false, "-")
                    exit(0)
                } makes { it is String }

                branch(true) {
                    token(false, "0x")
                    token(false, "0b")
                    exit(0)
                } makes {
                    when (it) {
                        "0x" -> 2
                        "0b" -> 1
                        else -> 0
                    }
                }
                name(false, Regex("[0-9.eE-]"), Regex("[eE-]"))
                branch(true) {
                    token(false, "d")
                    token(false, "D")
                }
            } makes {
                it as List<*>
                val negative = it[0] as Boolean
                val stringType = it[1] as Int
                val numberString = it[2] as String
                val containsExponent = numberString.contains(Regex("[eE]"))
                if (negative && stringType != 0) throw IllegalArgumentException("Negative signs are not supported for hex and binary strings")
                if (stringType != 0 && containsExponent) throw IllegalArgumentException("Exponents cannot be used here")
                val result = when(stringType) {
                    2 -> numberString.hexToDouble()
                    1 -> numberString.binaryToDouble()
                    0 -> ((if (negative) "-" else "") + numberString).toDouble()
                    else -> throw ParseException("Failed to parse number")
                }
                OnyxDouble(result)
            }
        }
    }
}