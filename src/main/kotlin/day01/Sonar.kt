package day01

import java.nio.file.Paths

class Sonar {
    fun countSlidingWindowIncrements(input: List<String>, windowSize: Int): Int {
        val filteredInput = input.mapNotNull { it.toIntOrNull() }

        var increments = 0

        for (i in 0 until filteredInput.size - windowSize) {
            val previousWindowSum = filteredInput.subList(i, i + windowSize).sum()
            val nextWindowSum = filteredInput.subList(i + 1, i + windowSize + 1).sum()
            if (nextWindowSum > previousWindowSum) increments++
        }

        return increments
    }
}

fun main() {
    val path = Paths.get("day01", "input.txt").toString()
    val lines = Sonar::class.java.classLoader.getResource(path).readText().lines()
    println(Sonar().countSlidingWindowIncrements(lines, 1))
    println(Sonar().countSlidingWindowIncrements(lines, 3))
}
