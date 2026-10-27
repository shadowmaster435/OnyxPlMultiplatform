package net.shadowmaster435.onyx.lang.clazz

import net.shadowmaster435.onyx.lang.function.OnyxTuples
import net.shadowmaster435.onyx.lang.impl.OnyxAccessImpl
import net.shadowmaster435.onyx.util.OnyxModifiers
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxStructDescriptor(
    override val modifiers: OnyxModifiers,
    val tuples: OnyxTuples
): OnyxAccessImpl {


    companion object {
        val template: ParsinatorTemplate = TODO("Struct template")
    }
}