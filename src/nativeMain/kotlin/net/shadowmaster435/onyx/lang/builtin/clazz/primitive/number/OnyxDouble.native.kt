package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.llvm.LLVMConstInt
import net.shadowmaster435.onyx.llvm.LLVMConstReal
import net.shadowmaster435.onyx.llvm.LLVMDoubleType
import net.shadowmaster435.onyx.llvm.LLVMInt8Type

@OptIn(ExperimentalForeignApi::class)
actual val OnyxDouble.compileFunc: () -> Any?
    get() = {
        LLVMConstReal(LLVMDoubleType(), held)
    }