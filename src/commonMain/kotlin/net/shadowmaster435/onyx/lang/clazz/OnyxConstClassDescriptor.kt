package net.shadowmaster435.onyx.lang.clazz

import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.function.AbstractOnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.lang.pkg.OnyxPackage
import net.shadowmaster435.onyx.lang.util.EnumMap
import net.shadowmaster435.onyx.lang.util.enumMapOf
import net.shadowmaster435.onyx.util.OnyxModifiers

class OnyxConstClassDescriptor(
    name: String,
    pkg: OnyxPackage,
    modifiers: OnyxModifiers,
    constructors: List<AbstractOnyxFunctionDescriptor>,
    fields: LinkedHashMap<String, OnyxFieldImpl>,
    functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor>,
    classes: LinkedHashMap<String, OnyxClassDescriptor>,
    type: OnyxClassType,
    supertypes: List<OnyxConstClassDescriptor>,
    companion: OnyxObject? = null,
    operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor> = enumMapOf(),
) : OnyxClassDescriptor(
    name, pkg, modifiers, fields, functions, classes, type, supertypes, companion, operatorOverloads, constructors =  constructors
) {

    override val constant = true

    init {
        fields.forEach {
            if (!it.value.classType.constant) throw RuntimeException("Fields must be final to be used in a constant class")
        }
    }
    fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        constructors: List<AbstractOnyxFunctionDescriptor>,
        supertypes: List<OnyxConstClassDescriptor>,
        companion: OnyxObject? = null,
        fields: LinkedHashMap<String, OnyxFieldImpl> = linkedMapOf(),
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor> = linkedMapOf(),
        classes: LinkedHashMap<String, OnyxClassDescriptor> = linkedMapOf(),
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor> = enumMapOf(),
        modifiers: OnyxModifiers = listOf(OnyxModifierType.PUBLIC)
    ) = OnyxConstClassDescriptor(
        name,
        pkg, modifiers, constructors,
        LinkedHashMap(buildMap { this@OnyxConstClassDescriptor.fields.forEach {
            put(it.key, fieldEntries[it.value])
        } } + fields),
        LinkedHashMap(buildMap { this@OnyxConstClassDescriptor.functions.forEach {
            put(it.key, functionEntries[it.value])
        } } + functions),
        LinkedHashMap(buildMap { this@OnyxConstClassDescriptor.classes.forEach {
            put(it.key, classEntries[it.value])
        } } + classes),
        type,
        supertypes + this,
        companion,
        enumMapOf(this.operatorOverloads + operatorOverloads)
    )

    fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        constructors: List<AbstractOnyxFunctionDescriptor>,
        supertype: OnyxConstClassDescriptor,
        companion: OnyxObject? = null,
        fields: LinkedHashMap<String, OnyxFieldImpl> = linkedMapOf(),
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor> = linkedMapOf(),
        classes: LinkedHashMap<String, OnyxClassDescriptor> = linkedMapOf(),
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor> = enumMapOf(),
        modifiers: OnyxModifiers = listOf(OnyxModifierType.PUBLIC)
    ) = extend(
        name, pkg, type, constructors, listOf(supertype), companion,
        fields, functions, classes, operatorOverloads, modifiers
    )

    fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        constructors: List<AbstractOnyxFunctionDescriptor>,
        companion: OnyxObject? = null,
        fields: LinkedHashMap<String, OnyxFieldImpl> = linkedMapOf(),
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor> = linkedMapOf(),
        classes: LinkedHashMap<String, OnyxClassDescriptor> = linkedMapOf(),
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor> = enumMapOf(),
        modifiers: OnyxModifiers = listOf(OnyxModifierType.PUBLIC)
    ) = extend(
        name, pkg, type, constructors, listOf(), companion,
        fields, functions, classes, operatorOverloads, modifiers
    )
}