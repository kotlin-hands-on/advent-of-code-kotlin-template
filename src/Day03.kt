fun main() {
    val inputs = readInput("day03tj")
    val input = inputs.fold("") { acc, str -> acc.plus(str) }
    val res1 = extractNumberPairs(input)
        .fold(0) { acc2, pair -> acc2 + pair.first * pair.second }
    println("day3-1: $res1")

    val input2 = input.split("""do()""").drop(1).map { it.split("""don't()""", limit = 2).first() }
    input2.forEach(::println)
    val res2 = input2.fold(0) {
        acc, input -> acc + extractNumberPairs(input)
        .fold(0) { acc2, pair -> acc2 + pair.first * pair.second }
    }
    println("day3-2: $res2")
}

fun extractNumberPairs(input: String): List<Pair<Int, Int>> {
    // Regex to capture numbers from mul(xxx,xxx)
    val pattern = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
    return pattern.findAll(input).map { matchResult ->
        // Extract the two captured groups and convert them to integers
        val first = matchResult.groupValues[1].toInt()
        val second = matchResult.groupValues[2].toInt()
        Pair(first, second)
    }.toList()
}