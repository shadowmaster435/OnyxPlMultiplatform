package net.shadowmaster435.onyx.ext

import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min


fun String.indexToLineColumn(index: Int): Pair<Int, Int> {
    if (index !in indices) throw IndexOutOfBoundsException("Index $index out of string bounds")
    var currentIndex = index
    var line = 0
    var column = 0
    for (it in this) {
        if (it == '\n') {
            line++
            currentIndex -= column
            column = 0
        } else column++
        if (column >= currentIndex) break
    }
    return line to currentIndex
}

fun String.hexToFloat(): Float {
    if (length != 8) throw RuntimeException("Invalid float hex string length $length")
    return Float.fromBits(hexToInt())
}

fun String.hexToDouble(): Double {
    if (length != 16) throw RuntimeException("Invalid float hex string length $length")
    return Double.fromBits(hexToLong())
}




fun String.substringUntil(endAt: Regex, startIndex: Int = 0): String {
    if (startIndex >= length - 1) return this
    var i = max(startIndex, 0)
    while (i < length) {
        if (substring(i + startIndex).matches(endAt)) return substring(0..<i)
        i++
    }
    return this
}

fun String.substringUntil(endAt: String, startIndex: Int = 0): String {
    if (endAt.length > length || startIndex >= length - 1) return this
    var i = max(startIndex, 0)
    while (i < length) {
        val s = startIndex + i
        val e = s + endAt.length
        if (e >= length) return this
        if (substring(s..<e) == endAt) return substring(0..<i)
        i++
    }
    return this
}

fun String.parseFuncCallLikeList(): List<Triple<String, List<String>, Int>> {
    val str = replace("\n", "").replace("\t", "")
    val strings = mutableListOf<String>()
    var holderString = ""
    var nameStr = ""
    val out = mutableListOf<Triple<String, List<String>, Int>>()
    var parsedOpString = false
    var literalEscape = false
    var passedInitialSpaces = false
    var i = 0
    fun finish() {
        out.add(Triple(nameStr, strings.toList(), i))
        holderString = ""
        nameStr = ""
        strings.clear()
        parsedOpString = false
        literalEscape = false
    }
    for (char in str) {
        if (!parsedOpString) {
            when(char) {
                ' ', '\t', '\n' -> if (!passedInitialSpaces) holderString += char
                '(', ')' -> if (!literalEscape) {
                    parsedOpString = true
                    nameStr = holderString
                    holderString = ""
                    passedInitialSpaces = true
                } else {
                    holderString += char
                    literalEscape = false
                    passedInitialSpaces = true
                }
                '\\' -> {
                    literalEscape = true
                    passedInitialSpaces = true
                }
                else -> holderString += char
            }
        } else when(char) {
            ' ' -> if (!passedInitialSpaces) holderString += char
            '(', ')' -> {
                if (!literalEscape) {
                    strings.add(holderString)
                    holderString = ""
                    finish()
                    passedInitialSpaces = true
                } else {
                    holderString += char
                    literalEscape = false
                    passedInitialSpaces = true
                }
            }
            ',' -> if (!literalEscape) {
                strings.add(holderString)
                holderString = ""
                passedInitialSpaces = true
            } else {
                holderString += char
                literalEscape = false
                passedInitialSpaces = true
            }
            '\\' -> {
                literalEscape = true
                passedInitialSpaces = true

            }
            else -> holderString += char
        }
        i++
    }
    return out.toList()
}

private fun String.substr(range: IntRange, printError: Boolean = false, errorCallback: (String) -> Unit = {}): String {
    return try {
        substring(range)
    } catch (e: Exception) {
        if (printError) {
            e.cause?.let {
                println(it.message)
            }
            e.printStackTrace()
        }
        val m = min(length, range.last)
        val str = substring(min(max(range.first, 0),m)..<min(length, range.last))
        errorCallback(str)
        str
    }
}
fun String.binaryToFloat() = binaryToHexString().hexToFloat()
fun String.binaryToDouble() = binaryToHexString().hexToDouble()

