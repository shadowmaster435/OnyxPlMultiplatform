package net.shadowmaster435.onyx.lang.builtin.clazz.primitive

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.pkg.onyxPrimitives
import net.shadowmaster435.onyx.util.JVMPrimitiveType


class OnyxAny: OnyxClassInstance(desc) {

    companion object {
        val desc = anyDesc
    }
}

internal val anyDesc = OnyxClassDescriptor(
    "Any", onyxPrimitives, linkedMapOf(), linkedMapOf(
        OnyxClassDescriptor.hashcodeDesc,
        OnyxClassDescriptor.equalDesc,
        OnyxClassDescriptor.toStringDesc,
    ), linkedMapOf(), OnyxClassType.OPEN, null
).apply { jvmNameOverride = JVMPrimitiveType.OBJECT.descriptor }