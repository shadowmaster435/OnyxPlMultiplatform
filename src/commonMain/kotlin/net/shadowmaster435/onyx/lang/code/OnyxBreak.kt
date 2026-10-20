package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.enums.code.OnyxExitType
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxBreak(at: OnyxCodeBlock? = null): OnyxExitStatement(at) {
    override val type = OnyxExitType.BREAK
    companion object {
        val template = ParsinatorTemplate {
            token(true, "break")
        }
    }
}
