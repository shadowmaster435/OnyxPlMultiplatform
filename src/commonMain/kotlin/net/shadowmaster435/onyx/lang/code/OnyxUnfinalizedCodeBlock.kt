package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxUnfinalizedClassTypeRef
import net.shadowmaster435.onyx.lang.function.OnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.lang.impl.OnyxRunnableImpl
import net.shadowmaster435.onyx.lang.util.expressionTemplate
import net.shadowmaster435.onyx.lang.util.memberTemplate
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxUnfinalizedCodeBlock(
    members: List<OnyxRunnableImpl>,
    val returnTypeUnfinal: OnyxUnfinalizedClassTypeRef?
) : OnyxCodeBlock(
    members
), OnyxFinalizable<OnyxCodeBlock> {

    override fun createFinal(): OnyxCodeBlock {
        val members = buildList {
            members.forEach {
                val new = if (it is OnyxFinalizable<*>) it.checkCreateFinal() else it
                if (new is OnyxRunnableImpl) add(it)
            }
        }
        val returnType = returnTypeUnfinal?.checkCreateFinal() ?: OnyxUnit.desc

        return OnyxCodeBlock(
            members, returnType
        ).apply { members.forEach {
            when(it) {
                is OnyxFunctionDescriptor -> it.block.parent = this

            }
            if (it is OnyxCodeBlock) it.parent = this }
        }
    }
    companion object {
        val template: ParsinatorTemplate = ParsinatorTemplate {
            chain(true) {
                token(false, "{")
                repeating(false, true) {
                    branch(false) {
                        use(memberTemplate)
                        use(expressionTemplate)
                    }
                }
                token(false, "}")
            }
        }
    }
}