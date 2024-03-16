package extension

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.extension.kt.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class NumericExtensionsTest : PAPIPluginTest() {

    @ParameterizedTest
    @ValueSource(ints=[0,1,2,3,4,5])
    fun calculateNextPageIndex_max4_shouldNeverBeGreaterThan4(value: Int) {
        val maxPageIndex = 4
        val resultOfNext = value.calculateIndexOfNextPage(maxPageIndex)
        assertTrue(
            resultOfNext <= maxPageIndex,
            "\"$value\"->calculateNextPageIndex(max=$maxPageIndex) \"$resultOfNext\" is greater than max page index (=$maxPageIndex)"
        )
    }

    @ParameterizedTest
    @ValueSource(ints=[-2,-1,0,1,2,3])
    fun calculatePreviousPageIndex_max4_shouldNeverBeLessThan0_and_shouldNeverBeGreaterThan4(value: Int) {
        val maxPageIndex = 4
        val resultOfPrevious = value.calculateIndexOfPreviousPage(maxPageIndex)
        assertTrue(
            resultOfPrevious >= 0,
            "\"$value\"->calculatePreviousPageIndex(max=$maxPageIndex) \"$resultOfPrevious\" is less than 0"
        )

        assertTrue(
            resultOfPrevious <= maxPageIndex,
            "\"$value\"->calculateNextPageIndex(max=$maxPageIndex) \"$resultOfPrevious\" is greater than max page index (=$maxPageIndex)"
        )
    }

    @ParameterizedTest
    @ValueSource(longs=[0,2000, 10000, 14444, 24000])
    fun convertTicksTo24Hour_withoutColor_shouldMatchRegex(value: Long) {
        val converted = value.convertTicksTo24Hour(withColor=false)
        assertTrue(converted.matches(REGEX_24HR_CLOCK.toRegex()), "Result does not match regular expression StringExtensions#REGEX_24HR_CLOCK")
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

    @RepeatedTest(100)
    fun toTimeAgoFormat_shouldEqualAMomentAgo_whenReceiverIsDifferenceBetweenNowAndBetweenAThousandthToTwoSeconds() {
        val randomMs = (1..2000).random()
        val difference = System.currentTimeMillis() - randomMs
        assertEquals("a moment ago", difference.toTimeAgoFormat(), "String neq \"a moment ago\" \n(gen ms: $randomMs)")
    }

    @RepeatedTest(100)
    fun toTimeAgoFormat_shouldEqualAFewMomentsAgo_whenReceiverIsDifferenceBetweenNowAndBetweenThreeToFiveSeconds() {
        val randomMs = (3000..5000).random()
        val difference = System.currentTimeMillis() - randomMs
        assertEquals("a few moments ago", difference.toTimeAgoFormat(), "String neq \"a few moments ago\" \n(gen ms: $randomMs)")
    }

    // Any amount of seconds more than 5 but less than 60 should be
    @RepeatedTest(100)
    fun toTimeAgoFormat_endsWithSecondsAgo_shouldBeTrue_whenReceiverIsDifferenceBetweenNowAndSixToFiftyNineSeconds() {
        val randomSecondsLessThanAMinute = (6..59).random()
        val thatSeconds = 1000L * randomSecondsLessThanAMinute
        val difference = System.currentTimeMillis() - thatSeconds
        val result = difference.toTimeAgoFormat()

        assertTrue(
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

        assertTrue(
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

        assertTrue(
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

        assertTrue(
            result.endsWith("year ago") || result.endsWith("years ago"),
            "String \"$result\" does not end with \"year ago\" and does not end with \"years ago\" \n(gen years: $thatYears)"
        )
    }
}