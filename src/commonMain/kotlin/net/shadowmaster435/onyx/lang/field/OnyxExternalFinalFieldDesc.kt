package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.function.OnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.lang.util.OnyxDefinitionName
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxParams

class OnyxExternalFinalFieldDesc(
    override val name: String,
    override val modifiers: List<OnyxModifierType>,
    override var classType: OnyxClassDescriptor,
    val extGetter: (OnyxClassInstance?) -> OnyxClassInstance,
    override val definitionName: OnyxDefinitionName? = null
): OnyxFieldImpl {
    override val selfInstance: OnyxFieldInstance? = null
    override var holderClass: OnyxClassDescriptor? = null

    override val isVirtual = false
    override val isDeferred = true
    override val isFinal = true
    override var getter: OnyxFunctionDescriptor? = null
    override var setter: OnyxFunctionDescriptor? = null
    override val initExpression: OnyxExpression? = null
    override val type = OnyxMemberType.FIELD
    override val isFinalized: Boolean get() = true
    override fun createFinal() = this
    override fun getRefVal(thisInstance: OnyxClassInstance?) = extGetter.invoke(thisInstance)

    override fun setRefVal(
        thisInstance: OnyxClassInstance?,
        v: OnyxClassInstance
    ) {}
    override fun instantiate(
        thisInst: OnyxClassInstance,
        params: OnyxParams
    ) = OnyxExternalFinalFieldInstance(this, (params[0] as OnyxInt).held, getter = extGetter)
}