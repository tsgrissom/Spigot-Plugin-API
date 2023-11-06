@file:Suppress("DEPRECATION")

package extension

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.extension.kt.*
import org.bukkit.ChatColor
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class StringExtensionsTest : PAPIPluginTest() {

    // MARK: ChatColor Tests

    @DisplayName("Do various forms of chat color expressed in Strings all contain ChatColor?")
    @Test
    fun doesStrWithMixedColorTypesContainsChatColor() {
        arrayOf(
            "&aText with untranslated color codes",
            "&bSome text with translated color codes".translateColor(),
            "${ChatColor.RED}Text with interpolated ChatColor enumeration"
        ).forEach {
            assertTrue(it.containsChatColor())
        }
    }

    @DisplayName("Does String prepended with an untranslated color code then String#translateAndStripColorCodes equal the original String?")
    @Test
    fun doesStrPrependedWithUntranslatedColorCodesThenTranslatedAndStrippedEqOriginalStr() {
        val original = "Some text that will have a chat color prepended"
        val pre = "&a&l"
        val combinedThenTranslatedAndStripped = "$pre$original".translateAndStripColorCodes()
        assertEquals(combinedThenTranslatedAndStripped, original)
    }

    @DisplayName("Does String consisting of only color codes and ChatColors passed to String#isOnlyColorCodes equal true?")
    @Test
    fun doesStrConsistingOfOnlyColorCodesMatchIsOnlyColorCodes() {
        arrayOf(
            "&b&l&m",
            ChatColor.RED.toString(),
            ChatColor.GREEN.toString() + ChatColor.BOLD.toString()
        ).forEach {
            assertTrue(it.isOnlyColorCodes())
        }
    }

    @DisplayName("Does String consisting of both ChatColors and text passed to String#isOnlyColorCodes equal false?")
    @Test
    fun doesSubstantialStrWithColorNotMatchIsOnlyCodes() {
        arrayOf(
            "&bThis is a colored string with substance",
            "${ChatColor.RED}This is another string"
        ).forEach {
            assertFalse(it.isOnlyColorCodes())
        }
    }

    @DisplayName("Does String#resolveChatColor when passed single-character color codes not equal null?")
    @Test
    fun doesResolveChatColorSucceedOnSingleCharColorCodes() {
        "0123456789abcdef".forEach { char ->
            assertNotNull("$char".resolveChatColor())
        }
    }

    @DisplayName("Does String#resolveChatColor when passed invalid single-character color codes equal null?")
    @Test
    fun doesResolveChatColorFailOnInvalidSingleCharColorCodes() {
        "ghijp".forEach { char ->
            assertNull("$char".resolveChatColor())
        }
    }

    @DisplayName("Does String#resolveChatColor when passed qualified input color codes not equal null?")
    @Test
    fun doesResolveChatColorSucceedOnQualifiedColorCodes() {
        arrayOf(
            "&a", "&l", "§b", "§k"
        ).forEach {
            assertNotNull(it.resolveChatColor())
        }
    }

    @DisplayName("Does String#resolveChatColor when passed invalid qualified input color codes equal null?")
    @Test
    fun doesResolveChatColorEqNullOnInvalidQualifiedColorCodes() {
        arrayOf(
            "&g", "&h", "§i", "§j"
        ).forEach {
            assertNull(it.resolveChatColor())
        }
    }

    @DisplayName("Does String#startsAndEndsWithSameChar(ignoreCase) when passed mixed-capitalization leading and trailing character palindromes always equal true?")
    @Test
    fun isStartsAndEndsIcTruthyOnPalindromeWithMixedCapitalization() {
        arrayOf(
            "civiC", "Madam", "leveL"
        ).forEach {
            assertTrue(it.startsAndEndsWithSameChar(ignoreCase=true))
        }
    }

    @DisplayName("Does String#equalsAny equal false when all arguments are similar Strings with different uppercasing?")
    @Test
    fun doesEqualsAnyEqualFalseWhenParametersAreSameStringWithDifferentUppercasing() {
        val original = "Hello world!"
        val comparedTo = arrayOf("hEllo world!", "Hello World!", "HELLO WORLD!")

        assertFalse(original.equalsAny(*comparedTo))
    }

    // MARK: Capitalization Tests

    @DisplayName("Does String#isCapitalized when all arguments are Strings with leading character as punctuation equal false?")
    @Test
    fun doesStringWithLeadingPunctuationCharacterToIsCapitalizedEqualFalse() {
        arrayOf(
            "-sometext",
            ".testing",
            ",foo",
            " bar"
        ).forEach { str ->
            assertFalse(str.isCapitalized())
        }
    }

    @DisplayName("Does String#capitalize when all arguments are Strings with leading character as punctuation equal the original String?")
    @Test
    fun doesStringWithLeadingPunctuationCharacterToCapitalizeFuncEqualOriginalString() {
        arrayOf(
            "-sometext",
            ".testing",
            ",foo",
            " bar"
        ).forEach { str ->
            assertEquals(str.capitalize(), str)
        }
    }

    // MARK: Percentage Tests

    @DisplayName("Does a non-percentage String value fail to match the percentage regular expression?")
    @Test
    fun doesNonPercentStrNotMatchRegex() {
        arrayOf(
            "10", "%10", "10 %"
        ).forEach {
            assertFalse(it.isPercentage())
        }
    }

    @DisplayName("Does a Percentage as a String Value Succeed in Matching the Percentage Regular Expression")
    @Test
    fun doesPercentStrMatchRegex() {
        arrayOf(
            "10%", "0.01%"
        ).forEach {
            assertTrue(it.isPercentage())
        }
    }

    // MARK: Prefixes/Suffix Tests

    @Test
    fun doesStringToRemovePrefixesNeqOriginalString() {
        arrayOf(
            "'Leading apostrophe str",
            "'Foobar",
            "'foobarbaz"
        ).forEach { str ->
            assertNotEquals(str.removePrefixes("'"), str)
        }
    }

    @Test
    fun doesStringToRemoveSuffixesNeqOriginalString() {
        arrayOf(
            "Trailing apostrophe'",
            "Another String, this time a trailing quote\"",
            "This String with lose the trailing period."
        ).forEach { str ->
            assertNotEquals(str.removeSuffixes("'", "\"", "."), str)
        }
    }

    // MARK: Quotation Tests

    @DisplayName("Does String#dequoted when passed a quoted String not equal the original String?")
    @Test
    fun doesDequotedQuotedStrNeqOriginalQuotedStr() {
        arrayOf(
            "\"Some text within quotes\"",
            "'Some more text within quotes'"
        ).forEach {
            assertNotEquals(it.dequoted(), it)
        }
    }

    @DisplayName("Does String#dequoted when passed a non-quoted String equal the original String?")
    @Test
    fun doesDequotedNonQuotedStrEqOriginalStr() {
        arrayOf(
            "Some text not within quotes\"",
            "\"Some text without a trailing quote",
            "'Some more text bit within quotes",
            "Some text with a trailing apostrophe'"
        ).forEach {
            assertEquals(it.dequoted(), it)
        }
    }

    @DisplayName("Does String#quoted not equal the original String?")
    @Test
    fun doesStringToQuotedFuncNeqOriginalString() {
        arrayOf(
            "foobar",
            "foo",
            "bar",
            "baz",
            "qux"
        ).forEach { str ->
            assertNotEquals(str.quoted(), str)
        }
    }
}