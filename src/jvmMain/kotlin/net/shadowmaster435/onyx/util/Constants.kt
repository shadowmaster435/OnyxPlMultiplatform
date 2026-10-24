package net.shadowmaster435.onyx.util

import net.shadowmaster435.onyx.exception.ImpossibleException
import org.objectweb.asm.Opcodes


fun isConstOpcode(number: Number) = !(number is Float || number is Double) && number.toLong() in -1..5
fun getConstOpcode(number: Number) = when(number.toLong()) {
    -1L -> Opcodes.ICONST_M1
    0L -> Opcodes.ICONST_0
    1L -> Opcodes.ICONST_1
    2L -> Opcodes.ICONST_2
    3L -> Opcodes.ICONST_3
    4L -> Opcodes.ICONST_4
    5L -> Opcodes.ICONST_5
    else -> null
}
