package net.shadowmaster435.onyx.lang.enums.modifier

import net.shadowmaster435.onyx.ext.containsAny
import net.shadowmaster435.onyx.ext.hasOnlyOne
import net.shadowmaster435.onyx.ext.isOnlyOne
import net.shadowmaster435.onyx.lang.enums.keyword.OnyxKeywordType
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate
import kotlin.enums.enumEntries
import kotlin.jvm.JvmSynthetic

enum class OnyxModifierType(val subtypes: HashSet<Subtype>, val string: String, val canChainWith: OnyxModifierType.(OnyxModifierType) -> Boolean = default, val requiresImport: String? = null) {
    PUBLIC(Subtype.ACCESS, "public"),
    PROTECTED(Subtype.ACCESS, "protected"),
    PRIVATE(Subtype.ACCESS, "private"),
    FILE(Subtype.ACCESS, "file"),
    PACKAGE(Subtype.ACCESS, "package"),
    INTERNAL(Subtype.ACCESS, "internal"),
    FINAL(Subtype.FINALITY, "final"),
    CONST(hashSetOf(Subtype.FINALITY, Subtype.META), "const"),
    ABSTRACT(Subtype.EXTENDABILITY, "abstract"),
    SEALED(Subtype.EXTENDABILITY, "sealed"),
    OPEN(Subtype.EXTENDABILITY, "open"),
    OVERRIDE(Subtype.EXTENDABILITY, "override"),
    META(Subtype.META, "meta"),
    COMPANION(Subtype.OTHER, "companion", {it != this}),
    ANNOTATION(Subtype.OTHER, "annotation", {it.subtypes.hasOnlyOne(Subtype.EXTENDABILITY)}),
    OPERATOR(Subtype.OTHER, "operator", {it != this}),
    VARARG(Subtype.OTHER, "vararg", {false}),
    TAILREC(Subtype.OTHER, "tailrec", {it != this}),
    ENUM(Subtype.OTHER, "enum", {it.subtypes.isOnlyOne(Subtype.ACCESS)}),
    VALUE(Subtype.OTHER, "value", {it.subtypes.isOnlyOne(Subtype.ACCESS)}),
    DATA(Subtype.OTHER, "data", {
        it.subtypes.isOnlyOne(Subtype.ACCESS) || subtypes.isOnlyOne(Subtype.META)
    }),
    LATEINIT(Subtype.OTHER, "lateinit", {it.subtypes.isOnlyOne(Subtype.ACCESS)}),

    LAZY(Subtype.SUGAR, "lazy", {true}, "delegate_sugar"),

    ;
    constructor(subtype: Subtype, string: String, canChainWith: OnyxModifierType.(OnyxModifierType) -> Boolean = default, requiresImport: String? = null) :
            this(hashSetOf(subtype), string, canChainWith, requiresImport)
    enum class Subtype {
        ACCESS,
        FINALITY,
        EXTENDABILITY,
        META,
        OTHER,
        SUGAR,
    }
    companion object {
        private val default: OnyxModifierType.(OnyxModifierType) -> Boolean = {!subtypes.containsAny(it.subtypes)}
        @JvmSynthetic internal fun template(make: (Any?) -> Any?) = ParsinatorTemplate {
            branch(true) {
                for (keyword in enumEntries<OnyxKeywordType>()) token(true, keyword.string)
            } makes make
        }
    }
}
