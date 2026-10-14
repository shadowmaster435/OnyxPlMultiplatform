package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType
import net.shadowmaster435.onyx.lang.impl.OnyxAccessImpl
import net.shadowmaster435.onyx.lang.impl.OnyxMemberImpl

abstract class AbstractOnyxFunction(
    val tuples: OnyxTuples,
    override val modifiers: List<OnyxModifierType>,
    val genericTuples: OnyxGenericTuples,
    val returnType: OnyxClassDescriptor = OnyxUnit.desc,
) : OnyxMemberImpl, OnyxAccessImpl {
    constructor(
        tuples: OnyxTuples,
        modifiers: List<OnyxModifierType>
    ) : this(tuples, modifiers, OnyxGenericTuples(listOf()))
    abstract fun invoke(thisInst: OnyxClassInstance?, params: List<OnyxClassInstance>, generics: List<OnyxGenericType>): OnyxClassInstance
    fun invoke(thisInst: OnyxClassInstance?) = invoke(thisInst,listOf(),listOf())
}