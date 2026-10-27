package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.array

import net.shadowmaster435.onyx.lang.enums.jvm.JVMPrimitiveType

actual val OnyxShortArray.compileFunc: (any: Any?) -> Any?
    get() = {
        JVMPrimitiveType.SHORT_ARRAY to held
    }