fun String.binaryToFloatOrNull() = try {binaryToFloat()} catch (_: Exception) {null}
fun String.binaryToDoubleOrNull() = try {binaryToDouble()} catch (_: Exception) {null}
fun String.hexToFloatOrNull() = try {hexToFloat()} catch (_: Exception) {null}
fun String.hexToDoubleOrNull() = try {hexToDouble()} catch (_: Exception) {null}
fun String.hexToIntOrNull() = try {hexToInt()} catch (_: Exception) {null}
fun String.hexToLongOrNull() = try {hexToLong()} catch (_: Exception) {null}
fun String.hexToByteOrNull() = try {hexToByte()} catch (_: Exception) {null}
fun String.hexToShortOrNull() = try {hexToShort()} catch (_: Exception) {null}
fun String.binaryToIntOrNull() = try {binaryToHexString().hexToInt()} catch (_: Exception) {null}
fun String.binaryToLongOrNull() = try {binaryToHexString().hexToLong()} catch (_: Exception) {null}
fun String.binaryToByteOrNull() = try {binaryToHexString().hexToByte()} catch (_: Exception) {null}
fun String.binaryToShortOrNull() = try {binaryToHexString().hexToShort()} catch (_: Exception) {null}
fun String.hexToUIntOrNull() = try {hexToUInt()} catch (_: Exception) {null}
fun String.hexToULongOrNull() = try {hexToULong()} catch (_: Exception) {null}
fun String.hexToUByteOrNull() = try {hexToUByte()} catch (_: Exception) {null}
fun String.hexToUShortOrNull() = try {hexToUShort()} catch (_: Exception) {null}
fun String.binaryToUIntOrNull() = try {binaryToHexString().hexToUInt()} catch (_: Exception) {null}
fun String.binaryToULongOrNull() = try {binaryToHexString().hexToULong()} catch (_: Exception) {null}
fun String.binaryToUByteOrNull() = try {binaryToHexString().hexToUByte()} catch (_: Exception) {null}
fun String.binaryToUShortOrNull() = try {binaryToHexString().hexToUShort()} catch (_: Exception) {null}
fun String.binaryToLong() = binaryToHexString().hexToLong()
fun String.binaryToInt() = binaryToHexString().hexToInt()
fun String.binaryToShort() = binaryToHexString().hexToShort()
fun String.binaryToByte() = binaryToHexString().hexToByte()


fun String.binaryToHexString(): String {
    val extra = run {
        var str = ""
        repeat(length % 4) {
            str += "0"
        }
        str
    }
    var str = extra
    var tst = ""
    repeat(ceil((length / 4.0)).toInt()) {
        tst += substr(it * 4..<(it * 4) + 4)
        str += when(val slice = substr(it * 4..<(it * 4) + 4)) {
            "0000" -> "0"
            "0001" -> "1"
            "0010" -> "2"
            "0011" -> "3"
            "0100" -> "4"
            "0101" -> "5"
            "0110" -> "6"
            "0111" -> "7"
            "1000" -> "8"
            "1001" -> "9"
            "1010" -> "a"
            "1011" -> "b"
            "1100" -> "c"
            "1101" -> "d"
            "1110" -> "e"
            "1111" -> "f"
            else -> throw RuntimeException("Failed to convert binary string to hex string due to non-binary character: $slice")
        }

    }
    return str
}

fun String.hexToBinaryString(): String {
    var str = ""
    val lower = lowercase()
    lower.forEach {
        str = when(it) {
            '0' -> "0000"
            '1' -> "0001"
            '2' -> "0010"
            '3' -> "0011"
            '4' -> "0100"
            '5' -> "0101"
            '6' -> "0110"
            '7' -> "0111"
            '8' -> "1000"
            '9' -> "1001"
            'a' -> "1010"
            'b' -> "1001"
            'c' -> "1010"
            'd' -> "1011"
            'e' -> "1110"
            'f' -> "1111"
            else -> throw RuntimeException("Failed to convert hex string to binary string due to non hex character: $it")
        } + str
    }
    return str
}

