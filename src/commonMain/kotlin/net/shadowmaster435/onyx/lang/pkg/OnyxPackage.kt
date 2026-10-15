package net.shadowmaster435.onyx.lang.pkg

class OnyxPackage(val packageName: String, val parent: OnyxPackage?) {
    private val children = hashMapOf<String, OnyxPackage>()
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

    override fun equals(other: Any?) = other === this

    companion object {

        fun byQualifiedName(qualifiedName: String) = byQualifiedName(globalRoot, qualifiedName)

        fun byQualifiedName(pkg: OnyxPackage, qualifiedName: String): OnyxPackage? {
            val arr = qualifiedName.split(".")
            var pkg = pkg
            for (str in arr) {
                pkg = pkg.children[str] ?: return null
            }
            return pkg
        }
    }

    override fun hashCode(): Int {
        var result = packageName.hashCode()
        result = 31 * result + parent.hashCode()
        result = 31 * result + children.hashCode()
        result = 31 * result + qualifiedName.hashCode()
        return result
    }

}