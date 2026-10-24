package net.shadowmaster435.onyx.lang.builtin.clazz.primitive

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.llvm.LLVMConstString

@OptIn(ExperimentalForeignApi::class)
actual val OnyxString.compileFunc: () -> Any?
    get() = {
        LLVMConstString(held, held.length.toUInt(), 0)
    }