package net.shadowmaster435.onyx.llvm.value_classes

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.llvm.LLVMTypeRef

@OptIn(ExperimentalForeignApi::class)
sealed interface StructFieldTypeImpl {
    val type: LLVMTypeRef
    fun getFieldType(index: Int): LLVMTypeRef
}