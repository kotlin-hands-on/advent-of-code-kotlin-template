fun main() {
    val day1Part1Input = """
            1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet
        """.trimIndent().lines()

    val day1Part2Input = """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen
        """.trimIndent().lines()

    check(part1(day1Part1Input) == 142)
    check(part2(day1Part2Input) == 281)

    val input = readInput("inputDay1")
    part1(input).println()
    part2(input).println()
}

fun part2(input: List<String>): Int {
    val digits = (0..9).map { it.toString() } +
            listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    fun Int.indexToExpectedDigit() =
        takeIf { it != -1 && (it % 10) != 0 }
            ?.let { it % 10 }

    fun String.firstDigit(): Int {
        for (partialStr in windowedSequence(5, partialWindows = true)) {
            return digits.indexOfFirst { partialStr.startsWith(it) }.indexToExpectedDigit() ?: continue
        }
        return 0
    }

    fun String.lastDigit(): Int {
        for (partialStr in reversedWindowedSequence(5, partialWindows = true)) {
            return digits.indexOfFirst { partialStr.endsWith(it) }.indexToExpectedDigit() ?: continue
        }
        return 0
    }

    return input.sumOf { (it.firstDigit() * 10) + it.lastDigit() }
}

fun part1(input: List<String>): Int {
    fun String.firstDigit(): Int =
        find { it.isDigit() }?.digitToInt() ?: 0

    fun String.lastDigit(): Int =
        findLast { it.isDigit() }?.digitToInt() ?: 0

    return input.sumOf { line ->
        (line.firstDigit() * 10) + line.lastDigit()
    }
}