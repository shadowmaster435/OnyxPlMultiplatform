package net.shadowmaster435.onyx.util

import net.shadowmaster435.onyx.exception.ImpossibleException

internal fun <T> orThrow(v: T?): T = v ?: throw ImpossibleException()
