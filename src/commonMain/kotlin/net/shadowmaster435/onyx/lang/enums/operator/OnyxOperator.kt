package net.shadowmaster435.onyx.lang.enums.operator

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.function.OnyxExternalFunctionDescriptor
import net.shadowmaster435.onyx.lang.function.OnyxTuple
import net.shadowmaster435.onyx.lang.function.OnyxTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate
import net.shadowmaster435.parsinator.util.ParsinatorContext
import kotlin.enums.enumEntries
import kotlin.jvm.JvmSynthetic

enum class OnyxOperator(
    val string: String,
    val type: OnyxOperatorType,
    val precedence: Int,
    val multiPrecedence: Boolean = false,
    val overloadableName: String? = null,
    val exclude: Boolean = false

) {
    INFIX("", OnyxOperatorType.INFIX, 8, exclude = true),
    GET("", OnyxOperatorType.SPECIAL, 0, exclude = true, overloadableName = "get"),
    SET("", OnyxOperatorType.SPECIAL, 0, exclude = true, overloadableName = "set"),
    INVOKE("", OnyxOperatorType.SPECIAL, 0, exclude = true, overloadableName = "invoke"),
    USHR_ASSIGN(">>>=", OnyxOperatorType.BINARY, 0, overloadableName = "ushrAssign"),
    SHR_ASSIGN(">>=", OnyxOperatorType.BINARY, 0, overloadableName = "shrAssign"),
    SHL_ASSIGN("=<<", OnyxOperatorType.BINARY, 0, overloadableName = "shlAssign"),
    NOT_NULL_ACCESS("!!.", OnyxOperatorType.ACCESS, 14),
    REF_EQUAL("===", OnyxOperatorType.BINARY, 1),
    INCLUSIVE_RANGE("..<", OnyxOperatorType.BINARY, 9, overloadableName = "rangeUntil"),
    REF_NOT_EQUAL("!==", OnyxOperatorType.BINARY, 1),
    NOT_IN("!in", OnyxOperatorType.BINARY, 6, overloadableName = "contains"),
    USHR(">>>", OnyxOperatorType.BINARY, 8, overloadableName = "ushr"),
    SHR(">>", OnyxOperatorType.BINARY, 8, overloadableName = "shr"),
    SHL("<<", OnyxOperatorType.BINARY, 8, overloadableName = "shl"),
    XOR_ASSIGN("^=", OnyxOperatorType.BINARY, 0, overloadableName = "xorAssign"),
    AND_ASSIGN("&=", OnyxOperatorType.BINARY, 0, overloadableName = "andAssign"),
    OR_ASSIGN("|=", OnyxOperatorType.BINARY, 0, overloadableName = "orAssign"),
    EQUAL("==", OnyxOperatorType.BINARY, 4, overloadableName = "equals"),
    NOT_EQUAL("!=", OnyxOperatorType.BINARY, 4, overloadableName = "equals"),
    IS_NOT("!is", OnyxOperatorType.BINARY, 6, overloadableName = "contains"),
    SAFE_AS("as?", OnyxOperatorType.BINARY, 12),
    AS("as", OnyxOperatorType.BINARY, 12),
    IS("is", OnyxOperatorType.BINARY, 6),
    IN("in", OnyxOperatorType.BINARY, 6, overloadableName = "contains"),
    LESS_EQ("<=", OnyxOperatorType.BINARY, 5, overloadableName = "compareTo"),
    GREATER_EQ(">=", OnyxOperatorType.BINARY, 5, overloadableName = "compareTo"),
    RANGE("..", OnyxOperatorType.BINARY, 9, overloadableName = "rangeTo"),
    ADD_ASSIGN("+=", OnyxOperatorType.BINARY, 0, overloadableName = "plusAssign"),
    SUB_ASSIGN("-=", OnyxOperatorType.BINARY, 0, overloadableName = "minusAssign"),
    MUL_ASSIGN("*=", OnyxOperatorType.BINARY, 0, overloadableName = "timesAssign"),
    DIV_ASSIGN("/=", OnyxOperatorType.BINARY, 0, overloadableName = "divAssign"),
    MOD_ASSIGN("%=", OnyxOperatorType.BINARY, 0, overloadableName = "modAssign"),
    NULLABLE_ACCESS("?.", OnyxOperatorType.ACCESS, 14),
    ELVIS("?:", OnyxOperatorType.BINARY, 7),
    DOUBLE_COLON("::", OnyxOperatorType.BINARY_OR_LEFT, 13),
    INC("++",OnyxOperatorType.UNARY, 13, overloadableName = "inc"),
    DEC("--", OnyxOperatorType.UNARY, 13, overloadableName = "dec"),
    AND("&&", OnyxOperatorType.BINARY, 3, overloadableName = "and"),
    OR("||", OnyxOperatorType.BINARY, 2, overloadableName = "or"),
    NOT_NULL("!!",OnyxOperatorType.UNARY_LEFT, 13),
    ACCESS(".", OnyxOperatorType.ACCESS, 14),
    PLUS("+", OnyxOperatorType.BINARY_OR_LEFT, 10, overloadableName = "plus"),
    MINUS("-", OnyxOperatorType.BINARY_OR_LEFT, 10, overloadableName = "minus"),
    ASTERISK("*", OnyxOperatorType.BINARY_OR_LEFT, (11 shl 16) or 1, true, "times|spread"),
    FWD_SLASH("/", OnyxOperatorType.BINARY, 11, overloadableName = "div"),
    PERCENT("%", OnyxOperatorType.BINARY, 11, overloadableName = "rem"),
    CARET("^", OnyxOperatorType.BINARY, 8, overloadableName = "xor"),
    TILDE("~", OnyxOperatorType.UNARY_LEFT, 14, overloadableName = "inv"),
    ASSIGN("=", OnyxOperatorType.BINARY, 0),
    COLON(":", OnyxOperatorType.BINARY_OR_LEFT, 12),
    AMPERSAND("&", OnyxOperatorType.BINARY_OR_LEFT, 8, overloadableName = "bitAnd"),
    VERTICAL_BAR("|", OnyxOperatorType.BINARY, 8, overloadableName = "bitOr"),
    DOLLAR_SIGN("$", OnyxOperatorType.BINARY_OR_LEFT, (13 shl 16) or 14, true, overloadableName = "dollarSign"),
    AT_SIGN("@", OnyxOperatorType.UNARY, 14),
    EXCLAMATION_POINT("!", OnyxOperatorType.UNARY_LEFT, 14, overloadableName = "not"),
    QUESTION_MARK("?", OnyxOperatorType.UNARY_RIGHT, 14, overloadableName = "questionMark"),
    LEFT_ARROW("<", OnyxOperatorType.BINARY, 5, overloadableName = "compareTo"),
    RIGHT_ARROW(">", OnyxOperatorType.BINARY, 5, overloadableName = "compareTo");
    val firstPrecedence get() = precedence and 0xff
    val secondPrecedence get() = (precedence and 0xff00) shr 16
    val overloadable get() = overloadableName != null

    fun externalOverload(
        retType: OnyxClassDescriptor,
        typeA: OnyxClassDescriptor,
        genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
        function: (thisInst: OnyxClassInstance?, list: List<OnyxClassInstance>) -> OnyxClassInstance
    ) = this to OnyxExternalFunctionDescriptor(
        overloadableName ?: throw IllegalArgumentException("Operator $this is not overloadable"),
        OnyxTuples(
            OnyxTuple(
                typeA,
                "value",
                false, false
            )
        ), listOf(OnyxModifierType.PUBLIC, OnyxModifierType.OPEN), genericTuples, retType, function
    )
    fun externalOverload(
        retType: OnyxClassDescriptor,
        typeA: OnyxClassDescriptor,
        typeB: OnyxClassDescriptor,
        genericTuples: OnyxGenericTuples = OnyxGenericTuples.empty,
        function: (thisInst: OnyxClassInstance?, list: List<OnyxClassInstance>) -> OnyxClassInstance
    ) = this to OnyxExternalFunctionDescriptor(
        overloadableName ?: throw IllegalArgumentException("Operator $this is not overloadable"),
        OnyxTuples(
            OnyxTuple(
                typeA,
                "a",
                false, false
            ),
            OnyxTuple(
                typeB,
                "b",
                false, false
            )
        ), listOf(OnyxModifierType.PUBLIC, OnyxModifierType.OPEN), genericTuples, retType, function
    )


    companion object {
        @JvmSynthetic internal fun binaryTemplate(make: ParsinatorContext.(Any?) -> Any?) = ParsinatorTemplate {
            branch(true) {
                for (op in enumEntries<OnyxOperator>()) if (op.type == OnyxOperatorType.BINARY)
                    token(true, op.string)
            } makes make
        }
        @JvmSynthetic internal fun unaryTemplate(make: ParsinatorContext.(Any?) -> Any?) = ParsinatorTemplate {
            branch(true) {
                for (op in enumEntries<OnyxOperator>()) if (op.type == OnyxOperatorType.UNARY)
                    token(true, op.string)
            } makes make
        }
        @JvmSynthetic internal fun unaryLeftTemplate(make: ParsinatorContext.(Any?) -> Any?) = ParsinatorTemplate {
            branch(true) {
                for (op in enumEntries<OnyxOperator>()) if (op.type == OnyxOperatorType.UNARY_LEFT)
                    token(true, op.string)
            } makes make
        }
        @JvmSynthetic internal fun unaryRightTemplate(make: ParsinatorContext.(Any?) -> Any?) = ParsinatorTemplate {
            branch(true) {
                for (op in enumEntries<OnyxOperator>()) if (op.type == OnyxOperatorType.UNARY_RIGHT)
                    token(true, op.string)
            } makes make
        }
        @JvmSynthetic internal fun binaryLeftTemplate(make: ParsinatorContext.(Any?) -> Any?) = ParsinatorTemplate {
            branch(true) {
                for (op in enumEntries<OnyxOperator>()) if (op.type == OnyxOperatorType.BINARY_OR_LEFT)
                    token(true, op.string)
            } makes make
        }
        @JvmSynthetic internal fun accessTemplate(make: ParsinatorContext.(Any?) -> Any?) = ParsinatorTemplate {
            branch(true) {
                for (op in enumEntries<OnyxOperator>()) if (op.type == OnyxOperatorType.ACCESS)
                    token(true, op.string)
            } makes make
        }
    }


}