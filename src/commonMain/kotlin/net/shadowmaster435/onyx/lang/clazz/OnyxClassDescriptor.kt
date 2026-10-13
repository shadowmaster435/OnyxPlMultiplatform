package net.shadowmaster435.onyx.lang.clazz

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxNull
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.field.OnyxFieldDesc
import net.shadowmaster435.onyx.lang.function.AbstractOnyxFunction
import net.shadowmaster435.onyx.lang.function.OnyxFunction
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.pkg.OnyxPackage
import net.shadowmaster435.onyx.lang.util.EnumMap
import net.shadowmaster435.onyx.lang.util.enumMapOf

open class OnyxClassDescriptor(
    val name: String,
    val pkg: OnyxPackage,
    fields: LinkedHashMap<String, OnyxFieldDesc>,
    functions: LinkedHashMap<String, AbstractOnyxFunction>,
    classes: LinkedHashMap<String, OnyxClassDescriptor>,
    val type: OnyxClassType,
    supertypes: List<OnyxClassDescriptor>,
    val companion: OnyxObject? = null,
    protected val operatorOverloads: EnumMap<OnyxOperator, OnyxFunction> = enumMapOf(),
    val genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
) {
    open val constant: Boolean = false
    protected val fieldEntries = fields.values.toTypedArray()
    protected val functionEntries = functions.values.toTypedArray()
    protected val classEntries = classes.values.toTypedArray()


    internal val fields = HashMap<String, Int>().apply {
        var i = 0
        fields.forEach {
            put(it.key, i)
            i++
        }
    }
    internal val functions = HashMap<String, Int>().apply {
        var i = 0
        functions.forEach {
            put(it.key, i)
            i++
        }
    }
    internal val classes = HashMap<String, Int>().apply {
        var i = 0
        classes.forEach {
            put(it.key, i)
            i++
        }
    }
    val supertypes = supertypes.toList()


    val equalsFunction = functions["equals"]
    val hashcodeFunction = functions["hashCode"]



    constructor(
        name: String,
        pkg: OnyxPackage,
        fields: LinkedHashMap<String, OnyxFieldDesc>,
        functions: LinkedHashMap<String, AbstractOnyxFunction>,
        classes: LinkedHashMap<String, OnyxClassDescriptor>,
        type: OnyxClassType,
        supertype: OnyxClassDescriptor,
        companion: OnyxObject? = null,
        operatorOverloads: EnumMap<OnyxOperator, OnyxFunction> = enumMapOf(),
        genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty
    ) : this(name, pkg, fields, functions, classes, type, listOf(supertype), companion, operatorOverloads, genericTuples)

    fun castableTo(other: OnyxClassDescriptor): Boolean {
        for(supertype in supertypes) {
            if (supertype == other || supertype.castableTo(other)) return true
        }
        return false
    }
    open fun safeCast(from: OnyxClassInstance) =
        if (castableTo(from.desc)) from else OnyxNull
    open fun cast(from: OnyxClassInstance): OnyxClassInstance {
        if (castableTo(from.desc)) return from
        throw ClassCastException("Class ${from.desc} cannot be cast to $this")
    }


    fun getFieldRefIndex(name: String) = fields[name]!!
    fun getField(refIndex: Int) = fieldEntries[refIndex]
    fun getField(name: String) = fieldEntries[fields[name]!!]
    fun getOperatorOverload(op: OnyxOperator) = operatorOverloads[op]

    open fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        supertypes: List<OnyxClassDescriptor>,
        companion: OnyxObject? = null,
        fields: LinkedHashMap<String, OnyxFieldDesc> = linkedMapOf(),
        functions: LinkedHashMap<String, AbstractOnyxFunction> = linkedMapOf(),
        classes: LinkedHashMap<String, OnyxClassDescriptor> = linkedMapOf(),
        operatorOverloads: EnumMap<OnyxOperator, OnyxFunction> = enumMapOf(),
        genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
    ) = OnyxClassDescriptor(
        name,
        pkg,
        LinkedHashMap(buildMap { this@OnyxClassDescriptor.fields.forEach {
            put(it.key, fieldEntries[it.value])
        } } + fields),
        LinkedHashMap(buildMap { this@OnyxClassDescriptor.functions.forEach {
            put(it.key, functionEntries[it.value])
        } } + functions),
        LinkedHashMap(buildMap { this@OnyxClassDescriptor.classes.forEach {
            put(it.key, classEntries[it.value])
        } } + classes),
        type,
        supertypes + this,
        companion,
        enumMapOf(this.operatorOverloads + operatorOverloads), genericTuples
    )

    open fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        supertype: OnyxClassDescriptor,
        companion: OnyxObject? = null,
        fields: LinkedHashMap<String, OnyxFieldDesc> = linkedMapOf(),
        functions: LinkedHashMap<String, AbstractOnyxFunction> = linkedMapOf(),
        classes: LinkedHashMap<String, OnyxClassDescriptor> = linkedMapOf(),
        operatorOverloads: EnumMap<OnyxOperator, OnyxFunction> = enumMapOf(),
        genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
    ) = extend(
        name, pkg, type, listOf(supertype), companion,
        fields, functions, classes, operatorOverloads, genericTuples
    )

    open fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        companion: OnyxObject? = null,
        fields: LinkedHashMap<String, OnyxFieldDesc> = linkedMapOf(),
        functions: LinkedHashMap<String, AbstractOnyxFunction> = linkedMapOf(),
        classes: LinkedHashMap<String, OnyxClassDescriptor> = linkedMapOf(),
        operatorOverloads: EnumMap<OnyxOperator, OnyxFunction> = enumMapOf(),
        genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
    ) = extend(
        name, pkg, type, listOf(), companion,
        fields, functions, classes, operatorOverloads, genericTuples
    )

    companion object {
        val equalDesc = "equals" to OnyxClassInstance.defaultEqualsFunction
        val hashcodeDesc = "hashCode" to OnyxClassInstance.defaultHashcodeFunction
    }
}