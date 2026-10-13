package net.shadowmaster435.onyx.util

open class ListBuilder<T> internal constructor() : AbstractMutableList<T>() {
    private val entries = ArrayList<T>()
    override val size: Int get() = entries.size
    override fun get(index: Int) = entries[index]

    override fun set(index: Int, element: T) = entries.set(index, element)

    override fun add(index: Int, element: T) {
        add(index, element)
    }

    override fun removeAt(index: Int) = entries.removeAt(index)
}