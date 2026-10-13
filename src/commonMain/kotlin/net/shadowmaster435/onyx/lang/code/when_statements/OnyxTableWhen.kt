package net.shadowmaster435.onyx.lang.code.when_statements

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock

class OnyxTableWhen(
    private val instProvider: (OnyxClassInstance?) -> OnyxClassInstance,
    returnType: OnyxClassDescriptor,
    private val elseBlock: OnyxCodeBlock?,
    private val blocks: Map<OnyxClassInstance, OnyxCodeBlock>,
) : AbstractOnyxWhen(returnType) {
    override val isTable = true


    override fun execute(
        thisInst: OnyxClassInstance?,
        params: List<OnyxClassInstance>
    ): OnyxClassInstance {
        val v = blocks[instProvider(thisInst)]?.execute(thisInst, params)?.first
        if (v != null) return v
        if (returnType === OnyxUnit.desc) return OnyxUnit
        if (elseBlock != null) return elseBlock.execute(thisInst, params).first
        throw NullPointerException("This should never happen")
    }
}