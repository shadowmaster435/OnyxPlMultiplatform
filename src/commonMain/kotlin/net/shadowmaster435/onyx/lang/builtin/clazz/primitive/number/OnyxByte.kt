package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

class OnyxByte(override val held: Byte) : OnyxNumber<Byte>(makeOverrideDesc<OnyxByte>("Byte")) {
    override val byteCount = 1
    override fun toByte() = held
    override fun toShort() = held.toShort()
    override fun toInt() = held.toInt()
    override fun toLong() = held.toLong()
    override fun toFloat() = held.toFloat()
    override fun toDouble() = held.toDouble()
    companion object {
        val desc = descOf(OnyxByte::class)
    }
}