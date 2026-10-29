package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.function.OnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxCodeBlockContainer
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.lang.util.OnyxDefinitionName
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxParams

class OnyxDeferredFinalFieldDesc(
    override val name: String,
    override var getter: OnyxFunctionDescriptor? = null,
    override val modifiers: List<OnyxModifierType>,
    override var classType: OnyxClassDescriptor,
    override val definitionName: OnyxDefinitionName? = null
): OnyxFieldImpl, OnyxCodeBlockContainer {
    override val selfInstance: OnyxFieldInstance? = null
    override val isVirtual = false
    override val isDeferred = true
    override val isFinal = true
    override var holderClass: OnyxClassDescriptor? = null

    override val initExpression: OnyxExpression? = null
    override var setter: OnyxFunctionDescriptor? = null
    override val type = OnyxMemberType.FIELD
    override val isFinalized: Boolean get() = true
    override fun createFinal() = this
    override fun getRefVal(thisInstance: OnyxClassInstance?) = getter?.invoke(thisInstance)
    override fun forEachBlock(consumer: (block: OnyxCodeBlock) -> Unit) {
        getter?.let { consumer(it.block) }
    }
    override fun setRefVal(
        thisInstance: OnyxClassInstance?,
        v: OnyxClassInstance
    ) {}
    override fun instantiate(
        thisInst: OnyxClassInstance,
        params: OnyxParams
    ) = OnyxDeferredFinalFieldInstance(this, (params[0] as OnyxInt).held)
}