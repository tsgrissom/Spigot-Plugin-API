package command

import PAPIPluginTest
import assertEmpty
import io.github.tsgrissom.pluginapi.command.flag.CommandFlagParser
import io.github.tsgrissom.pluginapi.command.flag.ValidCommandFlag
import org.junit.jupiter.api.Test

class CommandFlagParserTest : PAPIPluginTest() {

    private val flagGui = ValidCommandFlag("gui")

    private fun mockContextWithGuiFlag(passed: Boolean = true) =
        if (passed) mockCommandContext("--gui") else mockCommandContext()

    @Test
    fun doesContextWithGuiFlagPassedHaveEmptyUnknownFlags() {
        val mockWithGuiFlag = mockContextWithGuiFlag(passed=true)
        val parser = CommandFlagParser(mockWithGuiFlag.args, flagGui)
        assertEmpty(parser.getUnknownFlags())
    }
}