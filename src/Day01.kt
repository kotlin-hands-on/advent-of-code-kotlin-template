fun main() {
    val adventOfCodeUtils = AdventOfCodeUtils()
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = adventOfCodeUtils.readInput("Day01_test")
    check(part1(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = adventOfCodeUtils.readInput("Day01")
    part1(input).println()
    part2(input).println()
}
