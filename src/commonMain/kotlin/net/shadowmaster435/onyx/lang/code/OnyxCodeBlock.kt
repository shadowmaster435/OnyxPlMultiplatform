package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.code.OnyxExitType
import net.shadowmaster435.onyx.lang.impl.OnyxRunnableImpl
import kotlin.jvm.JvmInline

class OnyxCodeBlock(
    val members: List<OnyxRunnableImpl>, val returnType: OnyxClassDescriptor = OnyxUnit.desc
) {
    fun execute(thisInst: OnyxClassInstance?, params: List<OnyxClassInstance>): Pair<OnyxClassInstance, OnyxExitType?> {
        members.forEach {
            if (it is OnyxExitStatement)
                return it.execute(thisInst, params) to it.type
            else execute(thisInst, params)
        }
        return OnyxUnit to null
    }
    fun execute(thisInst: OnyxClassInstance?) = execute(thisInst,listOf())
    fun execute(thisInst: OnyxClassInstance?, singleParam: OnyxClassInstance) = execute(thisInst,listOf(singleParam))
}