package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import net.shadowmaster435.onyx.compiler.OnyxCompiledType
import net.shadowmaster435.onyx.exception.ParseException
import net.shadowmaster435.onyx.ext.binaryToFloat
import net.shadowmaster435.onyx.ext.hexToFloat
import net.shadowmaster435.onyx.lang.impl.OnyxCompilableConstImpl
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

expect val OnyxFloat.compileFunc: (Any?) -> Any?
class OnyxFloat(override val held: Float) : OnyxNumber<Float>(makeOverrideDesc<OnyxFloat>("Float")), OnyxCompilableConstImpl {
    override val byteCount = 4
    override fun toByte() = held.toInt().toByte()
    override fun toShort() = held.toInt().toShort()
    override fun toInt() = held.toInt()
    override fun toLong() = held.toLong()
    override fun toFloat() = held
    override fun toDouble() = held.toDouble()
    override fun compileConst(v: Any?) = OnyxCompiledType(compileFunc(v))
    companion object {
        val desc = descOf(OnyxFloat::class)
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
                    token(false, "f")
                    token(false, "F")
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
                    2 -> numberString.hexToFloat()
                    1 -> numberString.binaryToFloat()
                    0 -> ((if (negative) "-" else "") + numberString).toFloat()
                    else -> throw ParseException("Failed to parse number")
                }
                OnyxFloat(result)
            }
        }
    }
}