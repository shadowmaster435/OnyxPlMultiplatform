package net.shadowmaster435.onyx.lang.clazz

import net.shadowmaster435.onyx.exception.NonExistenceException
import net.shadowmaster435.onyx.exception.UninitializedException
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.function.AbstractOnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.lang.pkg.OnyxPackage
import net.shadowmaster435.onyx.lang.pkg.globalRoot
import net.shadowmaster435.onyx.lang.util.EnumMap
import net.shadowmaster435.onyx.util.OnyxModifiers
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxUnfinalizedClassTypeRef(
    val qname: String,
) : OnyxClassDescriptor(
    "", globalRoot, linkedMapOf(), linkedMapOf(), linkedMapOf(), OnyxClassType.UNINITIALIZED, null
), OnyxFinalizable<OnyxClassDescriptor> {
    override val pkg: OnyxPackage
        get() = throw UninitializedException("Class is not initialized")
    
    override val qualifiedName: String
        get() = qname

    override val fieldEntries: Array<OnyxFieldImpl>
        get() = throw uninitialized
    override val functionEntries: Array<AbstractOnyxFunctionDescriptor>
        get() = throw uninitialized
    override val classEntries: Array<OnyxClassDescriptor>
        get() = throw uninitialized
    override val fields: HashMap<String, Int>
        get() = throw uninitialized
    override val functions: HashMap<String, Int>
        get() = throw uninitialized
    override val classes: HashMap<String, Int>
        get() = throw uninitialized
    override val equalsFunction: AbstractOnyxFunctionDescriptor
        get() = throw uninitialized
    override val classType: OnyxClassType
        get() = throw uninitialized
    override val companions: List<OnyxObject>
        get() = throw uninitialized
    override val constructors: List<AbstractOnyxFunctionDescriptor>
        get() = throw uninitialized
    override val genericTuples: OnyxGenericTuples
        get() = throw uninitialized
    override val hashcodeFunction: AbstractOnyxFunctionDescriptor
        get() = throw uninitialized
    override val operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor>
        get() = throw uninitialized
    override val supertypes: List<OnyxClassDescriptor>
        get() = throw uninitialized
    override val modifiers: OnyxModifiers
        get() = throw uninitialized


    override fun getField(refIndex: Int): OnyxFieldImpl {
        throw uninitialized
    }

    override fun getFieldRefIndex(name: String): Int {
        throw uninitialized
    }

    override fun getOperatorOverload(op: OnyxOperator): AbstractOnyxFunctionDescriptor? {
        throw uninitialized
    }

    override fun castableTo(other: OnyxClassDescriptor) = throw uninitialized


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
        throw uninitialized
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
        throw uninitialized
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
        throw uninitialized
    }
    override fun cast(from: OnyxClassInstance) = throw uninitialized
    override fun safeCast(from: OnyxClassInstance) = throw uninitialized

    override fun getField(name: String): OnyxFieldImpl = throw uninitialized

    override fun createFinal(): OnyxClassDescriptor = OnyxPackage.loadClass(qname) ?: throw NonExistenceException("Cannot find class '$qname'")

    companion object {
        private val uninitialized get() = UninitializedException("Class is not initialized")
        val template: ParsinatorTemplate = TODO("Class parsinator template")

    }

}