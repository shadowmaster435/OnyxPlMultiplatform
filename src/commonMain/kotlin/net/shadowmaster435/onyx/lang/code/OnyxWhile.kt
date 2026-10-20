package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxBoolean
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.code.OnyxExitType
import net.shadowmaster435.onyx.lang.impl.OnyxPokable
import net.shadowmaster435.onyx.lang.impl.OnyxRunnableImpl
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxParams

class OnyxWhile(val expression: OnyxExpression, val codeBlock: OnyxCodeBlock): OnyxRunnableImpl {
    override fun execute(thisInst: OnyxClassInstance?, params: OnyxParams): OnyxUnit {
        while ((expression.evaluate(thisInst, params) as OnyxBoolean).held) {
            val type = codeBlock.execute(thisInst).second
            if (type != null && type != OnyxExitType.CONTINUE) break
        }
        return OnyxUnit
    }

}