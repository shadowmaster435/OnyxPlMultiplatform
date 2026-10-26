package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.llvm.LLVMConstInt
import net.shadowmaster435.onyx.llvm.LLVMInt8Type

@OptIn(ExperimentalForeignApi::class)
actual val OnyxByte.compileFunc: (Any?) -> Any?
    get() = {
        LLVMConstInt(LLVMInt8Type(), held.toULong(), 1)
    }