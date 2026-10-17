package net.shadowmaster435.onyx.lang.builtin.clazz.member

import net.shadowmaster435.onyx.lang.builtin.clazz.iterable.OnyxSet
import net.shadowmaster435.onyx.lang.builtin.clazz.other.OnyxModifier
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxAny
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxClass
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxString
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.field.OnyxFieldDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxAccessImpl
import net.shadowmaster435.onyx.lang.pkg.onyxPrimitives
import net.shadowmaster435.onyx.lang.util.externalFinalField

class OnyxField(override val held: OnyxFieldDescriptor): OnyxExternalClassInstance<OnyxFieldDescriptor>(desc) {

    companion object {
        val desc = OnyxClassDescriptor(
            "OField", onyxPrimitives, linkedMapOf(
                externalFinalField("modifiers", OnyxSet.desc) {
                    OnyxSet(buildSet {
                        (it as OnyxField).held.modifiers.forEach { mod ->
                            add(OnyxModifier(mod))
                        }
                    })
                },
                externalFinalField("name", OnyxString.desc) {
                    OnyxString((it as OnyxField).held.name)
                },
                externalFinalField("type", OnyxClass.desc) {
                    OnyxClass((it as OnyxField).held.classType)
                },
                *OnyxAccessImpl.functions()
            ), linkedMapOf(), linkedMapOf(), OnyxClassType.NORMAL, OnyxAny.desc
        )
    }

}