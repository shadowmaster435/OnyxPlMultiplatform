package net.shadowmaster435.onyx.lang.builtin.clazz.primitive

import net.shadowmaster435.onyx.compiler.OnyxCompiledType
import org.objectweb.asm.Opcodes

actual val OnyxBoolean.compileFunc: () -> Any?
    get() = { listOf(OnyxCompiledType(if (held) Opcodes.ICONST_1 else Opcodes.ICONST_0)) }