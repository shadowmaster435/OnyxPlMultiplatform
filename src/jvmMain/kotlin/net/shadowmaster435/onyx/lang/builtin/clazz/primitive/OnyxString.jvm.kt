package net.shadowmaster435.onyx.lang.builtin.clazz.primitive

import net.shadowmaster435.onyx.util.JVMPrimitiveType

actual val OnyxString.compileFunc: (Any?) -> Any?
    get() = {
        JVMPrimitiveType.STRING to held
    }