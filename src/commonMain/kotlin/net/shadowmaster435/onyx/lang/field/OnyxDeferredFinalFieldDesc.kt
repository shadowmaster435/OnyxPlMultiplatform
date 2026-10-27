package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.function.OnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.util.OnyxExpression

class OnyxDeferredFinalFieldDesc(
    override val name: String,
    override var getter: OnyxFunctionDescriptor? = null,
    override val modifiers: List<OnyxModifierType>,
    override val classType: OnyxClassDescriptor,
): OnyxFieldImpl {
    override val selfInstance: OnyxFieldInstance? = null
    override val isVirtual = false
    override val isDeferred = true
    override val isFinal = true

    override val initExpression: OnyxExpression? = null
    override var setter: OnyxFunctionDescriptor? = null
    override val type = OnyxMemberType.FIELD
    override fun instantiate(refIndex: Int) = OnyxDeferredFinalFieldInstance(
        this, refIndex
    )
    override fun getRefVal(thisInstance: OnyxClassInstance?) = getter?.invoke(thisInstance)

    override fun setRefVal(
        thisInstance: OnyxClassInstance?,
        v: OnyxClassInstance
    ) {}
}