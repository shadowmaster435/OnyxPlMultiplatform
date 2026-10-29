package net.shadowmaster435.onyx.lang.builtin.clazz.member

import net.shadowmaster435.onyx.lang.builtin.clazz.iterable.OnyxSet
import net.shadowmaster435.onyx.lang.builtin.clazz.other.OnyxModifier
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.*
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.field.OnyxFieldDescriptor
import net.shadowmaster435.onyx.lang.function.OnyxTuple
import net.shadowmaster435.onyx.lang.function.OnyxTuples
import net.shadowmaster435.onyx.lang.impl.OnyxAccessImpl
import net.shadowmaster435.onyx.lang.pkg.onyxPrimitives
import net.shadowmaster435.onyx.lang.util.externalFinalField
import net.shadowmaster435.onyx.lang.util.externalFunc
import net.shadowmaster435.onyx.lang.util.invalidPCount

class OnyxField(override val held: OnyxFieldDescriptor): OnyxExternalClassInstance<OnyxFieldDescriptor>(desc) {

    fun set(instance: OnyxClassInstance?, value: OnyxClassInstance) = held.setRefVal(instance, value)
    fun get(instance: OnyxClassInstance?) = held.getRefVal(instance)

    companion object {
        val desc = anyDesc.extend(
            "OField", onyxPrimitives, OnyxClassType.NORMAL, null, linkedMapOf(
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
                *OnyxAccessImpl.fields()
            ), linkedMapOf(
                externalFunc("get",  OnyxTuple(
                    anyDesc, "instance", false, true
                ).wrap()) { thisInst, list ->
                    thisInst as OnyxField
                    val inst = list.firstOrNull() ?: throw invalidPCount
                    thisInst.get(inst)
                },
                externalFunc("set", OnyxTuples(
                    OnyxTuple(
                        anyDesc, "instance", false, true
                    ),
                    OnyxTuple(
                        anyDesc, "value", false, true
                    )
                )) { thisInst, list ->
                    thisInst as OnyxField
                    val inst = list.firstOrNull() ?: throw invalidPCount
                    val value = list.getOrNull(1) ?: throw invalidPCount
                    thisInst.set(inst, value)
                    OnyxUnit
                },
            )
        )
    }

}