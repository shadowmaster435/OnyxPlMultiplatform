package net.shadowmaster435.onyx.util

import net.shadowmaster435.onyx.lang.impl.jvm.JVMDescribable

enum class JVMPrimitiveType(override val descriptor: String) : JVMDescribable {
    BOOL("Z"),
    BYTE("B"),
    SHORT("S"),
    INT("I"),
    LONG("J"),
    FLOAT("F"),
    DOUBLE("D"),
    VOID("V"),
    OBJECT("java/lang/Object"),
    STRING("java/lang/String"),
}