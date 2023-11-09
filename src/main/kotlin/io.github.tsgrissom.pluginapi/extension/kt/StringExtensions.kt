package io.github.tsgrissom.pluginapi.extension.kt

import io.github.tsgrissom.pluginapi.extension.bukkit.getValidInputAliases
import io.github.tsgrissom.pluginapi.func.NonFormattingChatColorPredicate
import org.bukkit.ChatColor

/* MARK: Equality Checks */

/**
 * Checks for equality between two Strings with case-insensitivity.
 * @param other The String to compare against.
 * @return Whether the Strings are equal regardless of character case.
 */
fun String.equalsIc(other: String) : Boolean =
    this.equals(other, ignoreCase=true)

/**
 * Checks for case-insensitive equality between the String and any number of other Strings.
 * @param others Any number of Strings to compare against.
 * @return Whether a match was made regardless of character case.
 */
fun String.equalsIc(vararg others: String) : Boolean =
    others.firstOrNull { this.equalsIc(it) } != null

/**
 * Checks for case-insensitive equality between the String and any number of other Strings encapsulated in a List.
 * @param others The encapsulation of Strings within a List.
 * @return Whether a match was made regardless of character case.
 */
fun String.equalsIc(others: List<String>) : Boolean =
    others.firstOrNull { this.equalsIc(it) } != null

/**
 * Checks for exact equality between one String and any number of other Strings with case-sensitivity.
 * @param others Any number of Strings to compare against.
 * @return Whether exact equality was found.
 */
fun String.equalsAny(vararg others: String) : Boolean =
    others.firstOrNull { this == it } != null

/* MARK: Miscellaneous Checks */

/**
 * Checks if the String is surrounded by the requisite String, optionally ignoring case-sensitivity.
 * @param str The String to check if it is surrounding the String being operated on.
 * @param ignoreCase Whether to ignore case when checking if the String is wrapped. Default=false.
 * @return Whether the String is surrounded by the requisite String.
 */
fun String.isWrappedWith(
    str: String,
    ignoreCase: Boolean = false
) : Boolean {
    if (this.isEmpty() || this.length == 1)
        return false
    return this.startsWith(str, ignoreCase=ignoreCase) && this.endsWith(str, ignoreCase=ignoreCase)
}

/**
 * Checks if the String is wrapped by the same character. Takes the first character of the String and compares it to
 * the last character of the String.
 * @param ignoreCase Whether to ignore case when checking if the String is wrapped. Default=false.
 * @return Whether the String is surrounded by the same character.
 */
fun String.isWrappedWithSameChar(ignoreCase: Boolean = false) : Boolean {
    if (this.isEmpty() || this.length == 1)
        return false
    return this.endsWith(this[0], ignoreCase=ignoreCase)
}

fun String.wrap(token: String) : String =
    "$token$this$token"

fun String.wrapIfMissing(wrapWith: Char, bothOrNone: Boolean = false) : String {
    if (this.isEmpty())
        return this
    val wrapStart = this[0] != wrapWith
    val wrapEnd = this[this.length - 1] != wrapWith
    if (!wrapStart && !wrapEnd)
        return this
    if (bothOrNone && (!wrapStart || !wrapEnd))
        return this

    val pre  = if (wrapStart) wrapWith else ""
    val post = if (wrapEnd) wrapWith else ""

    return "$pre$this$post"
}

fun String.wrapIfMissing(wrapWith: String, bothOrNone: Boolean = false) : String {
    if (this.isEmpty() || wrapWith.isEmpty())
        return this

    val wrapStart = !this.startsWith(wrapWith)
    val wrapEnd = !this.endsWith(wrapWith)

    if (!wrapStart && !wrapEnd)
        return this
    if (bothOrNone && (!wrapStart || !wrapEnd))
        return this

    val pre  = if (wrapStart) wrapWith else ""
    val post = if (wrapEnd) wrapWith else ""

    return "$pre$this$post"
}

fun String.unwrap(wrapWith: Char) : String {
    if (this.isEmpty() || this.length == 1)
        return this
    val endIndex = this.length - 1
    if (this[0] == wrapWith && this[endIndex] == wrapWith) {
        return this.substring(1, endIndex)
    }
    return this
}

fun String.unwrap(wrapWith: String, ignoreCase: Boolean = false) : String {
    val minLength = 2 * wrapWith.length
    if (this.isEmpty() || wrapWith.isEmpty() || this.length < minLength)
        return this
    if (this.startsWith(wrapWith, ignoreCase=ignoreCase) && this.endsWith(wrapWith, ignoreCase=ignoreCase)) {
        return this.substring(wrapWith.length, this.lastIndexOf(wrapWith, ignoreCase=ignoreCase))
    }
    return this
}

/**
 * Checks if the String is surrounded by single quotes. Determined by checking if
 * the String starts with a single quote and ends with a single quote.
 * @return Whether the String is surrounded by single quotes.
 */
