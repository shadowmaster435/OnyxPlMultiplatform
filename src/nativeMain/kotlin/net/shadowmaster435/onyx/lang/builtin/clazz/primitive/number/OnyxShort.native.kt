package net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.llvm.LLVMConstInt
import net.shadowmaster435.onyx.llvm.LLVMInt16Type

@OptIn(ExperimentalForeignApi::class)
actual val OnyxShort.compileFunc: (Any?) -> Any?
    get() = {
        LLVMConstInt(LLVMInt16Type(), held.toULong(), 1)
    }