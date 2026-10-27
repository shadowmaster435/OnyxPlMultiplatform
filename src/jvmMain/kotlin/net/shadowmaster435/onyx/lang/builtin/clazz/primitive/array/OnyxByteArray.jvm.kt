package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.array

import net.shadowmaster435.onyx.lang.enums.jvm.JVMPrimitiveType

actual val OnyxByteArray.compileFunc: (any: Any?) -> Any?
    get() = {
        JVMPrimitiveType.BYTE_ARRAY to held
    }