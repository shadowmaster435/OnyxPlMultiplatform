package net.shadowmaster435.onyx.lang.code.when_statements

import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxBoolean
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxClass
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock
import net.shadowmaster435.onyx.lang.impl.OnyxCodeBlockContainer
import net.shadowmaster435.onyx.util.OnyxExpression

class OnyxExpressionWhen(
    returnType: OnyxClassDescriptor,
    private val elseBlock: OnyxCodeBlock?,
    vararg branches: Branch
): AbstractOnyxWhen(returnType), OnyxCodeBlockContainer {
    private val branches = branches.toList()
    override val isTable = false
    override fun execute(
        parent: OnyxCodeBlock?,
        thisInst: OnyxClassInstance?,
        params: List<OnyxClassInstance>
    ): OnyxClassInstance {
        branches.forEach {
            val v = it.execute(parent,thisInst)
            if (v != null) return v
        }
        if (elseBlock != null) return elseBlock.execute(thisInst, params).first
        throw ImpossibleException()
    }

    override fun forEachBlock(consumer: (block: OnyxCodeBlock) -> Unit) {
        branches.forEach {
            it.expressions.forEach { expression ->
                expression.forEachBlock(consumer)
            }
            consumer(it.block)
        }
        elseBlock?.let { consumer(it) }
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


        fun execute(parent: OnyxCodeBlock?, thisInstance: OnyxClassInstance?): OnyxClassInstance? {
            expressions.forEach {
                if ((it.execute(parent, thisInstance) as OnyxBoolean).held)
                    return block.execute(thisInstance).first
            }
            return null
        }

    }
}