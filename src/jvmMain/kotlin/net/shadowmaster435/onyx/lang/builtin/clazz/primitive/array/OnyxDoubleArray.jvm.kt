package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.array

import net.shadowmaster435.onyx.util.JVMPrimitiveType

actual val OnyxDoubleArray.compileFunc: (any: Any?) -> Any?
    get() = {
        JVMPrimitiveType.DOUBLE_ARRAY to held
    }