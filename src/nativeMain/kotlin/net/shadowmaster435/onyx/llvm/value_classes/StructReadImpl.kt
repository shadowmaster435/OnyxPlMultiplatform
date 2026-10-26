package net.shadowmaster435.onyx.llvm.value_classes

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.compiler.OnyxNativeCompiler
import net.shadowmaster435.onyx.llvm.LLVMBuildLoad2
import net.shadowmaster435.onyx.llvm.LLVMBuildStructGEP2
import net.shadowmaster435.onyx.llvm.LLVMValueRef
import net.shadowmaster435.onyx.util.orThrow

@OptIn(ExperimentalForeignApi::class)
internal sealed interface StructReadImpl : StructFieldTypeImpl {
    val instance: LLVMValueRef

    fun read(compiler: OnyxNativeCompiler, index: Int): LLVMValueRef {
        val ptr: LLVMValueRef = orThrow(
            LLVMBuildStructGEP2(
                compiler.builder,
                type,
                instance,
                index.toUInt(),
                "read$index"
            )
        )
        return orThrow(
            LLVMBuildLoad2(
                compiler.builder,
                getFieldType(index),
                ptr,
                "read"
            )
        )
    }
}