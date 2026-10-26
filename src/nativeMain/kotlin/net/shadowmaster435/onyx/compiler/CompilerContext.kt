package net.shadowmaster435.onyx.compiler

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.lang.enums.scope.OnyxScopePoint
import net.shadowmaster435.onyx.lang.enums.scope.OnyxScopeType
import kotlin.math.max

@OptIn(ExperimentalForeignApi::class)
value class CompilerContext(val params: List<Any?>) {
    val scopePoint: OnyxScopePoint get() = params[0] as OnyxScopePoint
    val scopeType: OnyxScopeType get() = params[1] as OnyxScopeType
    val compiler: OnyxNativeCompiler get() = params[2] as OnyxNativeCompiler
    operator fun get(index: Int) = params.getOrNull(max(3, index))
}