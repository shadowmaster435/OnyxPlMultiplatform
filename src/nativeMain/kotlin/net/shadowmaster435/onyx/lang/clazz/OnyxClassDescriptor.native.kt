package net.shadowmaster435.onyx.lang.clazz

import net.shadowmaster435.onyx.llvm.classStructs
import net.shadowmaster435.onyx.llvm.value_classes.GlobalStruct

actual val OnyxClassDescriptor.compileFunc: (Any?) -> Any?
    get() = TODO("Not yet implemented")
actual val OnyxClassDescriptor.onCreation: (Any?) -> Unit
    get() = {
        classStructs[this] = compileConst(it).held as GlobalStruct
    }