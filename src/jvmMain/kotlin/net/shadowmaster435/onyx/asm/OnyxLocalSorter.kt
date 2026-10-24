package net.shadowmaster435.onyx.asm

import net.shadowmaster435.onyx.asm.OnyxClassWriter.Companion.combine
import net.shadowmaster435.onyx.asm.OnyxClassWriter.OnyxMethodWriter
import net.shadowmaster435.onyx.lang.field.OnyxFieldDescriptor
import net.shadowmaster435.onyx.lang.util.toJVMAccessFlags
import net.shadowmaster435.onyx.util.UnbuiltLocal
import org.objectweb.asm.Type
import org.objectweb.asm.commons.LocalVariablesSorter

internal class OnyxLocalSorter(
    val writer: OnyxMethodWriter,
    val field: OnyxFieldDescriptor,
) : LocalVariablesSorter(
    field.modifiers.toJVMAccessFlags().combine(),
    field.descriptor, writer
) {
    override fun visitCode() {
        super.visitCode()
        val index = newLocal(Type.getType(field.descriptor))
        field.compile(UnbuiltLocal(index, field, this))
    }
}