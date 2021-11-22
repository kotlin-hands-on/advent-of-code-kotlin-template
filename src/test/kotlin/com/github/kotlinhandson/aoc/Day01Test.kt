package com.github.kotlinhandson.aoc

import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test : DayTest() {

    override val day = Day01()

    @Test
    override fun `Part 1`() {
        assertEquals(0, day.part1("test_input"))    // check against test input
        assertEquals(0, day.part1())                // check solution against input data
    }

    @Test
    override fun `Part 2`() {
        assertEquals(0, day.part2("test_input"))
        assertEquals(0, day.part2())
    }
}
