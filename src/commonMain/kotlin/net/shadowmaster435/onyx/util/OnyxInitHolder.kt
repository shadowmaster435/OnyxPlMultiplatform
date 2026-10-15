package net.shadowmaster435.onyx.util

import net.shadowmaster435.onyx.exception.UninitializedException

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
        if (!initialized) throw UninitializedException(initFailMsg)
        return this.v!!
    }
}