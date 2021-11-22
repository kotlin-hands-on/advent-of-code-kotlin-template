fun part1(input: List<String>): Int {
    return input.size
}

fun part2(input: List<String>): Int {
    return input.size
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    assert(part1(testInput) == 1)

    val input = readInput("Day01")
    part1(input).also(::println)
    part2(input).also(::println)
}
