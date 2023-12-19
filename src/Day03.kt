fun main() {
    class NumberWithLocation(val number: Int, val row: Int, val cols: IntRange)

    fun List<String>.toNumberWithLocation() =
        flatMapIndexed { row, line ->
            "[0-9]+".toRegex().findAll(line).map { matchResult ->
                NumberWithLocation(
                    number = matchResult.value.toInt(),
                    row = row,
                    cols = matchResult.range,
                )
            }
        }

    fun List<String>.toSymbolLocations() =
        flatMapIndexed { row, line ->
            line.indices
                .filter { !line[it].isDigit() && line[it] != '.' }
                .map { col -> row to col }
        }

    fun NumberWithLocation.isAdjacentTo(otherLocation: Pair<Int, Int>) =
        otherLocation.let { (otherRow, otherCol) ->
            ((otherRow - 1)..(otherRow + 1)).contains(row) &&
                    ((otherCol - 1)..(otherCol + 1)).any(cols::contains)
        }

    fun part1(numbersWithLocation: List<NumberWithLocation>, symbolLocations: List<Pair<Int, Int>>): Int =
        numbersWithLocation.filter { symbolLocations.any(it::isAdjacentTo) }.sumOf { it.number }

    fun part2(numbersWithLocation: List<NumberWithLocation>, starLocations: List<Pair<Int, Int>>): Int =
        starLocations
            .map { starLocation -> numbersWithLocation.filter { it.isAdjacentTo(starLocation) } }
            .filter { it.size == 2 }
            .sumOf { (first, second) -> first.number * second.number }

    val day1Part1Input = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...$.*....
            .664.598..
        """.trimIndent().lines()

    day1Part1Input.let {
        val numbersWithLocation = it.toNumberWithLocation()
        val symbolLocations = it.toSymbolLocations()
        check(part1(numbersWithLocation, symbolLocations) == 4361)
        val starLocations = symbolLocations.filter { (r, c) -> it[r][c] == '*' }
        check(part2(numbersWithLocation, starLocations) == 467835)
    }

    val input = readInput("inputDay3")
    val numbersWithLocation = input.toNumberWithLocation()
    val symbolLocations = input.toSymbolLocations()
    part1(numbersWithLocation, symbolLocations).println()
    val starLocations = symbolLocations.filter { (r, c) -> input[r][c] == '*' }
    part2(numbersWithLocation, starLocations).println()
}
