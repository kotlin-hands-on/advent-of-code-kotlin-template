package com.github.kotlinhandson.aoc

import com.github.kotlinhandson.aoc.utils.ints

class Day01 : Day(1) {

    override fun part1(input: String): Int {
        return input.ints().sum()
    }

    override fun part2(input: String): Int {
        return 0
    }
}
