package net.shadowmaster435.onyx.lang.enums.modifier

import net.shadowmaster435.onyx.exception.UnknownException
import net.shadowmaster435.onyx.ext.containsAny
import net.shadowmaster435.onyx.ext.hasOnlyOne
import net.shadowmaster435.onyx.ext.isOnlyOne
import net.shadowmaster435.onyx.lang.enums.jvm.JVMAccessFlag
import net.shadowmaster435.onyx.lang.enums.keyword.OnyxKeywordType
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate
import kotlin.enums.enumEntries

enum class OnyxModifierType(
    val subtypes: HashSet<Subtype>, val string: String,
    val jvmFlags: List<JVMAccessFlag>,
    val canChainWith: OnyxModifierType.(OnyxModifierType) -> Boolean = default,
    val requiresImport: String? = null
) {
    PUBLIC(Subtype.ACCESS, "public", JVMAccessFlag.PUBLIC),
    PROTECTED(Subtype.ACCESS, "protected", JVMAccessFlag.PROTECTED),
    PRIVATE(Subtype.ACCESS, "private", JVMAccessFlag.PRIVATE),
    FILE(Subtype.ACCESS, "file", JVMAccessFlag.SYNTHETIC),
    PACKAGE(Subtype.ACCESS, "package", JVMAccessFlag.PACKAGE_PRIVATE),
    INTERNAL(Subtype.ACCESS, "internal", JVMAccessFlag.SYNTHETIC),
    FINAL(Subtype.FINALITY, "final", JVMAccessFlag.FINAL),
    CONST(hashSetOf(Subtype.FINALITY, Subtype.META), "const", listOf(JVMAccessFlag.STATIC, JVMAccessFlag.FINAL)),
    ABSTRACT(Subtype.EXTENDABILITY, "abstract", JVMAccessFlag.ABSTRACT),
    SEALED(Subtype.EXTENDABILITY, "sealed"),
    OPEN(Subtype.EXTENDABILITY, "open"),
    OVERRIDE(Subtype.EXTENDABILITY, "override"),
    META(Subtype.META, "meta"),
    COMPANION(Subtype.OTHER, "companion", {it != this}),
    ANNOTATION(Subtype.OTHER, "annotation", JVMAccessFlag.ANNOTATION, {it.subtypes.hasOnlyOne(Subtype.EXTENDABILITY)}),
    OPERATOR(Subtype.OTHER, "operator", {it != this}),
    VARARG(Subtype.OTHER, "vararg", JVMAccessFlag.VARARGS, {false}),
    TAILREC(Subtype.OTHER, "tailrec", {it != this}),
    ENUM(Subtype.OTHER, "enum", JVMAccessFlag.ENUM, {it.subtypes.isOnlyOne(Subtype.ACCESS)}),
    VALUE(Subtype.OTHER, "value", {it.subtypes.isOnlyOne(Subtype.ACCESS)}),
    DATA(Subtype.OTHER, "data", {
        it.subtypes.isOnlyOne(Subtype.ACCESS) || subtypes.isOnlyOne(Subtype.META)
    }),
    LATEINIT(Subtype.OTHER, "lateinit", {it.subtypes.isOnlyOne(Subtype.ACCESS)}),

    LAZY(Subtype.SUGAR, "lazy", {true}, "delegate_sugar"),

    ;
    val hasJVMFlag = jvmFlags.isNotEmpty()

    constructor(
        subtypes: HashSet<Subtype>, string: String,
        canChainWith: OnyxModifierType.(OnyxModifierType) -> Boolean = default,
        requiresImport: String? = null
    ) : this(subtypes, string, listOf(), canChainWith, requiresImport)

    constructor(subtype: Subtype, string: String, canChainWith: OnyxModifierType.(OnyxModifierType) -> Boolean = default, requiresImport: String? = null) :
            this(hashSetOf(subtype), string, listOf(), canChainWith, requiresImport)
    constructor(
        subtypes: HashSet<Subtype>, string: String,
        jvmFlag: JVMAccessFlag,
        canChainWith: OnyxModifierType.(OnyxModifierType) -> Boolean = default,
        requiresImport: String? = null
    ) : this(subtypes, string, listOf(jvmFlag), canChainWith, requiresImport)

    constructor(subtype: Subtype, string: String, jvmFlag: JVMAccessFlag, canChainWith: OnyxModifierType.(OnyxModifierType) -> Boolean = default, requiresImport: String? = null) :
            this(hashSetOf(subtype), string, listOf(jvmFlag), canChainWith, requiresImport)
    constructor(subtype: Subtype, string: String, jvmFlags: List<JVMAccessFlag>, canChainWith: OnyxModifierType.(OnyxModifierType) -> Boolean = default, requiresImport: String? = null) :
            this(hashSetOf(subtype), string, jvmFlags, canChainWith, requiresImport)
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
        val template = ParsinatorTemplate {
            repeating(false, true) {
                chain(false) {
                    whitespace(false)
                    branch(true) {
                        for (keyword in enumEntries<OnyxKeywordType>()) token(true, keyword.string)
                    }
                    whitespace(false)
                }
            } makes {
                @Suppress("UNCHECKED_CAST")
                it as List<List<*>>
                buildList {
                    for (repeatEntry in it) {
                        for (chainEntry in repeatEntry)
                            if (chainEntry is String) {
                                add(
                                    OnyxModifierType.entries.firstOrNull {
                                        e -> e.name == chainEntry
                                    } ?: throw UnknownException("Unknown modifier type '$chainEntry'")
                                )
                                break
                            }
                        }
                    }
                }
            }
    }

}
