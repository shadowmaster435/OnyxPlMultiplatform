package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType

class OnyxExternalFunctionDescriptor(
    name: String,
    tuples: OnyxTuples,
    modifiers: List<OnyxModifierType>,
    genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
    returnType: OnyxClassDescriptor = OnyxUnit.desc,

    val function: (thisInst: OnyxClassInstance?, list: List<OnyxClassInstance>) -> OnyxClassInstance
) : AbstractOnyxFunctionDescriptor(name, tuples, modifiers, genericTuples, returnType) {
    override val type = OnyxMemberType.FUNCTION

    constructor(
        name: String,
        tuples: OnyxTuples,
        function: (thisInst: OnyxClassInstance?, list: List<OnyxClassInstance>) -> OnyxClassInstance
    ) : this(name, tuples, listOf(OnyxModifierType.PUBLIC), function = function)
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