package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import net.shadowmaster435.onyx.exception.ParseException
import net.shadowmaster435.onyx.ext.binaryToInt
import net.shadowmaster435.onyx.ext.binaryToLong
import net.shadowmaster435.onyx.lang.util.numbersRegex
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxInt(override val held: Int) : OnyxNumber<Int>(makeOverrideDesc<OnyxInt>("Int")) {
    override val byteCount = 4
    override fun toByte() = held.toByte()
    override fun toShort() = held.toShort()
    override fun toInt() = held
    override fun toLong() = held.toLong()
    override fun toFloat() = held.toFloat()
    override fun toDouble() = held.toDouble()
    override fun onyxHashcode() = this
    companion object {
        val desc = descOf(OnyxInt::class)
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
                name(false, numbersRegex)
                branch(true) {
                    token(false, "i")
                    token(false, "I")
                }
            } makes {
                it as List<*>
                val negative = it[0] as Boolean
                val stringType = it[1] as Int
                val numberString = it[2] as String
                if (negative && stringType != 0) throw IllegalArgumentException("Negative signs are not supported for hex and binary strings")
                val result = when(stringType) {
                    2 -> numberString.hexToInt()
                    1 -> numberString.binaryToInt()
                    0 -> ((if (negative) "-" else "") + numberString).toInt()
                    else -> throw ParseException("Failed to parse number")
                }
                OnyxInt(result)
            }
        }
    }
}