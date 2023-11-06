package utility

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.utility.TimeUtility
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class TimeUtilityTest : PAPIPluginTest() {

    private val util = TimeUtility()

    @DisplayName("Does an assortment of valid Strings expressing time in seconds passed to TimeUtility#isInputInSeconds evaluate to true?")
    @ParameterizedTest
    @ValueSource(strings=[
        "10s",
        "90000000000s",
        "1S"
    ])
    fun isInputInSeconds_shouldBeTrueWhenValuesAreValidExpressionsOfTimeInSecondsAsStrings(value: String) =
        assertTrue(util.isInputInSeconds(value), "$value is not valid input as seconds")

    @DisplayName("Does an assortment of valid Strings expressing time in minutes passed to TimeUtility#isInputInMinutes evaluate to true?")
    @ParameterizedTest
    @ValueSource(strings=[
        "1m",
        "5M",
        "10000m"
    ])
    fun isInputInMinutes_shouldBeTrueWhenValuesAreValidExpressionsOfTimeInMinutesAsStrings(value: String) =
        assertTrue(util.isInputInMinutes(value), "$value is not valid input as minutes")

    @DisplayName("Does an assortment of valid Strings expressing time in hours passed to TimeUtility#isInputInHours evaluate to true?")
    @ParameterizedTest
    @ValueSource(strings=[
        "1h",
        "5H",
        "10000h"
    ])
    fun isInputInHours_shouldBeTrueWhenValuesAreValidExpressionsOfTimeInHoursAsStrings(value: String) =
        assertTrue(util.isInputInHours(value), "$value is not valid input as hours")

    // TODO More tests for 12h and 24h methods
}