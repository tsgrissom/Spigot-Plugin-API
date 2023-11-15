package extension

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.extension.kt.calculateIndexOfNextPage
import io.github.tsgrissom.pluginapi.extension.kt.calculateIndexOfPreviousPage
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class NumericExtensionsTest : PAPIPluginTest() {

    @ParameterizedTest
    @ValueSource(ints=[0,1,2,3,4,5])
    fun calculateNextPageIndex_max4_shouldNeverBeGreaterThan4(value: Int) {
        val maxPageIndex = 4
        val resultOfNext = value.calculateIndexOfNextPage(maxPageIndex)
        assertTrue(
            resultOfNext <= maxPageIndex,
            "\"$value\"->calculateNextPageIndex(max=$maxPageIndex) \"$resultOfNext\" is greater than max page index (=$maxPageIndex)")
    }

    @ParameterizedTest
    @ValueSource(ints=[-2,-1,0,1,2,3])
    fun calculatePreviousPageIndex_max4_shouldNeverBeLessThan0_and_shouldNeverBeGreaterThan4(value: Int) {
        val maxPageIndex = 4
        val resultOfPrevious = value.calculateIndexOfPreviousPage(maxPageIndex)
        assertTrue(
            resultOfPrevious >= 0,
            "\"$value\"->calculatePreviousPageIndex(max=$maxPageIndex) \"$resultOfPrevious\" is less than 0"
        )

        assertTrue(
            resultOfPrevious <= maxPageIndex,
            "\"$value\"->calculateNextPageIndex(max=$maxPageIndex) \"$resultOfPrevious\" is greater than max page index (=$maxPageIndex)"
        )
    }
}