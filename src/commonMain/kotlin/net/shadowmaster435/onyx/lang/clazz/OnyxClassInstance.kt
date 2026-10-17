package net.shadowmaster435.onyx.lang.clazz

import net.shadowmaster435.onyx.exception.ImpossibleException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxBoolean
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxString
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.number.OnyxInt
import net.shadowmaster435.onyx.lang.enums.modifier.OnyxModifierType
import net.shadowmaster435.onyx.lang.field.OnyxFieldInstance
import net.shadowmaster435.onyx.lang.function.OnyxExternalFunctionDescriptor
import net.shadowmaster435.onyx.lang.function.OnyxTuples
import net.shadowmaster435.onyx.lang.impl.OnyxPokable
import net.shadowmaster435.onyx.lang.impl.OnyxReferencableImpl

open class OnyxClassInstance(
    val desc: OnyxClassDescriptor,
): OnyxPokable, OnyxReferencableImpl {
    protected open val fieldData: MutableList<OnyxFieldInstance> = mutableListOf()
    protected open var initialized = false
    open val isObject = false
    override fun poke() {
        createFieldData()
    }

    override fun getRefVal(thisInstance: OnyxClassInstance?) = thisInstance
    override fun setRefVal(thisInstance: OnyxClassInstance?, v: OnyxClassInstance) {}

    open fun createFieldData() {
        var i = 0
        for ((_, fieldDesc) in this@OnyxClassInstance.desc.fields) {
            fieldData.add(desc.getField(fieldDesc).instantiate(i))
            i++
        }
    }
    open fun cast(to: OnyxClassDescriptor) = to.cast(this)
    open fun safeCast(to: OnyxClassDescriptor) = to.safeCast(this)
    open fun onyxIterator(): Iterator<OnyxClassInstance> =
        throw ImpossibleException("Tried to get iterator from non iterable class, this should never happen")


    open fun onyxEquals(other: OnyxClassInstance) = (desc.equalsFunction?.invoke(other)
        ?: throw ImpossibleException("No defined equals function, this should never happen.")) as OnyxBoolean

    open fun onyxHashcode() = (desc.hashcodeFunction?.invoke(this)
        ?: throw ImpossibleException("No defined hashcode function, this should never happen.")) as OnyxInt

    fun castableTo(to: OnyxClassDescriptor) = desc.castableTo(to)

    fun setField(refIndex: Int, value: OnyxClassInstance) {
        val field = fieldData.getOrNull(refIndex) ?: return
        field.set(value.cast(field.desc.classType))
    }
    fun setField(name: String, value: OnyxClassInstance) {
        val field = fieldData.getOrNull(desc.getFieldRefIndex(name)) ?: return
        field.set(value.cast(field.desc.classType))
    }
    fun getField(refIndex: Int) = fieldData[refIndex].get()
    fun getField(name: String) = fieldData[desc.getFieldRefIndex(name)].get()
    fun getFieldInstance(name: String) = fieldData[desc.getFieldRefIndex(name)]
    fun getFieldNullable(name: String) = fieldData.getOrNull(desc.getFieldRefIndex(name))?.get()



    companion object {


        val defaultEqualsFunction = OnyxExternalFunctionDescriptor(
            "equals",
            OnyxTuples.empty,
            listOf(
                OnyxModifierType.PUBLIC,
                OnyxModifierType.OPEN,
            )
        ) { thisInst, list -> thisInst?.onyxEquals(list.first()) ?: OnyxBoolean(false) }
        val defaultHashcodeFunction = OnyxExternalFunctionDescriptor(
            "hashCode",
            OnyxTuples.empty,
            listOf(
                OnyxModifierType.PUBLIC,
                OnyxModifierType.OPEN,
            )
        ) { thisInst, _ -> OnyxInt(thisInst.hashCode()) }
        val defaultToStringFunction = OnyxExternalFunctionDescriptor(
            "toString",
            OnyxTuples.empty,
            listOf(
                OnyxModifierType.PUBLIC,
                OnyxModifierType.OPEN,
            )
        ) { thisInst, _ -> OnyxString(thisInst.toString()) }
    }
}