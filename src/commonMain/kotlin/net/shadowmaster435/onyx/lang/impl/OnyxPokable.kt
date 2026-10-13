package net.shadowmaster435.onyx.lang.impl

import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance

interface OnyxPokable {
    fun poke()
    fun pokeWith(stick: OnyxClassInstance?) {}
}