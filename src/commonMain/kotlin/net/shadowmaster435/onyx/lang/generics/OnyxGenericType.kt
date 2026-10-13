package net.shadowmaster435.onyx.lang.generics

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.generics.OnyxGenericVariance

class OnyxGenericType(
    type: OnyxClassDescriptor,
    val variance: OnyxGenericVariance,
    val nullable: Boolean = false
) {
    var type: OnyxClassDescriptor? = type; private set
    fun erase() {
        if (variance.erasable) type = null
    }
    fun cast(from: OnyxClassInstance) = type?.cast(from) ?: throw err
    fun safeCast(from: OnyxClassInstance) = type?.safeCast(from) ?: throw err
    fun castableTo(from: OnyxClassInstance) = from.castableTo(type ?: throw err)

    companion object {
        private val err = NullPointerException("Tried to cast to erased type, this should never happen")
    }
}