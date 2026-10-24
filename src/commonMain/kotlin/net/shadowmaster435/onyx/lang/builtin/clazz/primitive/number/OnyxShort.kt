package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import net.shadowmaster435.onyx.compiler.OnyxCompiledType
import net.shadowmaster435.onyx.exception.ParseException
import net.shadowmaster435.onyx.ext.binaryToShort
import net.shadowmaster435.onyx.lang.impl.OnyxCompilableConstImpl
import net.shadowmaster435.onyx.lang.util.numbersRegex
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

expect val OnyxShort.compileFunc: () -> Any?
class OnyxShort(override val held: Short) : OnyxNumber<Short>(makeOverrideDesc<OnyxShort>("Short")), OnyxCompilableConstImpl {
    override val byteCount = 2
    override fun toByte() = held.toByte()
    override fun toShort() = held
    override fun toInt() = held.toInt()
    override fun toLong() = held.toLong()
    override fun toFloat() = held.toFloat()
    override fun toDouble() = held.toDouble()
    override fun compileConst() = OnyxCompiledType(compileFunc())


    companion object {
        val desc = descOf(OnyxShort::class)
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
                branch(false) {
                    token(false, "s")
                    token(false, "S")
                }
            } makes {
                it as List<*>
                val negative = it[0] as Boolean
                val stringType = it[1] as Int
                val numberString = it[2] as String
                if (negative && stringType != 0) throw IllegalArgumentException("Negative signs are not supported for hex and binary strings")
                val result = when(stringType) {
                    2 -> numberString.hexToShort()
                    1 -> numberString.binaryToShort()
                    0 -> ((if (negative) "-" else "") + numberString).toShort()
                    else -> throw ParseException("Failed to parse number")
                }

                OnyxShort(result)
            }
        }
    }
}