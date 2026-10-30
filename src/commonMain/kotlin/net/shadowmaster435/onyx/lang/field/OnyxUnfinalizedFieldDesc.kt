package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.exception.UninitializedException
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxUnfinalizedClassTypeRef
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.function.OnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.lang.util.OnyxDefinitionName
import net.shadowmaster435.onyx.lang.util.expressionTemplate
import net.shadowmaster435.onyx.lang.util.extensionDefinitionName
import net.shadowmaster435.onyx.lang.util.typeTemplate
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxModifiers
import net.shadowmaster435.onyx.util.OnyxParams
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxUnfinalizedFieldDesc(
    override val name: String,
    override val definitionName: OnyxDefinitionName?,
    override val modifiers: OnyxModifiers,
    override val initExpression: OnyxExpression?,
    override val isFinal: Boolean,
    private val classTypeUnfinal: OnyxUnfinalizedClassTypeRef
) : OnyxFieldImpl {
    override var holderClass: OnyxClassDescriptor?
        get() = throw uninitialized
        set(_) {throw uninitialized}

    override var classType: OnyxClassDescriptor get() = throw uninitialized
        set(_) {throw uninitialized}
    override var getter: OnyxFunctionDescriptor?
        get() = throw uninitialized
        set(_) {throw uninitialized}
    override var setter: OnyxFunctionDescriptor?
        get() = throw uninitialized
        set(_) {throw uninitialized}
    override val isDeferred: Boolean get() = throw uninitialized
    override val isVirtual: Boolean get() = throw uninitialized
    override val selfInstance: OnyxFieldInstance get() = throw uninitialized
    override val type = OnyxMemberType.FIELD
    override fun getRefVal(thisInstance: OnyxClassInstance?) = throw uninitialized

    override fun createFinal() = OnyxFieldDescriptor(
        name, null, classTypeUnfinal.checkCreateFinal(), modifiers, initExpression, isFinal, false, definitionName = definitionName
    )

    override fun setRefVal(
        thisInstance: OnyxClassInstance?,
        v: OnyxClassInstance
    ) = throw uninitialized

    override fun instantiate(
        thisInst: OnyxClassInstance,
        params: OnyxParams
    ) = throw uninitialized

    companion object {
        private val uninitialized get() = UninitializedException("Field is not initialized")
        @Suppress("UNCHECKED_CAST")
        val definitionTemplate = ParsinatorTemplate {
            chain(true) {
                use(OnyxModifierType.template)
                whitespace()
                branch(false) {
                    token(false,"val")
                    token(false,"var")
                }
                use(extensionDefinitionName)
                whitespace(false)

                chain(true) {
                    chain(false) {
                        whitespace(true)
                        token(false, ":")
                        whitespace(true)
                    }
                    use(typeTemplate)
                    whitespace(true)
                    branch(false) {
                      //  use(expressionTemplate)
                        exit(0) makes {null}
                    }
                }
            } makes {
                it as List<*>
                val modifiers = it[0] as OnyxModifiers
                val final = it[1] == "val"
                val name = it[2] as OnyxDefinitionName
                val typeChain = it[3] as List<*>
                val initExpression = (typeChain.last() as? OnyxExpression)
                val classType = OnyxUnfinalizedClassTypeRef(typeChain.first() as String)
                name to OnyxUnfinalizedFieldDesc(
                    name.name,
                    name,
                    modifiers,
                    initExpression,
                    final,
                    classType
                )
            }
        }
    }
}