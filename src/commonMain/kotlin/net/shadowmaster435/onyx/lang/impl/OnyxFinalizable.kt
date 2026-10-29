package net.shadowmaster435.onyx.lang.impl

interface OnyxFinalizable<T> {
    val isFinalized get() = false
    fun createFinal(): T
}