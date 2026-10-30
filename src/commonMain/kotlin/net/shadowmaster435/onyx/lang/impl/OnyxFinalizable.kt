package net.shadowmaster435.onyx.lang.impl

interface OnyxFinalizable<T> {
    val isFinalized get() = false
    fun createFinal(): T
    @Suppress("UNCHECKED_CAST") // safe as if this creates something not extending this interface it won't matter.
    fun checkCreateFinal(): T = if (!isFinalized) createFinal() else this as T
}