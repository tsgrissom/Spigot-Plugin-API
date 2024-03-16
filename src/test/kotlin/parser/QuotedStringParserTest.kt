package parser

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.extension.kt.isWrappedWithSameChar
import io.github.tsgrissom.pluginapi.parser.QuotedStringParser
import io.github.tsgrissom.pluginapi.parser.QuotedStringSearchMode
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class QuotedStringParserTest : PAPIPluginTest() {

    // MARK: Default Builder Tests
    @ParameterizedTest
    @ValueSource(strings=[
        "\"This is a valid quoted String\"",
        "'This is another valid quoted String'"
    ])
    fun whenInputIsValidQuotedString_throughDefaultParser_wasSuccessful_shouldBeTrue(value: String) {
        assertTrue(
            QuotedStringParser()
                .parse(value)
                .wasSuccessful()
        )
    }

    @ParameterizedTest
    @ValueSource(strings=[
        "\"This is a string with only leading double-quotes",
        "This is a string with only trailing double-quotes\"",
        "'This is a string with only leading single-quotes",
        "This is a string with only trailing single-quotes'"
    ])
    fun whenInputIsInvalidQuotedString_throughDefaultParser_getValue_shouldBeNull(value: String) {
        assertNull(
            QuotedStringParser()
                .parse(value)
                .getValue()
        )
    }



    @ParameterizedTest
    @ValueSource(strings=[
        "\"This is a string wherein the leading quote is a quotation mark and the trailing quote is an apostrophe'",
        "'This is a string wherein the leading quote is an apostrophe and the trailing quote is a quotation mark\""
    ])
    fun whenInputIsInvalidQuotedStringOfMixedQuotationMarkTypes_throughDefaultParser_wasUnsuccessful_shouldBeTrue(value: String) {
        assertTrue(
            QuotedStringParser()
                .parse(value)
                .wasUnsuccessful()
        )
    }

    // MARK: Custom Builder Tests

    @ParameterizedTest
    @ValueSource(strings=[
        "'This is a string quoted in apostrophes'",
        "\"This is a string quoted in quotation marks\""
    ])
    fun whenInputIsValidQuotedString_throughCustomParser_withOutputQuotationMarks_isWrappedWithSameChar_shouldBeTrue(value: String) {
        val parsed = QuotedStringParser()
            .outputWithQuotations(true)
            .parse(value)
            .getValue() ?: "Null"
        assertTrue(parsed.isWrappedWithSameChar())
    }

    @ParameterizedTest
    @ValueSource(strings=[
        "This is a string with an inner quote: \"Quoted text here\"",
        "This is another with some 'inside text' quoted in apostrophes",
        "This is a string with \"inside text\" quoted in double quotes",
        "'This is a string which is wholly within single quotes'",
        "\"This is a string which is wholly within double quotes\""
    ])
    fun whenInputIsValidInnerQuotedString_throughCustomParser_withSearchModeAsAny_getValue_shouldBeNotNull(value: String) {
        assertNotNull(
            QuotedStringParser()
                .searchMode(QuotedStringSearchMode.ANY)
                .parse(value)
                .getValue()
        )
    }
}