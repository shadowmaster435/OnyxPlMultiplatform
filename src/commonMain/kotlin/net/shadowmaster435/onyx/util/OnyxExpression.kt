package net.shadowmaster435.onyx.util

import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxUnit
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.field.OnyxFieldDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxPokable
import net.shadowmaster435.onyx.lang.impl.OnyxReferencableImpl
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

class OnyxExpression internal constructor(groups: List<OnyxExpressionBuilder.Group>, val returnType: OnyxClassDescriptor): OnyxRunnableImpl {
    private val groups = groups.sortedBy { it.precedence }




    fun evaluate(thisInst: OnyxClassInstance?, params: OnyxParams): OnyxClassInstance {
        groups.forEach { it.reset() }
        var accum: OnyxExpressionBuilder.Slot? = null
        groups.forEach { accum = it.evaluate(thisInst, params) }
        return accum!!.held ?: throw ImpossibleException("Expression failed to evaluate. This should never happen")
    }

    override fun execute(thisInst: OnyxClassInstance?, params: OnyxParams) =
        evaluate(thisInst, params)
}
class OnyxExpressionBuilder {
    internal class Group(val precedence: Int, val entries: List<Operation>) : Slot() {
        override fun reset() = entries.forEach { it.slot.reset() }
        fun evaluate(thisInst: OnyxClassInstance?, params: OnyxParams): Slot {
            var result: Slot? = null
            entries.forEach { operation ->
                result = operation.evaluate(params, thisInst)
            }
            return result!!
        }
    }
    internal open class Slot(val init: OnyxClassInstance? = null, val getter: ((a: OnyxClassInstance?) -> OnyxClassInstance?)? = null) {
        open var held: OnyxClassInstance? = init
        open fun reset() {
            held = if (getter == null) init else getter(init)
        }
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
                slot.held = unary!!(params, thisInst, {slot.held})
                slot
            } else {
                next!!.held = binary!!(params, thisInst,{slot.held}, {next!!.held})
                next!!
            }

    }
    private val groups = mutableListOf<Group>()

    private var currentGroupList = mutableListOf<Operation>()
    private var lastOperation: Operation? = null

    fun unary(precedence: Int, op: Unary) {
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
    fun binary(precedence: Int, op: Binary) {
        val operation = lastOperation
        if (operation != null) {
            if (precedence != operation.precedence) {
                val group = Group(operation.precedence!!, currentGroupList.toList())
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
        lastOperation = Operation(Slot(v))

    }
    fun next(v: (thisInst: OnyxClassInstance?) -> OnyxClassInstance?) {
        lastOperation = Operation(Slot(getter = v))
    }
    fun create(returnType: OnyxClassDescriptor = OnyxUnit.desc) = OnyxExpression(groups.toList(), returnType)

}

