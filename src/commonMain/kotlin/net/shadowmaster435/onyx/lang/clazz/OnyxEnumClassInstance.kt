package net.shadowmaster435.onyx.lang.clazz

import net.shadowmaster435.onyx.util.ListBuilder
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxEnumClassInstance(
    desc: OnyxClassDescriptor,
    val enumValues: List<OnyxEnumValue>
): OnyxClassInstance(desc) {
    override val isObject = false

    private val indexMap by lazy {
        HashMap<String, Int>().apply {
            var i = 0
            enumValues.forEach {
                put(it.name, i)
                i++
            }
        }
    }

    constructor(desc: OnyxClassDescriptor, builder: Builder.() -> Unit) : this(
        desc, Builder(desc).apply { builder() }.toList()
    )
    class Builder internal constructor(private val desc: OnyxClassDescriptor): ListBuilder<OnyxEnumValue>() {
        fun value(name: String, properties: Map<String, OnyxClassInstance>) {
            add(OnyxEnumValue(name, desc, size).apply {
                properties.forEach {
                    setField(it.key, it.value)
                }
            })
        }
    }
    companion object {
        val template: ParsinatorTemplate = TODO("Enum class parsinator template")
    }
}
