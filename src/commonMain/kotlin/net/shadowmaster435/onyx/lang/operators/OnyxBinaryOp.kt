package net.shadowmaster435.onyx.lang.operators

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance


abstract class OnyxBinaryOp(
    val a: OnyxClassProvider,
    val b: OnyxClassProvider,
): AbstractOnyxOp()
