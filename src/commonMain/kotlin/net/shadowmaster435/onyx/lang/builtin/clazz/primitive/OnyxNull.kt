package net.shadowmaster435.onyx.lang.builtin.clazz.primitive

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.pkg.onyxPrimitives
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate


object OnyxNull : OnyxExternalClassInstance<Nothing?>(Desc.desc) {
    override val held = null

    override fun onyxEquals(other: OnyxClassInstance) = OnyxBoolean(other === this)
    override fun onyxHashcode() = OnyxInt(0)

    object Desc {
        val desc = OnyxClassDescriptor(
            "Nothing", onyxPrimitives, linkedMapOf(), linkedMapOf(
                OnyxClassDescriptor.hashcodeDesc,
                OnyxClassDescriptor.equalDesc
            ), linkedMapOf(), OnyxClassType.NORMAL, OnyxAny.desc
        )
        val template = ParsinatorTemplate {
            token(false,"null") makes { OnyxNull }
        }
    }
}