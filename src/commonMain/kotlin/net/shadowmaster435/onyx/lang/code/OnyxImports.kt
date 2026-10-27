package net.shadowmaster435.onyx.lang.code

class OnyxImports {
    private val imports = hashMapOf<String, String>()
    operator fun get(name: String) = imports[name]


}