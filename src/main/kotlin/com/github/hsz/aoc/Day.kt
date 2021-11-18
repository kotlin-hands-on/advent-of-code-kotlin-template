package com.github.hsz.aoc

abstract class Day(year: Number, day: Number) {

    val input = Resources.asString("aoc${year}/day${day.toString().padStart(2, '0')}.txt")

    abstract fun part1(input: String): Number

    abstract fun part2(input: String): Number
}
