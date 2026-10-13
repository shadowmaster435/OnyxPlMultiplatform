package net.shadowmaster435.onyx.lang.generics

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxNull
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.function.OnyxTuple

class OnyxGenericTuples(val tuples: List<OnyxGenericType>) {
    constructor(vararg tuples: OnyxGenericType) : this(tuples.toList())
    val requiredTuples = tuples.size

    private fun normal(v: OnyxGenericType, t: OnyxGenericType) =
        v.type?.castableTo(t.type ?: throw err) ?: throw err



    private fun iter(input: Iterator<OnyxGenericType>) {
        var i = 0
        input.forEach { value ->
            val tuple = tuples[i]
            if (tuple.type === OnyxNull.desc && !tuple.nullable) throw NullPointerException("Generic defined as non null")
            if (!(normal(value, tuple))) throw err
            i++
        }
        if (i < requiredTuples) throw RuntimeException("Invalid parameter count.")
    }

    fun validate(input: Array<out OnyxGenericType>) = iter(input.iterator())
    fun validate(input: List<OnyxGenericType>) = iter(input.iterator())

    override fun toString(): String {
        var str = ""
        tuples.forEachIndexed { index, tuple ->
            str += if (index == tuples.size - 1) "$tuple" else "$tuple, "
        }
        return str
    }

    companion object {
        private val err = NullPointerException("Type erased for generic cast, this should never happen")
        val empty = OnyxGenericTuples(listOf())
    }
}