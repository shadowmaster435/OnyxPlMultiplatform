package net.shadowmaster435.onyx.lang.generics

import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.exception.NonExistenceException
import net.shadowmaster435.onyx.exception.UninitializedException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxObject
import net.shadowmaster435.onyx.lang.enums.code.OnyxNullability
import net.shadowmaster435.onyx.lang.enums.generics.OnyxGenericCovariance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.function.AbstractOnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.lang.pkg.OnyxPackage
import net.shadowmaster435.onyx.lang.util.*
import net.shadowmaster435.onyx.util.OnyxModifiers
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate
import net.shadowmaster435.parsinator.util.Stack
import kotlin.collections.plus

class OnyxUnfinalizedGenericParam(
    nullability: OnyxNullability,
    val boundsQName: String,
    val genericParams: List<OnyxUnfinalizedGenericParam> = listOf()
): OnyxGenericType(
    "",
    anyDesc,
    OnyxGenericCovariance.NONE,
    nullability
), OnyxFinalizable<OnyxGenericType> {

    //region Boilerplate
    override var genericType: OnyxClassDescriptor?
        set(_) {throw uninitialized}
        get() = throw uninitialized
    override val fieldEntries get() = throw uninitialized
    override val functionEntries get() = throw uninitialized
    override val classEntries get() = throw uninitialized
    override val fields get() = throw uninitialized
    override val functions get() = throw uninitialized
    override val classes get() = throw uninitialized
    override val pkg get() = throw uninitialized
    override val isEnum get() = throw uninitialized
    override val isObject get() = throw uninitialized
    override val name = genericName
    override val equalsFunction get() = throw uninitialized
    override val classType get() = throw uninitialized
    override val companions get() = throw uninitialized
    override val constructors get() = throw uninitialized
    override val genericTuples get() = throw uninitialized
    override val hashcodeFunction get() = throw uninitialized
    override val operatorOverloads get() = throw uninitialized
    override val supertypes get() = throw uninitialized
    override val modifiers get() = throw uninitialized
    override fun getField(name: String) = throw uninitialized
    override fun getField(refIndex: Int) = throw uninitialized
    override fun getFieldRefIndex(name: String) = throw uninitialized
    override fun getOperatorOverload(op: OnyxOperator) = throw uninitialized
    override fun castableTo(other: OnyxClassDescriptor) = throw uninitialized

    override fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        supertypes: List<OnyxClassDescriptor>,
        companion: OnyxObject?,
        fields: LinkedHashMap<String, OnyxFieldImpl>,
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor>,
        classes: LinkedHashMap<String, OnyxClassDescriptor>,
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor>,
        genericTuples: OnyxGenericTuples,
        modifiers: OnyxModifiers
    ) = throw uninitialized

    override fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        companion: OnyxObject?,
        fields: LinkedHashMap<String, OnyxFieldImpl>,
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor>,
        classes: LinkedHashMap<String, OnyxClassDescriptor>,
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor>,
        genericTuples: OnyxGenericTuples,
        modifiers: OnyxModifiers
    ) = throw uninitialized

    override fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        supertype: OnyxClassDescriptor,
        companion: OnyxObject?,
        fields: LinkedHashMap<String, OnyxFieldImpl>,
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor>,
        classes: LinkedHashMap<String, OnyxClassDescriptor>,
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor>,
        genericTuples: OnyxGenericTuples,
        modifiers: OnyxModifiers
    ) = throw uninitialized
    override fun erase() = throw uninitialized
    override fun cast(from: OnyxClassInstance) = throw uninitialized
    override fun safeCast(from: OnyxClassInstance) = throw uninitialized
    override fun castableTo(from: OnyxClassInstance) = throw uninitialized
    //endregion
    override fun createFinal() = OnyxGenericType(
        genericName,
        OnyxPackage.loadClass(boundsQName) ?: throw NonExistenceException("Cannot find class '$boundsQName'"),
        variance,
        nullability
    )

    override fun toString(): String {
        var str = boundsQName
        if (genericParams.isNotEmpty()) str += "<"
        else str += when(nullability) {
            OnyxNullability.NEVER_NULLABLE -> "!"
            OnyxNullability.NULLABLE -> "?"
            else -> ""
        }
        genericParams.forEach {
            val comma = if (it === genericParams.last()) "" else ", "
            str += "$it$comma"
        }
        if (genericParams.isNotEmpty()) str += ">" + when(nullability) {
            OnyxNullability.NEVER_NULLABLE -> "!"
            OnyxNullability.NULLABLE -> "?"
            else -> ""
        }
        return str
    }

    companion object {
        private val uninitialized get() = UninitializedException("Generic is not initialized")

        private fun createParams(list: list): List<OnyxUnfinalizedGenericParam> = buildList {
            list.forEach {
                add(
                    when(it) {
                        is Pair<*, *> -> OnyxUnfinalizedGenericParam(
                            it.first as OnyxNullability,
                            it.second as String
                        )
                        is Triple<*, *, *> -> OnyxUnfinalizedGenericParam(
                            it.first as OnyxNullability,
                            it.second as String,
                            createParams(it.third as list)
                        )
                        else -> throw ImpossibleException()
                    }
                )
            }
        }

        val inputParamTemplate = ParsinatorTemplate {
            chain(false) {
                recurse(false, {
                    val b = it.char == '<'
                    if (b) it.inc()
                    b
                }, {
                    val b = it.char == '>'
                    if (b) it.inc()
                    b
                }) {
                    branch(false) {
                        token(false, ",", false) makes {
                            0
                        }
                        chain(false) {
                            name(false, Regex("[a-zA-Z0-9_.]"))
                            branch(true) {
                                token(false, "?", false) makes {
                                    OnyxNullability.NULLABLE
                                }
                                token(false, "!", false) makes {
                                    OnyxNullability.NEVER_NULLABLE
                                }
                                manual(false, {true}) {
                                    held = OnyxNullability.NON_NULLABLE
                                    if (it.char == '<') {
                                        var ofs = run {
                                            var i = 0
                                            var depth = 0
                                            var started = false
                                            while (i < it.index) {
                                                when(it.string.getOrNull(i)) {
                                                    '<' -> {
                                                        started = true
                                                        depth++
                                                    }
                                                    '>' -> depth--
                                                }
                                                if (started && depth == 0) break
                                                i++
                                            }
                                            i
                                        }
                                        var depth = 0
                                        var started = false
                                        while (ofs < it.string.length) {
                                            when(it.string.getOrNull(ofs)) {
                                                '<' -> {
                                                    started = true
                                                    depth++
                                                }
                                                '>' -> depth--
                                            }
                                            if (depth <= 0 && started)
                                                break
                                            ofs++
                                        }
                                        held = when(it.atOffset(ofs + 1)) {
                                            "?" -> OnyxNullability.NULLABLE
                                            "!" -> OnyxNullability.NEVER_NULLABLE
                                            else -> OnyxNullability.NON_NULLABLE
                                        }
                                    }
                                    null
                                }
                            }
                        }
                    }
                }
            } makes {
                val list = (it as List<*>).first() as List<*>
                class Holder {
                    var nullability = OnyxNullability.NON_NULLABLE
                    var name = ""
                    var params = listOf<OnyxUnfinalizedGenericParam>()
                    fun create() = OnyxUnfinalizedGenericParam(
                        nullability, name, params
                    )
                    override fun toString() = name
                }
                var stack = Stack<Holder>()
                stack.push(Holder())
                fun recurse(list: List<*>): List<OnyxUnfinalizedGenericParam> = buildList {
                    list.forEach { subEntry ->
                        when(subEntry) {
                            is String -> stack.peek().name = subEntry
                            is List<*> -> {
                                stack.push(Holder())
                                val recursed = recurse(subEntry)
                                stack.pop()
                                stack.peek().params += recursed
                            }
                            is OnyxNullability -> stack.peek().nullability = subEntry
                            0 -> {
                                add(stack.pop().create())
                                stack.push(Holder())
                            }
                        }
                    }
                    if (stack.peek().name.isNotEmpty()) add(stack.peek().create())
                }
                recurse(list)
            }
        }
    }
}