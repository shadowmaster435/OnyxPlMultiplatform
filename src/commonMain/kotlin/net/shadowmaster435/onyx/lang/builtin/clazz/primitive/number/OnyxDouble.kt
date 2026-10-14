package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

class OnyxDouble(override val held: Double) : OnyxNumber<Double>(makeOverrideDesc<OnyxDouble>("Double")) {
    override val byteCount = 8
    override fun toByte() = held.toInt().toByte()
    override fun toShort() = held.toInt().toShort()
    override fun toInt() = held.toInt()
    override fun toLong() = held.toLong()
    override fun toFloat() = held.toFloat()
    override fun toDouble() = held
    companion object {
        val desc = descOf(OnyxDouble::class)
    }
}