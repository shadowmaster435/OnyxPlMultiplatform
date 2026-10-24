package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import net.shadowmaster435.onyx.compiler.OnyxCompiledType
import net.shadowmaster435.onyx.util.getConstOpcode
import net.shadowmaster435.onyx.util.isConstOpcode
import org.objectweb.asm.Opcodes

actual val OnyxByte.compileFunc: () -> Any?
    get() = { OnyxCompiledType(
        if (isConstOpcode(held)) getConstOpcode(held) else Opcodes.BIPUSH to held
    ) }