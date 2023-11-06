package utility

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.utility.TimeUtility
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class TimeUtilityTest : PAPIPluginTest() {

    @DisplayName("Does an assortment of valid Strings expressing time in seconds passed to TimeUtility#isInputInSeconds evaluate to true?")
    @Test
    fun isInputStringInSeconds() {
        val util = TimeUtility()
        fun assert(str: String) =
            assertTrue(util.isInputInSeconds(str), "$str is not valid input as seconds")
        val cases = arrayOf("10s", "90000000000s", "1S")
        cases.forEach { assert(it) }
    }

    @DisplayName("Does an assortment of valid Strings expressing time in minutes passed to TimeUtility#isInputInMinutes evaluate to true?")
    @Test
    fun isInputStringInMinutes() {
        val util = TimeUtility()
        fun assert(str: String) =
            assertTrue(util.isInputInMinutes(str), "$str is not valid input as minutes")
        val cases = arrayOf("1m", "5M", "10000m")
        cases.forEach { assert(it) }
    }

    @DisplayName("Does an assortment of valid Strings expressing time in hours passed to TimeUtility#isInputInHours evaluate to true?")
    @Test
    fun isInputStringInHours() {
        val util = TimeUtility()
        fun assert(str: String) =
            assertTrue(util.isInputInHours(str), "$str is not valid input as minutes")
        val cases = arrayOf("1h", "5H", "10000h")
        cases.forEach { assert(it) }
    }

    // TODO More tests for 12h and 24h methods
}