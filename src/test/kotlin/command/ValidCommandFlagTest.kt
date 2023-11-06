package command

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.command.flag.ValidCommandFlag
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ValidCommandFlagTest : PAPIPluginTest() {

    @Test
    fun doesSingleCharCommandFlagThrowException() {
        assertThrows<IllegalArgumentException>("Expected construction of ValidCommandFlag to throw IllegalArgumentException but it did not") {
            ValidCommandFlag("g")
        }
    }

    @Test
    fun doesCommandFlagWithLeadingHyphenCharThrowException() {
        assertThrows<IllegalArgumentException>(
            "Expected construction of ValidCommandFlag to throw IllegalArgumentException but it did not") {
            ValidCommandFlag("-invalidflag")
        }
    }

    @Test
    fun doesCommandFlagConsistingOfWhitespaceThrowException() {
        assertThrows<IllegalArgumentException>(
            "Expected construction of ValidCommandFlag to throw IllegalArgumentException but it did not"
        ) {
            ValidCommandFlag("   ")
        }
    }

    @Test
    fun doesCapitalizedCommandFlagShortNameEqCapitalLetter() {
        val flag = ValidCommandFlag("Target")
        assertEquals(flag.isShortNameUppercased(), flag.getShortName().startsWith("T"))
    }
}