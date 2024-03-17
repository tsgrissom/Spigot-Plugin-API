package extension

import io.github.tsgrissom.pluginapi.extension.kt.countAllDigits
import io.github.tsgrissom.pluginapi.extension.kt.countLeadingDigits
import io.github.tsgrissom.pluginapi.extension.kt.countTrailingDigits
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class DoubleExtensionsTest {

    // Helper Functions
    private fun randomDigit() : Int =
        (1..9).random()

    private fun randomDouble(
        integerDigitCount: Int    = (1..3).random(),
        fractionalDigitCount: Int = (0..3).random()
    ) : Double {
        var value = 0.0

        if (integerDigitCount == 3)
            value += (100 * randomDigit())
        if (integerDigitCount >= 2)
            value += (10 * randomDigit())
        if (integerDigitCount >= 1)
            value += randomDigit()

        if (fractionalDigitCount == 0)
            return value

        if (fractionalDigitCount >= 1)
            value += (0.1 * randomDigit())
        if (fractionalDigitCount >= 2)
            value += (0.01 * randomDigit())
        if (fractionalDigitCount == 3)
            value += (0.001 * randomDigit())

        return String.format("%.3f", value).toDouble()
    }

    // MARK: Double#countAllDigits Tests

    @Test
    fun countAllDigits_shouldEqualThree_whenValueIsDecimalWithOneIntegerPartAndADecimalToTheHundredths() {
        val value = 3.14
        assertEquals(3, value.countAllDigits())
    }

    @Test
    fun countAllDigits_shouldEqualZero_whenValueIsZero() {
        assertEquals(0, 0.0.countAllDigits())
    }

    @RepeatedTest(100)
    fun countAllDigits_shouldEqualSumOfProvidedIntegralAndFractionalDigits() {
        val integralDigits   = (1..3).random()
        val fractionalDigits = (0..3).random()
        val random = randomDouble(integerDigitCount=integralDigits, fractionalDigitCount=fractionalDigits)
        val sum = integralDigits + fractionalDigits
        val counted = random.countAllDigits()

        assertEquals(
            sum,
            counted,
            "Integral digits: $integralDigits\n" +
                    "Fractional digits: $fractionalDigits\n" +
                    "Random: $random\n" +
                    "Expected sum: $sum\n" +
                    "Counted digits: $counted\n",
        )
    }

    // MARK: Double#countLeadingDigits Tests

    @Test
    fun countLeadingDigits_shouldEqualZero_whenValueIsZero() {
        assertEquals(0, 0.0.countLeadingDigits())
    }

    @ParameterizedTest
    @ValueSource(doubles=[1.0, 1.1, 3.4])
    fun countLeadingDigits_shouldEqualOne_whenValueHasWholePartLessThanTenWithVariousFractionalParts(value: Double) {
        assertEquals(1, value.countLeadingDigits())
    }

    @RepeatedTest(100)
    fun countLeadingDigits_shouldEqualProvidedWholeDigits() {
        val wholeDigits = (1..3).random()
        val random = randomDouble(integerDigitCount=wholeDigits)
        val counted = random.countLeadingDigits()

        assertEquals(
            wholeDigits,
            counted,
            "Whole digits: $wholeDigits\n" +
                    "Random: $random\n" +
                    "Counted leading digits: $counted\n",
        )
    }

    // MARK: Double#countTrailingDigits Tests

    @Test
    fun countTrailingDigits_shouldEqualZero_whenValueIsZero() {
        assertEquals(0, 0.0.countTrailingDigits())
    }

    @ParameterizedTest
    @ValueSource(doubles=[1.0, 10.0, 3.0])
    fun countTrailingDigits_shouldEqualZero_whenValueIsWholeNumberAsDouble(value: Double) {
        assertEquals(0, value.countTrailingDigits())
    }

    @RepeatedTest(100)
    fun countTrailingDigits_shouldEqualProvidedFractionalDigits() {
        val fractionalDigits = (1..3).random()
        val random = randomDouble(fractionalDigitCount=fractionalDigits)
        val counted = random.countTrailingDigits()

        assertEquals(
            fractionalDigits,
            counted,
            "Fractional digits: $fractionalDigits\n" +
                    "Random: $random\n" +
                    "Counted trailing digits: $counted\n",
        )
    }
}