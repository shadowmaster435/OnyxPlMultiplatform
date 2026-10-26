package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.array

import net.shadowmaster435.onyx.util.JVMPrimitiveType

actual val OnyxFloatArray.compileFunc: (any: Any?) -> Any?
    get() = {
        JVMPrimitiveType.FLOAT_ARRAY to held
    }