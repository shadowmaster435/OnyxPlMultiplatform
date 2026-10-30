package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.code.OnyxExitType
import net.shadowmaster435.onyx.lang.impl.OnyxCodeBlockContainer
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.lang.impl.OnyxRunnableImpl
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxParams

class OnyxFor(
    val expression: OnyxExpression,
    val codeBlock: OnyxCodeBlock,
    override val isFinalized: Boolean = false
): OnyxRunnableImpl, OnyxCodeBlockContainer, OnyxFinalizable<OnyxFor> {
    override fun execute(parent: OnyxCodeBlock?, thisInst: OnyxClassInstance?, params: OnyxParams): OnyxUnit {
        val iterable = expression.evaluate(parent, thisInst, params).onyxIterator()
        for (i in iterable) {
            val type = codeBlock.execute(thisInst, i).second
            if (type != null && type != OnyxExitType.CONTINUE) break
        }
        return OnyxUnit
    }
    override fun createFinal() = OnyxFor(
        expression.checkCreateFinal(),
        if (codeBlock is OnyxUnfinalizedCodeBlock) codeBlock.checkCreateFinal() else codeBlock,
        true
    )
    override fun forEachBlock(consumer: (block: OnyxCodeBlock) -> Unit) {
        consumer(codeBlock)
    }
}