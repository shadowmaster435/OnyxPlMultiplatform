package net.shadowmaster435.onyx.util


expect fun iterateDirectoryTree(absolutePath: String, consumer: (relative: String, isDirectory: Boolean) -> Unit)
expect fun readFileOrNull(absolutePath: String): ByteArray?
expect fun readFileContentsOrNull(absolutePath: String): String?
expect fun fileExists(absolutePath: String): Boolean
fun readFile(absolutePath: String) = readFileOrNull(absolutePath)
    ?: throw NullPointerException("File at $absolutePath does not exist")
fun readFileContents(absolutePath: String) = readFileOrNull(absolutePath)
    ?: throw NullPointerException("File at $absolutePath does not exist")