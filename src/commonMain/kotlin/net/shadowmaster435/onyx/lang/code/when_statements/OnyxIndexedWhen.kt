package net.shadowmaster435.onyx.lang.code.when_statements

import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxUnfinalizedClassTypeRef
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock
import net.shadowmaster435.onyx.lang.code.OnyxUnfinalizedCodeBlock
import net.shadowmaster435.onyx.lang.impl.OnyxCodeBlockContainer
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.util.OnyxExpression

class OnyxIndexedWhen(
    private val intProvider: OnyxExpression,
    returnType: OnyxClassDescriptor,
    private val elseBlock: OnyxCodeBlock?,
    private val minIndex: Int,
    override val isFinalized: Boolean = false,
    private vararg val blocks: OnyxCodeBlock,
) : AbstractOnyxWhen(returnType), OnyxCodeBlockContainer, OnyxFinalizable<OnyxIndexedWhen> {
    override val isTable = true
    init {
        if (intProvider.returnType !== OnyxInt.desc) throw ImpossibleException(
            "Indexed when statement expression returns '${intProvider.returnType.qualifiedName}' instead of an int. This should never happen"
        )
    }
    constructor(
        intProvider: OnyxExpression,
        returnType: OnyxClassDescriptor,
        elseBlock: OnyxCodeBlock?,
        isFinalized: Boolean = false,
        vararg blocks: OnyxCodeBlock
    ) : this(intProvider, returnType, elseBlock, 0, isFinalized, *blocks)

    override fun forEachBlock(consumer: (block: OnyxCodeBlock) -> Unit) {
        intProvider.forEachBlock(consumer)
        blocks.forEach {
            consumer(it)
        }
        elseBlock?.let { consumer(it) }
    }

    override fun execute(
        parent: OnyxCodeBlock?,
        thisInst: OnyxClassInstance?,
        params: List<OnyxClassInstance>
    ): OnyxClassInstance {
        val i = (intProvider.evaluate(parent, thisInst, params) as OnyxInt).held
        if (i < 0) {
            if (elseBlock != null) return elseBlock.execute(thisInst, params).first
            throw ImpossibleException()
        }
        return blocks[i].execute(thisInst, params).first
    }

    override fun createFinal() = OnyxIndexedWhen(
        intProvider.checkCreateFinal(),
        if (returnType is OnyxUnfinalizedClassTypeRef) returnType.checkCreateFinal() else returnType,
        if (elseBlock is OnyxUnfinalizedCodeBlock) elseBlock.checkCreateFinal() else elseBlock,
        true
    )
}