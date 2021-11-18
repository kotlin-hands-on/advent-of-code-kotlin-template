package com.github.hsz.aoc

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest

open class DayTest(val day: Day) {
    fun test(
        function: Function1<String, Number>,
        answer: Number,
        data: Collection<Pair<String, Number>> = emptyList(),
        custom: Collection<Pair<Number, Number>> = emptyList(),
    ) =
        data.mapIndexed { index, (testInput, expected) ->
            DynamicTest.dynamicTest("Test case #${index + 1}") {
                Assertions.assertEquals(expected, function(testInput.trimIndent()))
            }
        } +
            custom.mapIndexed { index, (testValue, expected) ->
                DynamicTest.dynamicTest("Test case #${index + 1 + data.size}") {
                    Assertions.assertEquals(expected, testValue)
                }
            } +
            DynamicTest.dynamicTest("Solution") {
                Assertions.assertEquals(answer, function(day.input))
            }
}
