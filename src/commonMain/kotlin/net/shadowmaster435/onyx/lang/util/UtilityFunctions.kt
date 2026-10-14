package net.shadowmaster435.onyx.lang.util

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.field.OnyxExternalFinalFieldDesc
import net.shadowmaster435.onyx.lang.field.OnyxFieldDesc
import net.shadowmaster435.onyx.lang.function.OnyxExternalFunction
import net.shadowmaster435.onyx.lang.function.OnyxTuple
import net.shadowmaster435.onyx.lang.function.OnyxTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples

fun field(name: String, type: OnyxClassDescriptor,) {}
fun externalFinalField(name: String, type: OnyxClassDescriptor, getter: (OnyxClassInstance?) -> OnyxClassInstance) = name to
    OnyxExternalFinalFieldDesc(listOf(), type, extGetter = getter)
fun interfaceField(name: String, type: OnyxClassDescriptor) = name to OnyxFieldDesc(
    type, listOf(), isVirtual = true
)

fun externalOverload(
    op: OnyxOperator,
    tuples: OnyxTuples,
    returnType: OnyxClassDescriptor = OnyxUnit.desc,
    modifiers: List<OnyxModifierType> = listOf(),
    genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
    func: (thisInst: OnyxClassInstance?, list: List<OnyxClassInstance>) -> OnyxClassInstance
) {
    op to OnyxExternalFunction(
        tuples, modifiers, genericTuples, returnType, func
    )
}

fun externalFunc(
    tuples: OnyxTuples,
    modifiers: List<OnyxModifierType> = listOf(),
    returnType: OnyxClassDescriptor = OnyxUnit.desc,
    genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
    func: (thisInst: OnyxClassInstance?, list: List<OnyxClassInstance>) -> OnyxClassInstance
) {
    OnyxExternalFunction(
        tuples, modifiers, genericTuples, returnType, func
    )
}


fun simpleTuple(name: String, type: OnyxClassDescriptor, nullable: Boolean = false) = OnyxTuples(
    OnyxTuple(
        type,
        name, false, nullable
    )
)
