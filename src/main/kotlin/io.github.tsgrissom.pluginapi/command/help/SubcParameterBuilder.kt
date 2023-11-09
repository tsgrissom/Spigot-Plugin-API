package io.github.tsgrissom.pluginapi.command.help

import BungeeChatColor
import io.github.tsgrissom.pluginapi.extension.bukkit.appendc
import io.github.tsgrissom.pluginapi.extension.kt.translateColor
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text

class SubcParameterBuilder(
    val name: String,
    private var required: Boolean = false
) {

    private var hoverText: MutableList<String> = mutableListOf()
    private var underlined = false
    private var colorPunctuation = BungeeChatColor.DARK_GRAY
    private var colorValue = BungeeChatColor.YELLOW

    companion object {
        fun start(name: String) =
            SubcParameterBuilder(name)
    }

    fun colors(punctuation: BungeeChatColor, value: BungeeChatColor) : SubcParameterBuilder {
        this.colorPunctuation = punctuation
        this.colorValue = value
        return this
    }

    fun required(b: Boolean) : SubcParameterBuilder {
        this.required = b
        return this
    }

    fun required() : SubcParameterBuilder {
        this.required = true
        return this
    }

    fun optional() : SubcParameterBuilder {
        this.required = false
        return this
    }

    fun underlined(b: Boolean) : SubcParameterBuilder {
        this.underlined = b
        return this
    }

    fun hoverText(vararg text: String) : SubcParameterBuilder {
        this.hoverText = text.map { it.translateColor() }.toMutableList()
        return this
    }

    fun toComponents() : Array<BaseComponent> {
        val onHover: MutableList<Text> = mutableListOf()
        val requiredLine = ComponentBuilder()
            .appendc("Required: ", ChatColor.GRAY)
            .appendc(if (required) "Yes" else "No", if (required) ChatColor.GREEN else ChatColor.RED)
            .append("\n")
            .create()

        onHover.add(Text(requiredLine))

        if (hoverText.isNotEmpty()) {
            for ((i, str) in hoverText.withIndex()) {
                val postfix = if (i != (hoverText.size - 1)) "\n" else ""
                onHover.add(Text("$str$postfix"))
            }
        }

        val nameComp = TextComponent(name)
        nameComp.color = colorValue

        if (onHover.isNotEmpty()) {
            nameComp.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, onHover.toList())
            nameComp.isUnderlined = true
        }

        val comp = ComponentBuilder()
            .appendc(if (required) "<" else "[", colorPunctuation)
            .append(nameComp)
            .appendc(if (required) ">" else "]", colorPunctuation)
            .underlined(false)

        return comp.create()
    }
}