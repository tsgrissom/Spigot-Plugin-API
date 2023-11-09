package io.github.tsgrissom.pluginapi.command.help

import BungeeChatColor
import io.github.tsgrissom.pluginapi.command.CommandContext
import net.md_5.bungee.api.ChatColor.*
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.command.ConsoleCommandSender

class CommandUsageBuilder(
    private val context: CommandContext,
    private val subcommand: String = ""
) {
    private val parameters = mutableListOf<SubcParameterBuilder>()
    private val consoleParameters = mutableListOf<SubcParameterBuilder>()
    private var colorPrimary: BungeeChatColor = RED
    private var colorDetail: BungeeChatColor = DARK_RED

    companion object {
        fun start(context: CommandContext) =
            CommandUsageBuilder(context)
    }

    fun colors(primary: BungeeChatColor, detail: BungeeChatColor) : CommandUsageBuilder {
        colorPrimary = primary
        colorDetail = detail
        return this
    }

    fun withParameter(arg: SubcParameterBuilder) : CommandUsageBuilder {
        arg.colors(colorDetail, colorDetail)
        parameters.add(arg)
        return this
    }

    fun withConsoleParameter(arg: SubcParameterBuilder) : CommandUsageBuilder {
        arg.colors(colorDetail, colorDetail)
        consoleParameters.add(arg)
        return this
    }

    fun resetParameters() : CommandUsageBuilder {
        parameters.clear()
        return this
    }

    fun resetConsoleParameters() : CommandUsageBuilder {
        consoleParameters.clear()
        return this
    }

    private fun getLabelAsComponent() : TextComponent {
        val cmp = TextComponent("/")
        cmp.color = colorDetail
        cmp.addExtra("${context.label} ")
        if (subcommand.isNotEmpty()) {
            val subcCmp = TextComponent(subcommand) // TODO Opt-in hover for subcommand
            subcCmp.color = colorDetail
            cmp.addExtra(subcCmp)
            cmp.addExtra(" ")
        }
        return cmp
    }

    fun toComponents() : Array<BaseComponent> {
        val isConsoleContext = context.sender is ConsoleCommandSender && consoleParameters.isNotEmpty()
        val start = if (isConsoleContext)
            "Console Usage: "
        else
            "Usage: "
        val root = TextComponent(start)
        fun finish() = ComponentBuilder(root).create()

        root.color = colorPrimary
        root.addExtra(getLabelAsComponent())

        val contextualArguments = if (isConsoleContext) consoleParameters else parameters

        for ((i, arg) in contextualArguments.withIndex()) {
            root.addExtra(TextComponent(*arg.toComponents()))

            if (i != (consoleParameters.size - 1))
                root.addExtra(" ")
        }

        return finish()
    }
}