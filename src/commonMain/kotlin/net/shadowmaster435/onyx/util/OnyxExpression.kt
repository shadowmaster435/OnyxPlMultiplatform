package net.shadowmaster435.onyx.util

import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxConstructorCall
import net.shadowmaster435.onyx.lang.code.OnyxCodeBlock
import net.shadowmaster435.onyx.lang.impl.OnyxCodeBlockContainer
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.lang.impl.OnyxRunnableImpl
import kotlin.jvm.JvmSynthetic

private typealias Binary = (
    params: OnyxParams,
    parent: OnyxCodeBlock?,
    thisInst: OnyxClassInstance?,
    a: (OnyxParams) -> OnyxClassInstance?,
    b: (OnyxParams) -> OnyxClassInstance?)
-> OnyxClassInstance?
private typealias Unary = (
    parent: OnyxCodeBlock?,
    params: OnyxParams,
    thisInst: OnyxClassInstance?,
    a: (OnyxParams) -> OnyxClassInstance?)
-> OnyxClassInstance?

class OnyxExpression internal constructor(groups: List<OnyxExpressionBuilder.Slot>, val returnType: OnyxClassDescriptor):
    OnyxRunnableImpl,
    OnyxFinalizable<OnyxExpression>,
    OnyxCodeBlockContainer
{
    private val groups = groups.sortedBy { it.precedence }


    fun forEachOperation(opConsumer: (OnyxExpressionBuilder.Operation) -> Unit) {
        iterateGroups(groups, {}, opConsumer)
    }
    fun forEachValue(valConsumer: (OnyxClassInstance) -> Unit) {
        iterateGroups(groups, valConsumer, {})
    }


    private var actuallyFinalized = false
    override val isFinalized: Boolean
        get() = actuallyFinalized
    override fun createFinal(): OnyxExpression {
        groups.forEach { group ->
            when (val group = group) {
                is OnyxExpressionBuilder.Group -> group.entries.forEach {
                    it.createFinal()
                }
            }
        }
        return this.apply { actuallyFinalized = true }
    }
    fun evaluate(parent: OnyxCodeBlock?, thisInst: OnyxClassInstance?, params: OnyxParams): OnyxClassInstance {
        groups.forEach { it.reset(params) }
        var accum: OnyxExpressionBuilder.Slot? = null
        groups.forEach { accum = it.evaluate(parent, thisInst, params) }
        return accum!!.held ?: throw ImpossibleException("Expression failed to evaluate. This should never happen")
    }

    override fun execute(parent: OnyxCodeBlock?, thisInst: OnyxClassInstance?, params: OnyxParams) =
        evaluate(parent, thisInst, params)


    override fun forEachBlock(consumer: (block: OnyxCodeBlock) -> Unit) {
        forEachValue {
            if (it is OnyxCodeBlockContainer) it.forEachBlock(consumer)
        }
    }


    companion object {
        private fun iterateGroups(entries: List<OnyxExpressionBuilder.Slot>, valConsumer: (OnyxClassInstance) -> Unit, opConsumer: (OnyxExpressionBuilder.Operation) -> Unit) {
            entries.forEach {
                iterateSlot(it, valConsumer, opConsumer)
            }
        }

        private fun iterateSlot(slot: OnyxExpressionBuilder.Slot, valConsumer: (OnyxClassInstance) -> Unit, opConsumer: (OnyxExpressionBuilder.Operation) -> Unit) {
            when(slot) {
                is OnyxExpressionBuilder.Group -> slot.entries.forEach {
                    iterateSlot(it.slot, valConsumer, opConsumer)
                }
                is OnyxExpressionBuilder.GroupInstance -> {
                    slot.expression.forEachOperation(opConsumer)
                    slot.expression.forEachValue(valConsumer)
                }
                else -> slot.init?.let { init ->
                    valConsumer(init)
                }
            }
        }

        fun wrap(classInstance: OnyxClassInstance, type: OnyxClassDescriptor = classInstance.desc) = OnyxExpression(
            listOf(OnyxExpressionBuilder.Slot(classInstance, precedence = -1)), type
        )
        fun wrap(type: OnyxClassDescriptor, getter: (thisInst: OnyxClassInstance?, params: OnyxParams) -> OnyxClassInstance?) = OnyxExpression(
            listOf(OnyxExpressionBuilder.Slot(getter = getter, precedence = -1)), type
        )
    }

}
@Marker
class OnyxExpressionBuilder internal constructor(returnType: OnyxClassDescriptor) {
    var returnType: OnyxClassDescriptor = returnType; internal set

