package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.exception.VirtualAccessException
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.lang.impl.OnyxPokable
import net.shadowmaster435.onyx.lang.impl.OnyxReferencableImpl
import net.shadowmaster435.onyx.util.OnyxInitHolder
import net.shadowmaster435.onyx.util.OnyxParams

open class OnyxFieldInstance(
    val desc: OnyxFieldImpl,
    val referenceIndex: Int,
    open val final: Boolean = false,
    open val deferred: Boolean = false,
    open val virtual: Boolean = false
): OnyxPokable, OnyxReferencableImpl {

    open val held = OnyxInitHolder<OnyxClassInstance>()
        get() = if (virtual) throw virtualAccess else field

    open fun set(to: OnyxClassInstance, thisInst: OnyxClassInstance? = null) = if (virtual) throw virtualAccess else held.set(desc.classType.cast(to))
    open fun get(thisInst: OnyxClassInstance? = null) = if (virtual) throw virtualAccess else held.get()

    override fun getRefVal(thisInstance: OnyxClassInstance?) = get()
    override fun setRefVal(thisInstance: OnyxClassInstance?, v: OnyxClassInstance) {
        set(v, thisInstance)
    }

    override fun poke() { }

    fun initialize(
        thisInst: OnyxClassInstance,
        params: OnyxParams
    ) {
        val expr = desc.initExpression ?: throw NullPointerException("Missing expression for field")
        held.set(expr.evaluate(thisInst, params))
    }
    companion object {
        val virtualAccess = VirtualAccessException("Cannot access/modify a virtual field")
    }
}