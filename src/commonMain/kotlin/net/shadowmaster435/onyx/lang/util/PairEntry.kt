package net.shadowmaster435.onyx.lang.util

import kotlin.jvm.JvmInline

@JvmInline
value class PairEntry<K, V>(val entry: Pair<K, V>) : Map.Entry<K, V>{
    constructor(k: K, v: V) : this(k to v)
    override val key get() = entry.first
    override val value get() = entry.second
    companion object {
        fun <K, V> entries(entries: Array<out Pair<K, V>>): Set<Map.Entry<K, V>> = buildSet {
            for ((k, v) in entries) add(PairEntry(k, v))
        }
        fun <K, V> values(entries: Array<out Pair<K, V>>): Collection<V> = buildSet {
            for ((_, v) in entries) add(v)
        }
        fun <K, V> keys(entries: Array<out Pair<K, V>>): Set<K> = buildSet {
            for ((k, _) in entries) add(k)
        }
    }

}