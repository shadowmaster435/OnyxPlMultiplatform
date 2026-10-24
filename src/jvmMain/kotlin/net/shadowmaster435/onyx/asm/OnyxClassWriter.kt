package net.shadowmaster435.onyx.asm

import net.shadowmaster435.onyx.lang.enums.jvm.JVMAccessFlag
import net.shadowmaster435.onyx.lang.field.OnyxFieldDescriptor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

internal class OnyxClassWriter : ClassWriter(COMPUTE_FRAMES) {

    internal class OnyxMethodWriter(
        val methodName: String,
        parent: OnyxMethodWriter? = null
    ): MethodVisitor(
        Opcodes.ASM9, parent
    ) {


        fun newLocal(field: OnyxFieldDescriptor) {
            OnyxLocalSorter(this, field).visitCode()
        }


    }




    companion object {
        internal fun List<JVMAccessFlag>.combine(): Int {
            var flags = 0
            forEach {
                flags = flags or it.flag
            }
            return flags
        }
    }

}