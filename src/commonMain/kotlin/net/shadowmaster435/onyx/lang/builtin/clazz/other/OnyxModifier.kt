package net.shadowmaster435.onyx.lang.builtin.clazz.other

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxBoolean
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.pkg.onyxLang

class OnyxModifier(override val held: OnyxModifierType) : OnyxExternalClassInstance<OnyxModifierType>(desc) {
    override fun onyxEquals(other: OnyxClassInstance) = OnyxBoolean(other is OnyxModifier && other.held == held)
    companion object {
        val desc = anyDesc.extend(
            "OnyxModifier", onyxLang, OnyxClassType.ENUM
        )
    }
}