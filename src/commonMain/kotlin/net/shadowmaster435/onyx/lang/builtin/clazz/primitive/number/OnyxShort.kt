package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

class OnyxShort(override val held: Short) : OnyxNumber<Short>(makeOverrideDesc<OnyxShort>("Short")) {
    override val byteCount = 2
    override fun toByte() = held.toByte()
    override fun toShort() = held
    override fun toInt() = held.toInt()
    override fun toLong() = held.toLong()
    override fun toFloat() = held.toFloat()
    override fun toDouble() = held.toDouble()
}