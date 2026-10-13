package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.enums.code.OnyxExitType

class OnyxBreak(at: OnyxCodeBlock? = null): OnyxExitStatement(at) {
    override val type = OnyxExitType.BREAK
}
