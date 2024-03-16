package io.github.tsgrissom.pluginapi.parser

import io.github.tsgrissom.pluginapi.extension.kt.isWrappedWith
import io.github.tsgrissom.pluginapi.extension.kt.wrap

val REGEX_SINGLE_QUOTED = Regex("'(.*?)'")
val REGEX_DOUBLE_QUOTED = Regex("\"(.*?)\"")

enum class QuotationType {
    DOUBLE, SINGLE, EITHER
}

enum class QuotedStringSearchMode {
    ANY, STRICT_FIRST_LAST
}

enum class ParsedQuotedStringResult {
    SUCCESS, NO_QUOTATION_FOUND
}

data class ParsedQuotedString(
    private val value: String?,
    private val result: ParsedQuotedStringResult
) {

    constructor(value: String?) : this(value, ParsedQuotedStringResult.SUCCESS)
    constructor(result: ParsedQuotedStringResult) : this(null, result)

    fun wasSuccessful() : Boolean {
        return this.value != null && this.result == ParsedQuotedStringResult.SUCCESS
    }

    fun wasUnsuccessful() : Boolean {
        return !this.wasSuccessful()
    }

    fun isPresent() : Boolean {
        return this.value != null
    }

    fun getValue() : String? {
        return this.value
    }

    fun getResult() : ParsedQuotedStringResult {
        return this.result
    }
}

class QuotedStringParser(
    private var outputWithQuotations: Boolean = false,
    private var outputWithQuotationsType: QuotationType = QuotationType.DOUBLE,
    private var searchMode: QuotedStringSearchMode = QuotedStringSearchMode.STRICT_FIRST_LAST,
    private var searchQuotationType: QuotationType = QuotationType.EITHER
) {

    fun outputWithQuotations(b: Boolean) : QuotedStringParser {
        this.outputWithQuotations = b
        return this
    }

    fun outputWithQuotationsType(t: QuotationType) : QuotedStringParser {
        this.outputWithQuotationsType = t
        return this
    }

    fun searchMode(m: QuotedStringSearchMode) : QuotedStringParser {
        this.searchMode = m
        return this
    }

    fun searchQuotationType(t: QuotationType) : QuotedStringParser {
        this.searchQuotationType = t
        return this
    }

    fun parse(input: String) : ParsedQuotedString {
        return when (searchMode) {
            QuotedStringSearchMode.STRICT_FIRST_LAST -> parseRegexFirstLast(input)
            QuotedStringSearchMode.ANY -> parseAny(input)
        }
    }

    private fun wrapForOutputSettings(input: String) : String {
        return if (outputWithQuotations) {
            when (outputWithQuotationsType) {
                QuotationType.SINGLE -> input.wrap("'")
                else -> input.wrap("\"")
            }
        } else {
            input
        }
    }

    /**
     * Parses a nullable quoted String as a ParsedQuotedString using the strict first last mode.
     * Strict first-last: The quoted String must begin and end with a quotation mark character.
     * @param input The String to search for a quoted String within.
     * @return A ParsedQuotedString object containing a nullable String and a ParsedQuotedStringResult value.
     */
    private fun parseFastFirstLast(input: String) : ParsedQuotedString {
        if (searchQuotationType==QuotationType.EITHER) { // Either sign
            // Search for first sign then match to second
            var searchForSign = '"'

            if (input.startsWith('\'')) {
                searchForSign = '\''
            }

            if (input.endsWith(searchForSign)) {
                val stripped = input.removeSurrounding(searchForSign.toString())
                val output = wrapForOutputSettings(stripped)
                return ParsedQuotedString(value=output)
            }
        } else { // Double or single
            // Search for the specified sign

            val sign = when (searchQuotationType) {
                QuotationType.SINGLE -> '\''
                else -> '"'
            }

            if (input.isWrappedWith(sign.toString())) {
                val stripped = input.removeSurrounding(sign.toString())
                val output = wrapForOutputSettings(stripped)
                return ParsedQuotedString(value=output)
            }
        }

        return ParsedQuotedString(ParsedQuotedStringResult.NO_QUOTATION_FOUND)
    }

    private fun parseRegexFirstLast(input: String) : ParsedQuotedString {
        val matchesDouble = REGEX_DOUBLE_QUOTED.matches(input)
        val matchesSingle = REGEX_SINGLE_QUOTED.matches(input)

        if (!matchesDouble && !matchesSingle) {
            return ParsedQuotedString(ParsedQuotedStringResult.NO_QUOTATION_FOUND)
        }

        val seekingDouble = searchQuotationType==QuotationType.EITHER || searchQuotationType==QuotationType.DOUBLE
        val seekingSingle = searchQuotationType==QuotationType.EITHER || searchQuotationType==QuotationType.SINGLE

        if (seekingDouble && matchesDouble) {
            val stripped = input.removeSurrounding("\"")
            return ParsedQuotedString(value=wrapForOutputSettings(stripped))
        } else if (seekingSingle && matchesSingle) {
            val stripped = input.removeSurrounding("'")
            return ParsedQuotedString(value=wrapForOutputSettings(stripped))
        }

        return ParsedQuotedString(ParsedQuotedStringResult.NO_QUOTATION_FOUND)
    }

    /**
     * Parses a nullable quoted String as a ParsedQuotedString using any mode.
     * Any: The quoted String can begin and end at any point. The first quoted String will be taken.
     * @param input The String to search for a quoted String within.
     * @return A ParsedQuotedString object containing a nullable String and a ParsedQuotedStringResult value.
     */
    private fun parseAny(input: String) : ParsedQuotedString {
        val doubleMatches = REGEX_DOUBLE_QUOTED.find(input)
        val singleMatches = REGEX_SINGLE_QUOTED.find(input)

        if (doubleMatches == null && singleMatches == null) {
            return ParsedQuotedString(ParsedQuotedStringResult.NO_QUOTATION_FOUND)
        }
        // Something was matched

        val seekingDouble = searchQuotationType==QuotationType.EITHER || searchQuotationType==QuotationType.DOUBLE
        val seekingSingle = searchQuotationType==QuotationType.EITHER || searchQuotationType==QuotationType.SINGLE

        if (seekingDouble && doubleMatches != null && doubleMatches.groupValues.isNotEmpty()) {
            val found = doubleMatches.groupValues[0]
            val stripped = found.removeSurrounding("\"")
            return ParsedQuotedString(value=wrapForOutputSettings(stripped))
        } else if (seekingSingle && singleMatches != null && singleMatches.groupValues.isNotEmpty()) {
            val found = singleMatches.groupValues[0]
            val stripped = found.removeSurrounding("'")
            return ParsedQuotedString(value=wrapForOutputSettings(stripped))
        }

        return ParsedQuotedString(ParsedQuotedStringResult.NO_QUOTATION_FOUND)
    }
}