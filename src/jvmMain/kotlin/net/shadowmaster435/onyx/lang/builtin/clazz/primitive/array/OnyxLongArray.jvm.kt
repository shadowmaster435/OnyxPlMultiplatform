package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.array

import net.shadowmaster435.onyx.lang.enums.jvm.JVMPrimitiveType

actual val OnyxLongArray.compileFunc: (any: Any?) -> Any?
    get() = {
        JVMPrimitiveType.LONG_ARRAY to held
    }