package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.function.OnyxFunction
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.util.OnyxExpression

class OnyxDeferredFinalFieldDesc(
    override var getter: OnyxFunction? = null,
    override val modifiers: List<OnyxModifierType>,
): OnyxFieldImpl {
    override val initExpression: OnyxExpression? = null
    override var setter: OnyxFunction? = null
    override val isFinal = true
    override val type = OnyxMemberType.FIELD
    override fun poke() {}
    override fun instantiate(refIndex: Int) = OnyxFieldInstance(this, refIndex)

}