package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.lang.clazz.OnyxUnfinalizedClassTypeRef
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.generics.OnyxUnfinalizedGenericTuples
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable

class OnyxUnfinalizedVirtualFunctionDescriptor(
    name: String,
    modifiers: List<OnyxModifierType>,
    private val genericTuplesUnfinal: OnyxUnfinalizedGenericTuples,
    private val returnTypeUnfinal: OnyxUnfinalizedClassTypeRef,
    private val tuplesUnfinal: OnyxUnfinalizedTuples
) : OnyxVirtualFunctionDescriptor(
    name, tuplesUnfinal, modifiers, genericTuplesUnfinal, returnTypeUnfinal
), OnyxFinalizable<OnyxVirtualFunctionDescriptor> {
    override fun createFinal() = OnyxVirtualFunctionDescriptor(
        name,
        tuplesUnfinal.checkCreateFinal(),
        modifiers,
        genericTuplesUnfinal.checkCreateFinal(),
        returnTypeUnfinal.checkCreateFinal()
    )
}