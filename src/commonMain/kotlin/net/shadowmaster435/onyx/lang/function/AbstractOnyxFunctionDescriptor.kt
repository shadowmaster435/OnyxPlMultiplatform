package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType
import net.shadowmaster435.onyx.lang.impl.OnyxAccessImpl
import net.shadowmaster435.onyx.lang.impl.OnyxMemberImpl
import net.shadowmaster435.onyx.lang.impl.OnyxNamedImpl
import net.shadowmaster435.onyx.lang.util.accessChainStringTemplate
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

abstract class AbstractOnyxFunctionDescriptor(
    override val name: String,
    val tuples: OnyxTuples,
    override val modifiers: List<OnyxModifierType>,
    val genericTuples: OnyxGenericTuples,
    val returnType: OnyxClassDescriptor = OnyxUnit.desc,
) : OnyxMemberImpl, OnyxAccessImpl, OnyxNamedImpl {
    abstract fun invoke(thisInst: OnyxClassInstance?, params: List<OnyxClassInstance>, generics: List<OnyxGenericType>): OnyxClassInstance
    fun invoke(thisInst: OnyxClassInstance?) = invoke(thisInst,listOf(),listOf())
    companion object {
        private val expressionTemplate: ParsinatorTemplate get() = TODO("Expression parsinator template")

        fun functionCallTemplate(make: (Any?) -> Any?) = ParsinatorTemplate {
            chain(true) {
                use(accessChainStringTemplate{it})
                whitespace(true)
                token(false, "(")
                repeating(true, true) {
                    use(expressionTemplate)
                    whitespace(true)
                    trailing {
                        token(true,",")
                        whitespace(true)
                    }
                }
                token(false, ")")
            }
        }
    }
}