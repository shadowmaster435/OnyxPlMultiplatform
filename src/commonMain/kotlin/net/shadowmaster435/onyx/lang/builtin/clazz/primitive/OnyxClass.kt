package net.shadowmaster435.onyx.lang.builtin.clazz.primitive

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.function.OnyxExternalFunction
import net.shadowmaster435.onyx.lang.function.OnyxTuple
import net.shadowmaster435.onyx.lang.function.OnyxTuples
import net.shadowmaster435.onyx.lang.pkg.onyxPrimitives

class OnyxClass(override val held: OnyxClassDescriptor) : OnyxExternalClassInstance<OnyxClassDescriptor>(desc) {
    override fun onyxEquals(other: OnyxClassInstance) = OnyxBoolean(other is OnyxClass && other.held == held)
    companion object {
        val desc = OnyxClassDescriptor(
            "OClass", onyxPrimitives, linkedMapOf(), linkedMapOf(
                OnyxClassDescriptor.hashcodeDesc,
                OnyxClassDescriptor.equalDesc,
                "cast" to OnyxExternalFunction(
                    OnyxTuples(
                        OnyxTuple(OnyxAny.desc, "to", false, false)
                    )
                ) { thisInst: OnyxClassInstance?, list: List<OnyxClassInstance> ->
                    (thisInst as OnyxClass).held.cast(list[0])
                }
            ), linkedMapOf(), OnyxClassType.NORMAL, OnyxAny.desc
        )
    }
}