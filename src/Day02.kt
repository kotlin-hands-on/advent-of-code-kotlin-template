fun main() {
    val input =
        readInput("day02").map { it.split(" ").toList().map { it.toInt() } }

    val safeList = input.map {
        isSafe(it)
    }
    val res1 = safeList.filter { it }.size
    "day2-1: $res1".println()
    val lessSafeList = input.map {
        isSafe(it) || bruteForceCheckSafe(it)
    }
    val res2 = lessSafeList.filter { it }.size
    "day2-2: $res2".println()
}

fun isSafe(input: List<Int>): Boolean {
    val isIncreasing: Boolean = input[0] <= input[1]
    input.forEachIndexed { index, _ ->
        if (index >= input.size - 1) return true
        if (isIncreasing) {
            if (input[index + 1] - input[index] < 1 || input[index + 1] - input[index] > 3) return false
        } else {
            if (input[index] - input[index + 1] < 1 || input[index] - input[index + 1] > 3) return false
        }
    }
    return true
}

fun bruteForceCheckSafe(input: List<Int>): Boolean {
    for (i in input.indices) {
        val sublist = input.toMutableList()
        sublist.removeAt(i)
        if (isSafe(sublist)) return true
    }
    return false
}