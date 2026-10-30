package net.shadowmaster435.onyx.lang.generics

import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.exception.IncorrectParametersException
import net.shadowmaster435.onyx.exception.InvalidParameterCountException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxNull

open class OnyxGenericTuples(val tuples: List<OnyxGenericType>) {
    constructor(vararg tuples: OnyxGenericType) : this(tuples.toList())
    open val requiredTuples = tuples.size

    protected open fun normal(v: OnyxGenericType, t: OnyxGenericType) =
        v.genericType?.castableTo(t.genericType ?: throw err) ?: throw err



    protected open fun iter(input: Iterator<OnyxGenericType>) {
        var i = 0
        input.forEach { value ->
            val tuple = tuples[i]
            if (tuple.genericType === OnyxNull.desc && !tuple.nullability.nullable) throw IncorrectParametersException("Generic defined as non null")
            if (!(normal(value, tuple))) throw err
            i++
        }
        if (i < requiredTuples) throw InvalidParameterCountException("Invalid parameter count.")
    }

    open fun validate(input: Array<out OnyxGenericType>) = iter(input.iterator())
    open fun validate(input: List<OnyxGenericType>) = iter(input.iterator())

    override fun toString(): String {
        var str = ""
        tuples.forEachIndexed { index, tuple ->
            str += if (index == tuples.size - 1) "$tuple" else "$tuple, "
        }
        return str
    }

    companion object {
        private val err = ImpossibleException("Type erased for generic cast, this should never happen")
        val empty = OnyxGenericTuples(listOf())
    }
}