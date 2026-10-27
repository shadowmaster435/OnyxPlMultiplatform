package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.array

import net.shadowmaster435.onyx.lang.enums.jvm.JVMPrimitiveType

actual val OnyxIntArray.compileFunc: (any: Any?) -> Any?
    get() = {
        JVMPrimitiveType.INT_ARRAY to held
    }