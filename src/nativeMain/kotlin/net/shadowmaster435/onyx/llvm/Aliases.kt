@file:OptIn(ExperimentalForeignApi::class)
package net.shadowmaster435.onyx.llvm

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CPointerVarOf
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.ExperimentalForeignApi

typealias LLVMOpaqueType = cnames.structs.LLVMOpaqueType
typealias LLVMOpaqueValue = cnames.structs.LLVMOpaqueValue
typealias LLVMOpaqueModule = cnames.structs.LLVMOpaqueModule
typealias CPtrList<T> = CValuesRef<CPointerVarOf<CPointer<T>>>
