package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import net.shadowmaster435.onyx.compiler.OnyxCompiledType
import net.shadowmaster435.onyx.util.JVMPrimitiveType

actual val OnyxShort.compileFunc: (Any?) -> Any?
    get() = { OnyxCompiledType(
        JVMPrimitiveType.SHORT to held
    ) }