package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.enums.code.OnyxExitType
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxContinue(at: OnyxCodeBlock? = null): OnyxExitStatement(at) {
    override val type = OnyxExitType.CONTINUE

    companion object {
        val template = ParsinatorTemplate {
            token(true, "continue")
        }
    }
}