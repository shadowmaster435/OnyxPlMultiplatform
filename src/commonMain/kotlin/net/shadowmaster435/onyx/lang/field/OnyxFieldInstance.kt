package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.lang.impl.OnyxPokable
import net.shadowmaster435.onyx.util.OnyxInitHolder
import net.shadowmaster435.onyx.util.OnyxParams

open class OnyxFieldInstance(
    val desc: OnyxFieldImpl,
    val referenceIndex: Int,
    val type: OnyxClassDescriptor,
): OnyxPokable {

    open val held = OnyxInitHolder<OnyxClassInstance>()

    fun set(to: OnyxClassInstance) = held.set(type.cast(to))
    fun get() = held.get()

    override fun poke() { }

    fun initialize(
        thisInst: OnyxClassInstance,
        params: OnyxParams
    ) {
        val expr = desc.initExpression ?: throw NullPointerException("Missing expression for field")
        held.set(expr.evaluate(thisInst))
    }

}