import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import io.github.tsgrissom.pluginapi.command.CommandContext
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

open class PAPIPluginTest {

    lateinit var server: ServerMock
    lateinit var plugin: TestPlugin

    fun mockCommandContext(
        sender: CommandSender = server.consoleSender,
        command: Command = server.commandMap.getCommand("test")!!,
        label: String = "test",
        args: Array<out String>
    ) : CommandContext =
        CommandContext(sender, command, label, args)
    fun mockCommandContext(vararg args: String) : CommandContext =
        mockCommandContext(args=args)

    @BeforeEach
    fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(TestPlugin::class.java)
    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }
}