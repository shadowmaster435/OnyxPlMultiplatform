package net.shadowmaster435.onyx.lang.builtin.clazz.primitive

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.llvm.LLVMConstInt
import net.shadowmaster435.onyx.llvm.LLVMInt1Type

@OptIn(ExperimentalForeignApi::class)
actual val OnyxBoolean.compileFunc: () -> Any?
    get() = {
        LLVMConstInt(LLVMInt1Type(), (if (held) 1 else 0).toULong(), 0)
    }