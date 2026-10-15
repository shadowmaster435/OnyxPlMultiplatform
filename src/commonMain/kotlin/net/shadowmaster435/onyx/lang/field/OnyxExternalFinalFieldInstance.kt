package net.shadowmaster435.onyx.lang.field

import net.shadowmaster435.onyx.exception.NonExistenceException
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.util.OnyxInitHolder

class OnyxExternalFinalFieldInstance(
    desc: OnyxFieldImpl,
    referenceIndex: Int,
    val getter: (OnyxClassInstance?) -> OnyxClassInstance
) : OnyxFieldInstance(desc, referenceIndex, true) {
    override val held: OnyxInitHolder<OnyxClassInstance>
        get() = throw NonExistenceException("Held does not exist on external fields")

    override fun get(thisInst: OnyxClassInstance?) = getter(thisInst)
}