package net.shadowmaster435.onyx.lang.impl

import net.shadowmaster435.onyx.compiler.OnyxCompiledType
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.enums.jvm.JVMApplicability
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.field.OnyxFieldInstance
import net.shadowmaster435.onyx.lang.function.OnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.impl.jvm.JVMDescribable
import net.shadowmaster435.onyx.lang.impl.jvm.JVMFlaggable
import net.shadowmaster435.onyx.lang.util.OnyxDefinitionName
import net.shadowmaster435.onyx.util.OnyxExpression

interface OnyxFieldImpl :
    OnyxMemberImpl,
    OnyxAccessImpl,
    OnyxNamedImpl,
    OnyxReferencableImpl,
    JVMFlaggable,
    JVMDescribable,
    OnyxCompilableConstImpl,
    OnyxInstantiatable<OnyxFieldInstance>,
    OnyxFinalizable<OnyxFieldImpl>
{
    override val applicability: JVMApplicability
        get() = JVMApplicability.FIELD
    override val descriptor: String
        get() = classType.descriptor
    var holderClass: OnyxClassDescriptor?
    var classType: OnyxClassDescriptor
    var getter: OnyxFunctionDescriptor?
    var setter: OnyxFunctionDescriptor?
    val initExpression: OnyxExpression?
    val definitionName: OnyxDefinitionName?
    val isLateInit get() = modifiers.contains(OnyxModifierType.LATEINIT)
    val isLazy get() = modifiers.contains(OnyxModifierType.LAZY)
    val isDeferred: Boolean
    val isVirtual: Boolean
    val selfInstance: OnyxFieldInstance?
    override fun compileConst(v: Any?): OnyxCompiledType {
        TODO("Field compile functions")
    }

}