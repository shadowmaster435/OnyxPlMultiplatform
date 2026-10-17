package net.shadowmaster435.onyx.lang.impl

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType

interface OnyxParamGetterImpl {
    fun get(param: String): OnyxClassInstance?
    fun getGeneric(param: String): OnyxGenericType?
}