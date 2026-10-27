package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxBoolean
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.impl.OnyxRunnableImpl
import net.shadowmaster435.onyx.util.OnyxExpression

class OnyxIfStatement(val expression: OnyxExpression, val codeBlock: OnyxCodeBlock, val elif: OnyxIfStatement? = null, val elseBlock: OnyxCodeBlock? = null): OnyxRunnableImpl {
    override fun execute(parent: OnyxCodeBlock?, thisInst: OnyxClassInstance?, params: List<OnyxClassInstance>): OnyxClassInstance? {
        return if (((expression.evaluate(parent, thisInst, params)) as OnyxBoolean).held)
            codeBlock.execute(thisInst, params).first
        else elif?.execute(parent, thisInst, params) ?: elseBlock?.execute(thisInst, params)?.first
    }

}