package io.github.tsgrissom.pluginapi.parser

enum class ParsedPercentageResult {
    SUCCESS, INVALID_FORM, CANT_BE_NEGATIVE, CANT_BE_ZERO
}

data class ParsedPercentage(
    private val value: Double?,
    private val result: ParsedPercentageResult
) {

    constructor(value: Double?) : this(value, ParsedPercentageResult.SUCCESS)
    constructor(result: ParsedPercentageResult) : this(null, result)

    fun wasSuccessful() : Boolean {
        return value != null && result == ParsedPercentageResult.SUCCESS
    }

    fun wasUnsuccessful() : Boolean {
        return !this.wasSuccessful()
    }

    fun isPresent() : Boolean {
        return this.value != null
    }

    fun getValue() : Double? {
        return this.value
    }

    fun getResult() : ParsedPercentageResult {
        return this.result
    }
}

class PercentageParser(
    private var acceptFractionalValues: Boolean = true,
    private var acceptNegativeNumbers: Boolean = true,
    private var acceptZero: Boolean = true,
    private var acceptOmittedPercentSign: Boolean = false
) {

    fun acceptFractionalValues(b: Boolean) : PercentageParser {
        this.acceptFractionalValues = b
        return this
    }

    fun acceptNegativeNumbers(b: Boolean) : PercentageParser {
        this.acceptNegativeNumbers = b
        return this
    }

    fun acceptZero(b: Boolean) : PercentageParser {
        this.acceptZero = b
        return this
    }

    fun acceptOmittedPercentSign(b: Boolean) : PercentageParser {
        this.acceptOmittedPercentSign = b
        return this
    }

    fun parse(input: String) : ParsedPercentage {
        if (!acceptOmittedPercentSign && !input.endsWith("%"))
            return ParsedPercentage(ParsedPercentageResult.INVALID_FORM)

        return if (acceptFractionalValues)
            parseAsDouble(input)
        else
            parseAsInt(input)
    }

    private fun parseAsDouble(input: String) : ParsedPercentage {
        val sansPercent = input.removeSuffix("%")
        val toDouble = sansPercent.toDoubleOrNull()
            ?: return ParsedPercentage(ParsedPercentageResult.INVALID_FORM)

        if (toDouble < 0.0 && !acceptNegativeNumbers)
            return ParsedPercentage(ParsedPercentageResult.CANT_BE_NEGATIVE)
        if (toDouble == 0.0 && !acceptZero)
            return ParsedPercentage(ParsedPercentageResult.CANT_BE_ZERO)

        return ParsedPercentage(toDouble)
    }

    private fun parseAsInt(input: String) : ParsedPercentage {
        val sansPercent = input.removeSuffix("%")
        val toInt = sansPercent.toIntOrNull()
            ?: return ParsedPercentage(ParsedPercentageResult.INVALID_FORM)

        if (toInt < 0 && !acceptNegativeNumbers)
            return ParsedPercentage(ParsedPercentageResult.CANT_BE_NEGATIVE)
        if (toInt == 0 && !acceptZero)
            return ParsedPercentage(ParsedPercentageResult.CANT_BE_ZERO)

        return ParsedPercentage(toInt.toDouble())
    }
}

