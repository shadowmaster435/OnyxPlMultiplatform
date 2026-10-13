package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType

class OnyxExternalFunction(
    tuples: OnyxTuples,
    modifiers: List<OnyxModifierType>,
    genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
    val function: (thisInst: OnyxClassInstance?, list: List<OnyxClassInstance>) -> OnyxClassInstance
) : AbstractOnyxFunction(tuples, modifiers, genericTuples) {
    override val type = OnyxMemberType.FUNCTION
    override fun poke() {}

    constructor(
        tuples: OnyxTuples,
        function: (thisInst: OnyxClassInstance?, list: List<OnyxClassInstance>) -> OnyxClassInstance
    ) : this(tuples, listOf(OnyxModifierType.PUBLIC), function = function)
    override fun invoke(
        thisInst: OnyxClassInstance?,
        params: List<OnyxClassInstance>,
        generics: List<OnyxGenericType>
    ): OnyxClassInstance {
        genericTuples.validate(generics)
        tuples.validate(params)
        return function(thisInst, params)
    }

}