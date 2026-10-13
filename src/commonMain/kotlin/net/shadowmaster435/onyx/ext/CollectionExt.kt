package net.shadowmaster435.onyx.ext

fun <T> Collection<T>.containsAny(collection: Collection<T>): Boolean {
    for (it in this) {
        if (collection.contains(it)) return true
    }
    return false
}

fun <T> Collection<T>.isOnlyOne(v: T): Boolean {
    return size == 1 && first() == v
}
fun <T> Collection<T>.hasOnlyOne(v: T): Boolean {
    var hit = false
    for (it in this) {
        if (it == v)
            if (hit) return false
            else hit = true
    }
    return true
}