package net.shadowmaster435.onyx.compiler

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCValues
import net.shadowmaster435.onyx.llvm.*
import net.shadowmaster435.onyx.llvm.value_classes.GlobalStruct
import net.shadowmaster435.onyx.llvm.value_classes.StructType
import net.shadowmaster435.onyx.util.orThrow

@ExperimentalForeignApi
class OnyxNativeCompiler {
    private var actualModule: LLVMModuleRef? = null
    private var actualBuilder: LLVMBuilderRef? = null
    private var actualCtx: LLVMContextRef? = null
    internal val module get() = actualModule ?: throw notCompiling
    internal val builder get() = actualBuilder ?: throw notCompiling
    internal val ctx get() = actualCtx ?: throw notCompiling



    fun compile(moduleName: String, init: OnyxNativeCompiler.() -> Unit) {
        actualModule = LLVMModuleCreateWithName(moduleName)
        actualBuilder = LLVMCreateBuilder()
        actualCtx = LLVMContextCreate()
        init()

        LLVMDisposeModule(module)
        LLVMDisposeBuilder(actualBuilder)
        LLVMContextDispose(actualCtx)
        actualCtx = null
        actualBuilder = null
        actualModule = null
    }

    //region Util Funcs
    fun str(v: String) = orThrow(LLVMConstStringInContext(ctx,v, v.length.toUInt(), 0))
    fun strType(length: Int) = orThrow(LLVMArrayType(LLVMInt8TypeInContext(ctx), length.toUInt()))
    fun boolType() = orThrow(LLVMInt1Type())
    fun byteType() = orThrow(LLVMInt8Type())
    fun shortType() = orThrow(LLVMInt16Type())
    fun intType() = orThrow(LLVMInt32Type())
    fun longType() = orThrow(LLVMInt64Type())
    fun int(v: Int) = orThrow(LLVMConstInt(intType(), v.toULong(), 1))
    fun uint(v: UInt) = orThrow(LLVMConstInt(intType(), v.toULong(), 0))
    fun long(v: Int) = orThrow(LLVMConstInt(longType(), v.toULong(), 1))
    fun uLong(v: UInt) = orThrow(LLVMConstInt(longType(), v.toULong(), 0))
    fun byte(v: Int) = orThrow(LLVMConstInt(byteType(), v.toULong(), 1))
    fun ubyte(v: UInt) = orThrow(LLVMConstInt(byteType(), v.toULong(), 0))
    fun short(v: Int) = orThrow(LLVMConstInt(shortType(), v.toULong(), 1))
    fun ushort(v: UInt) = orThrow(LLVMConstInt(shortType(), v.toULong(), 0))
    fun bool(v: Boolean) = orThrow(LLVMConstInt(boolType(), (if (v) 1uL else 0uL), 0))


    fun globalConstStruct(name: String, types: List<LLVMTypeRef>, values: List<LLVMValueRef>): GlobalStruct {
        val structType = LLVMStructCreateNamed(ctx, "_$name")
        LLVMStructSetBody(structType, types.toCValues(), types.size.toUInt(), 0)
        val initializer = LLVMConstNamedStruct(structType, values.toCValues(), values.size.toUInt())
        val struct = LLVMAddGlobal(module, structType, name)
        LLVMSetInitializer(struct, initializer)
        LLVMSetLinkage(struct, LLVMLinkage.LLVMInternalLinkage)
        return GlobalStruct(
            StructType(Triple(orThrow(structType), name, types)) to orThrow(struct)
        )
    }

    fun namedStructType(typeName: String, vararg types: LLVMTypeRef) = namedStructType(typeName, types.toList())

    fun namedStructType(typeName: String, types: List<LLVMTypeRef>): StructType {
        val structType = LLVMStructCreateNamed(ctx, typeName)
        LLVMStructSetBody(structType, types.toCValues(), types.size.toUInt(), 0)
        return StructType(Triple(orThrow(structType), typeName, types))
    }

    fun structType(vararg types: LLVMTypeRef): StructType {
        val types = types.toList()
        val structType = LLVMStructTypeInContext(ctx, types.toCValues(), types.size.toUInt(), 0)
        return StructType(Triple(orThrow(structType), null, types))
    }
    fun globalConstString(name: String, value: String): LLVMValueRef {
        val length = value.length.toUInt()
        val arrayType = LLVMArrayType(LLVMInt8TypeInContext(ctx), length)
        val globalString = LLVMAddGlobal(module, arrayType, ".$name")
        val constString: LLVMValueRef? = LLVMConstStringInContext(ctx, value, length, 0)
        LLVMSetInitializer(globalString, constString)
        LLVMSetGlobalConstant(globalString, 1);
        LLVMSetLinkage(globalString, LLVMLinkage.LLVMPrivateLinkage)
        LLVMSetUnnamedAddress(globalString, LLVMUnnamedAddr.LLVMGlobalUnnamedAddr)
        LLVMSetAlignment(globalString, 1u)
        val zeroIndex = LLVMConstInt(LLVMInt64TypeInContext(ctx), 0u, 1)
        val indices = listOf(zeroIndex, zeroIndex).toCValues()
        return orThrow(
            LLVMBuildInBoundsGEP2(builder, arrayType, globalString, indices, 2u, name)
        )
    }
    //endregion

    companion object {
        private val notCompiling get() = NullPointerException("Not compiling")

        const val str = "foobar"

    }
}