@file:Suppress("DEPRECATION")

package extension

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.extension.kt.*
import org.bukkit.ChatColor
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class StringExtensionsTest : PAPIPluginTest() {

    // MARK: ChatColor Tests

    @DisplayName("Do various forms of chat color expressed in Strings all contain ChatColor?")
    @Test
    fun containsChatColor_shouldBeTrueWhenValuesAreVariedFormsOfColoredText() {
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
    fun translateAndStripColorCodes_shouldEqualOriginalStrWhenValueIsStringPrependedWithUntranslatedColor() {
        val original = "Some text that will have a chat color prepended"
        val pre = "&a&l"
        val combinedThenTranslatedAndStripped = "$pre$original".translateAndStripColorCodes()
        assertEquals(combinedThenTranslatedAndStripped, original)
    }

    @DisplayName("Does String consisting of only color codes and ChatColors passed to String#isOnlyColorCodes equal true?")
    @Test
    fun isOnlyColorCodes_shouldBeTrueWhenValuesAreOnlyColorCodesAndChatColors() {
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
    fun isOnlyColorCodes_shouldBeFalseWhenValuesAreMultiFormatChatColorsAndText() {
        arrayOf(
            "&bThis is a colored string with substance",
            "${ChatColor.RED}This is another string",
            "§lThis is some bold text"
        ).forEach { str ->
            assertFalse(str.isOnlyColorCodes())
        }
    }

    @DisplayName("Does String#resolveChatColor when passed single-character color codes not equal null?")
    @Test
    fun resolveChatColor_shouldBeNonNullWhenValuesAreValidSingleCharColorCodes() {
        "0123456789abcdef".forEach { char ->
            assertNotNull("$char".resolveChatColor())
        }
    }

    @DisplayName("Does String#resolveChatColor when passed invalid single-character color codes equal null?")
    @Test
    fun resolveChatColor_shouldBeNullWhenValuesAreInvalidSingleCharColorCodes() {
        "ghijp".forEach { char ->
            assertNull("$char".resolveChatColor())
        }
    }

    @DisplayName("Does String#resolveChatColor when passed qualified input color codes not equal null?")
    @ParameterizedTest
    @ValueSource(strings=["&a", "&l", "§b", "§k"])
    fun resolveChatColor_shouldBeNonNullWhenValuesAreValidQualifiedColorCodes(value: String) =
        assertNotNull(value.resolveChatColor())

    @DisplayName("Does String#resolveChatColor when passed invalid qualified input color codes equal null?")
    @ParameterizedTest
    @ValueSource(strings=["&g", "&h", "§i", "§j"])
    fun resolveChatColor_shouldBeNullWhenValuesAreInvalidQualifiedColorCodes(value: String) =
        assertNull(value.resolveChatColor())

    @DisplayName("Does String#isWrappedWithSameChar(ignoreCase) equal true when passed mixed-capitalization leading and trailing character palindromes always equal true?")
    @ParameterizedTest
    @ValueSource(strings=[
        "civiC",
        "Madam",
        "leveL"
    ])
    fun startsAndEndsWithSameCharIgnoreCase_shouldBeTrueWhenValuesAreMixedUppercasedLeadingTrailingCharPalindromes(value: String) =
        assertTrue(value.isWrappedWithSameChar(ignoreCase=true))

    @DisplayName("Does String#equalsAny equal false when all arguments are similar Strings with different uppercasing?")
    @ParameterizedTest
    @ValueSource(strings=[
        "hEllo world!",
        "Hello World!",
        "HELLO WORLD!"
    ])
    fun equalsAny_shouldBeFalseWhenValuesAreSimilarButHaveVariedUppercasing(comparedTo: String) =
        assertFalse("Hello world!".equalsAny(comparedTo))

    // MARK: Capitalization Tests

    @DisplayName("Does String#isCapitalized when all arguments are Strings with leading character as punctuation equal false?")
    @ParameterizedTest
    @ValueSource(strings=[
        "-sometext",
        ".testing",
        ",foo",
        " bar"
    ])
    fun isCapitalized_shouldBeFalseWhenValuesHaveLeadingCharacterAsPunctuation(value: String) =
        assertFalse(value.isCapitalized())

    @DisplayName("Does String#capitalize when all arguments are Strings with leading character as punctuation equal the original String?")
    @ParameterizedTest
    @ValueSource(strings=[
        "-sometext",
        ".testing",
        ",foo",
        " bar"
    ])
    fun capitalize_shouldEqualOriginalStrWhenValuesHaveLeadingCharacterAsPunctuation(value: String) =
        assertEquals(value.capitalize(), value)

    @DisplayName("Does String#capitalizeEachWordAllCaps not equal the original String?")
    @ParameterizedTest
    @ValueSource(strings=[
        "HELLO",
        "HELLO_WORLD",
        "FOO_BAR",
        "FOO_BAZ"
    ])
    fun capitalizeEachWordAllCaps_shouldNeqOriginalStr(value: String) =
        assertNotEquals(value.capitalizeEachWordAllCaps(), value)

    // MARK: Miscellaneous Mutation Tests

    @ParameterizedTest
    @ValueSource(strings=[
        "Text  ",
        "Another     "
    ])
    fun truncate_shouldNotEndWithEllipsesWhenLessThanMaxWidthAfterTrim(value: String) =
        assertFalse(value.truncate(12, trimBefore=true, postfix="...").endsWith("..."))

    @ParameterizedTest
    @ValueSource(strings=[
        "Some long text with trailing whitespace",
        "Something with extended test foobar",
    ])
    fun truncate_shouldEndWithEllipsesWhenGreaterThanMaxWidth(value: String) {
        val truncated = value.truncate(12)
        assertTrue(truncated.endsWith("..."), "\"$truncated\" does not end with ...®")
    }

    // MARK: Percentage Tests

    @DisplayName("Does a non-percentage String value fail to match the percentage regular expression?")
    @ParameterizedTest
    @ValueSource(strings=[
        "10",
        "%10",
        "10 %",
        "-1%"
    ])
    fun isPercent_shouldBeFalseWhenValuesAreNotPercentagesAsStrings(value: String) =
        assertFalse(value.isPercentage())

    @DisplayName("Does a Percentage as a String Value Succeed in Matching the Percentage Regular Expression")
    @ParameterizedTest
    @ValueSource(strings=[
        "10%",
        "0.01%"
    ])
    fun isPercent_shouldBeTrueWhenValuesArePercentagesAsStrings(value: String) =
        assertTrue(value.isPercentage())

    // MARK: Prefixes/Suffix Tests

    @ParameterizedTest
    @ValueSource(strings=[
        "Foobar",
        "Hello world",
        "This is some text that will be wrapped"
    ])
    fun wrap_shouldStartAndEndWithToken(value: String) {
        val token = "\""
        val newValue = value.wrap(token)
        assertTrue(newValue.startsWith(token) && newValue.endsWith(token))
    }

    @ParameterizedTest
    @ValueSource(strings=[
        "\"Some text that is already quoted\"",
        "\"This text is already surrounded w/ quotes\"",
        "\"Text within quotes should not be double-quoted\""
    ])
    fun wrapIfMissing_shouldEqOriginalValueWhenAlreadyWrappedWithChar(value: String) {
        val char = '\"'
        val newValue = value.wrapIfMissing(char)
        assertEquals(newValue, value)
    }

    // TODO Test bothOrNone parameter of String#wrapIfMissing

    @ParameterizedTest
    @ValueSource(strings=[
        "dText wrapped in lowercase D charsd",
        "dSome more text wrapped in lowercase Dd",
        "dFoobard"
    ])
    fun unwrap_shouldNotStartOrEndWithUnwrappedChar(value: String) {
        val char = 'd'
        val newValue = value.unwrap(char)
        assertFalse(newValue.startsWith(char))
        assertFalse(newValue.endsWith(char))
    }

    @ParameterizedTest
    @ValueSource(strings=[
        "'Text within apostrophes'",
        "'Some more text within apostrophes'",
        "'Yet another text within apostrophes'"
    ])
    fun unwrap_shouldNotStartOrEndWithUnwrappedToken(value: String) {
        val token = "'"
        val newValue = value.unwrap(token)
        assertFalse(newValue.startsWith(token))
        assertFalse(newValue.endsWith(token))
    }

    @DisplayName("Does String#removePrefixes not equal original String when receivers all contain the prefixes?")
    @ParameterizedTest
    @ValueSource(strings=[
        "'Leading apostrophe str",
        "'Foobar",
        "'foobarbaz"
    ])
    fun removePrefixes_shouldNeqOriginalString(value: String) =
        assertNotEquals(value.removePrefixes("'"), value)

    @DisplayName("Does String#removeSuffixes not equal original String when receivers all contain the suffixes?")
    @ParameterizedTest
    @ValueSource(strings=[
        "Trailing apostrophe'",
        "Another String, this time a trailing quote\"",
        "This String with lose the trailing period."
    ])
    fun removeSuffixes_shouldNeqOriginalString(value: String) =
        assertNotEquals(value.removeSuffixes("'", "\"", "."), value)

    // MARK: Quotation Tests

    @DisplayName("Does String#dequoted when passed a quoted String not equal the original String?")
    @ParameterizedTest
    @ValueSource(strings=[
        "\"Some text within quotes\"",
        "'Some more text within quotes'"
    ])
    fun dequoted_shouldNeqOriginalStrWhenQuoted(value: String) =
        assertNotEquals(value.dequoted(), value)

    @DisplayName("Does String#dequoted when passed a non-quoted String equal the original String?")
    @ParameterizedTest
    @ValueSource(strings=[
        "Some text not within quotes\"",
        "\"Some text without a trailing quote",
        "'Some more text bit within quotes",
        "Some text with a trailing apostrophe'"
    ])
    fun dequoted_shouldEqOriginalStrWhenNonQuoted(value: String) =
        assertEquals(value.dequoted(), value)

    @DisplayName("Does String#quoted not equal the original String?")
    @ParameterizedTest
    @ValueSource(strings=["foobar", "foo", "bar", "baz", "qux"])
    fun quoted_shouldNeqOriginalString(value: String) =
        assertNotEquals(value.quoted(), value)
}