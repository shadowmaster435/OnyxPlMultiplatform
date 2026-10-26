package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.llvm.LLVMConstInt
import net.shadowmaster435.onyx.llvm.LLVMConstReal
import net.shadowmaster435.onyx.llvm.LLVMFloatType
import net.shadowmaster435.onyx.llvm.LLVMInt8Type

@OptIn(ExperimentalForeignApi::class)
actual val OnyxFloat.compileFunc: (Any?) -> Any?
    get() = {
        LLVMConstReal(LLVMFloatType(), held.toDouble())
    }