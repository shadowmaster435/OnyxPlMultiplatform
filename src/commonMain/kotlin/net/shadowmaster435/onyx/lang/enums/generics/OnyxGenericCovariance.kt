package net.shadowmaster435.onyx.lang.enums.generics

enum class OnyxGenericCovariance(val erasable: Boolean, val string: String) {
    NONE(true, "none"),
    IN(true, "in"),
    OUT(true, "out"),
    REIFIED(false, "reified"),
    PHI(false, "phi");
}