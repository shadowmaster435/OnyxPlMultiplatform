package net.shadowmaster435.onyx.lang.util

import net.shadowmaster435.onyx.exception.InvalidParameterCountException
import net.shadowmaster435.onyx.ext.filterDuplicates
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.field.OnyxExternalFinalFieldDesc
import net.shadowmaster435.onyx.lang.field.OnyxFieldDescriptor
import net.shadowmaster435.onyx.lang.function.AbstractOnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.function.OnyxExternalFunctionDescriptor
import net.shadowmaster435.onyx.lang.function.OnyxTuple
import net.shadowmaster435.onyx.lang.function.OnyxTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.util.OnyxModifiers
import net.shadowmaster435.parsinator.dsl.ParsinatorBuilder

val nameRegex = Regex("[_abAB09]")
val numbersRegex = Regex("[09]")
val decimalNumbersRegex = Regex("[09.]")
val invalidPCount get() = InvalidParameterCountException()

fun ParsinatorBuilder.name(optional: Boolean) = name(optional, nameRegex, numbersRegex)

fun field(name: String, type: OnyxClassDescriptor, modifiers: OnyxModifiers) {
    name to OnyxFieldDescriptor(name, type, modifiers, )
}
fun externalFinalField(name: String, type: OnyxClassDescriptor, getter: (OnyxClassInstance?) -> OnyxClassInstance) = name to
    OnyxExternalFinalFieldDesc(name, listOf(), type, extGetter = getter)
fun interfaceField(name: String, type: OnyxClassDescriptor) = name to OnyxFieldDescriptor(
    name, type, listOf(), isVirtual = true
)

fun externalOverload(
    op: OnyxOperator,
    tuples: OnyxTuples,
    returnType: OnyxClassDescriptor = OnyxUnit.desc,
    modifiers: OnyxModifiers = listOf(),
    genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
    func: (thisInst: OnyxClassInstance?, list: List<OnyxClassInstance>) -> OnyxClassInstance
) {
    op to OnyxExternalFunctionDescriptor(
        op.overloadableName ?: throw IllegalArgumentException("Operator $op is not overloadable"),
        tuples, modifiers, genericTuples, returnType, func
    )
}

fun externalFunc(
    name: String,
    tuples: OnyxTuples,
    modifiers: OnyxModifiers = listOf(),
    returnType: OnyxClassDescriptor = OnyxUnit.desc,
    genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
    func: (thisInst: OnyxClassInstance?, list: List<OnyxClassInstance>) -> OnyxClassInstance
): Pair<String, AbstractOnyxFunctionDescriptor> = name to OnyxExternalFunctionDescriptor(
    name, tuples, modifiers, genericTuples, returnType, func
)


fun simpleTuple(name: String, type: OnyxClassDescriptor, nullable: Boolean = false) = OnyxTuples(
    OnyxTuple(
        type,
        name, false, nullable
    )
)

fun OnyxModifiers.modifierString(): String {
    var str = ""
    for (mod in this) {
        val space = if (mod === last()) "" else " "
        str += mod.string + space
    }
    return str
}

fun OnyxModifiers.toJVMAccessFlags() = buildList {
    this@toJVMAccessFlags.forEach {
        addAll(it.jvmFlags)
    }
    filterDuplicates()
}
