package net.shadowmaster435.onyx.lang.clazz

import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxObject(
    desc: OnyxClassDescriptor
): OnyxClassInstance(desc) {
    override val isObject = false

    companion object {
        val template: ParsinatorTemplate = TODO("Object class parsinator template")
    }
}