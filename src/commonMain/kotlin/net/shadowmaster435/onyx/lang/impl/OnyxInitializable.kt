package net.shadowmaster435.onyx.lang.impl

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.util.OnyxParams

interface OnyxInitializable {
    fun initialize(
        thisInst: OnyxClassInstance?,
        params: OnyxParams
    )
}