package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.compiler.OnyxCompiledType
import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.function.OnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxCodeBlockContainer
import net.shadowmaster435.onyx.lang.impl.OnyxCompilableConstImpl
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.lang.util.OnyxDefinitionName
import net.shadowmaster435.onyx.lang.util.modifierString
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxParams

expect val OnyxFieldDescriptor.compileFunc: (Any?) -> Any?
class OnyxFieldDescriptor(
    override val name: String,
    override var holderClass: OnyxClassDescriptor?,
    override var classType: OnyxClassDescriptor,
    override val modifiers: List<OnyxModifierType>,
    override val initExpression: OnyxExpression? = null,
    override val isFinal: Boolean = false,
    override val isVirtual: Boolean = false,
    selfInstance: OnyxFieldInstance? = null,
    override val definitionName: OnyxDefinitionName? = null
): OnyxFieldImpl, OnyxCompilableConstImpl, OnyxCodeBlockContainer {
    override val isDeferred: Boolean = false
    override var getter: OnyxFunctionDescriptor? = null
    override var setter: OnyxFunctionDescriptor? = null
    override val type = OnyxMemberType.FIELD
    override fun toString(): String = modifiers.modifierString() + " " + (if (isFinal) "var" else "val") + " " + name
    override val isFinalized: Boolean get() = true
    override fun createFinal() = this
    override var selfInstance = selfInstance; internal set

    override fun getRefVal(thisInstance: OnyxClassInstance?) =
        thisInstance?.getField(name) ?: selfInstance?.get(thisInstance) ?:
        throw ImpossibleException("Unbound field. This should never happen")

    override fun setRefVal(
        thisInstance: OnyxClassInstance?,
        v: OnyxClassInstance
    ) {
        (selfInstance ?: thisInstance?.getFieldInstance(name) ?:
        throw ImpossibleException("Unbound field. This should never happen")).set(v.cast(classType), thisInstance)
    }

    override fun forEachBlock(consumer: (block: OnyxCodeBlock) -> Unit) {
        initExpression?.forEachBlock(consumer)
    }
    override fun compileConst(v: Any?) = OnyxCompiledType(compileFunc(v))
    override fun instantiate(
        thisInst: OnyxClassInstance,
        params: OnyxParams
    ) = OnyxFieldInstance(
        this, (params[0] as OnyxInt).held, isFinal, isDeferred, isVirtual
    )

}