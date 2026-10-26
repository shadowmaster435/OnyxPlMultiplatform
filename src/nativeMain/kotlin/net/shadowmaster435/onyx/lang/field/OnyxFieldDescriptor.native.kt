package net.shadowmaster435.onyx.lang.field

import kotlinx.cinterop.ExperimentalForeignApi
import net.shadowmaster435.onyx.compiler.CompilerContext
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.util.list
import net.shadowmaster435.onyx.llvm.classStructs

@OptIn(ExperimentalForeignApi::class)
actual val OnyxFieldDescriptor.compileFunc: (Any?) -> Any?
    get() = {
        it as CompilerContext
        val compiler = it.compiler
        val data = it[0] as list
        val field = data[0] as OnyxFieldDescriptor
        val holderClass = data.getOrNull(1) as? OnyxClassDescriptor
        val name = ((holderClass?.qualifiedName ?: "") + ".$name-${classType.qualifiedName}")
        val classStruct = classStructs[field.classType]!!
        var modifierFlags = 0u
        OnyxModifierType.entries.forEach { modifierType ->
            modifierFlags = when(modifierType) {
                OnyxModifierType.FINAL -> (modifierFlags or (if (field.isFinal) 1u else 0u)) shl 1
                OnyxModifierType.META -> (modifierFlags or (if (field.isMeta) 1u else 0u)) shl 1
                else -> (modifierFlags or (if (field.modifiers.contains(modifierType)) 1u else 0u)) shl 1
            }
        }
        modifierFlags = (modifierFlags or (if (field.selfInstance != null) 1u else 0u)) shl 1

        it.compiler.globalConstStruct(
            name, listOf(
                compiler.strType(name.length),
                classStruct.type,
                compiler.intType(),

            ), listOf(
                compiler.str(name),
                classStruct.instance,
                compiler.uint(modifierFlags)
            )

        )

    }