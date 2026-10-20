package net.shadowmaster435.onyx.lang.enums.code

enum class OnyxNullability(
    val nullable: Boolean,
    val isInput: Boolean = false,
) {
    INPUT(true, true),
    NULLABLE(true),
    NON_NULLABLE(false),
    TRUE_NON_NULLABLE(false)
}