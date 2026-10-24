package net.shadowmaster435.onyx.lang.generics

import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.exception.IncorrectParametersException
import net.shadowmaster435.onyx.exception.InvalidParameterCountException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxNull
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.enums.code.OnyxNullability
import net.shadowmaster435.onyx.lang.enums.generics.OnyxGenericCovariance
import net.shadowmaster435.onyx.lang.util.list
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxGenericTuples(val tuples: List<OnyxGenericType>) {
    constructor(vararg tuples: OnyxGenericType) : this(tuples.toList())
    val requiredTuples = tuples.size

    private fun normal(v: OnyxGenericType, t: OnyxGenericType) =
        v.genericType?.castableTo(t.genericType ?: throw err) ?: throw err



    private fun iter(input: Iterator<OnyxGenericType>) {
        var i = 0
        input.forEach { value ->
            val tuple = tuples[i]
            if (tuple.genericType === OnyxNull.desc && !tuple.nullability.nullable) throw IncorrectParametersException("Generic defined as non null")
            if (!(normal(value, tuple))) throw err
            i++
        }
        if (i < requiredTuples) throw InvalidParameterCountException("Invalid parameter count.")
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
        private val err = ImpossibleException("Type erased for generic cast, this should never happen")
        val empty = OnyxGenericTuples(listOf())
        val definitionTemplate: ParsinatorTemplate = ParsinatorTemplate {
            chain(true) {
                token(false, "<", false, true)
                whitespace(true)
                repeating(false, false) {
                    use(OnyxGenericType.definitionTemplate)
                    whitespace(true)
                    trailing { token(true, ",", false, true) }
                }
                whitespace(true)
                token(false, ">",false, true)
            } makes {
                it as list
                val entries = it[0] as List<*>
                OnyxGenericTuples(buildList {
                    for (entry in entries) {
                        if (entry is OnyxGenericType) add(entry)
                    }
                })
            }
        }
        val inputParamTemplate: ParsinatorTemplate = ParsinatorTemplate {
            chain(false) {
                token(false, "<", excludeFromChains = true)
                whitespace(true)
                repeating(true, false) {
                    use(OnyxGenericType.inputParamTemplate)
                    whitespace(true)
                    trailing { token(true, ",", excludeFromChains = true) }
                }
                whitespace(true)
                token(false, ">", excludeFromChains = true)
            } makes {
                it as List<*>
                val inputs = it[0] as List<*>
                buildList {
                    var type: OnyxClassDescriptor? = null
                    repeat(inputs.size) { i ->
                        val isSecond = (i and 1) != 0
                        if (isSecond) {
                            val nullability = inputs[i] as OnyxNullability
                            add(
                                OnyxGenericType(
                                    "", type!!, OnyxGenericCovariance.NONE, nullability
                                )
                            )
                        } else type = inputs[i] as OnyxClassDescriptor
                    }
                }
            }
        }
        val definitionInputParamTemplate = ParsinatorTemplate {
            chain(false) {
                token(false, "<", excludeFromChains = true)
                whitespace(true)
                repeating(true, false) {
                    use(OnyxGenericType.definitionInputParamTemplate)
                    whitespace(true)
                    trailing { token(true, ",", excludeFromChains = true) }
                }
                whitespace(true)
                token(false, ">", excludeFromChains = true)
            } makes {
                it as List<*>
                val inputs = it[0] as List<*>
                buildList {
                    var type: OnyxClassDescriptor? = null
                    repeat(inputs.size) { i ->
                        val isSecond = (i and 1) != 0
                        if (isSecond) {
                            val nullability = inputs[i] as OnyxNullability
                            add(OnyxGenericType(
                                "", type!!, OnyxGenericCovariance.NONE, nullability
                            ))
                        } else type = inputs[i] as OnyxClassDescriptor
                    }
                }
            }
        }
    }
}