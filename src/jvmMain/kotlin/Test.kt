import net.shadowmaster435.onyx.lang.field.OnyxUnfinalizedFieldDesc

fun main() {
    println(OnyxUnfinalizedFieldDesc.definitionTemplate.wrap().parse("val test"))
}
