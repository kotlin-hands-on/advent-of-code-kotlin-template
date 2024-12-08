fun main() {
    val input = readInput("day07")
    val inputMap = input.map {
        it.split(": ")[0].toLong() to it.split(": ")[1].split(' ').map { it.toLong() }
    }
    val res1 = part1(inputMap)
    println("day07-1: $res1")
    val res2 = part2(inputMap)
    println("day07-2: $res2")
}

private fun part1(input: List<Pair<Long, List<Long>>>): Long {
    var res = input.map {
        if (computeAddMult(it.second, it.second[0], it.first, 1)) it.first else 0
    }.sum()
    return res
}

private fun computeAddMult(input: List<Long>, curr: Long, target: Long, idx: Int): Boolean {
    if (idx >= input.size) return curr == target
    return computeAddMult(input, curr + input[idx], target, idx + 1) || computeAddMult(
        input,
        curr * input[idx],
        target,
        idx + 1
    )
}

private fun part2(input: List<Pair<Long, List<Long>>>): Long {
    var res = input.map {
        if (compute(it.second, it.second[0], it.first, 1)) it.first else 0
    }.sum()
    return res
}

private fun compute(input: List<Long>, curr: Long, target: Long, idx: Int): Boolean {
    if (idx >= input.size) return curr == target
    if (curr > target) return false

    return compute(input, curr + input[idx], target, idx + 1) || compute(
        input,
        curr * input[idx],
        target,
        idx + 1
    ) || compute(input, computeConcat(curr, input[idx]), target, idx + 1)
}

private fun computeConcat(num1: Long, num2: Long): Long {
    if (num1 == 0L) return num2
    if (num2 == 0L) return num1
    return num1.toString().plus(num2.toString()).toLong()
}