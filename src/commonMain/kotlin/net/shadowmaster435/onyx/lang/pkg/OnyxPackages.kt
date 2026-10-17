package net.shadowmaster435.onyx.lang.pkg

val globalRoot = OnyxPackage("", null)

val onyxRoot = globalRoot.add("onyx")
val onyxLang = onyxRoot.add("lang")
val onyxIo = onyxRoot.add("io")
val onyxCollections = onyxRoot.add("collections")
val onyxPrimitives = onyxLang.add("primitives")
val onyxReflection = onyxRoot.add("reflection")



