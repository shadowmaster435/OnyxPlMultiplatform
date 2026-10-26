package net.shadowmaster435.onyx.llvm.value_classes

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.llvm.LLVMValueRef

@OptIn(ExperimentalForeignApi::class)
value class GlobalStruct (
    private val v: Pair<StructType, LLVMValueRef>
) : StructReadImpl {
    override val type get() = v.first.type
    override val instance get() = v.second
    override fun getFieldType(index: Int) = v.first.getFieldType(index)
}