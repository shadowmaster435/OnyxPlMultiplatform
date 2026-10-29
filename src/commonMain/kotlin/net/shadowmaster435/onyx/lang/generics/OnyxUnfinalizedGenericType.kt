package net.shadowmaster435.onyx.lang.generics

import net.shadowmaster435.onyx.exception.NonExistenceException
import net.shadowmaster435.onyx.exception.UninitializedException
import net.shadowmaster435.onyx.lang.builtin.clazz.primitive.anyDesc
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.clazz.OnyxClassInstance
import net.shadowmaster435.onyx.lang.clazz.OnyxObject
import net.shadowmaster435.onyx.lang.enums.code.OnyxNullability
import net.shadowmaster435.onyx.lang.enums.generics.OnyxGenericCovariance
import net.shadowmaster435.onyx.lang.enums.keyword.OnyxKeywordType
import net.shadowmaster435.onyx.lang.enums.lang.OnyxClassType
import net.shadowmaster435.onyx.lang.enums.operator.OnyxOperator
import net.shadowmaster435.onyx.lang.function.AbstractOnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxFieldImpl
import net.shadowmaster435.onyx.lang.impl.OnyxFinalizable
import net.shadowmaster435.onyx.lang.pkg.OnyxPackage
import net.shadowmaster435.onyx.lang.util.*
import net.shadowmaster435.onyx.util.OnyxModifiers
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxUnfinalizedGenericType(
    genericNameUnfinal: String,
    variance: OnyxGenericCovariance,
    nullability: OnyxNullability,
    val boundsQName: String
): OnyxGenericType(
    genericNameUnfinal,
    anyDesc,
    variance,
    nullability
), OnyxFinalizable<OnyxGenericType> {
    //region Boilerplate
    override var genericType: OnyxClassDescriptor?
        set(_) {throw uninitialized}
        get() = throw uninitialized
    override val fieldEntries: Array<OnyxFieldImpl>
        get() = throw uninitialized
    override val functionEntries: Array<AbstractOnyxFunctionDescriptor>
        get() = throw uninitialized
    override val classEntries: Array<OnyxClassDescriptor>
        get() = throw uninitialized
    override val fields: HashMap<String, Int>
        get() = throw uninitialized
    override val functions: HashMap<String, Int>
        get() = throw uninitialized
    override val classes: HashMap<String, Int>
        get() = throw uninitialized
    override val pkg: OnyxPackage
        get() = throw uninitialized
    override val name = genericName
    override val equalsFunction: AbstractOnyxFunctionDescriptor
        get() = throw uninitialized
    override val classType: OnyxClassType
        get() = throw uninitialized
    override val companions: List<OnyxObject>
        get() = throw uninitialized
    override val constructors: List<AbstractOnyxFunctionDescriptor>
        get() = throw uninitialized
    override val genericTuples: OnyxGenericTuples
        get() = throw uninitialized
    override val hashcodeFunction: AbstractOnyxFunctionDescriptor
        get() = throw uninitialized
    override val operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor>
        get() = throw uninitialized
    override val supertypes: List<OnyxClassDescriptor>
        get() = throw uninitialized
    override val modifiers: OnyxModifiers
        get() = throw uninitialized
    override fun getField(name: String) = throw uninitialized
    override fun getField(refIndex: Int) = throw uninitialized
    override fun getFieldRefIndex(name: String) = throw uninitialized
    override fun getOperatorOverload(op: OnyxOperator) = throw uninitialized
    override fun castableTo(other: OnyxClassDescriptor) = throw uninitialized

    override fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        supertypes: List<OnyxClassDescriptor>,
        companion: OnyxObject?,
        fields: LinkedHashMap<String, OnyxFieldImpl>,
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor>,
        classes: LinkedHashMap<String, OnyxClassDescriptor>,
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor>,
        genericTuples: OnyxGenericTuples,
        modifiers: OnyxModifiers
    ) = throw uninitialized

    override fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        companion: OnyxObject?,
        fields: LinkedHashMap<String, OnyxFieldImpl>,
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor>,
        classes: LinkedHashMap<String, OnyxClassDescriptor>,
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor>,
        genericTuples: OnyxGenericTuples,
        modifiers: OnyxModifiers
    ) = throw uninitialized

    override fun extend(
        name: String,
        pkg: OnyxPackage,
        type: OnyxClassType,
        supertype: OnyxClassDescriptor,
        companion: OnyxObject?,
        fields: LinkedHashMap<String, OnyxFieldImpl>,
        functions: LinkedHashMap<String, AbstractOnyxFunctionDescriptor>,
        classes: LinkedHashMap<String, OnyxClassDescriptor>,
        operatorOverloads: EnumMap<OnyxOperator, AbstractOnyxFunctionDescriptor>,
        genericTuples: OnyxGenericTuples,
        modifiers: OnyxModifiers
    ) = throw uninitialized
    override fun erase() = throw uninitialized
    override fun cast(from: OnyxClassInstance) = throw uninitialized
    override fun safeCast(from: OnyxClassInstance) = throw uninitialized
    override fun castableTo(from: OnyxClassInstance) = throw uninitialized
    //endregion
    override fun createFinal() = OnyxGenericType(
        genericName,
        OnyxPackage.loadClass(boundsQName) ?: throw NonExistenceException("Cannot find class '$boundsQName'"),
        variance,
        nullability
    )

    companion object {
        private val uninitialized get() = UninitializedException("Generic is not initialized")
        val definitionTemplate = ParsinatorTemplate {
            chain(false) {
                branch(false) {
                    chain(true) {
                        use(OnyxKeywordType.covarianceTemplate)
                        whitespace(false)
                    } makes {(it as list)[0]}
                    exit(0) makes { OnyxGenericCovariance.NONE }
                }
                name(false, nameRegex, numbersRegex)

                whitespace(true)
                chain(true) {
                    token(false, ":", false, true)
                    whitespace(true)
                    use(typeTemplate)
                    branch(false) {
                        token(false, "?", false) makes {
                            OnyxNullability.NULLABLE
                        }
                        exit(0) makes { OnyxNullability.NON_NULLABLE }
                    }
                }

            } makes {
                it as list
                val covariance = it[0] as OnyxGenericCovariance
                val name = it[1] as String
                val bounds = it.getOrNull(2) as? List<*>
                val boundsNullability = (bounds?.getOrNull(1) as? OnyxNullability) ?: OnyxNullability.INPUT
                val boundsQName = (bounds?.getOrNull(0) as? String) ?: anyDesc.qualifiedName
                OnyxUnfinalizedGenericType(
                    name,
                    covariance,
                    boundsNullability,
                    boundsQName
                )
            }
        }

        val inputParamTemplate = ParsinatorTemplate {
            chain(false) {
                use(typeTemplate)
                whitespace(true)
                branch(false) {
                    token(false, "?", false) makes {
                        OnyxNullability.NULLABLE
                    }
                    exit(0) makes { OnyxNullability.NON_NULLABLE }
                }
            } makes {
                it as List<*>
                OnyxUnfinalizedGenericType(
                    "",
                    OnyxGenericCovariance.NONE,
                    it[1] as OnyxNullability,
                    it[0] as String
                )
            }
        }

        val definitionInputParamTemplate = ParsinatorTemplate {
            chain(false) {
                branch(false) {
                    chain(true) {
                        use(OnyxKeywordType.covarianceTemplate)
                        whitespace(false)
                    } makes {(it as list)[0]}
                    exit(0) makes { OnyxGenericCovariance.NONE }
                }
                whitespace(true)
                use(typeTemplate)
                whitespace(true)
                branch(false) {
                    chain(true) {
                        token(false, "&", false, true)
                        whitespace(true)
                        token(false, "Any", false, true)
                        whitespace(false)
                    } makes {
                        OnyxNullability.TRUE_NON_NULLABLE
                    }
                    token(false, "?", false) makes {
                        OnyxNullability.NULLABLE
                    }
                    exit(0) makes { OnyxNullability.NON_NULLABLE }
                }
            } makes {
                it as List<*>
                OnyxUnfinalizedGenericType(
                    "",
                    it[0] as OnyxGenericCovariance,
                    it[2] as OnyxNullability,
                    it[1] as String
                )
            }
        }
    }
}