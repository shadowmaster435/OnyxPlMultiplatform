package net.shadowmaster435.onyx.util

class OnyxInitHolder<T : Any> internal constructor(
    val initFailMsg: String = "Value not initialized.",
    private val onSet: (v: T?) -> Unit = {}
) {
    var initialized = false; private set
    private var v: T? = null
    fun set(v: T?) {
        initialized = true
        this.v = v
        onSet(v)
    }
    fun get(): T {
        if (!initialized) throw NullPointerException(initFailMsg)
        return this.v!!
    }
}