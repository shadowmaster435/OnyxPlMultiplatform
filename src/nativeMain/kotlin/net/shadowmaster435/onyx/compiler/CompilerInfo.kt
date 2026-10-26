package net.shadowmaster435.onyx.compiler

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.llvm.LLVMContextRef

class CompilerInfo @OptIn(ExperimentalForeignApi::class) constructor(
    val ctx: LLVMContextRef
) {
}