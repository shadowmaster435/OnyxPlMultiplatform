package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.impl.OnyxRunnableImpl
import net.shadowmaster435.onyx.util.OnyxBlockReturn

open class OnyxCodeBlock(
    val members: List<OnyxRunnableImpl>, val returnType: OnyxClassDescriptor = OnyxUnit.desc
) {
    var parent: OnyxCodeBlock? = null; internal set
    fun execute(thisInst: OnyxClassInstance?, params: List<OnyxClassInstance>): OnyxBlockReturn {
        members.forEach {
            if (it is OnyxExitStatement)
                return Triple(it.execute(parent, thisInst, params), it.type, it.at)
            else {
                val ret = execute(thisInst, params)
                if (ret.third != null && ret.third !== this) return ret
            }
        }
        return Triple(OnyxUnit, null, null)
    }
    fun execute(thisInst: OnyxClassInstance?): OnyxBlockReturn = execute(thisInst,listOf())
    fun execute(thisInst: OnyxClassInstance?, singleParam: OnyxClassInstance): OnyxBlockReturn =
        execute(thisInst,listOf(singleParam))
}