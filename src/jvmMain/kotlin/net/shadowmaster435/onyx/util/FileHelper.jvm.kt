package net.shadowmaster435.onyx.util

import java.io.File

actual fun iterateDirectoryTree(
    absolutePath: String,
    consumer: (relative: String, isDirectory: Boolean) -> Unit
) {
    val rootDir = File(absolutePath)
    fun recurse(dir: File) {
        dir.listFiles()?.forEach {
            val isDir = it.isDirectory
            if (isDir) recurse(it)
            consumer(dir.toRelativeString(rootDir), isDir)
        }
    }
    if (rootDir.exists() && rootDir.isDirectory) {
        recurse(rootDir)
    }
}

actual fun readFileOrNull(absolutePath: String) = File(absolutePath).run {
    if (exists()) readBytes()
    else null
}

actual fun readFileContentsOrNull(absolutePath: String) = File(absolutePath).run {
    if (exists()) readText()
    else null
}

actual fun fileExists(absolutePath: String) = File(absolutePath).exists()