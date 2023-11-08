package io.github.tsgrissom.pluginapi.chat

import BungeeChatColor
import io.github.tsgrissom.pluginapi.extension.bukkit.getDynamicHoverEvent
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text

open class FormattedListBuilder(
    private val name: String = "List"
) {

    var withColor: Boolean = true
    var withCounter: Boolean = false
    var delimiter: String = ", "
    var colorPrimary: BungeeChatColor = BungeeChatColor.GOLD
    var colorSecondary: BungeeChatColor = BungeeChatColor.GRAY
    var colorPunctuation: BungeeChatColor = BungeeChatColor.DARK_GRAY
    var colorValue: BungeeChatColor = BungeeChatColor.YELLOW

    companion object {
        fun start(name: String = "List") =
            FormattedListBuilder(name=name)
    }

    fun withColor(b: Boolean = true) : FormattedListBuilder {
        this.withColor = b
        return this
    }

    fun withCounter(b: Boolean = true) : FormattedListBuilder {
        this.withCounter = b
        return this
    }

    fun delimiter(str: String) : FormattedListBuilder {
        this.delimiter = str
        return this
    }

    fun colorPrimary(color: BungeeChatColor) : FormattedListBuilder {
        this.colorPrimary = color
        return this
    }

    fun colorSecondary(color: BungeeChatColor) : FormattedListBuilder {
        this.colorSecondary = color
        return this
    }

    fun colorPunctuation(color: BungeeChatColor) : FormattedListBuilder {
        this.colorPunctuation = color
        return this
    }

    fun colorValue(color: BungeeChatColor) : FormattedListBuilder {
        this.colorPrimary = color
        return this
    }

    fun colors(primary: BungeeChatColor, secondary: BungeeChatColor, punctuation: BungeeChatColor, value: BungeeChatColor) : FormattedListBuilder {
        colorPrimary(primary)
        colorSecondary(secondary)
        colorPunctuation(punctuation)
        colorValue(value)
        return this
    }

    fun resetColors() : FormattedListBuilder {
        this.colorPrimary = BungeeChatColor.GOLD
        this.colorPunctuation = BungeeChatColor.DARK_GRAY
        this.colorValue = BungeeChatColor.YELLOW
        return this
    }

    open fun formatEntryToTextComponent(entry: String) : TextComponent {
        val comp = TextComponent(entry)
        if (withColor)
            comp.color = colorValue
        return comp
    }

    private fun getCounter(collection: Collection<String>) : TextComponent {
        val counterOpen = TextComponent("(")
        val counterClose = TextComponent(")")
        val counterBody = TextComponent("${collection.size}")
        counterOpen.color = colorPunctuation
        counterClose.color = colorPunctuation
        counterBody.color = colorSecondary
        val counter = TextComponent(counterOpen)
        counter.addExtra(counterBody)
        counter.addExtra(counterClose)
        return counter
    }

    fun format(collection: Collection<String>) : Array<BaseComponent> {
        val root = TextComponent()
        fun colorIf(comp: TextComponent, color: BungeeChatColor) {
            if (withColor)
                comp.color = color
        }
        fun lineBreak() {
            root.addExtra("\n")
        }
        fun finish() = ComponentBuilder(root).create()

        val listName = TextComponent(name)
        colorIf(listName, colorPrimary)
        val colon = TextComponent(": ")
        colorIf(colon, colorPunctuation)

        root.addExtra(listName)
        if (withCounter && collection.isNotEmpty()) {
            root.addExtra(" ")
            root.addExtra(getCounter(collection))
        }
        root.addExtra(colon)

        if (collection.isEmpty()) {
            val none = TextComponent("None")
            none.color = BungeeChatColor.RED
            root.addExtra(none)
            return finish()
        }

        val delim = TextComponent(delimiter)
        colorIf(delim, colorPunctuation)

        for ((i, elem) in collection.withIndex()) {
            val entry = formatEntryToTextComponent(elem)
            root.addExtra(entry)
            if (i != (collection.size - 1))
                root.addExtra(delim)
        }

        return finish()
    }

    fun formatMultiLine(collection: Collection<String>) : Array<BaseComponent> {
        val root = TextComponent()
        fun colorIf(comp: TextComponent, color: BungeeChatColor) {
            if (withColor)
                comp.color = color
        }
        fun lineBreak() {
            root.addExtra("\n")
        }
        fun finish() = ComponentBuilder(root).create()

        val listName = TextComponent(name)
        val colon = TextComponent(": ")
        colorIf(colon, colorPunctuation)

        root.addExtra(listName)
        colorIf(listName, colorPrimary)
        if (withCounter && collection.isNotEmpty()) {
            root.addExtra(" ")
            root.addExtra(getCounter(collection))
        }
        root.addExtra(colon)

        if (collection.isEmpty()) {
            val none = TextComponent("None")
            none.color = BungeeChatColor.RED
            root.addExtra(none)
            return finish()
        }

        lineBreak()
        for ((i, elem) in collection.withIndex()) {
            val entry = formatEntryToTextComponent(elem)
            val pre = TextComponent(" - ")
            colorIf(pre, colorPunctuation)
            root.addExtra(pre)
            root.addExtra(entry)
            if (i != (collection.size - 1))
                lineBreak()
        }

        return finish()
    }

    // TODO Format which accepts Collection<Any> and a converter function

    fun build(collection: Collection<String>, multiline: Boolean = false) : Array<BaseComponent> =
        if (multiline) this.formatMultiLine(collection) else this.format(collection)

}

class PlainFormattedListBuilder(
    name: String = "List"
) : FormattedListBuilder(name) {
    init {
        withColor(false)
    }
}

class ClickFormattedListBuilder(
    name: String = "List",
    private val onClickAction: (String) -> ClickEvent.Action,
    private val onClickValue: (String) -> String
) : FormattedListBuilder(name) {
    override fun formatEntryToTextComponent(entry: String): TextComponent {
        val comp = TextComponent(entry)
        if (withColor)
            comp.color = colorValue
        val clickEvent = ClickEvent(onClickAction(entry), onClickValue(entry))
        comp.clickEvent = clickEvent
        comp.hoverEvent = clickEvent.getDynamicHoverEvent()
        return comp
    }
}

class HoverFormattedListBuilder(
    name: String = "List",
    private val onHoverElement: (String) -> Array<Text>
) : FormattedListBuilder(name) {
    override fun formatEntryToTextComponent(entry: String) : TextComponent {
        val comp = TextComponent(entry)
        if (withColor)
            comp.color = colorValue
        comp.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, *onHoverElement(entry))
        return comp
    }
}