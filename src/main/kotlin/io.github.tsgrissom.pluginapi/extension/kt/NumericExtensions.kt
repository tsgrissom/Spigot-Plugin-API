package io.github.tsgrissom.pluginapi.extension.kt

import BukkitChatColor

// TODO Write tests
/**
 * Formats the Double to have the requisite number of fractional digits trailing the decimal separator.
 * @param n The number of fractional digits allowed to trail the decimal separator.
 * @return A new Double containing the requisite number of fractional digits trailing the decimal separator.
 */
fun Double.roundToDigits(n: Int) : Double =
    String.format("%.${n}f", this).toDouble()

fun Int.calculateIndexOfNextPage(maxPage: Int) : Int {
    return if ((maxPage - 1) > this)
        this + 1
    else
        0
}

fun Int.calculateIndexOfPreviousPage(maxPage: Int) : Int {
    return if (this > 0)
        this - 1
    else
        maxPage - 1
}

fun Long.convertTicksTo24Hour(
    withColor: Boolean = true,
    colorValue: BukkitChatColor = BukkitChatColor.RED,
    colorSecondary: BukkitChatColor = BukkitChatColor.DARK_GRAY
) : String {
    if (this < 0 || this > 24000)
        throw IllegalArgumentException("\"$this\" is out of Range[0->24000]. Cannot convert ticks to 24 hour clock.")

    val ticksPerDay = 24000
    val ticksPerHour = ticksPerDay / 24

    // Calculate the number of hours and minutes
    val hours = (this % ticksPerDay) / ticksPerHour
    val minutes = ((this % ticksPerDay) % ticksPerHour) * 60 / ticksPerHour

    // Format the time as "HH:mm"
    val ccVal = if (withColor) colorValue else ""
    val ccSec = if (withColor) colorSecondary else ""
    return String.format(
        "%s%02d%s:%s%02d",
        ccVal, hours, ccSec, ccVal, minutes
    )
}