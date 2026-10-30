package net.shadowmaster435.onyx.lang.clazz

import net.shadowmaster435.onyx.lang.function.OnyxFunctionCall
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable

class OnyxUnfinalizedConstructorCall(
    private val descUnfinal: OnyxUnfinalizedClassDesc,
    private val ctor: OnyxFunctionCall
) : OnyxClassInstance(descUnfinal), OnyxFinalizable<OnyxClassInstance> {
    override val isConstructorCall: Boolean get() = true
    override fun createFinal() = OnyxConstructorCall(descUnfinal.checkCreateFinal(), ctor)
}