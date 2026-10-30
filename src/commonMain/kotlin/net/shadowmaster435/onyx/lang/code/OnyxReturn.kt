package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.code.OnyxExitType
import net.shadowmaster435.onyx.lang.impl.OnyxCodeBlockContainer
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.util.OnyxExpression
import net.shadowmaster435.onyx.util.OnyxParams
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxReturn(
    private val expr: OnyxExpression,
    at: OnyxCodeBlock? = null,
    override val isFinalized: Boolean = false

) : OnyxExitStatement(at), OnyxCodeBlockContainer, OnyxFinalizable<OnyxReturn> {
    override val type = OnyxExitType.RETURN

    override fun execute(
        parent: OnyxCodeBlock?,
        thisInst: OnyxClassInstance?,
        params: OnyxParams
    ) = expr.evaluate(parent, thisInst, params)

    override fun forEachBlock(consumer: (block: OnyxCodeBlock) -> Unit) {
        expr.forEachBlock(consumer)
    }

    override fun createFinal() = OnyxReturn(
        expr.checkCreateFinal(), at, true
    )
    companion object {
        val template = ParsinatorTemplate {
            token(false, "return")
        }
    }
}