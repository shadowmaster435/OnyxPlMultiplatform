package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

class OnyxInt(override val held: Int) : OnyxNumber<Int>(makeOverrideDesc<OnyxInt>("Int")) {
    override val byteCount = 4
    override fun toByte() = held.toByte()
    override fun toShort() = held.toShort()
    override fun toInt() = held
    override fun toLong() = held.toLong()
    override fun toFloat() = held.toFloat()
    override fun toDouble() = held.toDouble()
    override fun onyxHashcode() = this
}