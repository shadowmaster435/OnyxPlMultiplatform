package net.shadowmaster435.onyx.lang.code

import net.shadowmaster435.onyx.exception.UnknownException

class OnyxImports {
    private val imports = hashMapOf<String, String>()
    operator fun get(name: String) = imports[name]


}