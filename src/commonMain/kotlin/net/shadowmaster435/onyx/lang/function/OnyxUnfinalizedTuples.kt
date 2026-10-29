package net.shadowmaster435.onyx.lang.function

import net.shadowmaster435.onyx.exception.IncorrectParametersException
import net.shadowmaster435.onyx.exception.InvalidParameterCountException
import net.shadowmaster435.onyx.exception.TypeMismatchException
import net.shadowmaster435.onyx.exception.UninitializedException
import net.shadowmaster435.onyx.exception.UnknownException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.OnyxNull
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.lang.util.list
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxUnfinalizedTuples(private val tuplesUnfinal: List<OnyxUnfinalizedTuple>): OnyxTuples(tuplesUnfinal), OnyxFinalizable<OnyxTuples> {

    override val requiredTuples get() = throw uninitialized

    override fun normal(v: OnyxClassInstance, t: OnyxClassDescriptor) = throw uninitialized
    override fun strict(v: OnyxClassInstance, t: OnyxClassDescriptor) = throw uninitialized
    override fun error(v: OnyxClassInstance, t: OnyxClassDescriptor) = throw uninitialized



    override fun iter(input: Iterator<OnyxClassInstance>, strict: Boolean) = throw uninitialized

    override fun validateStrict(input: Array<out OnyxClassInstance>) = throw uninitialized
    override fun validateStrict(input: List<OnyxClassInstance>) = throw uninitialized
    override fun validate(input: Array<out OnyxClassInstance>) = throw uninitialized
    override fun validate(input: List<OnyxClassInstance>) = throw uninitialized
    override fun createFinal() = OnyxTuples(
        buildList { tuplesUnfinal.forEach { add(it.createFinal()) } }
    )


    companion object {
        private val uninitialized get() = UninitializedException("Generic is not initialized")
        val empty = OnyxUnfinalizedTuples(listOf())
        val functionTemplate = ParsinatorTemplate {
            chain(false) {
                token(false, "(", false, true)
                whitespace(true)
                repeating(false, true) {
                    use(OnyxUnfinalizedTuple.functionTemplate)
                    trailing {
                        whitespace(true)
                        token(false, ",", excludeFromChains = true)
                    }
                }
                whitespace(true)
                token(false, ")", false, true)
            } makes {
                it as list
                OnyxUnfinalizedTuples(buildList {
                    (it[0] as list).forEach { tuple ->
                        add(tuple as OnyxUnfinalizedTuple)
                    }
                })
            }
        }
    }

}