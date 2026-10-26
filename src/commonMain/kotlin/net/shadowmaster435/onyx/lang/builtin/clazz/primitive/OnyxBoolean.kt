package net.shadowmaster435.onyx.lang.builtin.clazz.primitive

import net.shadowmaster435.onyx.compiler.OnyxCompiledType
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxExternalClassInstance
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.impl.OnyxCompilableConstImpl
import net.shadowmaster435.onyx.lang.pkg.onyxPrimitives
import net.shadowmaster435.onyx.util.JVMPrimitiveType
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

expect val OnyxBoolean.compileFunc: (Any?) -> Any?
class OnyxBoolean(override val held: Boolean) : OnyxExternalClassInstance<Boolean>(desc), OnyxCompilableConstImpl {
    override fun compileConst(v: Any?) = OnyxCompiledType(compileFunc(v))

    override fun onyxEquals(other: OnyxClassInstance) = OnyxBoolean(other is OnyxBoolean && other.held == held)
    companion object {

        val desc = OnyxClassDescriptor(
            "Boolean", onyxPrimitives, linkedMapOf(), linkedMapOf(
                OnyxClassDescriptor.hashcodeDesc,
                OnyxClassDescriptor.equalDesc
            ), linkedMapOf(), OnyxClassType.OPEN, OnyxAny.desc
        ).apply { jvmNameOverride = JVMPrimitiveType.BOOL.descriptor }
        val template = ParsinatorTemplate {
            branch(true) {
                token(false, "true")
                token(false, "false")
            } makes { OnyxBoolean(it == "true") }
        }
    }
}
