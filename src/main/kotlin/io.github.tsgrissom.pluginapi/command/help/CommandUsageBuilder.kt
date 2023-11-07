package io.github.tsgrissom.pluginapi.command.help

import BungeeChatColor
import io.github.tsgrissom.pluginapi.command.CommandContext
import net.md_5.bungee.api.ChatColor.*
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder

/*
 * Should be able to generate a few types of command usage texts
 * Examples:
 * "Usage: /heal <player>"
 * "Usage: /whois [temporary,permanent]"
 * "Usage: /whois [temporary,permanent]. Do /whois ? for help."
 */
class CommandUsageBuilder(
    val context: CommandContext
) {
    // TODO Implement CommandUsageGenerator

    private val arguments = mutableListOf<SubcParameterBuilder>()
    private val colorPrimary: BungeeChatColor = GOLD
    private val colorDetail: BungeeChatColor = RED

    companion object {
        fun start(context: CommandContext) =
            CommandUsageBuilder(context)
    }

    fun withArgument(arg: SubcParameterBuilder) : CommandUsageBuilder {
        arguments.add(arg)
        return this
    }

    fun toComponents() : Array<BaseComponent> {
        val cmp = ComponentBuilder()

        return cmp.create()
    }
}