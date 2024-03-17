package io.github.tsgrissom.pluginapi.extension.kt

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