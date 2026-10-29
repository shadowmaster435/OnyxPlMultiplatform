package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.exception.VirtualAccessException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxUnfinalizedClassDesc
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType
import net.shadowmaster435.onyx.lang.generics.OnyxUnfinalizedGenericTuples

open class OnyxVirtualFunctionDescriptor(
    name: String,
    tuples: OnyxTuples,
    modifiers: List<OnyxModifierType> = listOf(),
    genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
    returnType: OnyxClassDescriptor = OnyxUnit.desc,
) : AbstractOnyxFunctionDescriptor(
    name, tuples, modifiers + OnyxModifierType.ABSTRACT, genericTuples, returnType
) {
    override val type = OnyxMemberType.FUNCTION
    constructor(
        name: String,
        tuples: OnyxTuples,
    ) : this(name, tuples, listOf(OnyxModifierType.PUBLIC))

    override fun invoke(thisInst: OnyxClassInstance?, params: List<OnyxClassInstance>, generics: List<OnyxGenericType>) =
        throw VirtualAccessException("Cannot directly invoke a virtual function")
}