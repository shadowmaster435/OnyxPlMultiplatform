package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.code.OnyxExitType
import net.shadowmaster435.onyx.lang.impl.OnyxRunnableImpl
import net.shadowmaster435.onyx.util.OnyxExpression

class OnyxFor(val expression: OnyxExpression, val codeBlock: OnyxCodeBlock): OnyxRunnableImpl {
    override fun execute(thisInst: OnyxClassInstance?, params: List<OnyxClassInstance>): OnyxUnit {
        val iterable = expression.evaluate(thisInst).onyxIterator()
        for (i in iterable) {
            val type = codeBlock.execute(thisInst, i).second
            if (type != null && type != OnyxExitType.CONTINUE) break
        }
        return OnyxUnit
    }
}