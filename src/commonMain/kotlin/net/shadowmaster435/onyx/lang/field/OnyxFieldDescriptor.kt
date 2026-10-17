package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.function.OnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.lang.impl.OnyxReferencableImpl
import net.shadowmaster435.onyx.lang.util.modifierString
import net.shadowmaster435.onyx.lang.util.nameRegex
import net.shadowmaster435.onyx.lang.util.numbersRegex
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxFieldDescriptor(
    override val name: String,
    override val classType: OnyxClassDescriptor,
    override val modifiers: List<OnyxModifierType>,
    override val initExpression: OnyxExpression? = null,
    override val isFinal: Boolean = false,
    override val isVirtual: Boolean = false,
    override val selfInstance: OnyxFieldInstance? = null
): OnyxFieldImpl {
    override val isDeferred: Boolean = false
    override var getter: OnyxFunctionDescriptor? = null
    override var setter: OnyxFunctionDescriptor? = null
    override val type = OnyxMemberType.FIELD
    override fun poke() {}
    override fun pokeWith(stick: OnyxClassInstance?) {}
    override fun instantiate(refIndex: Int) = OnyxFieldInstance(
        this, refIndex, isFinal, isDeferred, isVirtual
    )
    override fun toString(): String = modifiers.modifierString() + " " + (if (isFinal) "var" else "val") + " " + name

    override fun getRefVal(thisInstance: OnyxClassInstance?) =
        thisInstance?.getField(name) ?: selfInstance?.get(thisInstance) ?:
        throw ImpossibleException("Unbound field. This should never happen")

    override fun setRefVal(
        thisInstance: OnyxClassInstance?,
        v: OnyxClassInstance
    ) {
        (thisInstance?.getFieldInstance(name) ?: selfInstance ?:
        throw ImpossibleException("Unbound field. This should never happen")).set(v, thisInstance)
    }


    companion object {
        @Suppress("UNCHECKED_CAST")
        val definitionTemplate = ParsinatorTemplate {
            chain(true) {
                use(OnyxModifierType.template)
                whitespace()
                branch(false) {
                    token(false,"val")
                    token(false,"var")
                }
                whitespace(false)
                name(false, nameRegex, numbersRegex)
                chain(true) {
                    branch(false) {
                        chain(false) {
                            whitespace()
                            token(false, ":")
                            whitespace()
                        }
                        chain(false) {
                            whitespace(false)
                            token(false, "by")
                            whitespace(false)
                        }
                    }
                    name(false, nameRegex, numbersRegex)
                }
            } makes {
                it as List<*>
                var ofs = 0
                val modifiers = it[0] as? List<OnyxModifierType> ?: run {
                    ofs++
                    listOf()
                }
                val final = it[1 - ofs] == "val"
                val name = it[2 - ofs] as String
                val typeChain = it[3 - ofs] as List<*>
                val deferred = (typeChain[0] as List<*>).firstOrNull {s -> s is String} == "by"
                val typeName = typeChain[1] as String
                val initExpression = TODO("Field init expression parsing")
                val classType = TODO("Field class type parsing")
                name to if (deferred) {
                    val getter = TODO("Onyx deferred getter parsing")
                    if (final)
                        OnyxDeferredFinalFieldDesc(name, getter, modifiers, classType)
                    else {
                        val setter = TODO("Onyx deferred setter parsing")
                        OnyxDeferredMutableFieldDesc(name, getter, setter, modifiers, classType)
                    }
                } else OnyxFieldDescriptor(name, classType, modifiers, initExpression, final)
            }
        }
    }

}