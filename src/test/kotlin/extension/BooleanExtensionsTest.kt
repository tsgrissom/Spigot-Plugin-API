package extension

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.extension.kt.BooleanFormat
import io.github.tsgrissom.pluginapi.extension.kt.fmt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

private fun Boolean.fmtTrueFalse(
    capitalize: Boolean = false,
    withColor: Boolean = false
) : String =
    this.fmt(BooleanFormat.TRUE_FALSE, capitalize=capitalize, withColor=withColor)

class BooleanExtensionsTest : PAPIPluginTest() {

    @DisplayName("Does Boolean#fmt with no settings when the receiver is the true literal equal a plain String containing the \"true\" literal?")
    @Test
    fun boolFmtNoColorNoCapitalizationOnTrueEqStringLiteralOfTrue() {
        val tAsStr = "${true}"
        val tFormattedNoSettings = true.fmtTrueFalse()
        assertEquals(tFormattedNoSettings, tAsStr)
    }

    @DisplayName("Does Boolean#fmt with only capitalization enabled when the receiver is the true literal equal a plain String containing the \"true\" literal passed through String#capitalize?")
    @Test
    fun boolFmtNoColorCapitalizedEqStringLiteralOfTrueCapitalized() {
        val tAsStr = "${true}"
        val capitalizedStr = tAsStr.capitalize()
        val fmtCapitalized = true.fmtTrueFalse(capitalize=true, withColor=false)
        assertEquals(capitalizedStr, fmtCapitalized)
    }
}