package net.shadowmaster435.onyx.lang.impl

import net.shadowmaster435.onyx.ext.containsAny
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxBoolean
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.util.externalFinalField

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

    companion object {
        private val functions = listOf(
            externalFinalField("isFinal", OnyxBoolean.desc) {OnyxBoolean((it as OnyxAccessImpl).isFinal)},
            externalFinalField("isConst", OnyxBoolean.desc) {OnyxBoolean((it as OnyxAccessImpl).isConst)},
            externalFinalField("isMeta", OnyxBoolean.desc) {OnyxBoolean((it as OnyxAccessImpl).isMeta)},
            externalFinalField("isPublic", OnyxBoolean.desc) {OnyxBoolean((it as OnyxAccessImpl).isPublic)},
            externalFinalField("isProtected", OnyxBoolean.desc) {OnyxBoolean((it as OnyxAccessImpl).isProtected)},
            externalFinalField("isPrivate", OnyxBoolean.desc) {OnyxBoolean((it as OnyxAccessImpl).isPrivate)},
            externalFinalField("isInternal", OnyxBoolean.desc) {OnyxBoolean((it as OnyxAccessImpl).isInternal)},
            externalFinalField("isFile", OnyxBoolean.desc) {OnyxBoolean((it as OnyxAccessImpl).isFile)},
            externalFinalField("isPackage", OnyxBoolean.desc) {OnyxBoolean((it as OnyxAccessImpl).isPackage)}
        ).toTypedArray()
        fun functions() = functions.clone()

    }

}