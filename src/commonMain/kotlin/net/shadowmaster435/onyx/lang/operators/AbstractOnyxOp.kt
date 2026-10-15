package net.shadowmaster435.onyx.lang.operators

import net.shadowmaster435.onyx.exception.NotABooleanException
import net.shadowmaster435.onyx.exception.NotANumberException
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator


internal typealias OnyxClassProvider = () -> OnyxClassInstance

abstract class AbstractOnyxOp {
    abstract val op: OnyxOperator
    val nan get() = NotANumberException("Not a number")
    val nab get() = NotABooleanException("Not a boolean")

    abstract fun evaluate(): OnyxClassInstance
}