package com.github.hsz.aoc

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest

abstract class DayTest(val day: Day) {

    fun test(
        function: (String) -> Number,
        answer: Number,
        testData: Collection<Pair<String, Number>> = emptyList(),
        asserts: Collection<Pair<Number, Number>> = emptyList(),
    ) =
        testData.mapIndexed { index, (testInput, expected) ->
            DynamicTest.dynamicTest("Test case #${index + 1}") {
                Assertions.assertEquals(expected, function(testInput.trimIndent()))
            }
        } +
            asserts.mapIndexed { index, (testValue, expected) ->
                DynamicTest.dynamicTest("Test case #${index + 1 + testData.size}") {
                    Assertions.assertEquals(expected, testValue)
                }
            } +
            DynamicTest.dynamicTest("Solution") {
                Assertions.assertEquals(answer, function(day.input))
            }
}
