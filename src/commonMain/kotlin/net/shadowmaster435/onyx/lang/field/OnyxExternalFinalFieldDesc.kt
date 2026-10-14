package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.function.OnyxFunction
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.util.OnyxExpression

class OnyxExternalFinalFieldDesc(
    override val modifiers: List<OnyxModifierType>,
    override val classType: OnyxClassDescriptor,
    val extGetter: (OnyxClassInstance?) -> OnyxClassInstance,

): OnyxFieldImpl {
    override val isVirtual = false
    override val isDeferred = true
    override val isFinal = true
    override var getter: OnyxFunction? = null
    override var setter: OnyxFunction? = null
    override val initExpression: OnyxExpression? = null
    override val type = OnyxMemberType.FIELD



    override fun poke() {}
    override fun instantiate(refIndex: Int) = OnyxExternalFinalFieldInstance(
        this, refIndex, getter = extGetter
    )

}