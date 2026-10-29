package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxBoolean
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.code.OnyxExitType
import net.shadowmaster435.onyx.lang.impl.OnyxCodeBlockContainer
import net.shadowmaster435.onyx.lang.impl.OnyxRunnableImpl
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxParams

class OnyxWhile(val expression: OnyxExpression, val codeBlock: OnyxCodeBlock, val doBlock: OnyxCodeBlock? = null): OnyxRunnableImpl, OnyxCodeBlockContainer {
    override fun execute(parent: OnyxCodeBlock?, thisInst: OnyxClassInstance?, params: OnyxParams): OnyxUnit {
        while ((expression.evaluate(parent, thisInst, params) as OnyxBoolean).held) {
            val type = codeBlock.execute(thisInst).second
            if (type != null && type != OnyxExitType.CONTINUE) break
        }
        return OnyxUnit
    }
    override fun forEachBlock(consumer: (block: OnyxCodeBlock) -> Unit) {

        consumer(codeBlock)
        doBlock?.let { consumer(it) }
    }
}