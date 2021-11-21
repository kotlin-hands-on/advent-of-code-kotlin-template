package com.github.hsz.aoc

import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test : DayTest() {

    override val day = Day01()

    @Test
    fun part1() {
        assertEquals(0, day.part1("foo")) // check against test input
        assertEquals(0, day.part1()) // check against input data
    }

    @Test
    fun part2() {
        assertEquals(0, day.part2("bar"))
        assertEquals(0, day.part2())
    }
}
