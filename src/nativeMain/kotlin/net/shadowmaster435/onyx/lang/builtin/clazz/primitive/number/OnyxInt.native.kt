package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.llvm.LLVMConstInt
import net.shadowmaster435.onyx.llvm.LLVMInt32Type
import net.shadowmaster435.onyx.llvm.LLVMInt8Type

@OptIn(ExperimentalForeignApi::class)
actual val OnyxInt.compileFunc: () -> Any?
    get() = {
        LLVMConstInt(LLVMInt32Type(), held.toULong(), 1)
    }