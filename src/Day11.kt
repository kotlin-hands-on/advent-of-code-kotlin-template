import kotlin.math.abs
import kotlin.math.log10

class Day11 : Application {
    override fun run(fileName: String): Pair<Long, Long> {
        val input = readInput(fileName)[0].split(' ').map { it.toLong() }
        input.println()
        val res1 = this.part1(input)
        val res2 = this.part2(input)
        return res1 to res2
    }

    private fun part1(input: List<Long>): Long {
        var resMap = mutableMapOf<Long, Long>()
        input.forEach {
            resMap[it] = 1
        }
        var resList = input.toMutableList()
        for (i in 0 until 25) {
            resList = computePart1(resList)
        }
        for (i in 0 until 25) {
            resMap = computeAlternate(resMap)
        }
        resMap.map{it.value}.sum().println()
        return resList.size.toLong()
    }

    private fun computePart1(input: MutableList<Long>): MutableList<Long> {
        var idx = 0
        var length = input.size
        while (idx < length) {
            if (input[idx].isEvenDigits()) {
                val pair = splitNum(input[idx])
                input.add(idx++, pair.first)
                input[idx] = pair.second
                length++
            } else {
                if (input[idx] == 0L) input[idx] = 1
                else input[idx] = input[idx] * 2024
            }
            idx++
        }
        return input
    }

    private fun computeAlternate(input: MutableMap<Long, Long>): MutableMap<Long, Long> {
        val newMap = mutableMapOf<Long, Long>()
        input.forEach { it ->
            if (it.key.isEvenDigits()) {
                val pair = splitNum(it.key)
                newMap[pair.first] = newMap.getOrDefault(pair.first, 0L) + it.value
                newMap[pair.second] = newMap.getOrDefault(pair.second, 0L) + it.value
            } else {
                if (it.key == 0L) {
                    newMap[1] = newMap.getOrDefault(1, 0L) + it.value
                } else {
                    newMap[it.key * 2024] = newMap.getOrDefault(it.key * 2024, 0L) + it.value
                }
            }
        }
        return newMap
    }

    private fun part2(input: List<Long>): Long {
        var resMap = mutableMapOf<Long, Long>()
        input.forEach {
            resMap[it] = 1
        }
        for (i in 0 until 75) {
            resMap = computeAlternate(resMap)
        }
        return resMap.map { it.value }.sum()
    }

    private fun Long.isEvenDigits(): Boolean = length() % 2 == 0

    private fun Long.length() = when (this) {
        0L -> 1
        else -> log10(abs(toDouble())).toInt() + 1
    }

    private fun splitNum(input: Long): Pair<Long, Long> = Pair(
        input.toString().substring(0, input.length() / 2).toLong(),
        input.toString().substring(input.length() / 2).toLong()
    )
}