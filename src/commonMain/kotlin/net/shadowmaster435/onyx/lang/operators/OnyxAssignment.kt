package net.shadowmaster435.onyx.lang.operators

import net.shadowmaster435.onyx.lang.field.OnyxFieldInstance

abstract class OnyxAssignment(
    val field: OnyxFieldInstance,
    val v: OnyxClassProvider,
): AbstractOnyxOp()
