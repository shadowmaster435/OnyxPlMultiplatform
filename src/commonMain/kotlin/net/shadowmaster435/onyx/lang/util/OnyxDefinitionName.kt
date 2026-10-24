package net.shadowmaster435.onyx.lang.util

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples

class OnyxDefinitionName(
    val name: String,
    val extensionClass: OnyxClassDescriptor?,
    val extensionGenerics: OnyxGenericTuples?,

) {
}