interface Application {
    fun run(fileName: String): Pair<Long, Long>
}

fun main(args: Array<String>) {
    val day = 18
    runApp(day, true)
    runApp(day, false)
}

fun runApp(day: Int, isTest: Boolean = false) {
    val app: Application = chooseClassFromDay(day)
    when (isTest) {
        true -> app.run("day${day.toString().padStart(2, '0')}test").printRes(day, true)
        false -> app.run("day${day.toString().padStart(2, '0')}").printRes(day, false)

    }
}

fun chooseClassFromDay(day: Int): Application = when(day) {
    9 -> Day09()
    10 -> Day10()
    11 -> Day11()
    12 -> Day12()
    13 -> Day13()
    14 -> Day14()
    15 -> Day15()
    16 -> Day16()
    17 -> Day17()
    18 -> Day18()
    19 -> Day19()
    20 -> Day20()
    21 -> Day21()
    22 -> Day22()
    23 -> Day23()
    24 -> Day24()
    25 -> Day25()
    else -> throw IllegalArgumentException("Unknown day: $day")
}

fun Pair<Long, Long>.printRes(day: Int, isTest: Boolean = false) {
    val dayStr =
        if (isTest) "day${day.toString().padStart(2, '0')}-test" else "day-${day.toString().padStart(2, '0')}"
    println("${dayStr}-1: $first")
    println("${dayStr}-2: $second")
}