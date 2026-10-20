package net.shadowmaster435.onyx.lang.generics

import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxObject
import net.shadowmaster435.onyx.lang.enums.code.OnyxNullability
import net.shadowmaster435.onyx.lang.enums.generics.OnyxGenericVariance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.function.AbstractOnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.lang.pkg.OnyxPackage
import net.shadowmaster435.onyx.lang.pkg.globalRoot
import net.shadowmaster435.onyx.lang.util.EnumMap
import net.shadowmaster435.onyx.util.OnyxModifiers

class OnyxGenericType(
    val genericName: String,
    type: OnyxClassDescriptor,
    val variance: OnyxGenericVariance,
    val nullability: OnyxNullability
): OnyxClassDescriptor(
    "", globalRoot, listOf(), linkedMapOf(), linkedMapOf(), linkedMapOf(),
    OnyxClassType.NORMAL, listOf()
) {
    var genericType: OnyxClassDescriptor? = type; private set
    override val fieldEntries: Array<OnyxFieldImpl>
        get() = erasureCheck().fieldEntries
    override val functionEntries: Array<AbstractOnyxFunctionDescriptor>
        get() = erasureCheck().functionEntries
    override val classEntries: Array<OnyxClassDescriptor>
        get() = erasureCheck().classEntries
    override val fields: HashMap<String, Int>
        get() = erasureCheck().fields
    override val functions: HashMap<String, Int>
        get() = erasureCheck().functions
    override val classes: HashMap<String, Int>
        get() = erasureCheck().classes
    override val pkg: OnyxPackage
        get() = erasureCheck().pkg
    override val name: String
        get() = erasureCheck().name
    override val equalsFunction: AbstractOnyxFunctionDescriptor?
        get() = erasureCheck().equalsFunction
    override val classType: OnyxClassType
        get() = erasureCheck().classType
    override val companion: OnyxObject?
        get() = erasureCheck().companion
    override val constant: Boolean
        get() = erasureCheck().constant
    override val constructors: List<AbstractOnyxFunctionDescriptor>
        get() = erasureCheck().constructors
    override val genericTuples: OnyxGenericTuples
        get() = erasureCheck().genericTuples
    override val hashcodeFunction: AbstractOnyxFunctionDescriptor?
        get() = erasureCheck().hashcodeFunction
    override val operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor>
        get() = erasureCheck().operatorOverloads
    override val supertypes: List<OnyxClassDescriptor>
        get() = erasureCheck().supertypes
    override val modifiers: OnyxModifiers
        get() = super.modifiers
    override fun getField(name: String): OnyxFieldImpl {
        return erasureCheck().getField(name)
    }

    override fun getField(refIndex: Int): OnyxFieldImpl {
        return erasureCheck().getField(refIndex)
    }

    override fun getFieldRefIndex(name: String): Int {
        return erasureCheck().getFieldRefIndex(name)
    }

    override fun getOperatorOverload(op: OnyxOperator): AbstractOnyxFunctionDescriptor? {
        return erasureCheck().getOperatorOverload(op)
    }

    override fun castableTo(other: OnyxClassDescriptor) = erasureCheck().castableTo(other)

    override fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        supertypes: List<OnyxClassDescriptor>,
        companion: OnyxObject?,
        fields: LinkedHashMap<String, OnyxFieldImpl>,
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor>,
        classes: LinkedHashMap<String, OnyxClassDescriptor>,
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor>,
        genericTuples: OnyxGenericTuples,
        modifiers: OnyxModifiers
    ): OnyxClassDescriptor {
        return erasureCheck().extend(
            name,
            pkg,
            type,
            supertypes,
            companion,
            fields,
            functions,
            classes,
            operatorOverloads,
            genericTuples
        )
    }

    override fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        companion: OnyxObject?,
        fields: LinkedHashMap<String, OnyxFieldImpl>,
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor>,
        classes: LinkedHashMap<String, OnyxClassDescriptor>,
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor>,
        genericTuples: OnyxGenericTuples,
        modifiers: OnyxModifiers
    ): OnyxClassDescriptor {
        return erasureCheck().extend(
            name,
            pkg,
            type,
            companion,
            fields,
            functions,
            classes,
            operatorOverloads,
            genericTuples,
            modifiers
        )
    }

    override fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        supertype: OnyxClassDescriptor,
        companion: OnyxObject?,
        fields: LinkedHashMap<String, OnyxFieldImpl>,
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor>,
        classes: LinkedHashMap<String, OnyxClassDescriptor>,
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor>,
        genericTuples: OnyxGenericTuples,
        modifiers: OnyxModifiers
    ): OnyxClassDescriptor {
        return erasureCheck().extend(
            name,
            pkg,
            type,
            supertype,
            companion,
            fields,
            functions,
            classes,
            operatorOverloads,
            genericTuples,
            modifiers
        )
    }

    private fun erasureCheck(): OnyxClassDescriptor = if (genericType == null) throw err else genericType!!

    fun erase() {
        if (variance.erasable) genericType = null
    }
    override fun cast(from: OnyxClassInstance) = erasureCheck().cast(from)
    override fun safeCast(from: OnyxClassInstance) = erasureCheck().safeCast(from)
    fun castableTo(from: OnyxClassInstance) = from.castableTo(erasureCheck())

    companion object {
        private val err = ImpossibleException("Tried to cast to erased type, this should never happen")
    }
}