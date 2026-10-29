package net.shadowmaster435.onyx.lang.clazz

import net.shadowmaster435.onyx.exception.NonExistenceException
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock
import net.shadowmaster435.onyx.lang.function.OnyxFunctionCall
import net.shadowmaster435.onyx.lang.impl.OnyxCodeBlockContainer
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable

class OnyxConstructorCall(
    descUnfinal: OnyxClassDescriptor,
    private val ctor: OnyxFunctionCall
) : OnyxClassInstance(descUnfinal), OnyxCodeBlockContainer {
    override val isConstructorCall: Boolean get() = true
    override fun forEachBlock(consumer: (block: OnyxCodeBlock) -> Unit) {
        ctor.forEachBlock(consumer)
    }

}