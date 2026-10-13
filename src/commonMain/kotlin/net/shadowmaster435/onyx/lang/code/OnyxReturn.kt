package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.code.OnyxExitType
import net.shadowmaster435.onyx.util.OnyxExpression

class OnyxReturn(private val expr: OnyxExpression, at: OnyxCodeBlock? = null) : OnyxExitStatement(at) {
    override val type = OnyxExitType.RETURN

    override fun execute(
        thisInst: OnyxClassInstance?,
        params: List<OnyxClassInstance>
    ) = expr.evaluate(thisInst)

}