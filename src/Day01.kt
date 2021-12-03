fun main() {
    fun part1(input: List<Int>): Int {
        return input.size
    }

    fun part2(input: List<Int>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test").toInts()
    check(part1(testInput) == 1)

    val input = readInput("Day01").toInts()
    println(part1(input))
    println(part2(input))
}
