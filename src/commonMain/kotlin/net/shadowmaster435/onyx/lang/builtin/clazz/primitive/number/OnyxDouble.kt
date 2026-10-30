package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import net.shadowmaster435.onyx.compiler.OnyxCompiledType
import net.shadowmaster435.onyx.exception.ParseException
import net.shadowmaster435.onyx.ext.binaryToDouble
import net.shadowmaster435.onyx.ext.hexToDouble
import net.shadowmaster435.onyx.lang.impl.OnyxCompilableConstImpl
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

expect val OnyxDouble.compileFunc: (Any?) -> Any?
class OnyxDouble(override val held: Double) : OnyxNumber<Double>(desc), OnyxCompilableConstImpl {
    override val byteCount = 8
    override fun toByte() = held.toInt().toByte()
    override fun toShort() = held.toInt().toShort()
    override fun toInt() = held.toInt()
    override fun toLong() = held.toLong()
    override fun toFloat() = held.toFloat()
    override fun toDouble() = held
    override fun compileConst(v: Any?) = OnyxCompiledType(compileFunc(v))
    companion object {
        val desc = makeOverrideDesc<OnyxInt>("Int")
        val template = ParsinatorTemplate {
            chain(true) {
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
                    token(false, "d")
                    token(false, "D")
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