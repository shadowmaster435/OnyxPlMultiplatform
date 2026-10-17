package net.shadowmaster435.onyx.util

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath


actual fun iterateDirectoryTree(
    absolutePath: String,
    consumer: (relative: String, isDirectory: Boolean) -> Unit
) {
    val fs = FileSystem.SYSTEM
    val rootPath = absolutePath.toPath()
    val rootDir = fs.listOrNull(rootPath) ?: return
    fun recurse(dir: List<Path>) {
        dir.forEach {
            val isDir = fs.metadataOrNull(it)?.isDirectory == true
            if (isDir) recurse(fs.list(it))
            consumer(it.relativeTo(rootPath).toString(), isDir)
        }
    }
    recurse(rootDir)
}

actual fun fileExists(absolutePath: String) = FileSystem.SYSTEM.run {
    this.exists(absolutePath.toPath())
}

actual fun readFileOrNull(absolutePath: String): ByteArray? {
    return if (fileExists(absolutePath)) FileSystem.SYSTEM.read(absolutePath.toPath()) {
        this.readByteArray()
    } else null
}
actual fun readFileContentsOrNull(absolutePath: String): String? {
    return if (fileExists(absolutePath)) FileSystem.SYSTEM.read(absolutePath.toPath()) {
        readUtf8()
    } else null
}