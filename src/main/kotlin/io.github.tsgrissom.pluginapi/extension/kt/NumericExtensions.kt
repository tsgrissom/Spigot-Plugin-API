package io.github.tsgrissom.pluginapi.extension.kt

import org.bukkit.ChatColor

// TODO Write tests
/**
 * Formats the Double to have the requisite number of fractional digits trailing the decimal separator.
 * @param n The number of fractional digits allowed to trail the decimal separator.
 * @return A new Double containing the requisite number of fractional digits trailing the decimal separator.
 */
fun Double.roundToDigits(n: Int) : Double =
    String.format("%.${n}f", this).toDouble()

fun Long.convertTicksTo24Hour(withColor: Boolean = false) : String {
    val ticksPerDay = 24000
    val ticksPerHour = ticksPerDay / 24

    // Calculate the number of hours and minutes
    val hours = (this % ticksPerDay) / ticksPerHour
    val minutes = ((this % ticksPerDay) % ticksPerHour) * 60 / ticksPerHour

    // Format the time as "HH:mm"
    val valueColor = if (withColor) ChatColor.RED else ""
    val accentColor = if (withColor) ChatColor.DARK_GRAY else ""
    return String.format("${valueColor}%02d${accentColor}:${valueColor}%02d", hours, minutes)
}