package io.github.tsgrissom.pluginapi.chat

import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.ChatColor as BungeeChatColor

class TextBoxGenerator(
    private val decorationColor: BungeeChatColor,
    private val withColor: Boolean = true
) {

    private val contents: MutableList<TextComponent> = mutableListOf()

    fun withLine(text: TextComponent) : TextBoxGenerator {
        // If all smashed into one line, do something like if not empty, prepend newline char
        this.contents.add(text)
        return this
    }

    fun withLines(vararg text: TextComponent) : TextBoxGenerator {
        this.contents.addAll(text)
        return this
    }

    fun withLine(str: String) : TextBoxGenerator {
        this.contents.add(TextComponent(str))
        return this
    }

    fun clearLines() : TextBoxGenerator {
        this.contents.clear()
        return this
    }

    fun toComponents() : Array<BaseComponent> {
        // 40 chars wide
        val horizontalLine = " ----------------------------------------" // TODO Chars + length options
        val pre = " | "
        val builder = ComponentBuilder()

        builder.append("${horizontalLine}\n")
        if (withColor)
            builder.color(decorationColor)

        for ((i, line) in contents.withIndex()) {
            fun newLine() {
                if (i != (contents.size - 1))
                    builder.append("\n")
            }

            builder.append(pre)
            if (withColor)
                builder.color(decorationColor)

            builder.append(line)
            newLine()
        }

        builder.append(horizontalLine)
        if (withColor)
            builder.color(decorationColor)

        return builder.create()
    }
}