package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.exception.ParseException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock
import net.shadowmaster435.onyx.lang.code.OnyxReturn
import net.shadowmaster435.onyx.lang.code.OnyxUnfinalizedCodeBlock
import net.shadowmaster435.onyx.lang.enums.lang.OnyxMemberType
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.generics.OnyxGenericTuples
import net.shadowmaster435.onyx.lang.generics.OnyxGenericType
import net.shadowmaster435.onyx.lang.generics.OnyxUnfinalizedGenericTuples
import net.shadowmaster435.onyx.lang.util.*
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxModifiers
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

open class OnyxFunctionDescriptor(
    name: String,
    tuples: OnyxTuples,
    val block: OnyxCodeBlock,
    modifiers: List<OnyxModifierType>,
    genericTuples: OnyxGenericTuples,
    returnType: OnyxClassDescriptor = OnyxUnit.desc,
) : AbstractOnyxFunctionDescriptor(name, tuples, modifiers, genericTuples, returnType) {
    override val type = OnyxMemberType.FUNCTION
    override fun forEachBlock(consumer: (block: OnyxCodeBlock) -> Unit) {
        consumer(block)
    }
    override fun invoke(
        thisInst: OnyxClassInstance?,
        params: List<OnyxClassInstance>,
        generics: List<OnyxGenericType>
    ): OnyxClassInstance {
        genericTuples.validate(generics)
        tuples.validate(params)
        return block.execute(thisInst, params).first
    }
}