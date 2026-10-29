package net.shadowmaster435.onyx.lang.clazz

import net.shadowmaster435.onyx.exception.UninitializedException
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.field.OnyxUnfinalizedFieldDesc
import net.shadowmaster435.onyx.lang.function.OnyxUnfinalizedFunctionDescriptor
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.lang.pkg.OnyxPackage
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxUnfinalizedClassDesc(
    name: String,
    pkg: OnyxPackage,
    val fieldsUnfinalized: List<OnyxUnfinalizedFieldDesc> = listOf(),
    val classesUnfinalized: List<OnyxUnfinalizedClassDesc> = listOf(),
    val functionsUnfinalized: List<OnyxUnfinalizedFunctionDescriptor> = listOf(),
    val supertypesUnfinalized: List<OnyxUnfinalizedClassTypeRef> = listOf(),
    val companionsUnfinalized: List<OnyxUnfinalizedClassDesc> = listOf(),
) : OnyxClassDescriptor(
    name, pkg, linkedMapOf(), linkedMapOf(), linkedMapOf(), OnyxClassType.UNINITIALIZED, null
), OnyxFinalizable<OnyxClassDescriptor> {
    override val genericTuples: OnyxGenericTuples
        get() = throw UninitializedException("Class descriptor is not initialized")

    override fun createFinal(): OnyxClassDescriptor = TODO("Class descriptor finalizer")

    companion object {
        val template: ParsinatorTemplate = TODO("Class parsinator template")

    }

}