package io.github.tsgrissom.pluginapi.chat

import BungeeChatColor
import net.md_5.bungee.api.ChatColor.*
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.TextComponent

class TextBoxBuilder(
    private val decorationColor: BungeeChatColor = GRAY,
    private val withColor: Boolean = true,
    private val decorationPrefix: String = " ",
    private val decorationChar: Char = '-',
    private val repeatDecorationChar: Int = 40,
    private val linePrefix: String = " | "
) {

    private val contents: MutableList<TextComponent> = mutableListOf()

    companion object {
        fun start(decorationColor: BungeeChatColor) : TextBoxBuilder =
            TextBoxBuilder(decorationColor=decorationColor)
    }

    fun withLine(vararg text: TextComponent) : TextBoxBuilder {
        this.contents.addAll(text)
        return this
    }

    fun withLine(vararg text: String) : TextBoxBuilder {
        text.forEach { this.contents.add(TextComponent(it)) }
        return this
    }

    fun clearLines() : TextBoxBuilder {
        this.contents.clear()
        return this
    }

    fun toComponents() : Array<BaseComponent> {
        val repeated = "$decorationChar".repeat(repeatDecorationChar)
        val horizontalLine = "$decorationPrefix$repeated"
        val builder = ComponentBuilder()

        builder.append("${horizontalLine}\n")
        if (withColor)
            builder.color(decorationColor)

        for (line in contents) {
            builder.append(linePrefix)
            if (withColor)
                builder.color(decorationColor)

            builder.append(line).append("\n")
        }

        builder.append(horizontalLine)
        if (withColor)
            builder.color(decorationColor)

        return builder.create()
    }
}