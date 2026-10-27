package net.shadowmaster435.onyx.lang.impl

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.util.OnyxParams

interface OnyxInstantiatable<T> {
    fun instantiate(
        thisInst: OnyxClassInstance,
        params: OnyxParams
    ): T
}