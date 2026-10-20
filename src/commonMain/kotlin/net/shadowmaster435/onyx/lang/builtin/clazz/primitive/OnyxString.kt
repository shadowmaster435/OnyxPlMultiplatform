package net.shadowmaster435.onyx.lang.builtin.clazz.primitive

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.pkg.onyxPrimitives
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate
import net.shadowmaster435.parsinator.util.ParsinatorErrorType

class OnyxString(override val held: String) : OnyxExternalClassInstance<String>(desc) {
    override fun onyxEquals(other: OnyxClassInstance) = OnyxBoolean(other is OnyxString && other.held == held)


    companion object {
        val desc = OnyxClassDescriptor(
            "Any", onyxPrimitives, linkedMapOf(), linkedMapOf(
                OnyxClassDescriptor.hashcodeDesc,
                OnyxClassDescriptor.equalDesc
            ), linkedMapOf(), OnyxClassType.OPEN, OnyxAny.desc
        )
        val template = ParsinatorTemplate {
            manual(false, {it.char == '\"'}) {
                var str = ""
                var escaped = false
                var success = false
                var stringTemplateIndexes = mutableListOf<Pair<IntRange, Boolean>>()
                while (it.inbounds(0)) {
                    if (it.char == '\\' && !escaped) {
                        escaped = true
                        it.inc()
                        continue
                    }
                    if (!escaped) {
                        if (it.char == '$') {
                            val start = it.index
                            if (it.atOffset(1) == "{") {
                                var depth = 1
                                it.inc(2)
                                while (depth > 0 && it.inbounds(0)) {
                                    str += it.char!!
                                    if (it.char == '{') depth++
                                    if (it.char == '}') depth--
                                    it.inc()
                                }
                                if (depth != 0) return@manual ParsinatorErrorType.EOF
                                else stringTemplateIndexes.add(start..it.index to true)
                            } else stringTemplateIndexes.add(it.index..it.index to false)
                        } else if (it.char == '\"') break else str += it.char
                    } else {
                        escaped = false
                        str += it.char
                    }
                    it.inc()
                }
                if (success) {
                    held = str to stringTemplateIndexes
                    null
                } else ParsinatorErrorType.EOF
            } makes {
                @Suppress("UNCHECKED_CAST")
                it as Pair<String, MutableList<Pair<IntRange, Boolean>>>
                val (str, entries) = it
                TODO("String template parsing")

            }
        }
    }
}