package net.shadowmaster435.onyx.lang.enums.generics

enum class OnyxGenericVariance(val erasable: Boolean) {
    IN(true),
    OUT(true),
    REIFIED(false),
    PHI(false);
}