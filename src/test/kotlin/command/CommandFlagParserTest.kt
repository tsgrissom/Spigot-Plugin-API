package command

import PAPIPluginTest
import assertEmpty
import assertNotEmpty
import io.github.tsgrissom.pluginapi.command.flag.CommandFlagParser
import io.github.tsgrissom.pluginapi.command.flag.ValidCommandFlag.Companion.FLAG_GRAPHICAL
import org.junit.jupiter.api.Test

class CommandFlagParserTest : PAPIPluginTest() {

    @Test
    fun getUnknownFlags_shouldBeEmptyWhenContextSpecifiesValidFlagsForParser() =
        arrayOf(
            mockCommandContext("--gui"),
            mockCommandContext("-g")
        ).forEach { context ->
            val parser = CommandFlagParser(context.args, FLAG_GRAPHICAL)
            assertEmpty(parser.getUnknownFlags())
        }

    @Test
    fun getUnknownFlags_shouldBeNonEmptyWhenContextSpecifiesUnknownFlagsForParser() =
        arrayOf(
            mockCommandContext("-G", "--gui"),
            mockCommandContext("--foo")
        ).forEach { context ->
            val parser = CommandFlagParser(context.args, FLAG_GRAPHICAL)
            assertNotEmpty(parser.getUnknownFlags())
        }
}