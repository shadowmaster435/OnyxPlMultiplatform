package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.exception.ParseException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxUnfinalizedClassDesc
import net.shadowmaster435.onyx.lang.clazz.OnyxUnfinalizedClassTypeRef
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock
import net.shadowmaster435.onyx.lang.code.OnyxReturn
import net.shadowmaster435.onyx.lang.code.OnyxUnfinalizedCodeBlock
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxUnfinalizedGenericTuples
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.lang.pkg.OnyxPackage
import net.shadowmaster435.onyx.lang.util.OnyxDefinitionName
import net.shadowmaster435.onyx.lang.util.extensionDefinitionName
import net.shadowmaster435.onyx.lang.util.list
import net.shadowmaster435.onyx.lang.util.typeTemplate
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxModifiers
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

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
        tuplesUnfinal.createFinal(),
        modifiers,
        genericTuplesUnfinal.createFinal(),
        returnTypeUnfinal.createFinal()
    )
}