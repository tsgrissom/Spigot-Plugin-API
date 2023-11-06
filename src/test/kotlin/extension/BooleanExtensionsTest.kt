package extension

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.extension.kt.BooleanFormat
import io.github.tsgrissom.pluginapi.extension.kt.fmt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BooleanExtensionsTest : PAPIPluginTest() {

    private fun Boolean.fmtTrueFalse(
        capitalize: Boolean = false,
        withColor: Boolean = false
    ) : String =
        this.fmt(BooleanFormat.TRUE_FALSE, capitalize=capitalize, withColor=withColor)

    @DisplayName("Does Boolean#fmt with no settings when the receiver is the true literal equal a plain String containing the \"true\" literal?")
    @ParameterizedTest
    @ValueSource(booleans=[true, false])
    fun fmt_valueFormattedWithNoCapitalizationAndNoColorShouldEqValueExpressedAsStringLiteral(b: Boolean) {
        val asStr = "$b"
        val formatted = b.fmtTrueFalse(capitalize=false, withColor=false)
        assertEquals(formatted, asStr)
    }

    @DisplayName("Does Boolean#fmt with only capitalization enabled when the receiver is the true literal equal a plain String containing the \"true\" literal passed through String#capitalize?")
    @ParameterizedTest
    @ValueSource(booleans=[true, false])
    fun fmt_valueFormattedWithCapitalizationAndNoColorShouldEqValueExpressedAsStringLiteralCapitalized(b: Boolean) {
        val asStr = "$b".capitalize()
        val formatted = b.fmtTrueFalse(capitalize=true, withColor=false)
        assertEquals(formatted, asStr)
    }
}