package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import net.shadowmaster435.onyx.compiler.OnyxCompiledType
import net.shadowmaster435.onyx.lang.enums.jvm.JVMPrimitiveType

actual val OnyxLong.compileFunc: (Any?) -> Any?
    get() = { OnyxCompiledType(

        JVMPrimitiveType.LONG to held
    ) }