package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

class OnyxFloat(override val held: Float) : OnyxNumber<Float>(makeOverrideDesc<OnyxFloat>("Float")) {
    override val byteCount = 4
    override fun toByte() = held.toInt().toByte()
    override fun toShort() = held.toInt().toShort()
    override fun toInt() = held.toInt()
    override fun toLong() = held.toLong()
    override fun toFloat() = held
    override fun toDouble() = held.toDouble()
}