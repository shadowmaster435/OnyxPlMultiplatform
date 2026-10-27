package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.code.OnyxExitType
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxParams
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxReturn(private val expr: OnyxExpression, at: OnyxCodeBlock? = null) : OnyxExitStatement(at) {
    override val type = OnyxExitType.RETURN

    override fun execute(
        parent: OnyxCodeBlock?,
        thisInst: OnyxClassInstance?,
        params: OnyxParams
    ) = expr.evaluate(parent, thisInst, params)
    companion object {
        val template = ParsinatorTemplate {
            token(false, "return")
        }
    }
}