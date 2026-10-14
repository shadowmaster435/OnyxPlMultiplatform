package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

class OnyxLong(override val held: Long) : OnyxNumber<Long>(makeOverrideDesc<OnyxLong>("Long")) {
    override val byteCount = 8
    override fun toByte() = held.toByte()
    override fun toShort() = held.toShort()
    override fun toInt() = held.toInt()
    override fun toLong() = held
    override fun toFloat() = held.toFloat()
    override fun toDouble() = held.toDouble()
    companion object {
        val desc = descOf(OnyxLong::class)
    }
}