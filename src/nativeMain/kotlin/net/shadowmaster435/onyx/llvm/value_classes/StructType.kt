package net.shadowmaster435.onyx.llvm.value_classes

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.compiler.OnyxNativeCompiler
import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.llvm.LLVMBuildAlloca
import net.shadowmaster435.onyx.llvm.LLVMBuildStore
import net.shadowmaster435.onyx.llvm.LLVMBuildStructGEP2
import net.shadowmaster435.onyx.llvm.LLVMTypeRef
import net.shadowmaster435.onyx.llvm.LLVMValueRef
import net.shadowmaster435.onyx.util.orThrow


@OptIn(ExperimentalForeignApi::class)
value class StructType (
    private val v: Triple<LLVMTypeRef, String?, List<LLVMTypeRef>>
) : StructFieldTypeImpl {
    override val type get() = v.first
    val name get() = v.second ?: throw NullPointerException("Struct is unnamed")
    val named get() = v.second != null

    fun instantiate(compiler: OnyxNativeCompiler, vararg values: LLVMValueRef) = instantiate(compiler, values.toList())

    fun instantiate(compiler: OnyxNativeCompiler, values: List<LLVMValueRef?>): LLVMValueRef? {
        val type = v.first
        val inst = LLVMBuildAlloca(
            compiler.builder,
            type,
            "make"
        )
        values.forEachIndexed { index, pointer ->
            if (pointer != null) {
                val pointer = orThrow(
                    LLVMBuildStructGEP2(
                        compiler.builder,
                        type,
                        inst,
                        index.toUInt(),
                        "ptr$index"
                    )
                )
                LLVMBuildStore(compiler.builder, pointer, pointer)
            }
        }
        return inst
    }

    override fun getFieldType(index: Int) = v.third[index]


}