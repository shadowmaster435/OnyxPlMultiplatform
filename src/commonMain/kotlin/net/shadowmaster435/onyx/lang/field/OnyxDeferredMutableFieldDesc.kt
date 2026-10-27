package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.function.OnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxParams

class OnyxDeferredMutableFieldDesc(
    override val name: String,
    override var getter: OnyxFunctionDescriptor? = null,
    override var setter: OnyxFunctionDescriptor? = null,
    override val modifiers: List<OnyxModifierType>,
    override val classType: OnyxClassDescriptor,
): OnyxFieldImpl {
    override val selfInstance: OnyxFieldInstance? = null
    override val isFinal = false
    override val isDeferred = true
    override val isVirtual = false
    override val initExpression: OnyxExpression? = null
    override fun instantiate(
        thisInst: OnyxClassInstance,
        params: OnyxParams
    ) = OnyxDeferredMutableFieldInstance(this, (params[0] as OnyxInt).held)
    override fun getRefVal(thisInstance: OnyxClassInstance?) = getter?.invoke(thisInstance)

    override fun setRefVal(
        thisInstance: OnyxClassInstance?,
        v: OnyxClassInstance
    ) {
        setter?.invoke(thisInstance, listOf(v), emptyList())
    }


    override val type = OnyxMemberType.FIELD
}