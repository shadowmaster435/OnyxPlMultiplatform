@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package net.shadowmaster435.onyx.lang.impl

import net.shadowmaster435.onyx.compiler.OnyxCompiledType

interface OnyxCompilableConstImpl {
    fun compileConst(): OnyxCompiledType
}
