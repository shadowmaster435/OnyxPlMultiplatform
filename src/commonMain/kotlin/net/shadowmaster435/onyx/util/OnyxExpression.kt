package net.shadowmaster435.onyx.util

import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.impl.OnyxRunnableImpl

private typealias Binary = (
    params: OnyxParams,

    thisInst: OnyxClassInstance?,
    a: (OnyxParams) -> OnyxClassInstance?,
    b: (OnyxParams) -> OnyxClassInstance?)
-> OnyxClassInstance?
private typealias Unary = (
    params: OnyxParams,
    thisInst: OnyxClassInstance?,
    a: (OnyxParams) -> OnyxClassInstance?)
-> OnyxClassInstance?

class OnyxExpression internal constructor(groups: List<OnyxExpressionBuilder.Slot>, val returnType: OnyxClassDescriptor): OnyxRunnableImpl {
    private val groups = groups.sortedBy { it.precedence }

    fun evaluate(thisInst: OnyxClassInstance?, params: OnyxParams): OnyxClassInstance {
        groups.forEach { it.reset(params) }
        var accum: OnyxExpressionBuilder.Slot? = null
        groups.forEach { accum = it.evaluate(thisInst, params) }
        return accum!!.held ?: throw ImpossibleException("Expression failed to evaluate. This should never happen")
    }

    override fun execute(thisInst: OnyxClassInstance?, params: OnyxParams) =
        evaluate(thisInst, params)

    companion object {
        fun wrap(classInstance: OnyxClassInstance, type: OnyxClassDescriptor = classInstance.desc) = OnyxExpression(
            listOf(OnyxExpressionBuilder.Slot(classInstance, precedence = -1)), type
        )
        fun wrap(type: OnyxClassDescriptor, getter: (thisInst: OnyxClassInstance?, params: OnyxParams) -> OnyxClassInstance?) = OnyxExpression(
            listOf(OnyxExpressionBuilder.Slot(getter = getter, precedence = -1)), type
        )
    }

}
@Marker
class OnyxExpressionBuilder internal constructor(val returnType: OnyxClassDescriptor) {
    internal class Group(precedence: Int, val entries: List<Operation>) : Slot(precedence = precedence) {
        override fun reset(params: OnyxParams) = entries.forEach { it.slot.reset(params) }
        override fun evaluate(thisInst: OnyxClassInstance?, params: OnyxParams): Slot {
            var result: Slot? = null
            entries.forEach { operation ->
                result = operation.evaluate(params, thisInst)
            }
            return result!!
        }
    }

    internal open class GroupInstance(private val expression: OnyxExpression) : Slot(null, {
        thisInst: OnyxClassInstance?, params: OnyxParams ->
        expression.evaluate(thisInst, params)
    }, 15) {
        override fun reset(params: OnyxParams) {}
    }

    internal open class Slot(
        val init: OnyxClassInstance? = null,
        val getter: ((thisInst: OnyxClassInstance?, params: OnyxParams) -> OnyxClassInstance?)? = null,
        val precedence: Int
    ) {
        open var held: OnyxClassInstance? = init
        open fun reset(params: OnyxParams) {
            held = if (getter == null) init else getter(init, params)
        }
        open fun evaluate(thisInst: OnyxClassInstance?, params: OnyxParams) = this
    }
    internal class Operation(var slot: Slot) {
        var isUnary = true
        var next: Slot? = null
        var precedence: Int? = null
        var binary: Binary? = null
            set(v) {field = v; isUnary = false}
        var unary: Unary? = null
        fun evaluate(params: OnyxParams, thisInst: OnyxClassInstance?): Slot =
            if (isUnary) {
                slot.held = unary!!(params, thisInst) { slot.held }
                slot
            } else {
                next!!.held = binary!!(params, thisInst,{slot.held}, {next!!.held})
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
    fun use(expression: OnyxExpression) {
        val operation = lastOperation
        if (operation != null) groups.add(GroupInstance(expression))
    }

    fun group(init: @Marker OnyxExpressionBuilder.() -> Unit) {
        val operation = lastOperation
        if (operation != null) {
            val builder = OnyxExpressionBuilder(anyDesc)
            builder.init()
            groups.add(GroupInstance(builder.create()))
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