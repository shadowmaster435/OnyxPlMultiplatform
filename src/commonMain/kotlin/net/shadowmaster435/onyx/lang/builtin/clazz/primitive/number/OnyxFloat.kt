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
            chain(false) {
                branch(false) {
                    token(false, "-", false) makes {true}
                    exit(0) makes { false }
                }
                branch(false) {
                    token(false, "0x", false) makes {2}
                    token(false, "0b", false) makes {1}
                    exit(0) makes {0}
                }
                name(false, Regex("[0-9.eE-]"), Regex("[eE-]"))
                branch(true) {
                    token(false, "f", false)
                    token(false, "F",false)
                }
            } makes {
                it as List<*>
                val negative = it[0] as Boolean
                val stringType = it[1] as Int
                val numberString = it[2] as String
                val containsExponent = numberString.contains(Regex("[eE]"))
                if (negative && stringType != 0) throw negativeHex
                if (stringType != 0 && containsExponent) throw noExponents
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