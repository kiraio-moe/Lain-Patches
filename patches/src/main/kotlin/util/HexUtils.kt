package util

import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.rawResourcePatch

/**
 * A binary pattern matching helper that support wildcards.
 * Based on https://github.com/MorpheApp/morphe-patches-library/patch-library/src/main/kotlin/app/morphe/patches/all/misc/hex/HexPatchBuilder.kt
 */
fun wildcardHexPatch(
    ignoreMissingTargetFiles: Boolean = false,
    block: WildcardHexPatchBuilder.() -> Unit,
) = rawResourcePatch {
    execute {
        val replacements = WildcardHexPatchBuilder().apply(block)

        replacements
            .groupBy { it.targetFilePath }
            .forEach { (targetFilePath, replacements) ->
                val targetFile = get(targetFilePath, true)

                if (ignoreMissingTargetFiles && !targetFile.exists())
                    return@forEach

                var bytes = targetFile.readBytes()

                replacements.forEach { replacement ->
                    bytes = replacement.applyPattern(bytes)
                }

                targetFile.writeBytes(bytes)
            }
    }
}

@Suppress("JavaDefaultMethodsNotOverriddenByDelegation")
class WildcardHexPatchBuilder internal constructor(
    private val replacements: MutableSet<WildcardReplacement> = mutableSetOf(),
) : Set<WildcardReplacement> by replacements {

    infix fun String.asPatternTo(replacementPattern: String) =
        this to replacementPattern

    infix fun Pair<String, String>.inFile(filePath: String) {
        replacements += WildcardReplacement(first, second, filePath)
    }
}

class WildcardReplacement(
    private val searchPattern: String,
    private val replacementPattern: String,
    internal val targetFilePath: String,
) {

    private val searchBytes = parsePattern(searchPattern)
    private val replacementBytes = parsePattern(replacementPattern)

    fun applyPattern(targetFileBytes: ByteArray): ByteArray {
        val matches = findAllMatches(targetFileBytes)

        if (matches.isEmpty()) {
            throw PatchException("Pattern not found: ${patternBytesToString(searchBytes)}")
        }

        return applyReplacement(targetFileBytes, matches)
    }

    private fun findAllMatches(haystack: ByteArray): List<Int> {
        val matches = mutableListOf<Int>()
        var startPos = 0

        while (true) {
            val index = indexOfPatternIn(haystack, startPos)
            if (index == -1) break
            matches.add(index)
            startPos = index + 1 // Allow overlapping matches
        }

        return matches
    }

    private fun indexOfPatternIn(haystack: ByteArray, startPos: Int): Int {
        val needleLength = searchBytes.size

        if (needleLength == 0 || startPos > haystack.size - needleLength)
            return -1

        outer@
        for (i in startPos..haystack.size - needleLength) {
            for (j in searchBytes.indices) {
                val pattern = searchBytes[j]
                val value = haystack[i + j].toInt() and 0xFF

                if ((value and pattern.mask) != pattern.value)
                    continue@outer
            }

            return i
        }

        return -1
    }

    private fun applyReplacement(
        targetFileBytes: ByteArray,
        matchPositions: List<Int>
    ): ByteArray {
        // Calculate new size
        val originalSize = targetFileBytes.size
        val replacementSize = replacementBytes.size
        val searchSize = searchBytes.size

        if (replacementSize == searchSize) {
            // Simple in-place replacement
            return targetFileBytes.clone().apply {
                matchPositions.forEach { pos ->
                    replacementBytes.forEachIndexed { index, pattern ->
                        if (pattern.mask != 0x00) {
                            // Only write non-wildcard bytes
                            this[pos + index] = pattern.value.toByte()
                        }
                        // Wildcard bytes (mask == 0x00) leave original unchanged
                    }
                }
            }
        } else {
            // Need to resize the byte array
            val sizeDelta = (replacementSize - searchSize) * matchPositions.size
            val result = ByteArray(originalSize + sizeDelta)

            var resultPos = 0
            var searchPos = 0
            var matchIndex = 0

            while (searchPos < originalSize) {
                val nextMatch = matchPositions.getOrNull(matchIndex) ?: -1

                if (nextMatch == searchPos) {
                    // Apply replacement with wildcard support
                    replacementBytes.forEachIndexed { index, pattern ->
                        if (pattern.mask != 0x00) {
                            result[resultPos + index] = pattern.value.toByte()
                        } else {
                            // For wildcards in replacement, copy from original if available
                            val sourcePos = searchPos + index
                            if (sourcePos < originalSize) {
                                result[resultPos + index] = targetFileBytes[sourcePos]
                            }
                        }
                    }

                    resultPos += replacementSize
                    searchPos += searchSize
                    matchIndex++
                } else {
                    // Copy original bytes
                    result[resultPos] = targetFileBytes[searchPos]
                    resultPos++
                    searchPos++
                }
            }

            return result
        }
    }

    private fun patternBytesToString(pattern: List<PatternByte>) =
        pattern.joinToString(" ") {
            when (it.mask) {
                0x00 -> "??"
                0xF0 -> "%X?".format(it.value ushr 4)
                0x0F -> "?%X".format(it.value and 0xF)
                else -> "%02X".format(it.value)
            }
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WildcardReplacement

        if (searchPattern != other.searchPattern) return false
        if (replacementPattern != other.replacementPattern) return false
        if (targetFilePath != other.targetFilePath) return false

        return true
    }

    override fun hashCode(): Int {
        var result = searchPattern.hashCode()
        result = 31 * result + replacementPattern.hashCode()
        result = 31 * result + targetFilePath.hashCode()
        return result
    }

    companion object {

        private fun parsePattern(pattern: String): List<PatternByte> {
            return pattern
                .trim()
                .split(Regex("\\s+"))
                .map(::parseToken)
        }

        private fun parseToken(token: String): PatternByte {
            require(token.length == 2) {
                "Invalid token '$token'"
            }

            val hi = token[0]
            val lo = token[1]

            return when {
                hi == '?' && lo == '?' ->
                    PatternByte(0x00, 0x00)

                hi == '?' ->
                    PatternByte(
                        hex(lo),
                        0x0F
                    )

                lo == '?' ->
                    PatternByte(
                        hex(hi) shl 4,
                        0xF0
                    )

                else ->
                    PatternByte(
                        token.toInt(16),
                        0xFF
                    )
            }
        }

        private fun hex(c: Char): Int =
            c.digitToInt(16)
    }
}

private data class PatternByte(
    val value: Int,
    val mask: Int,
)
