package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.exception.IncorrectParametersException
import net.shadowmaster435.onyx.exception.InvalidParameterCountException
import net.shadowmaster435.onyx.exception.TypeMismatchException
import net.shadowmaster435.onyx.exception.UnknownException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxNull
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.util.list
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

open class OnyxTuples(val tuples: List<OnyxTuple>) {
    constructor(vararg tuples: OnyxTuple) : this(tuples.toList())
    val nameMap = hashMapOf<String, Int>().apply {
        tuples.forEachIndexed { index, tuple ->
            put(tuple.name, index)
        }
    }

    open val requiredTuples = run {
        var count = 0
        tuples.forEach { if (!it.optional) count++ }
        count
    }

    protected open fun normal(v: OnyxClassInstance, t: OnyxClassDescriptor) = v.desc.castableTo(t)
    protected open fun strict(v: OnyxClassInstance, t: OnyxClassDescriptor) = t == v
    protected open fun error(v: OnyxClassInstance, t: OnyxClassDescriptor) {
        throw TypeMismatchException("Invalid value type '$v' for tuple of type $t")
    }



    protected open fun iter(input: Iterator<OnyxClassInstance>, strict: Boolean) {
        var i = 0
        input.forEach { value ->
            val tuple = tuples[i]
            if (value === OnyxNull && !tuple.nullable) throw IncorrectParametersException("Tuple ${tuple.name} defined as non-null")
            if (!(if (strict) strict(value, tuple.type) else normal(value, tuple.type))) error(value, tuple.type)
            i++
        }
        if (i < requiredTuples) throw InvalidParameterCountException("Invalid parameter count.")
    }

    open fun validateStrict(input: Array<out OnyxClassInstance>) = iter(input.iterator(), true)
    open fun validateStrict(input: List<OnyxClassInstance>) = iter(input.iterator(), true)
    open fun validate(input: Array<out OnyxClassInstance>) = iter(input.iterator(), false)
    open fun validate(input: List<OnyxClassInstance>) = iter(input.iterator(), false)

    fun getTuple(name: String) = nameMap[name] ?: throw UnknownException("Unknown tuple $name")

    override fun toString(): String {
        var str = ""
        tuples.forEachIndexed { index, tuple ->
            str += if (index == tuples.size - 1) "$tuple" else "$tuple, "
        }
        return str
    }

    companion object {
        val empty = OnyxTuples(listOf())
    }

}