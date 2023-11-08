package command

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.command.flag.ValidCommandFlag
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ValidCommandFlagTest : PAPIPluginTest() {

    @Test
    fun constructor_shouldThrowIllegalArgumentExceptionWhenQualifiedNameIsSingleCharacter() {
        assertThrows<IllegalArgumentException>(
            "Expected construction of ValidCommandFlag to throw IllegalArgumentException but it did not"
        ) { ValidCommandFlag("g") }
    }

    @Test
    fun constructor_shouldThrowIllegalArgumentExceptionWhenQualifiedNameHasHyphenatedPrefix() {
        assertThrows<IllegalArgumentException>(
            "Expected construction of ValidCommandFlag to throw IllegalArgumentException but it did not"
        ) { ValidCommandFlag("-invalidflag") }
    }

    @Test
    fun constructor_shouldThrowIllegalArgumentExceptionWhenQualifiedNameConsistsOfWhitespace() {
        assertThrows<IllegalArgumentException>(
            "Expected construction of ValidCommandFlag to throw IllegalArgumentException but it did not"
        ) { ValidCommandFlag("   ") }
    }

    @Test
    fun isShortNameUppercased_shouldEqualCapitalizedQualifiedName() {
        val flag = ValidCommandFlag("Target")
        assertEquals(flag.isShortNameUppercased(), flag.getShortName().startsWith("T"))
    }
}