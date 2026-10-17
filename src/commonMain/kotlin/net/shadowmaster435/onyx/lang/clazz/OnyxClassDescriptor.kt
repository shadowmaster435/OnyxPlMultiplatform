package net.shadowmaster435.onyx.lang.clazz

import net.shadowmaster435.onyx.exception.UnknownException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxNull
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.function.AbstractOnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.impl.OnyxAccessImpl
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.lang.impl.OnyxMemberImpl
import net.shadowmaster435.onyx.lang.impl.OnyxNamedImpl
import net.shadowmaster435.onyx.lang.pkg.OnyxPackage
import net.shadowmaster435.onyx.lang.util.EnumMap
import net.shadowmaster435.onyx.lang.util.enumMapOf
import net.shadowmaster435.onyx.util.OnyxModifiers

open class OnyxClassDescriptor(
    override val name: String,
    open val pkg: OnyxPackage,
    override val modifiers: OnyxModifiers,
    fields: LinkedHashMap<String, OnyxFieldImpl>,
    functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor>,
    classes: LinkedHashMap<String, OnyxClassDescriptor>,
    open val classType: OnyxClassType,
    supertypes: List<OnyxClassDescriptor>,
    open val companion: OnyxObject? = null,
    internal open val operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor> = enumMapOf(),
    open val genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
    open val constructors: List<AbstractOnyxFunctionDescriptor> = listOf(),
): OnyxMemberImpl, OnyxAccessImpl, OnyxNamedImpl {
    open val constant: Boolean = false
    internal open val fieldEntries = fields.values.toTypedArray()
    internal open val functionEntries = functions.values.toTypedArray()
    internal open val classEntries = classes.values.toTypedArray()

    override val type: OnyxMemberType = OnyxMemberType.CLASS

    override fun poke() {

    }



    internal open val fields = HashMap<String, Int>().apply {
        var i = 0
        fields.forEach {
            put(it.key, i)
            i++
        }
    }
    internal open val functions = HashMap<String, Int>().apply {
        var i = 0
        functions.forEach {
            put(it.key, i)
            i++
        }
    }
    internal open val classes = HashMap<String, Int>().apply {
        var i = 0
        classes.forEach {
            put(it.key, i)
            i++
        }
    }
    open val supertypes = supertypes.toList()


    open val equalsFunction = functions["equals"]
    open val hashcodeFunction = functions["hashCode"]

    fun findSubclass(path: String) = findSubclass(path.split('.'))

    fun findSubclass(path: List<String>): OnyxClassDescriptor? {
        var current = classEntries[classes[path.first()] ?: return null]
        fun iter(index: Int = 1): OnyxClassDescriptor? {
            if (index > path.size) return current
            current = classEntries[classes[path[index]] ?: return null]
            return iter(index + 1)
        }
        return iter()
    }



    constructor(
        name: String,
        pkg: OnyxPackage,
        fields: LinkedHashMap<String, OnyxFieldImpl>,
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor>,
        classes: LinkedHashMap<String, OnyxClassDescriptor>,
        type: OnyxClassType,
        supertype: OnyxClassDescriptor?,
        companion: OnyxObject? = null,
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor> = enumMapOf(),
        genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
        modifiers: OnyxModifiers = listOf(OnyxModifierType.PUBLIC)
    ) : this(name, pkg, modifiers, fields, functions, classes, type,
        if (supertype == null) listOf() else listOf(supertype),
        companion, operatorOverloads, genericTuples
    )

    open fun castableTo(other: OnyxClassDescriptor): Boolean {
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

    open fun getFieldRefIndex(name: String) = fields[name]!!
    open fun getField(refIndex: Int) = fieldEntries[refIndex]
    open fun getField(name: String) = fieldEntries[fields[name]!!]
    open fun getOperatorOverload(op: OnyxOperator) = operatorOverloads[op]

    open fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        supertypes: List<OnyxClassDescriptor>,
        companion: OnyxObject? = null,
        fields: LinkedHashMap<String, OnyxFieldImpl> = linkedMapOf(),
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor> = linkedMapOf(),
        classes: LinkedHashMap<String, OnyxClassDescriptor> = linkedMapOf(),
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor> = enumMapOf(),
        genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
        modifiers: OnyxModifiers = listOf(OnyxModifierType.PUBLIC)
    ) = OnyxClassDescriptor(
        name,
        pkg,
        modifiers,
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
        fields: LinkedHashMap<String, OnyxFieldImpl> = linkedMapOf(),
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor> = linkedMapOf(),
        classes: LinkedHashMap<String, OnyxClassDescriptor> = linkedMapOf(),
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor> = enumMapOf(),
        genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
        modifiers: OnyxModifiers = listOf(OnyxModifierType.PUBLIC)
    ) = extend(
        name, pkg, type, listOf(supertype), companion,
        fields, functions, classes, operatorOverloads, genericTuples, modifiers
    )

    open fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        companion: OnyxObject? = null,
        fields: LinkedHashMap<String, OnyxFieldImpl> = linkedMapOf(),
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor> = linkedMapOf(),
        classes: LinkedHashMap<String, OnyxClassDescriptor> = linkedMapOf(),
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor> = enumMapOf(),
        genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
        modifiers: OnyxModifiers = listOf(OnyxModifierType.PUBLIC)
    ) = extend(
        name, pkg, type, listOf(anyDesc), companion,
        fields, functions, classes, operatorOverloads, genericTuples, modifiers
    )

    companion object {
        val equalDesc = "equals" to OnyxClassInstance.defaultEqualsFunction
        val hashcodeDesc = "hashCode" to OnyxClassInstance.defaultHashcodeFunction
        val toStringDesc = "toString" to OnyxClassInstance.defaultToStringFunction
    }
}