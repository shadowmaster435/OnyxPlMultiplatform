package net.shadowmaster435.onyx.lang.code.when_statements

import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxUnfinalizedClassTypeRef
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock
import net.shadowmaster435.onyx.lang.code.OnyxUnfinalizedCodeBlock
import net.shadowmaster435.onyx.lang.impl.OnyxCodeBlockContainer
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.util.OnyxExpression

class OnyxTableWhen(
    private val instProvider: OnyxExpression,
    returnType: OnyxClassDescriptor,
    private val elseBlock: OnyxCodeBlock?,
    blocks: Map<OnyxClassDescriptor, OnyxCodeBlock>,
    override val isFinalized: Boolean = false
) : AbstractOnyxWhen(returnType), OnyxCodeBlockContainer, OnyxFinalizable<OnyxTableWhen> {
    override val isTable = true
    private val blocks = HashMap(blocks)

    init {
        if (!instProvider.returnType.castableTo(returnType)) throw ClassCastException(
            "Cannot cast '${instProvider.returnType.qualifiedName}' to '${returnType.qualifiedName}'"
        )
    }

    override fun execute(
        parent: OnyxCodeBlock?,
        thisInst: OnyxClassInstance?,
        params: List<OnyxClassInstance>
    ): OnyxClassInstance {
        val v = blocks[instProvider.evaluate(parent, thisInst, params).desc]?.execute(thisInst, params)?.first
        if (v != null) return v
        if (returnType === OnyxUnit.desc) return OnyxUnit
        if (elseBlock != null) return elseBlock.execute(thisInst, params).first
        throw ImpossibleException("If you see this as an error, The developer of onyx messed up something really bad")
    }

    override fun forEachBlock(consumer: (block: OnyxCodeBlock) -> Unit) {
        instProvider.forEachBlock(consumer)
        blocks
        elseBlock?.let { consumer(it) }
    }

    override fun createFinal() = OnyxTableWhen(
        instProvider.checkCreateFinal(),
        if (returnType is OnyxUnfinalizedClassTypeRef) returnType.checkCreateFinal() else returnType,
        if (elseBlock is OnyxUnfinalizedCodeBlock) elseBlock.checkCreateFinal() else elseBlock,
        buildMap { blocks.forEach {
            put(
                if (it.key is OnyxUnfinalizedClassTypeRef) (it.key as OnyxUnfinalizedClassTypeRef).checkCreateFinal() else it.key,
                if (it.value is OnyxUnfinalizedCodeBlock) (it.value as OnyxUnfinalizedCodeBlock).checkCreateFinal() else it.value,
            )
        } }, true
    )
}