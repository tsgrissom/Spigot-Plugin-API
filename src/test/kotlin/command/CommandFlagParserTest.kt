package command

import PAPIPluginTest
import assertEmpty
import assertNotEmpty
import io.github.tsgrissom.pluginapi.command.flag.CommandFlagParser
import io.github.tsgrissom.pluginapi.command.flag.ValidCommandFlag
import org.junit.jupiter.api.Test

class CommandFlagParserTest : PAPIPluginTest() {

    private val flagGui = ValidCommandFlag("gui")

    @Test
    fun doesContextWithGuiFlagPassedHaveEmptyUnknownFlags() {
        arrayOf(
            mockCommandContext("--gui"),
            mockCommandContext("-g")
        ).forEach { context ->
            assertEmpty(CommandFlagParser(context.args, flagGui).getUnknownFlags())
        }
    }

    @Test
    fun doesContextWithUnknownFlagPassedHaveNonEmptyUnknownFlags() {
        arrayOf(
            mockCommandContext("-G", "--gui"),
            mockCommandContext("--foo")
        ).forEach { context ->
            assertNotEmpty(CommandFlagParser(context.args, flagGui).getUnknownFlags())
        }
    }
}