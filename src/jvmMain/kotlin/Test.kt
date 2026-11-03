import net.shadowmaster435.onyx.lang.enums.code.OnyxNullability
import net.shadowmaster435.onyx.lang.generics.OnyxUnfinalizedGenericParam
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate
import net.shadowmaster435.parsinator.util.Stack


val inputParamTemplate = ParsinatorTemplate {
    chain(false) {
        recurse(false, {
            val b = it.char == '<'
            if (b) it.inc()
            b
        }, {
            val b = it.char == '>'
            if (b) it.inc()
            b
        }) {
            branch(false) {
                token(false, ",", false) makes {
                    0
                }
                chain(false) {
                    name(false, Regex("[a-zA-Z0-9_.]"))
                    branch(true) {
                        token(false, "?", false) makes {
                            OnyxNullability.NULLABLE
                        }
                        token(false, "!", false) makes {
                            OnyxNullability.NEVER_NULLABLE
                        }
                        manual(false, {true}) {
                            held = OnyxNullability.NON_NULLABLE
                            if (it.char == '<') {
                                var ofs = run {
                                    var i = 0
                                    var depth = 0
                                    var started = false
                                    while (i < it.index) {
                                        when(it.string.getOrNull(i)) {
                                            '<' -> {
                                                started = true
                                                depth++
                                            }
                                            '>' -> depth--
                                        }
                                        if (started && depth == 0) break
                                        i++
                                    }
                                    i
                                }
                                var depth = 0
                                var started = false
                                while (ofs < it.string.length) {
                                    when(it.string.getOrNull(ofs)) {
                                        '<' -> {
                                            started = true
                                            depth++
                                        }
                                        '>' -> depth--
                                    }
                                    if (depth <= 0 && started)
                                        break
                                    ofs++
                                }
                                held = when(it.atOffset(ofs + 1)) {
                                    "?" -> OnyxNullability.NULLABLE
                                    "!" -> OnyxNullability.NEVER_NULLABLE
                                    else -> OnyxNullability.NON_NULLABLE
                                }
                            }
                            null
                        }
                    }
                }
            }
        }
    } makes {
        val list = (it as List<*>).first() as List<*>
        println(list)
        class Holder {
            var nullability = OnyxNullability.NON_NULLABLE
            var name = ""
            var params = listOf<OnyxUnfinalizedGenericParam>()
            fun create() = OnyxUnfinalizedGenericParam(
                nullability, name, params
            )

            override fun toString() = name
        }

        var stack = Stack<Holder>()
        stack.push(Holder())
        fun recurse(list: List<*>): List<OnyxUnfinalizedGenericParam> = buildList {
            list.forEach { subEntry ->
                when(subEntry) {
                    is String -> stack.peek().name = subEntry
                    is List<*> -> {
                        val next = Holder()
                        stack.push(next)
                        val recursed = recurse(subEntry)
                        stack.pop()
                        val peeking = stack.peek()
                        peeking.params += recursed

                    }
                    is OnyxNullability -> stack.peek().nullability = subEntry
                    0 -> {
                        add(stack.pop().create())
                        stack.push(Holder())

                    }
                }
            }
            if (stack.peek().name.isNotEmpty()) add(stack.peek().create())
        }
        recurse(list).apply {
            println(this)
        }
    }
}

fun main() {
    println(inputParamTemplate.wrap().parse("test<testa?,fd<fsd!>!>?"))

//    println(OnyxUnfinalizedFieldDesc.definitionTemplate.wrap().parse("val test = 4"))
}
