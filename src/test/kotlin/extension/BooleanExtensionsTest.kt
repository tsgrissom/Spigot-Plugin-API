package extension

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.extension.kt.BooleanFormat
import io.github.tsgrissom.pluginapi.extension.kt.fmt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private fun Boolean.fmtTrueFalse(
    capitalize: Boolean = false,
    withColor: Boolean = false
) : String =
    this.fmt(BooleanFormat.TRUE_FALSE, capitalize=capitalize, withColor=withColor)

class BooleanExtensionsTest : PAPIPluginTest() {

    @Test
    fun boolFmtNoSettingsEq() {
        val tAsStr = "${true}"
        val tFormattedNoSettings = true.fmtTrueFalse()
        assertEquals(tFormattedNoSettings, tAsStr)
    }

    @Test
    fun boolFmtCapitalizeEqStrCapitalize() {
        val tAsStr = "${true}"
        val capitalizedStr = tAsStr.capitalize()
        val fmtCapitalized = true.fmtTrueFalse(capitalize=true, withColor=false)
        assertEquals(capitalizedStr, fmtCapitalized)
    }
}