package net.shadowmaster435.onyx.lang.pkg

import net.shadowmaster435.onyx.exception.UnknownException
import net.shadowmaster435.onyx.lang.clazz.OnyxClassDescriptor
import net.shadowmaster435.onyx.lang.enums.lang.OnyxPackageMember
import net.shadowmaster435.onyx.lang.field.OnyxFieldDescriptor
import net.shadowmaster435.onyx.lang.function.AbstractOnyxFunctionDescriptor
import net.shadowmaster435.onyx.lang.impl.OnyxAccessImpl
import net.shadowmaster435.onyx.lang.util.accessChainStringTemplate
import net.shadowmaster435.parsinator.dsl.ParsinatorTemplate

class OnyxPackage(val packageName: String, val parent: OnyxPackage?) {
    private val children = hashMapOf<String, OnyxPackage>()
    private val functions = hashMapOf<String, AbstractOnyxFunctionDescriptor>()
    private val classes = hashMapOf<String, OnyxClassDescriptor>()
    private val fields = hashMapOf<String, OnyxFieldDescriptor>()
    val isRoot get() = packageName.isEmpty()


    val qualifiedName = run {
        var cur = ""
        var parent = parent
        while (parent != null) {
            if (parent !== globalRoot) cur = "${parent.packageName}.$cur"
            parent = parent.parent
        }
        "$cur$packageName"
    }
    fun packageTreeString(indentLevel: Int = 0): String {
        var str = " ".repeat(indentLevel) + packageName + "\n"
        for ((_, value) in children) {
            str += value.packageTreeString(indentLevel + 1)
        }
        return str
    }

    fun add(packageName: String): OnyxPackage {
        var wasPresent = true
        val new = children[packageName] ?: run {
            wasPresent = false
            OnyxPackage(packageName, this)
        }
        if (!wasPresent) children[packageName] = new
        return new
    }
    fun treeString(indentLevel: Int = 0): String {
        var str = " ".repeat(indentLevel) + packageName + "\n"
        forEachClass {
            str += it.name + "\n"
        }
        for ((_, value ) in children) {
            str += value.packageTreeString(indentLevel + 1)
        }
        return str
    }
    fun relativize(qualifiedName: String) =
        if (isRoot) qualifiedName
        else qualifiedName.replaceFirst("$qualifiedName.", "")


    operator fun set(name: String, clazz: OnyxClassDescriptor) {
        classes[name] = clazz
    }
    operator fun set(name: String, field: OnyxFieldDescriptor) {
        fields[name] = field
    }
    operator fun set(name: String, func: AbstractOnyxFunctionDescriptor) {
        functions[name] = func
    }

    fun findClassBySimpleName(name: String): OnyxClassDescriptor? {
        var clazz: OnyxClassDescriptor? = null
        forEachClass {
            if (it.classType.name == name) {
                clazz = it
                return@forEachClass
            }
        }
        if (clazz == null) {
            children.forEach {
                val subClass = it.value.findClassBySimpleName(name)
                if (subClass != null) return subClass
            }
        }
        return clazz
    }

    fun forEachFunction(consumer: (AbstractOnyxFunctionDescriptor) -> Unit) {
        functions.forEach { (_, function) ->
            consumer(function)
        }
    }
    fun forEachClass(consumer: (OnyxClassDescriptor) -> Unit) {
        classes.forEach { (_, function) ->
            consumer(function)
        }
    }
    fun forEachField(consumer: (OnyxFieldDescriptor) -> Unit) {
        fields.forEach { (_, function) ->
            consumer(function)
        }
    }

    fun forEachMember(consumer: (inst: OnyxAccessImpl, type: OnyxPackageMember) -> Unit) {
        forEachClass { consumer(it, OnyxPackageMember.CLASS) }
        forEachField { consumer(it, OnyxPackageMember.FIELD) }
        forEachFunction { consumer(it, OnyxPackageMember.FUNCTION) }
    }

    override fun toString() = qualifiedName

    fun byQualifiedName(qualifiedName: String) = getOrMakeByQualifiedName(relativize(qualifiedName), this)
    fun exists(qualifiedName: String) = getOrMakeByQualifiedName(relativize(qualifiedName), this)



    override fun hashCode() = qualifiedName.hashCode()

    override fun equals(other: Any?) = other is OnyxPackage && qualifiedName == other.qualifiedName


    companion object {



        val importTemplate = ParsinatorTemplate {
            chain(false) {
                token(false, "import")
                whitespace(false)
                use(accessChainStringTemplate(::byQualifiedName))
                chain(true) {
                    whitespace(true)
                    token(true, ".")
                    whitespace(true)
                    token(true, "*")
                } makes {true}
            }
        }
        val template = ParsinatorTemplate {
            chain(false) {
                token(false, "package")
                whitespace(false)
                use(accessChainStringTemplate(::byQualifiedName))
            }
        }

        private fun getOrMakeByQualifiedName(name: String, startPkg: OnyxPackage = globalRoot): OnyxPackage {
            val arr = name.split(".")
            var current: OnyxPackage = startPkg
            for (string in arr) current = current.children[string] ?: current.add(string)
            return current
        }


        fun byQualifiedName(qualifiedName: String) = getOrMakeByQualifiedName(qualifiedName)
        fun exists(qualifiedName: String): Boolean {
            val arr = qualifiedName.split(".")
            var current: OnyxPackage = globalRoot
            for (string in arr) current = current.children[string] ?: return false
            return true
        }

        fun findClassByQName(qualifiedName: String): OnyxClassDescriptor? {
            val arr = qualifiedName.split(".")
            var current = globalRoot
            var currentClass: OnyxClassDescriptor? = null
            var subclassIndex = 0
            for (entry in arr) {
                if (currentClass == null) {
                    val child = current.children[entry]
                    subclassIndex++
                    if (child != null) {
                        current = child
                        continue
                    } else currentClass = current.classes[entry] ?: return null
                }
                currentClass = currentClass.findSubclass(arr.subList(subclassIndex, arr.size - 1))
            }
            return currentClass
        }
    }

}