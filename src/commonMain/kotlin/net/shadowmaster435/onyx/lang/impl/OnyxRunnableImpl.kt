package net.shadowmaster435.onyx.lang.impl

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance

interface OnyxRunnableImpl {
    fun execute(thisInst: OnyxClassInstance? = null, params: List<OnyxClassInstance> = listOf()): OnyxClassInstance?

}