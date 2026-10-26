package net.shadowmaster435.onyx.llvm.value_classes

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.compiler.OnyxNativeCompiler
import net.shadowmaster435.onyx.llvm.LLVMBuildStore
import net.shadowmaster435.onyx.llvm.LLVMBuildStructGEP2
import net.shadowmaster435.onyx.llvm.LLVMValueRef
import net.shadowmaster435.onyx.util.orThrow

@OptIn(ExperimentalForeignApi::class)
internal sealed interface StructWriteImpl : StructFieldTypeImpl {
    val instance: LLVMValueRef

    fun write(compiler: OnyxNativeCompiler, index: Int, value: LLVMValueRef): LLVMValueRef {
        val ptr: LLVMValueRef = orThrow(
            LLVMBuildStructGEP2(
                compiler.builder,
                type,
                instance,
                index.toUInt(),
                "write$index"
            )
        )
        return orThrow(
            LLVMBuildStore(
                compiler.builder,
                value,
                ptr,
            )
        )
    }
}