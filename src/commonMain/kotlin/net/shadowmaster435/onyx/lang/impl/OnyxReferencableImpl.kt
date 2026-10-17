package net.shadowmaster435.onyx.lang.impl

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance

interface OnyxReferencableImpl {
    fun getRefVal(thisInstance: OnyxClassInstance?): OnyxClassInstance?
    fun setRefVal(thisInstance: OnyxClassInstance?, v: OnyxClassInstance)
}