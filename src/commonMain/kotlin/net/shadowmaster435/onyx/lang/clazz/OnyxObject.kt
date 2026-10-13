package net.shadowmaster435.onyx.lang.clazz

class OnyxObject(
    desc: OnyxClassDescriptor
): OnyxClassInstance(desc) {
    override val isObject = false
}