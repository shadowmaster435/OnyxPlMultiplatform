package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.code.OnyxExitType
import net.shadowmaster435.onyx.lang.impl.OnyxRunnableImpl
import kotlin.jvm.JvmSynthetic

sealed class OnyxExitStatement(at: OnyxCodeBlock?) : OnyxRunnableImpl {
    var at: OnyxCodeBlock? = at; @JvmSynthetic internal set
    abstract val type: OnyxExitType

    override fun execute(
        parent: OnyxCodeBlock?,
        thisInst: OnyxClassInstance?,
        params: List<OnyxClassInstance>
    ): OnyxClassInstance = OnyxUnit

}