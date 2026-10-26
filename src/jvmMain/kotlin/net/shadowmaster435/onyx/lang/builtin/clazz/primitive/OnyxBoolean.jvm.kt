package net.shadowmaster435.onyx.lang.builtin.clazz.primitive

import net.shadowmaster435.onyx.util.JVMPrimitiveType

actual val OnyxBoolean.compileFunc: (Any?) -> Any?
    get() = {
        JVMPrimitiveType.BOOL to held
    }