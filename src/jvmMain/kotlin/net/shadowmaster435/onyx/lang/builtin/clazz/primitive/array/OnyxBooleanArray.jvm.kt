package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.array

import net.shadowmaster435.onyx.lang.enums.jvm.JVMPrimitiveType

actual val OnyxBooleanArray.compileFunc: (any: Any?) -> Any?
    get() = {
        JVMPrimitiveType.BOOL_ARRAY to held
    }