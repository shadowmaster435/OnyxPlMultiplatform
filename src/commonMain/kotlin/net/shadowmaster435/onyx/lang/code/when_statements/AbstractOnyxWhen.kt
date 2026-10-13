package net.shadowmaster435.onyx.lang.code.when_statements

import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxRunnableImpl

sealed class AbstractOnyxWhen(
    val returnType: OnyxClassDescriptor,
): OnyxRunnableImpl {
    abstract val isTable: Boolean

}