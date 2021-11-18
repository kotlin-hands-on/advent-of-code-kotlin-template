package com.github.hsz.aoc.aoc2021

import com.github.hsz.aoc.DayTest
import org.junit.jupiter.api.TestFactory

class Day01Test : DayTest(Day01()) {

    @TestFactory
    fun `Part 1`() = test(
        function = day::part1,
        testData = listOf(
            "" to 0,
        ),
        answer = 0
    )

    @TestFactory
    fun `Part 2`() = test(
        function = day::part2,
        testData = listOf(
            "" to 0,
        ),
        answer = 0
    )
}

