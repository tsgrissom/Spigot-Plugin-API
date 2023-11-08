package command

import PAPIPluginTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CommandContextTest : PAPIPluginTest() {

    @Test
    fun getExecutedString_splitBySpaceShouldEqExpectedLength() {
        val expected = 4
        val mockContext = mockCommandContext("first", "second", "third")
        val executedStr = mockContext.getExecutedString()
        val split = executedStr.split(" ")
        assertEquals(expected, split.size)
    }

    @Test
    fun getLength_shouldEqExpectedLength() {
        val contextNoArgs = mockCommandContext()
        val contextSomeArgs = mockCommandContext("first", "second")

        assertEquals(0, contextNoArgs.getLength(includeLabel=false))
        assertEquals(1, contextNoArgs.getLength(includeLabel=true))

        val someArgsLen = contextSomeArgs.getLength(includeLabel=false)
        assertEquals(2, someArgsLen)
    }

    @Test
    fun getAnyQuotedString_shouldNeqNullWhenQuotedStringExists() =
        arrayOf(
            mockCommandContext("'This", "is", "some", "args", "in", "quotes'"),
            mockCommandContext("\"This", "is", "more", "args", "in", "quotes\""),
            mockCommandContext("'This", "should", "be'", "valid")
        ).forEach { assertNotNull(it.getAnyQuotedString()) }

    @Test
    fun getAnyQuotedString_shouldBeNullWhenInvalidQuotedStringExists() =
        arrayOf(
            mockCommandContext("'Some", "text", "with", "leading", "but", "no", "trailing"),
            mockCommandContext("Another\""),
            mockCommandContext("'This", "case", "is", "'invalid"),
            mockCommandContext("'Mixed", "quote", "chars", "invalidate\"")
        ).forEach { assertNull(it.getAnyQuotedString()) }

    @Test
    fun getQuotedStringFromArgsRange_shouldBeNonNullWhenRangeOfArgsIsValidQuotedString() {
        val context = mockCommandContext("'This", "is", "quoted'", "with", "apostrophes")
        assertNotNull(context.getQuotedStringFromArgsRange(0, 2))
    }

    @Test
    fun getQuotedStringFromArgsRange_shouldBeNullWhenRangeIsInvalidQuotedString() {
        val context = mockCommandContext("'This", "is", "quoted'", "with", "apostrophes")
        assertNull(context.getQuotedStringFromArgsRange(0, 4))
    }
}