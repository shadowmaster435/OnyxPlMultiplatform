package net.shadowmaster435.onyx.lang.code.when_statements

import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock

class OnyxIndexedWhen(
    private val intProvider: (OnyxClassInstance?) -> Int,
    returnType: OnyxClassDescriptor,
    private val elseBlock: OnyxCodeBlock?,
    private val minIndex: Int,
    private vararg val blocks: OnyxCodeBlock,
) : AbstractOnyxWhen(returnType) {
    override val isTable = true

    constructor(
        intProvider: (OnyxClassInstance?) -> Int,
        returnType: OnyxClassDescriptor,
        elseBlock: OnyxCodeBlock?,
        vararg blocks: OnyxCodeBlock
    ) : this(intProvider, returnType, elseBlock, 0, *blocks)

    override fun execute(
        parent: OnyxCodeBlock?,
        thisInst: OnyxClassInstance?,
        params: List<OnyxClassInstance>
    ): OnyxClassInstance {
        val i = intProvider(thisInst) - minIndex
        if (i < 0) {
            if (elseBlock != null) return elseBlock.execute(thisInst, params).first
            throw ImpossibleException()
        }
        return blocks[i].execute(thisInst, params).first
    }
}