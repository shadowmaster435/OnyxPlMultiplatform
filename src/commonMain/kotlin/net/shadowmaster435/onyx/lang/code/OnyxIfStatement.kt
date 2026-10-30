package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxBoolean
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.impl.OnyxCodeBlockContainer
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.lang.impl.OnyxRunnableImpl
import net.shadowmaster435.onyx.util.OnyxExpression

class OnyxIfStatement(
    val expression: OnyxExpression,
    val codeBlock: OnyxCodeBlock,
    val elif: OnyxIfStatement? = null,
    val elseBlock: OnyxCodeBlock? = null,
    override val isFinalized: Boolean = false
): OnyxRunnableImpl, OnyxCodeBlockContainer, OnyxFinalizable<OnyxIfStatement> {
    override fun execute(parent: OnyxCodeBlock?, thisInst: OnyxClassInstance?, params: List<OnyxClassInstance>): OnyxClassInstance? {
        return if (((expression.evaluate(parent, thisInst, params)) as OnyxBoolean).held)
            codeBlock.execute(thisInst, params).first
        else elif?.execute(parent, thisInst, params) ?: elseBlock?.execute(thisInst, params)?.first
    }

    override fun forEachBlock(consumer: (block: OnyxCodeBlock) -> Unit) {
        expression.forEachBlock(consumer)
        consumer(codeBlock)
        elif?.forEachBlock(consumer)
        elseBlock?.let { consumer(it) }
    }
    override fun createFinal(): OnyxIfStatement = OnyxIfStatement(
        expression.checkCreateFinal(),
        if (codeBlock is OnyxUnfinalizedCodeBlock) codeBlock.checkCreateFinal() else codeBlock,
        elif?.checkCreateFinal(),
        elseBlock?.let { if (it is OnyxUnfinalizedCodeBlock) it.checkCreateFinal() else it },
        true
    )
}