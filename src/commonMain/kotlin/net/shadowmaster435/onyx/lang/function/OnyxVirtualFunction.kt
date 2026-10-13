package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.function.OnyxFunction
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType

class OnyxVirtualFunction(tuples: OnyxTuples, modifiers: List<OnyxModifierType> = listOf(), genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty) : AbstractOnyxFunction(
    tuples, modifiers + OnyxModifierType.ABSTRACT, genericTuples
) {
    override val type = OnyxMemberType.FUNCTION
    override fun poke() {}
    constructor(
        tuples: OnyxTuples,
    ) : this(tuples, listOf(OnyxModifierType.PUBLIC))
    override fun invoke(thisInst: OnyxClassInstance?, params: List<OnyxClassInstance>, generics: List<OnyxGenericType>) =
        throw UnsupportedOperationException("Cannot directly invoke a virtual function")
}