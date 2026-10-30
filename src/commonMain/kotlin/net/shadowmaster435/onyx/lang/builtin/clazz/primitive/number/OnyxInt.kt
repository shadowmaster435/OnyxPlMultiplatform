package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import net.shadowmaster435.onyx.compiler.OnyxCompiledType
import net.shadowmaster435.onyx.exception.ParseException
import net.shadowmaster435.onyx.ext.binaryToInt
import net.shadowmaster435.onyx.lang.impl.OnyxCompilableConstImpl
import net.shadowmaster435.onyx.lang.util.numbersRegex
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

expect val OnyxInt.compileFunc: (Any?) -> Any?
class OnyxInt(override val held: Int) : OnyxNumber<Int>(desc), OnyxCompilableConstImpl {
    override val byteCount = 4
    override fun toByte() = held.toByte()
    override fun toShort() = held.toShort()
    override fun toInt() = held
    override fun toLong() = held.toLong()
    override fun toFloat() = held.toFloat()
    override fun toDouble() = held.toDouble()
    override fun onyxHashcode() = this
    override fun compileConst(v: Any?) = OnyxCompiledType(compileFunc(v))
    companion object {
        val desc = makeOverrideDesc<OnyxInt>("Int")
        val template = ParsinatorTemplate {
            chain(true) {
                branch(false) {
                    token(false, "-", false) makes {
                        println(indexRange)
                        true}
                    exit(0) makes { false }
                }
                branch(false) {
                    token(false, "0x", false) makes {2}
                    token(false, "0b", false) makes {1}
                    exit(0) makes {0}
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
                if (negative && stringType != 0) throw negativeHex
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