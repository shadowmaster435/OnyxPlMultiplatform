package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.exception.IncorrectParametersException
import net.shadowmaster435.onyx.exception.TypeMismatchException
import net.shadowmaster435.onyx.exception.UnknownException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxNull
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxTuples(val tuples: List<OnyxTuple>) {
    constructor(vararg tuples: OnyxTuple) : this(tuples.toList())
    private val nameMap = hashMapOf<String, Int>().apply {
        tuples.forEachIndexed { index, tuple ->
            put(tuple.name, index)
        }
    }

    val requiredTuples = run {
        var count = 0
        tuples.forEach { if (!it.optional) count++ }
        count
    }

    private fun normal(v: OnyxClassInstance, t: OnyxClassDescriptor) = v.desc.castableTo(t)
    private fun strict(v: OnyxClassInstance, t: OnyxClassDescriptor) = t == v
    private fun error(v: OnyxClassInstance, t: OnyxClassDescriptor) {
        throw TypeMismatchException("Invalid value type '$v' for tuple of type $t")
    }



    private fun iter(input: Iterator<OnyxClassInstance>, strict: Boolean) {
        var i = 0
        input.forEach { value ->
            val tuple = tuples[i]
            if (value === OnyxNull && !tuple.nullable) throw NullPointerException("Tuple ${tuple.name} defined as non-null")
            if (!(if (strict) strict(value, tuple.type) else normal(value, tuple.type))) error(value, tuple.type)
            i++
        }
        if (i < requiredTuples) throw IncorrectParametersException("Invalid parameter count.")
    }

    fun validateStrict(input: Array<out OnyxClassInstance>) = iter(input.iterator(), true)
    fun validateStrict(input: List<OnyxClassInstance>) = iter(input.iterator(), true)
    fun validate(input: Array<out OnyxClassInstance>) = iter(input.iterator(), false)
    fun validate(input: List<OnyxClassInstance>) = iter(input.iterator(), false)

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
        val template = ParsinatorTemplate {
            token(false, "(", false, true)
            whitespace(true)
            repeating(false, true) {
                use(OnyxTuple.template)
                trailing {
                    whitespace(true)
                    token(false, ",", excludeFromChains = true)
                }
            }
            whitespace(true)
            token(false, ")", false, true)
        }

    }

}