    internal class Group(precedence: Int, val entries: List<Operation>) : Slot(precedence = precedence) {
        override fun reset(params: OnyxParams) = entries.forEach { it.slot.reset(params) }
        override fun evaluate(parent: OnyxCodeBlock?, thisInst: OnyxClassInstance?, params: OnyxParams): Slot {
            var result: Slot? = null
            entries.forEach { operation ->
                result = operation.evaluate(parent, params, thisInst)
            }
            return result!!
        }
        override fun createFinal() {
            entries.forEach {
                it.createFinal()
            }
        }

    }

    internal open class GroupInstance(expression: OnyxExpression, parent: OnyxCodeBlock?) : Slot(null, {
        thisInst: OnyxClassInstance?, params: OnyxParams ->
        expression.evaluate(parent, thisInst, params)
    }, 15) {
        var expression = expression; @JvmSynthetic internal set
        override fun reset(params: OnyxParams) {}
        override fun createFinal() {
            expression.checkCreateFinal()
        }
    }

    open class Slot internal constructor(
        init: OnyxClassInstance? = null,
        val getter: ((thisInst: OnyxClassInstance?, params: OnyxParams) -> OnyxClassInstance?)? = null,
        val precedence: Int,
    ) {
        var init: OnyxClassInstance? = init; internal set
        open var held: OnyxClassInstance? = init
        open fun reset(params: OnyxParams) {
            held = if (getter == null) init else getter(init, params)
        }
        open fun createFinal() {
            val init = init
            if (init is OnyxConstructorCall) this.init = init.execute(null, null, listOf())
        }
        open fun evaluate(parent: OnyxCodeBlock?, thisInst: OnyxClassInstance?, params: OnyxParams) = this
    }
    class Operation internal constructor(var slot: Slot) {
        var isUnary = true
        var next: Slot? = null
        var precedence: Int? = null
        var binary: Binary? = null
            set(v) {field = v; isUnary = false}
        var unary: Unary? = null

        fun createFinal() = slot.createFinal()

        fun evaluate(parent: OnyxCodeBlock?, params: OnyxParams, thisInst: OnyxClassInstance?): Slot =
            if (isUnary) {
                slot.held = unary!!(parent, params, thisInst) { slot.held }
                slot
            } else {
                next!!.held = binary!!(params, parent, thisInst,{slot.held}, {next!!.held})
                next!!
            }

    }
    private val groups = mutableListOf<Slot>()

    private var currentGroupList = mutableListOf<Operation>()
    private var lastOperation: Operation? = null

    fun unary(precedence: Int, op: @Marker Unary) {
        val operation = lastOperation
        if (operation != null) {
            if (precedence != operation.precedence) {
                val group = Group(operation.precedence!!, currentGroupList.toList())
                lastOperation!!.unary = op
                lastOperation!!.precedence = precedence
                lastOperation = Operation(group)
                lastOperation!!.precedence = group.precedence
                currentGroupList = mutableListOf()
            } else {
                operation.unary = op
                operation.precedence = precedence
            }
        }
    }
    fun binary(precedence: Int, op: @Marker Binary) {
        val operation = lastOperation
        if (operation != null) {
            if (precedence != operation.precedence) {
                val group = Group(operation.precedence!!, currentGroupList.toList())
                groups.add(group)
                lastOperation!!.binary = op
                lastOperation!!.precedence = precedence
                lastOperation = Operation(group)
                lastOperation!!.precedence = group.precedence
                currentGroupList = mutableListOf()
            } else {
                operation.binary = op
                operation.precedence = precedence
            }
        }
    }

    fun next(v: OnyxClassInstance?) {
        val next = Operation(Slot(v, precedence = -1))
        currentGroupList.add(next)
        lastOperation = next
    }
    fun next(v: @Marker (thisInst: OnyxClassInstance?, params: OnyxParams) -> OnyxClassInstance?) {
        val next = Operation(Slot(getter = v, precedence = -1))
        currentGroupList.add(next)
        lastOperation = next
    }
    fun use(expression: OnyxExpression, parent: OnyxCodeBlock?) {
        val operation = lastOperation
        if (operation != null) groups.add(GroupInstance(expression, parent))
    }

    fun group(parent: OnyxCodeBlock?, init: @Marker OnyxExpressionBuilder.() -> Unit) {
        val operation = lastOperation
        if (operation != null) {
            val builder = OnyxExpressionBuilder(anyDesc)
            builder.init()
            groups.add(GroupInstance(builder.create(), parent))
        }
    }


    internal fun create() = OnyxExpression(groups.toList(), returnType)
}

fun expression(returnType: OnyxClassDescriptor = OnyxUnit.desc, init: @Marker OnyxExpressionBuilder.() -> Unit): OnyxExpression {
    val builder = OnyxExpressionBuilder(returnType)
    builder.init()
    return builder.create()
}


@DslMarker
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
private annotation class Marker