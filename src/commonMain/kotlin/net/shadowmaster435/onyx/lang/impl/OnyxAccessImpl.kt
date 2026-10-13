package net.shadowmaster435.onyx.lang.impl

import net.shadowmaster435.onyx.ext.containsAny
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType

interface OnyxAccessImpl : OnyxModifierImpl {
    val isFinal get() = modifiers.containsAny(listOf(OnyxModifierType.FINAL, OnyxModifierType.CONST))
    val isConst get() = modifiers.contains(OnyxModifierType.CONST)
    val isMeta get() = modifiers.containsAny(listOf(OnyxModifierType.CONST, OnyxModifierType.META))
    val isPublic get() = modifiers.contains(OnyxModifierType.PUBLIC)
    val isProtected get() = modifiers.contains(OnyxModifierType.PROTECTED)
    val isPrivate get() = modifiers.contains(OnyxModifierType.PRIVATE)
    val isInternal get() = modifiers.contains(OnyxModifierType.INTERNAL)
    val isFile get() = modifiers.contains(OnyxModifierType.FILE)
    val isPackage get() = modifiers.contains(OnyxModifierType.PACKAGE)
}