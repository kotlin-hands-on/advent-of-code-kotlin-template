package com.github.kotlinhandson.aoc

import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test : DayTest() {

    override val day = Day01()

    @Test
    fun `Part 1`() {
        assertEquals(0, day.part1("foo")) // check against test input
        assertEquals(0, day.part1()) // check against input data
    }

    @Test
    fun `Part 2`() {
        assertEquals(0, day.part2("bar"))
        assertEquals(0, day.part2())
    }
}
