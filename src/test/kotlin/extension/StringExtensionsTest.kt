package extension

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.extension.kt.*
import org.bukkit.ChatColor
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StringExtensionsTest : PAPIPluginTest() {

    @Test
    fun doesDequotedQuotedStrNeqOriginalQuotedStr() {
        val cases = arrayOf(
            "\"Some text within quotes\"",
            "'Some more text within quotes'"
        )
        cases.forEach { assertNotEquals(it.dequoted(), it) }
    }

    @Test
    fun doesDequotedNonQuotedStrEqOriginalStr() {
        val cases = arrayOf(
            "Some text within quotes\"",
            "\"Some text with a leading quote",
            "'Some more text within quotes",
            "Some text with a trailing apostrophe'"
        )
        cases.forEach { assertEquals(it.dequoted(), it) }
    }

    @Test
    fun doesNonPercentStrNotMatchRegex() {
        val cases = arrayOf("10", "%10", "10 %")
        cases.forEach { assertFalse(it.isPercentage()) }
    }

    @Test
    fun doesPercentStrMatchRegex() {
        val cases = arrayOf("10%", "0.01%")
        cases.forEach { assertTrue(it.isPercentage()) }
    }

    @Suppress("DEPRECATION")
    @Test
    fun doesStrWithMixedColorTypesContainsChatColor() {
        val cases = arrayOf(
            "&aText with untranslated color codes",
            "&bSome text with translated color codes".translateColor(),
            "${ChatColor.RED}Text with interpolated ChatColor enumeration"
        )
        cases.forEach { assertTrue(it.containsChatColor()) }
    }

    @Test
    fun doesStrPrependedWithUntranslatedColorCodesThenTranslatedAndStrippedEqOriginalStr() {
        val original = "Some text that will have a chat color prepended"
        val pre = "&a&l"
        val combinedThenTranslatedAndStripped = "$pre$original".translateAndStripColorCodes()
        assertEquals(combinedThenTranslatedAndStripped, original)
    }

    @Suppress("DEPRECATION")
    @Test
    fun doesStrConsistingOfOnlyColorCodesMatchIsOnlyCodes() {
        val cases = arrayOf(
            "&b&l&m",
            ChatColor.RED.toString(),
            ChatColor.GREEN.toString() + ChatColor.BOLD.toString()
        )
        cases.forEach { assertTrue(it.isOnlyColorCodes()) }
    }

    @Suppress("DEPRECATION")
    @Test
    fun doesSubstantialStrWithColorNotMatchIsOnlyCodes() {
        val cases = arrayOf(
            "&bThis is a colored string with substance",
            "${ChatColor.RED}This is another string"
        )
        cases.forEach { assertFalse(it.isOnlyColorCodes()) }
    }

    @Test
    fun isStartsAndEndsIcTruthyOnPalindromeWithMixedCapitalization() {
        val cases = arrayOf("civiC", "Madam", "leveL")
    }
}