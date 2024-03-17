package io.github.tsgrissom.pluginapi.extension.kt

import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.log10

// TODO Write tests
/**
 * Formats the Double to have the requisite number of fractional digits trailing the decimal separator.
 * @param n The number of fractional digits allowed to trail the decimal separator.
 * @return A new Double containing the requisite number of fractional digits trailing the decimal separator.
 */
fun Double.roundToDigits(n: Int) : Double =
    String.format("%.${n}f", this).toDouble()

fun Double.countAllDigits(): Int {
    if (this == 0.0) // Special case for 0
        return 0

    val abs = abs(this)
    val log = ceil(log10(abs))
    val intPartDigits = log.toInt()

    // Convert to string to count decimal places (including leading 0)
    val stringRep = abs.toString()
    val decimalPointIndex = stringRep.indexOf('.')
    val decimalPlaces = if (decimalPointIndex == -1) 0 else stringRep.length - decimalPointIndex - 1

    var sumOfDigits = intPartDigits + decimalPlaces

    if (this % 1.0 == 0.0 && this != 1.0) { // If receiver is a whole number > 1, a trailing zero will have been counted
        sumOfDigits -= 1
    }

    return sumOfDigits + (if (this < 0.0) 1 else 0)
}

fun Double.countLeadingDigits() : Int {
    if (this == 0.0)
        return 0
    if (this == 1.0)
        return 1

    val abs = abs(this)
    val log = ceil(log10(abs))

    return log.toInt() + (if (this < 0.0) 1 else 0)
}

fun Double.countTrailingDigits() : Int {
    if (this == 0.0)
        return 0
    if (this % 1.0 == 0.0)
        return 0

    val abs = abs(this)
    val stringRep = abs.toString()
    val decimalPointIndex = stringRep.indexOf('.')
    val decimalPlaces =
        if (decimalPointIndex == -1) 0
        else stringRep.length - decimalPointIndex - 1

    return decimalPlaces
}