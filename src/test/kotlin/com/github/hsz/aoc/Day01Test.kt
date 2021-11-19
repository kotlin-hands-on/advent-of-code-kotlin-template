package com.github.hsz.aoc

import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test : DayTest(Day01()) {
    @Test
    fun part1() {
        assertEquals(0, day.part1(""))
    }

    @Test
    fun part2() {
        assertEquals(0, day.part2(""))
        assertEquals(0, day.part2(day.input))
    }
}
