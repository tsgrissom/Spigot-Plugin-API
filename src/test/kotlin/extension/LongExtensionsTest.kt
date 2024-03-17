package extension

import io.github.tsgrissom.pluginapi.extension.kt.REGEX_24HR_CLOCK
import io.github.tsgrissom.pluginapi.extension.kt.convertTicksTo24Hour
import io.github.tsgrissom.pluginapi.extension.kt.toTimeAgoFormat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LongExtensionsTest {

    @ParameterizedTest
    @ValueSource(longs=[0,2000, 10000, 14444, 24000])
    fun convertTicksTo24Hour_withoutColor_shouldMatchRegex(value: Long) {
        val converted = value.convertTicksTo24Hour(withColor=false)
        Assertions.assertTrue(
            converted.matches(REGEX_24HR_CLOCK.toRegex()),
            "Result does not match regular expression StringExtensions#REGEX_24HR_CLOCK"
        )
    }

    @ParameterizedTest
    @ValueSource(longs=[-1000, 24001])
    fun convertTicksTo24Hour_shouldThrowIllegalArgumentExceptionWhenValueIsOutOfRange(value: Long) {
        assertThrows<IllegalArgumentException>(
            "Expected $value->convertTicksTo24Hour to throw IllegalArgumentException when out of range but it did not"
        ) {
            value.convertTicksTo24Hour()
        }
    }

    @DisplayName("Does the difference between the current time in milliseconds and a random ")
    @RepeatedTest(100)
    fun toTimeAgoFormat_shouldEqualAMomentAgo_whenReceiverIsDifferenceBetweenNowAndBetweenAThousandthToTwoSeconds() {
        val randomMs = (1..2000).random()
        val difference = System.currentTimeMillis() - randomMs
        Assertions.assertEquals(
            "a moment ago",
            difference.toTimeAgoFormat(),
            "String neq \"a moment ago\" \n(gen ms: $randomMs)"
        )
    }

    @RepeatedTest(100)
    fun toTimeAgoFormat_shouldEqualAFewMomentsAgo_whenReceiverIsDifferenceBetweenNowAndBetweenThreeToFiveSeconds() {
        val randomMs = (3000..5000).random()
        val difference = System.currentTimeMillis() - randomMs
        Assertions.assertEquals(
            "a few moments ago",
            difference.toTimeAgoFormat(),
            "String neq \"a few moments ago\" \n(gen ms: $randomMs)"
        )
    }

    // Any amount of seconds more than 5 but less than 60 should be
    @RepeatedTest(100)
    fun toTimeAgoFormat_endsWithSecondsAgo_shouldBeTrue_whenReceiverIsDifferenceBetweenNowAndSixToFiftyNineSeconds() {
        val randomSecondsLessThanAMinute = (6..59).random()
        val thatSeconds = 1000L * randomSecondsLessThanAMinute
        val difference = System.currentTimeMillis() - thatSeconds
        val result = difference.toTimeAgoFormat()

        Assertions.assertTrue(
            result.endsWith("second ago") || result.endsWith("seconds ago"),
            "String \"$result\" does not end with \"second ago\" and does not end with \"seconds ago\" \n(gen ms: $thatSeconds)"
        )
    }

    // 86400000L = a day in milliseconds
    @RepeatedTest(100)
    fun toTimeAgoFormat_endsWithDaysAgo_shouldBeTrue_whenReceiverIsDifferenceBetweenNowAndOneToTenDaysAgo() {
        val randomDays = (1..28).random()
        val thatDays = 86400000L * randomDays
        val difference = System.currentTimeMillis() - thatDays
        val result = difference.toTimeAgoFormat()

        Assertions.assertTrue(
            result.endsWith("day ago") || result.endsWith("days ago"),
            "String \"$result\" does not end with \"day ago\" and does not end with \"days ago\" \n(gen days: $thatDays)"
        )
    }

    // 2592000000L = a month in milliseconds
    @RepeatedTest(100)
    fun toTimeAgoFormat_endsWithMonthAgoOrMonthsAgo_shouldBeTrue_whenReceiverIsDifferenceBetweenNowAndOneToElevenMonthsAgo() {
        val aMonth = 2592000000L
        val randomMonths = (1..11).random()
        val thatMonths = aMonth * randomMonths
        val difference = System.currentTimeMillis() - thatMonths
        val result = difference.toTimeAgoFormat()

        Assertions.assertTrue(
            result.endsWith("month ago") || result.endsWith("months ago"),
            "String \"$result\" does not end with \"month ago\" and does not end with \"months ago\" \n(gen months: $thatMonths)"
        )
    }

    @RepeatedTest(100)
    fun toTimeAgoFormat_endsWithDayAgoOrEndsWithDaysAgo_shouldBeTrue_whenReceiverIsDifferenceBetweenNowAndAtLeastOneYear() {
        val aYear = 2592000000L * 12
        val randomYears = (1..255).random()
        val thatYears = aYear * randomYears
        val difference = System.currentTimeMillis() - thatYears
        val result = difference.toTimeAgoFormat()

        Assertions.assertTrue(
            result.endsWith("year ago") || result.endsWith("years ago"),
            "String \"$result\" does not end with \"year ago\" and does not end with \"years ago\" \n(gen years: $thatYears)"
        )
    }
}