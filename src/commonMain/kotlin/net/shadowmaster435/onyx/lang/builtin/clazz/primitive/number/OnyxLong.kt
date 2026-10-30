package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import net.shadowmaster435.onyx.compiler.OnyxCompiledType
import net.shadowmaster435.onyx.exception.ParseException
import net.shadowmaster435.onyx.ext.binaryToLong
import net.shadowmaster435.onyx.lang.impl.OnyxCompilableConstImpl
import net.shadowmaster435.onyx.lang.util.numbersRegex
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

expect val OnyxLong.compileFunc: (Any?) -> Any?
class OnyxLong(override val held: Long) : OnyxNumber<Long>(desc), OnyxCompilableConstImpl {
    override val byteCount = 8
    override fun toByte() = held.toByte()
    override fun toShort() = held.toShort()
    override fun toInt() = held.toInt()
    override fun toLong() = held
    override fun toFloat() = held.toFloat()
    override fun toDouble() = held.toDouble()
    override fun compileConst(v: Any?) = OnyxCompiledType(compileFunc(v))
    companion object {
        val desc = makeOverrideDesc<OnyxLong>("Long")
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
                name(false, numbersRegex)
                branch(false) {
                    token(false, "l")
                    token(false, "L")
                }
            } makes {
                it as List<*>
                val negative = it[0] as Boolean
                val stringType = it[1] as Int
                val numberString = it[2] as String
                if (negative && stringType != 0) throw negativeHex
                val result = when(stringType) {
                    2 -> numberString.hexToLong()
                    1 -> numberString.binaryToLong()
                    0 -> ((if (negative) "-" else "") + numberString).toLong()
                    else -> throw ParseException("Failed to parse number")
                }
                OnyxLong(result)
            }
        }
    }
}