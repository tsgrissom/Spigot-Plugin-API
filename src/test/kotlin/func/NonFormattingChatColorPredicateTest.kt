package func

import BukkitChatColor
import PAPIPluginTest
import io.github.tsgrissom.pluginapi.func.NonFormattingChatColorPredicate
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class NonFormattingChatColorPredicateTest : PAPIPluginTest() {

    @Test
    fun filterStreamOfAllChatColorsByNonFormattingChatColorPredicate_shouldNotContainAnyFormattingChatColors() {
        val filtered = BukkitChatColor.entries.filter { NonFormattingChatColorPredicate().test(it) }
        arrayOf(
            BukkitChatColor.BOLD,
            BukkitChatColor.ITALIC,
            BukkitChatColor.MAGIC,
            BukkitChatColor.RESET,
            BukkitChatColor.STRIKETHROUGH,
            BukkitChatColor.UNDERLINE
        ).forEach { ccFormat ->
            assertFalse(
                filtered.contains(ccFormat),
                "All ChatColors filtered by NonFormattingChatColorPredicate contains \"${ccFormat.name}\""
            )
        }
    }
}