package utility

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.extension.kt.isInputInHours
import io.github.tsgrissom.pluginapi.extension.kt.isInputInMinutes
import io.github.tsgrissom.pluginapi.extension.kt.isInputInSeconds
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class TimeUtilityTest : PAPIPluginTest() {

    @DisplayName("Does an assortment of valid Strings expressing time in seconds passed to TimeUtility#isInputInSeconds evaluate to true?")
    @ParameterizedTest
    @ValueSource(strings=[
        "10s",
        "90000000000s",
        "1S"
    ])
    fun isInputInSeconds_shouldBeTrueWhenValuesAreValidExpressionsOfTimeInSecondsAsStrings(value: String) =
        assertTrue(value.isInputInSeconds(), "String \"$value\" is not valid input as seconds")

    @DisplayName("Does an assortment of valid Strings expressing time in minutes passed to TimeUtility#isInputInMinutes evaluate to true?")
    @ParameterizedTest
    @ValueSource(strings=[
        "1m",
        "5M",
        "10000m"
    ])
    fun isInputInMinutes_shouldBeTrueWhenValuesAreValidExpressionsOfTimeInMinutesAsStrings(value: String) =
        assertTrue(value.isInputInMinutes(), "String \"$value\" is not valid input as minutes")

    @DisplayName("Does an assortment of valid Strings expressing time in hours passed to TimeUtility#isInputInHours evaluate to true?")
    @ParameterizedTest
    @ValueSource(strings=[
        "1h",
        "5H",
        "10000h"
    ])
    fun isInputInHours_shouldBeTrueWhenValuesAreValidExpressionsOfTimeInHoursAsStrings(value: String) =
        assertTrue(value.isInputInHours(), "String \"$value\" is not valid input as hours")

    // TODO More tests for 12h and 24h methods
}