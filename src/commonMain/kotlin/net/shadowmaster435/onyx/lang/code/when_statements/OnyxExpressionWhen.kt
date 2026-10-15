package net.shadowmaster435.onyx.lang.code.when_statements

import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxBoolean
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxClass
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock
import net.shadowmaster435.onyx.util.OnyxExpression

class OnyxExpressionWhen(
    returnType: OnyxClassDescriptor,
    private val elseBlock: OnyxCodeBlock?,
    vararg branches: Branch
): AbstractOnyxWhen(returnType) {
    private val branches = branches.toList()
    override val isTable = false
    override fun execute(
        thisInst: OnyxClassInstance?,
        params: List<OnyxClassInstance>
    ): OnyxClassInstance {
        branches.forEach {
            val v = it.execute(thisInst)
            if (v != null) return v
        }
        if (elseBlock != null) return elseBlock.execute(thisInst, params).first
        throw ImpossibleException()
    }

    class Branch (
        val expressions: List<OnyxExpression>,
        val block: OnyxCodeBlock
    ) {
        init {
            expressions.forEach {
                if (it.returnType !== OnyxBoolean.desc || it.returnType !== OnyxClass.desc)
                    throw IllegalArgumentException("When branches only accept booleans or class names")
            }
        }


        fun execute(thisInstance: OnyxClassInstance?): OnyxClassInstance? {
            expressions.forEach {
                if ((it.execute(thisInstance) as OnyxBoolean).held)
                    return block.execute(thisInstance).first
            }
            return null
        }

    }
}