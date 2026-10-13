package net.shadowmaster435.onyx.lang.enums.operator

import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate
import kotlin.enums.enumEntries
import kotlin.jvm.JvmSynthetic

enum class OnyxOperator(
    val string: String,
    val type: OnyxOperatorType,
    val precedence: Int,
    val multiPrecedence: Boolean = false,
    val overloadable: Boolean = false

) {
    INFIX("infix", OnyxOperatorType.INFIX, 8),
    USHR_ASSIGN(">>>=", OnyxOperatorType.BINARY, 0, overloadable = true),
    SHR_ASSIGN(">>=", OnyxOperatorType.BINARY, 0, overloadable = true),
    SHL_ASSIGN("=<<", OnyxOperatorType.BINARY, 0, overloadable = true),
    NOT_NULL_ACCESS("!!.", OnyxOperatorType.ACCESS, 14),
    REF_EQUAL("===", OnyxOperatorType.BINARY, 1),
    INCLUSIVE_RANGE("..<", OnyxOperatorType.BINARY, 9, overloadable = true),
    REF_NOT_EQUAL("!==", OnyxOperatorType.BINARY, 1),
    NOT_IN("!in", OnyxOperatorType.BINARY, 6, overloadable = true),
    USHR(">>>", OnyxOperatorType.BINARY, 8, overloadable = true),
    SHR(">>", OnyxOperatorType.BINARY, 8, overloadable = true),
    SHL("<<", OnyxOperatorType.BINARY, 8, overloadable = true),
    XOR_ASSIGN("^=", OnyxOperatorType.BINARY, 0, overloadable = true),
    AND_ASSIGN("&=", OnyxOperatorType.BINARY, 0, overloadable = true),
    OR_ASSIGN("|=", OnyxOperatorType.BINARY, 0, overloadable = true),
    EQUAL("==", OnyxOperatorType.BINARY, 4, overloadable = true),
    NOT_EQUAL("!=", OnyxOperatorType.BINARY, 4, overloadable = true),
    IS_NOT("!is", OnyxOperatorType.BINARY, 6, overloadable = true),
    SAFE_AS("as?", OnyxOperatorType.BINARY, 12),
    AS("as", OnyxOperatorType.BINARY, 12),
    IS("is", OnyxOperatorType.BINARY, 6),
    IN("in", OnyxOperatorType.BINARY, 6, overloadable = true),
    LESS_EQ("<=", OnyxOperatorType.BINARY, 5, overloadable = true),
    GREATER_EQ(">=", OnyxOperatorType.BINARY, 5, overloadable = true),
    RANGE("..", OnyxOperatorType.BINARY, 9, overloadable = true),
    ADD_ASSIGN("+=", OnyxOperatorType.BINARY, 0, overloadable = true),
    SUB_ASSIGN("-=", OnyxOperatorType.BINARY, 0, overloadable = true),
    MUL_ASSIGN("*=", OnyxOperatorType.BINARY, 0, overloadable = true),
    DIV_ASSIGN("/=", OnyxOperatorType.BINARY, 0, overloadable = true),
    MOD_ASSIGN("%=", OnyxOperatorType.BINARY, 0, overloadable = true),
    NULLABLE_ACCESS("?.", OnyxOperatorType.ACCESS, 14),
    ELVIS("?:", OnyxOperatorType.BINARY, 7),
    DOUBLE_COLON("::", OnyxOperatorType.BINARY_OR_LEFT, 13),
    INC("++",OnyxOperatorType.UNARY, 13, overloadable = true),
    DEC("--", OnyxOperatorType.UNARY, 13, overloadable = true),
    AND("&&", OnyxOperatorType.BINARY, 3, overloadable = true),
    OR("||", OnyxOperatorType.BINARY, 2, overloadable = true),
    NOT_NULL("!!",OnyxOperatorType.UNARY_LEFT, 13, overloadable = true),
    ACCESS(".", OnyxOperatorType.ACCESS, 14),
    PLUS("+", OnyxOperatorType.BINARY_OR_LEFT, 10, overloadable = true),
    MINUS("-", OnyxOperatorType.BINARY_OR_LEFT, 10, overloadable = true),
    ASTERISK("*", OnyxOperatorType.BINARY_OR_LEFT, (11 shl 16) or 1, true, true),
    FWD_SLASH("/", OnyxOperatorType.BINARY, 11, overloadable = true),
    PERCENT("%", OnyxOperatorType.BINARY, 11, overloadable = true),
    CARET("^", OnyxOperatorType.BINARY, 8, overloadable = true),
    TILDE("~", OnyxOperatorType.UNARY_LEFT, 14, overloadable = true),
    ASSIGN("=", OnyxOperatorType.BINARY, 0),
    COLON(":", OnyxOperatorType.BINARY_OR_LEFT, 12, overloadable = true),
    AMPERSAND("&", OnyxOperatorType.BINARY_OR_LEFT, 8, overloadable = true),
    VERTICAL_BAR("|", OnyxOperatorType.BINARY, 8, overloadable = true),
    DOLLAR_SIGN("$", OnyxOperatorType.BINARY_OR_LEFT, (13 shl 16) or 14, true, overloadable = true),
    AT_SIGN("@", OnyxOperatorType.UNARY, 14),
    EXCLAMATION_POINT("!", OnyxOperatorType.UNARY_LEFT, 14, overloadable = true),
    QUESTION_MARK("?", OnyxOperatorType.UNARY_LEFT, 14, overloadable = true),
    LEFT_ARROW("<", OnyxOperatorType.BINARY, 5, overloadable = true),
    RIGHT_ARROW(">", OnyxOperatorType.BINARY, 5, overloadable = true);
    val firstPrecedence get() = precedence and 0xff
    val secondPrecedence get() = (precedence and 0xff00) shr 16

    companion object {
        @JvmSynthetic internal fun binaryTemplate(make: (Any?) -> Any?) = ParsinatorTemplate {
            branch(true) {
                for (op in enumEntries<OnyxOperator>()) if (op.type == OnyxOperatorType.BINARY)
                    token(true, op.string)
            } makes make
        }
        @JvmSynthetic internal fun unaryTemplate(make: (Any?) -> Any?) = ParsinatorTemplate {
            branch(true) {
                for (op in enumEntries<OnyxOperator>()) if (op.type == OnyxOperatorType.UNARY)
                    token(true, op.string)
            } makes make
        }
        @JvmSynthetic internal fun unaryLeftTemplate(make: (Any?) -> Any?) = ParsinatorTemplate {
            branch(true) {
                for (op in enumEntries<OnyxOperator>()) if (op.type == OnyxOperatorType.UNARY_LEFT)
                    token(true, op.string)
            } makes make
        }
        @JvmSynthetic internal fun binaryLeftTemplate(make: (Any?) -> Any?) = ParsinatorTemplate {
            branch(true) {
                for (op in enumEntries<OnyxOperator>()) if (op.type == OnyxOperatorType.BINARY_OR_LEFT)
                    token(true, op.string)
            } makes make
        }
        @JvmSynthetic internal fun accessTemplate(make: (Any?) -> Any?) = ParsinatorTemplate {
            branch(true) {
                for (op in enumEntries<OnyxOperator>()) if (op.type == OnyxOperatorType.ACCESS)
                    token(true, op.string)
            } makes make
        }
    }


}