fun String.isSingleQuoted() : Boolean =
    this.isWrappedWith("'")

/**
 * Checks if the String is surrounded by double quotes. Determined by checking if
 * the String starts with a double quote and ends with a double quote.
 * @return Whether the String is surrounded by double quotes.
 */
fun String.isDoubleQuoted() : Boolean =
    this.isWrappedWith("\"")

/**
 * Checks if the String is surrounded by either kind of quote (single or double.)
 * To return `true`, either `String#isSingleQuoted` or `String#isDoubleQuoted` must
 * be true. Mixed leading/trailing quote types will return `false`.
 * @return Whether the String is quoted.
 */
fun String.isQuoted() : Boolean =
    (this.isSingleQuoted() || this.isDoubleQuoted())

/**
 * Checks if the String is not surrounded by either kind of quote (single or double.)
 * @return Whether the String is not quoted.
 */
fun String.isNotQuoted() : Boolean =
    !this.isQuoted()

/**
 * Wraps the String in quotation marks. By default, uses proper quotation mark characters. The String will not be
 * wrapped if it is already wrapped in the requisite characters.
 * @param single If enabled, apostrophes will be used to wrap the String instead of quotation marks.
 * @return The String wrapped in quotes.
 */
fun String.quoted(single: Boolean = false) : String =
    if (single) this.singleQuoted() else this.doubleQuoted()

/**
 * Wraps the String in quotation marks if the String is not wrapped in them already.
 * @return The String wrapped in quotation marks.
 */
fun String.doubleQuoted() : String =
    this.wrapIfMissing("\"", bothOrNone=true)

/**
 * Wraps the String in apostrophes if the String is not wrapped in them already.
 * @return The String wrapped in apostrophes.
 */
fun String.singleQuoted() : String =
    this.wrapIfMissing("'", bothOrNone=true)

/**
 * Removes surrounding pairs of matching quote characters from the String. If the String is not quoted, returns the
 * String as-is.
 * @return The String without surrounding quotations.
 */
fun String.dequoted() : String {
    if (!this.isQuoted())
        return this

    return if (this.isSingleQuoted())
        this.unwrap('\'')
    else if (this.isDoubleQuoted())
        this.unwrap('\"')
    else
        this
}

// TODO Whole number percentages, decimal percentages
/**
 * Checks if the String is in the format of a percentage input.
 * @return Whether the String is in percentage form.
 */
fun String.isPercentage() : Boolean =
    """^\d+(\.\d+)?%$""".toRegex().matches(this)

/* MARK: String Mutations */

// TODO Write test
fun String.removePrefixes(vararg prefixes: String, once: Boolean = false, ignoreCase: Boolean = false) : String {
    var s = this
    for (pre in prefixes) {
        if (s.startsWith(pre, ignoreCase=ignoreCase)) {
            s = s.removePrefix(pre)
            if (once)
                return s
        }
    }
    return s
}

// TODO Write test
fun String.removeSuffixes(
    vararg suffixes: String,
    once: Boolean = false,
    ignoreCase: Boolean = false
) : String {
    var s = this
    for (suff in suffixes) {
        if (s.endsWith(suff, ignoreCase=ignoreCase)) {
            s = s.removeSuffix(suff)
            if (once)
                return s
        }
    }
    return s
}

/**
 * Checks if the first character of the String is uppercased.
 * @return Whether the String is capitalized.
 */
fun String.isCapitalized() : Boolean {
    if (this.trim().isEmpty())
        return false

    return this[0].isUpperCase()
}

/**
 * Capitalizes a String by only altering the first letter. Alternate method offered
 * by `String#capitalizeAllCaps()`.
 * @return The String capitalized.
 */
fun String.capitalize() : String {
    if (this.isEmpty())
        return this

    var str = this.substring(0, 1).uppercase()

    if (this.length == 1)
        return str

    str += (this.substring(1, this.length)).lowercase()
    return str
}

/**
 * Capitalizes the String where it is expected to already be in all capital letters.
 * Lowercases the entire String and then capitalizes it.
 * @return The all caps String lowercased with only the first letter capitalized.
 */
fun String.capitalizeAllCaps() : String =
    this.lowercase().capitalize()

// TODO Write tests
/**
 * Capitalizes each word of the String where it is expected to be in all capital letters with each word delimited by a
 * specific String called the delimiter.
 * For example: Words of enum names are typically delimited by an underscore, so you can use this method to capitalize
 * each word of enum names by using the default delimiter "_".
 * @param delimiter The String to make each cleave at in the process of turning the String into words.
 * @return The processed String with each word capitalized.
 */
fun String.capitalizeEachWordAllCaps(delimiter: String = "_") : String {
    if (!this.contains(delimiter))
        return this.capitalizeAllCaps()

    val split = this.split(delimiter)
    var build = String()

    for ((i, s) in split.withIndex()) {
        build += s.capitalize()

        if (i != (split.size - 1))
            build += " "
    }

    return build
}

