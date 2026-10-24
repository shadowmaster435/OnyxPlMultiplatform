package net.shadowmaster435.onyx.lang.enums.jvm

import net.shadowmaster435.onyx.lang.enums.jvm.JVMApplicability.*

enum class JVMAccessFlag(val flag: Int, val applicability: Set<JVMApplicability>) {
    PACKAGE_PRIVATE(0x0000, setOf(CLASS, FIELD, METHOD)),
    PUBLIC(0x0001, setOf(CLASS, FIELD, METHOD)),
    PRIVATE(0x0002, setOf(CLASS, FIELD, METHOD)),
    PROTECTED(0x0004, setOf(CLASS, FIELD, METHOD)),
    STATIC(0x0008, setOf(FIELD, METHOD)),
    FINAL(0x0010, setOf(CLASS, FIELD, METHOD, PARAMETER)),
    SUPER(0x0020, setOf(CLASS)),
    SYNCHRONIZED(0x0020, setOf(METHOD)),
    VOLATILE(0x0040, setOf(FIELD)),
    BRIDGE(0x0040, setOf(METHOD)),
    VARARGS(0x0080, setOf(METHOD)),
    TRANSIENT(0x0080, setOf(FIELD)),
    NATIVE(0x0100, setOf(METHOD)),
    ABSTRACT(0x0400, setOf(CLASS, METHOD)),
    INTERFACE(0x0200, setOf(CLASS)),
    SYNTHETIC(0x1000, setOf(CLASS, FIELD, METHOD)),
    ANNOTATION(0x2000, setOf(CLASS)),
    ENUM(0x4000, setOf(CLASS, FIELD)),
}