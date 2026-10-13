package net.shadowmaster435.onyx.lang.enums.keyword

import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate
import kotlin.enums.enumEntries
import kotlin.jvm.JvmSynthetic

enum class OnyxKeywordType(val string: String, val subtype: SubType, val requiresImport: String? = null) {
    IF("if", SubType.FLOW_CONTROL),
    ELSE("else", SubType.FLOW_CONTROL),
    WHEN("when", SubType.FLOW_CONTROL),
    WHILE("while", SubType.FLOW_CONTROL),
    DO("do", SubType.FLOW_CONTROL),
    TRY("try", SubType.FLOW_CONTROL),
    CATCH("catch", SubType.FLOW_CONTROL),
    RETURN("return", SubType.FLOW_CONTROL),
    BREAK("break", SubType.FLOW_CONTROL),
    CONTINUE("continue", SubType.FLOW_CONTROL),
    FINALLY("finally", SubType.FLOW_CONTROL),
    THROW("throw", SubType.FLOW_CONTROL),
    TRUE("true", SubType.CONSTANT),
    FALSE("false", SubType.CONSTANT),
    NULL("null", SubType.CONSTANT),
    SUPER("super", SubType.CONSTANT),
    THIS("this", SubType.CONSTANT),
    VAL("val", SubType.DEFINITION),
    VAR("var", SubType.DEFINITION),
    FUN("fun", SubType.DEFINITION),
    IMPORT("import", SubType.DEFINITION),
    CLASS("class", SubType.DEFINITION),
    OBJECT("object", SubType.DEFINITION),
    INTERFACE("interface", SubType.DEFINITION),
    IN("in", SubType.COVARIANCE),
    OUT("out", SubType.COVARIANCE),
    WHERE("where", SubType.COVARIANCE),
    PHI("phi", SubType.INLINING),
    INLINE("inline", SubType.INLINING),
    INFIX("infix", SubType.INLINING),
    REIFIED("reified", SubType.INLINING),
    NOINLINE("noinline", SubType.INLINING),
    CROSSINLINE("crossinline", SubType.INLINING),
    TYPEALIAS("typealias", SubType.INLINING),
    USING("using", SubType.INLINING),
    CONTEXT("typealias", SubType.INLINING, "context"),
    IFDEF("ifdef", SubType.INLINING, "preprocessor"),
    IFNDEF("ifndef", SubType.INLINING, "preprocessor"),
    ELIF("elif", SubType.INLINING, "preprocessor"),
    DEFINE("define", SubType.INLINING, "preprocessor"),

    BY("by", SubType.OTHER),
    SET("set", SubType.OTHER),
    GET("get", SubType.OTHER),
    IT("it", SubType.OTHER),
    CONSTRUCTOR("constructor", SubType.OTHER),
    EXPECT("constructor", SubType.OTHER, "multiplatform"),
    ACTUAL("constructor", SubType.OTHER, "multiplatform"),
    REPEAT("repeat", SubType.SUGAR, "sugar.flow.loop.repeat"),
    LOOP("repeat", SubType.SUGAR, "sugar.flow.loop.loop"),
    ATTEMPT("attempt", SubType.SUGAR, "sugar.flow.statement.attempt"),


    ;

    enum class SubType {
        FLOW_CONTROL,
        CONSTANT,
        DEFINITION,
        COVARIANCE,
        INLINING,
        OTHER,
        SUGAR,
    }
    companion object {
        @JvmSynthetic internal fun template(make: (Any?) -> Any?) = ParsinatorTemplate {
            branch(true) {
                for (keyword in enumEntries<OnyxKeywordType>()) token(true, keyword.string)
            } makes make
        }

    }
}