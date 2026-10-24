package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import net.shadowmaster435.onyx.compiler.OnyxCompiledType
import net.shadowmaster435.onyx.util.JVMPrimitiveType

actual val OnyxInt.compileFunc: () -> Any?
    get() = { OnyxCompiledType(

        JVMPrimitiveType.INT to held
    ) }