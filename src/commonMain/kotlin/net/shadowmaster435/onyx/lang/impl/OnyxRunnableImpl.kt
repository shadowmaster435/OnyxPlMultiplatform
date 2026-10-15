package net.shadowmaster435.onyx.lang.impl

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.util.OnyxParams

interface OnyxRunnableImpl {
    fun execute(thisInst: OnyxClassInstance? = null, params: OnyxParams = listOf()): OnyxClassInstance?

}