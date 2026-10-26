package net.shadowmaster435.onyx.llvm

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.*
import net.shadowmaster435.onyx.exception.ImpossibleException

@OptIn(ExperimentalForeignApi::class)
object ThreadHelper {

    private val module = threadModule(true)
    private lateinit var threadHandlePtrType: CPointer<LLVMOpaqueType>
    private lateinit var threadAttributePtrType: CPointer<LLVMOpaqueType>

    private lateinit var threadCreateType: CPointer<LLVMOpaqueType>
    private lateinit var threadCreateFunc: CPointer<LLVMOpaqueValue>
    private lateinit var workerFunctionType: CPointer<LLVMOpaqueType>
    private lateinit var workerFunction: CPointer<LLVMOpaqueValue>
    private lateinit var threadParamType: CPtrList<LLVMOpaqueType>


    private fun <T> check(v: T?): T = v ?: throw ImpossibleException()

    private fun threadModule(dumpIr: Boolean = false, addressSpace: UInt = 0u): LLVMModuleRef {
        val module = LLVMModuleCreateWithName("onyx_threading")
        val builder = LLVMCreateBuilder()

        val int8PtrType = LLVMPointerType(LLVMInt8Type(), addressSpace)
        val int32Type = LLVMInt32Type()

        threadHandlePtrType = check(LLVMPointerType(LLVMInt8Type(), addressSpace))
        threadAttributePtrType = check(LLVMPointerType(LLVMInt8Type(), addressSpace))

        val threadWorkerParams: CPtrList<LLVMOpaqueType> = buildList {
            add(int8PtrType)
        }.toCValues()
        workerFunctionType = check(LLVMFunctionType(int8PtrType, threadWorkerParams, 1u, 0))
        workerFunction = check(LLVMAddFunction(module, "worker", workerFunctionType))

        threadParamType = listOf(
            threadHandlePtrType,
            threadAttributePtrType,
            LLVMPointerType(workerFunctionType, 0u),  // worker function pointer
            int8PtrType // argument pointer passed to worker
        ).toCValues()
        threadCreateType = check(
            LLVMFunctionType(int32Type, threadParamType, 4u, 0)
        )
        threadCreateFunc = check(
            LLVMAddFunction(module, "pthread_create", threadCreateType)
        )


        if (dumpIr) LLVMDumpModule(module)
        LLVMDisposeBuilder(builder)
        LLVMDisposeModule(module)
        return check(module)
    }

}