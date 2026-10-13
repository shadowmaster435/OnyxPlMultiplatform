package net.shadowmaster435.onyx.lang.util

import kotlin.enums.EnumEntries
import kotlin.enums.enumEntries

class EnumMap <K: Enum<K>, V>: Map<K, V> {
    private val enumEntries: EnumEntries<K>

    override val keys: Set<K> by lazy {
        buildSet {
            enumEntries.forEach { e -> cachedEntries[e.ordinal]?.let { add(e) } }
        }
    }
    override val values by lazy {
        buildList {
            cachedEntries.forEach { v -> v?.let { add(it) } }
        }
    }
    override val entries by lazy {
        buildSet {
            enumEntries.forEach { e -> cachedEntries[e.ordinal]?.let { add(PairEntry(e, it)) } }
        }
    }
    override val size: Int
    private val cachedEntries: Array<V?>

    constructor(original: EnumMap<K, V>) : this(
        original.enumEntries,
        buildList { for ((k, v) in original) add(k to v) }.toTypedArray()
    )
    constructor(enumEntries: EnumEntries<K>, original: Map<K, V>) : this(
        enumEntries,
        buildList { for ((k, v) in original) add(k to v) }.toTypedArray()
    )


    constructor(
        enumEntries: EnumEntries<K>,
        entries: Array<out Pair<K, V>>
    ) {
        this.enumEntries = enumEntries
        @Suppress("UNCHECKED_CAST")
        cachedEntries = arrayOfNulls<Any?>(enumEntries.size) as Array<V?>
        for (entry in enumEntries) {
            val v = entries.find { entries.contains(it) }?.second
            cachedEntries[entry.ordinal] = v
        }
        size = entries.size
    }

    override fun isEmpty() = size == 0

    override fun containsKey(key: K) = cachedEntries[key.ordinal] != null

    override fun containsValue(value: V) = cachedEntries.contains(value)

    override fun get(key: K) = cachedEntries[key.ordinal]
}

inline fun <reified K: Enum<K>, V> enumMapOf(vararg entries: Pair<K, V>) = EnumMap(enumEntries<K>(), entries)
inline fun <reified K: Enum<K>, V> enumMapOf(original: Map<K, V>) = EnumMap(enumEntries<K>(),original)
