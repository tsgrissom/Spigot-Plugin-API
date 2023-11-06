package data

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.command.flag.ValidCommandFlag
import io.github.tsgrissom.pluginapi.data.QuotedStringSearchResult
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class QuotedStringSearchResultTest : PAPIPluginTest() {

    @ParameterizedTest
    @ValueSource(strings=[
        "'Non-quoted",
        "This String is not quoted'",
        "\"Another non-quoted String",
        "A String with a trailing quote mark\""
    ])
    fun constructor_shouldThrowIllegalArgumentExceptionWhenNonQuotedStringPassedAsQuotedString(value: String) {
        assertThrows<IllegalArgumentException>(
            "Expected construction of QuotedStringSearchResult with non-quoted String to throw IllegalArgumentException, it did not"
        ) { QuotedStringSearchResult(value, 0, 0) }
    }
}