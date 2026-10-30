package net.shadowmaster435.onyx.lang.generics

import net.shadowmaster435.onyx.exception.UninitializedException
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.lang.util.list
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxUnfinalizedGenericTuples(private val tuplesUnfinal: List<OnyxUnfinalizedGenericType>): OnyxGenericTuples(tuplesUnfinal), OnyxFinalizable<OnyxGenericTuples> {
    override val requiredTuples get() = throw uninitialized
    override fun normal(v: OnyxGenericType, t: OnyxGenericType) = throw uninitialized
    override fun iter(input: Iterator<OnyxGenericType>) = throw uninitialized
    override fun validate(input: Array<out OnyxGenericType>) = throw uninitialized
    override fun validate(input: List<OnyxGenericType>) = throw uninitialized
    override fun createFinal() = OnyxGenericTuples(
        buildList { tuplesUnfinal.forEach { add(it.checkCreateFinal()) } }
    )

    companion object {
        private val uninitialized get() = UninitializedException("Generic is not initialized")
        val definitionTemplate: ParsinatorTemplate = ParsinatorTemplate {
            branch(false) {
                chain(true) {
                    token(false, "<", false, true)
                    whitespace(true)
                    repeating(false, false) {
                        use(OnyxUnfinalizedGenericType.definitionTemplate)
                        whitespace(true)
                        trailing { token(true, ",", false, true) }
                    }
                    whitespace(true)
                    token(false, ">",false, true)
                } makes {
                    it as list
                    val entries = it[0] as List<*>
                    OnyxUnfinalizedGenericTuples(buildList {
                        for (entry in entries) {
                            if (entry is OnyxUnfinalizedGenericType) add(entry)
                        }
                    })
                }
                exit(0) makes {emptyList<OnyxUnfinalizedGenericType>()}
            }
        }
        val inputParamTemplate: ParsinatorTemplate = ParsinatorTemplate {
            chain(false) {
                token(false, "<", excludeFromChains = true)
                whitespace(true)
                repeating(true, false) {
                    use(OnyxUnfinalizedGenericType.inputParamTemplate)
                    whitespace(true)
                    trailing { token(true, ",", excludeFromChains = true) }
                }
                whitespace(true)
                token(false, ">", excludeFromChains = true)
            } makes {
                it as List<*>
                val inputs = it[0] as List<*>
                buildList {
                    inputs.forEach { p -> add(p as OnyxUnfinalizedGenericType) }
                }
            }
        }
        val definitionInputParamTemplate = ParsinatorTemplate {
            chain(false) {
                token(false, "<", excludeFromChains = true)
                whitespace(true)
                repeating(true, false) {
                    use(OnyxUnfinalizedGenericType.definitionInputParamTemplate)
                    whitespace(true)
                    trailing { token(true, ",", excludeFromChains = true) }
                }
                whitespace(true)
                token(false, ">", excludeFromChains = true)
            } makes {
                it as List<*>
                val inputs = it[0] as List<*>
                buildList {
                    inputs.forEach { p -> add(p as OnyxUnfinalizedGenericType) }
                }
            }
        }
    }
}