import kotlin.math.max

private enum class Color {
    RED, GREEN, BLUE
}

private class Game(val id: Int, val sets: List<Map<Color, Int>>)

fun main() {
    fun stringToGame(line: String) =
        line.splitNTrim(':').let { (game, setStr) ->
            Game(
                id = game.splitNTrim(' ')[1].toInt(),
                sets = setStr.splitNTrim(';')
                    .map { str ->
                        str.splitNTrim(',').associate {
                            val (n, color) = it.splitNTrim(' ')
                            Color.valueOf(color.uppercase()) to n.toInt()
                        }
                    }
            )
        }

    val day2Part1Input =
        """
            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
            Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
            Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
            Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
        """.trimIndent().lines()

    day2Part1Input.map { stringToGame(it) }.let {
        check(part1(it) == 8)
        check(part2(it) == 2286)
    }

    val input = readInput("inputDay2").map { stringToGame(it) }
    part1(input).println()
    part2(input).println()
}

private fun part1(input: List<Game>): Int {
    val cubesInBag = mapOf(
        Color.RED to 12, Color.GREEN to 13, Color.BLUE to 14,
    )

    fun Game.isPossible() =
        sets.all { colorToNumberSet ->
            colorToNumberSet.all { (color, n) ->
                cubesInBag[color]!! >= n
            }
        }

    return input.filter { it.isPossible() }.sumOf { it.id }
}

private fun part2(input: List<Game>) =
    input.sumOf { game ->
        val cubesRequired = mutableMapOf<Color, Int>()
        game.sets
            .forEach { gameSet ->
                gameSet.forEach { (color, n) ->
                    cubesRequired[color] = max((cubesRequired[color] ?: 0), n)
                }
            }
        cubesRequired.values.reduce(Int::times)
    }
