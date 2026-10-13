package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.function.OnyxFunction
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.util.OnyxExpression

class OnyxDeferredMutableFieldDesc(
    override var getter: OnyxFunction? = null,
    override var setter: OnyxFunction? = null,
    override val modifiers: List<OnyxModifierType>,
): OnyxFieldImpl {
    override val isFinal = false
    override val initExpression: OnyxExpression? = null
    override fun instantiate(refIndex: Int) = OnyxFieldInstance(this, refIndex)

    override val type = OnyxMemberType.FIELD
    override fun poke() {}
}