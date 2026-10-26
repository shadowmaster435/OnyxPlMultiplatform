package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.llvm.LLVMConstInt
import net.shadowmaster435.onyx.llvm.LLVMInt32Type

@OptIn(ExperimentalForeignApi::class)
actual val OnyxInt.compileFunc: (Any?) -> Any?
    get() = {
        LLVMConstInt(LLVMInt32Type(), held.toULong(), 1)
    }