/**
 * Replaces placeholders (map keys surrounded by percent signs) with their corresponding
 * map value to allow the user to access a variety of info at configuration time.
 * @param replacements The Map of replacements to substitute into the String.
 * @return The String with substitutions made.
 */
fun String.replaceMap(replacements: Map<String, String>) : String {
    var str = this
    replacements.entries.forEach { (key, value) ->
        str = str.replace("%${key}", value)
    }
    return str
}

/**
 * Replaces placeholders (map keys surrounded by percent signs) with their corresponding
 * map value to allow the user to access a variety of info at configuration time.
 * @param replacements The Map of replacements to substitute into the String.
 * @return The List of Strings with substitutions made.
 */
fun MutableList<String>.replaceMap(replacements: Map<String, String>) : MutableList<String> {
    for ((i, line) in this.withIndex()) {
        val replaced = line.replaceMap(replacements)
        this[i] = replaced
    }
    return this
}

/**
 * Truncates a String to the max width if its length is greater than that number. If it is truncated, the postfix will
 * be appended to the end of the result.
 * @param maxWidth The maximum width of the String in characters.
 * @param trimBefore Whether the String should be trimmed before checking if it exceeds the max width.
 * @param postfix The String to append to the result if truncation is necessary. Set to empty String to have none.
 */
fun String.truncate(
    maxWidth: Int,
    trimBefore: Boolean = false,
    postfix: String = "...",
) : String {
    var str = this
    if (trimBefore)
        str = str.trim()
    val shouldTruncate = str.length > maxWidth
    if (shouldTruncate)
        str = str.substring(0, maxWidth)
    if (postfix.isNotEmpty() && shouldTruncate)
        str += postfix
    return str
}

/* MARK: ChatColor Related */

/**
 * Translates the standard alternate ChatColor code (&) into valid color codes.
 * @return The String with properly translated color codes.
 */
fun String.translateColor() : String =
    ChatColor.translateAlternateColorCodes('&', this)

/**
 * Removes ChatColor codes from the String.
 * @return The String sans ChatColor color codes.
 */
fun String.stripColor() : String =
    ChatColor.stripColor(this)!!

/**
 * Sequentially translates and strips ChatColor codes from the String. Effectively sanitizes the String of possible
 * color codes to avoid exploitation.
 * @return The String sans both valid color codes in addition to untranslated color codes.
 */
fun String.translateAndStripColorCodes() : String =
    this.translateColor().stripColor()

/**
 * Whether the String contains either valid ChatColor color codes or untranslated color codes.
 * This is determined by translating and stripping color codes and comparing the given String to the new String.
 * @return Whether the String contains color codes.
 */
fun String.containsChatColor() : Boolean {
    val tas = this.translateAndStripColorCodes()
    return this != tas
}

/**
 * Translates the standard alternate ChatColor code (&) into valid color codes.
 * @return The `List<String>`, each line with properly translated color codes.
 */
fun List<String>.translateColor() : List<String> {
    val ls = mutableListOf<String>()
    this.forEach { ls.add(it.translateColor()) }
    return ls
}

/**
 * Checks if the String consists of only ChatColors and no other text.
 * Determined by translating alternate codes, stripping the colors, and
 * comparing the resulting String to an empty String.
 * @return Whether the String consists of only ChatColors.
 */
fun String.isOnlyColorCodes() : Boolean {
    val stripped = this.translateAndStripColorCodes().trim()
    return stripped == ""
}

/**
 * Resolves the String to a ChatColor with a few techniques. First, method tries to match the String to an ampersand
 * followed by a valid chat color character. Next, it tries to match the string to a valid chat color character if the
 * String is only one character long. Finally, the method tries to match the String to the valid input aliases of each
 * enumerated ChatColor. This could be the regular spelling ("DARK_GRAY"), spelling without underscores ("DARKGRAY"),
 * or even special cases of alternative spellings ("DARKGREY" or "DARK_GREY", invalid but common mistake.)
 * @return A ChatColor or null.
 */
fun String.resolveChatColor() : ChatColor? {
    if (this.isEmpty())
        return null

    if (this.length == 1) {
        return ChatColor.getByChar(this)
    } else if (this.length == 2 && (this.startsWith("&") || this.startsWith("§"))) {
        var colorCode = this.removePrefix("&")
        if (colorCode.startsWith("§"))
            colorCode = colorCode.removePrefix("§")

        return ChatColor.getByChar(colorCode)
    } else {
        // TODO Something to support hex codes

        for (c in ChatColor.entries.filter { NonFormattingChatColorPredicate().test(it) }) {
            val aliases = c.getValidInputAliases().toList()

            if (this.equalsIc(aliases))
                return c
        }

        return null
    }
}