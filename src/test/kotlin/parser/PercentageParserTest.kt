package parser

import PAPIPluginTest
import io.github.tsgrissom.pluginapi.parser.ParsedPercentage
import io.github.tsgrissom.pluginapi.parser.ParsedPercentageResult
import io.github.tsgrissom.pluginapi.parser.PercentageParser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PercentageParserTest : PAPIPluginTest() {

    private fun assertSuccessful(result: ParsedPercentage) =
        Assertions.assertTrue(result.wasSuccessful(), "Parsing was unsuccessful; Expected success")
    private fun assertUnsuccessful(result: ParsedPercentage) =
        Assertions.assertTrue(result.wasUnsuccessful(), "Parsing was successful; Expected failure")

    // MARK: Default Builder Tests

    @ParameterizedTest
    @ValueSource(strings=[
        "100%",
        "10.5%",
        "0.0%",
        "0%",
        "-0.75%",
        "-1%",
    ])
    fun defaultBuilder_result_wasSuccessful_isTrueWhenVariousValidInputs(value: String) {
        val result = PercentageParser().parse(value)
        assertSuccessful(result)
    }

    @ParameterizedTest
    @ValueSource(strings=[
        "foo",
        "0",
        "100.0",
        "-1",
        "%10"
    ])
    fun defaultBuilder_result_state_shouldEqualInvalidFormWhenVariousInvalidForms(value: String) {
        val result = PercentageParser().parse(value)
        val expected = ParsedPercentageResult.INVALID_FORM
        Assertions.assertEquals(expected, result.getResult())
    }

    // MARK: Custom Builder Tests

    @ParameterizedTest
    @ValueSource(strings=[
        "0",
        "0%",
        "0.0"
    ])
    fun customBuilder_noZeroesAndAcceptOmittedPercentSign_hasIssue_isTrueWhenInputIsZero(value: String) {
        val result = PercentageParser()
            .acceptZero(false)
            .acceptOmittedPercentSign(true)
            .parse(value)
        assertUnsuccessful(result)
    }

    @ParameterizedTest
    @ValueSource(strings=[
        "0",
        "1.5",
        "0.05"
    ])
    fun customBuilder_acceptOmittedPercentSign_result_wasSuccessful_whenImplicitPercentSignInput(value: String) {
        val result = PercentageParser()
            .acceptOmittedPercentSign(true)
            .parse(value)
        assertSuccessful(result)
    }

    @ParameterizedTest
    @ValueSource(strings=[
        "100.0%",
        "0.5%",
        "-1.5%"
    ])
    fun customBuilder_noFractionalValues_hasIssue_isTrueWhenFractionalInput(value: String) {
        val result = PercentageParser()
            .acceptFractionalValues(false)
            .parse(value)
        assertUnsuccessful(result)
    }

    @ParameterizedTest
    @ValueSource(strings=[
        "-1%",
        "-0.05%",
        "-100%"
    ])
    fun customBuilder_noNegativeValues_hasIssue_isTrueWhenNegativeInput(value: String) {
        val result = PercentageParser()
            .acceptNegativeNumbers(false)
            .parse(value)
        assertUnsuccessful(result)
    }
}