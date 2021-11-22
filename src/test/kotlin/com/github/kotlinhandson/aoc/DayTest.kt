package com.github.kotlinhandson.aoc

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class DayTest {

    abstract val day: Day

    abstract fun `Part 1`()

    abstract fun `Part 2`()

    @AfterAll
    fun solve() {
        with(day) {
            println("Solutions for Day $number:")
            println("Part 1: ${part1()}")
            println("Part 2: ${part2()}")
        }
    }
}
