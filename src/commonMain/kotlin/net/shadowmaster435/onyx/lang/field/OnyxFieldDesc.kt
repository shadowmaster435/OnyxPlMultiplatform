package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.function.OnyxFunction
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxInitHolder

class OnyxFieldDesc(
    override val classType: OnyxClassDescriptor,
    override val modifiers: List<OnyxModifierType>,
    override val initExpression: OnyxExpression? = null,
    override val isFinal: Boolean = false,

    override val isVirtual: Boolean = false,
): OnyxFieldImpl {
    override val isDeferred: Boolean = false
    override var getter: OnyxFunction? = null
    override var setter: OnyxFunction? = null
    override val type = OnyxMemberType.FIELD
    override fun poke() {}
    override fun pokeWith(stick: OnyxClassInstance?) {}
    override fun instantiate(refIndex: Int) = OnyxFieldInstance(
        this, refIndex, isFinal, isDeferred, isVirtual
    )
}