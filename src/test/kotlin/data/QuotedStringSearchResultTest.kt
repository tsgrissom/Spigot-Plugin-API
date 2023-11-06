package data

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.command.flag.ValidCommandFlag
import io.github.tsgrissom.pluginapi.data.QuotedStringSearchResult
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class QuotedStringSearchResultTest : PAPIPluginTest() {

    @Test
    fun doesNonQuotedStringPassedToQuotedStringResultConstructorThrowException() {
        arrayOf(
            "'Non-quoted",
            "This String is not quoted'",
            "\"Another non-quoted String",
            "A String with a trailing quote mark\""
        ).forEach { str ->
            assertThrows<IllegalArgumentException>("Expected construction of QuotedStringSearchResult with non-quoted String to throw IllegalArgumentException, it did not") {
                QuotedStringSearchResult(str, 0, 0)
            }
        }
    }
}