package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import net.shadowmaster435.onyx.compiler.OnyxCompiledType
import net.shadowmaster435.onyx.exception.ParseException
import net.shadowmaster435.onyx.ext.binaryToByte
import net.shadowmaster435.onyx.lang.impl.OnyxCompilableConstImpl
import net.shadowmaster435.onyx.lang.util.numbersRegex
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate


expect val OnyxByte.compileFunc: (Any?) -> Any?
class OnyxByte(override val held: Byte) : OnyxNumber<Byte>(desc), OnyxCompilableConstImpl {
    override val byteCount = 1
    override fun toByte() = held
    override fun toShort() = held.toShort()
    override fun toInt() = held.toInt()
    override fun toLong() = held.toLong()
    override fun toFloat() = held.toFloat()
    override fun toDouble() = held.toDouble()
    override fun compileConst(v: Any?) = OnyxCompiledType(compileFunc(v))


    companion object {
        val desc = makeOverrideDesc<OnyxByte>("Byte")
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
                branch(true) {
                    token(false, "b")
                    token(false, "B")
                }
            } makes {
                it as List<*>
                val negative = it[0] as Boolean
                val stringType = it[1] as Int
                val numberString = it[2] as String
                if (negative && stringType != 0) throw negativeHex
                val result = when(stringType) {
                    2 -> numberString.hexToByte()
                    1 -> numberString.binaryToByte()
                    0 -> ((if (negative) "-" else "") + numberString).toByte()
                    else -> throw ParseException("Failed to parse number")
                }
                OnyxByte(result)
            }
        }
    }
}