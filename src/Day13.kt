class Day13 : Application {
    private var inputMap = mutableListOf<MutableList<Char>>()
    override fun run(fileName: String): Pair<Long, Long> {
        inputMap = readInput(fileName).map { it.toMutableList() }.toMutableList()
        inputMap.println()

        val res1 = this.part1()
        val res2 = this.part2()
        return res1 to res2
    }

    private fun part1(): Long {
        var res = 0L
        for (i in inputMap.indices) {
            for (j in inputMap.indices) {
                if (inputMap[i][j] != '.') {
                    val output = compute(inputMap[i][j], Pair(i, j))
                    res += output.first * output.second
                    cleanUpMap()
                }
            }
        }
        return res
    }

    private fun compute(target: Char, coord: Pair<Int, Int>): Pair<Int, Int> {
        if (coord.first > inputMap.lastIndex || coord.first < 0 || coord.second > inputMap[0].lastIndex || coord.second < 0) return 0 to 1
        when (inputMap[coord.first][coord.second]) {
            '-' -> return 0 to 0
            target -> {
                inputMap[coord.first][coord.second] = '-'
                return listOf(
                    compute(target, Pair(coord.first - 1, coord.second)),
                    compute(target, Pair(coord.first, coord.second + 1)),
                    compute(target, Pair(coord.first + 1, coord.second)),
                    compute(target, Pair(coord.first, coord.second - 1))
                ).fold(Pair(1, 0)) { acc, pair -> Pair(acc.first + pair.first, acc.second + pair.second) }
            }
            else -> return 0 to 1
        }
    }

    private fun cleanUpMap() {
        inputMap = inputMap.map {
            it.map { c ->
                when (c) {
                    '-' -> '.'
                    else -> c
                }
            }.toMutableList()
        }.toMutableList()
    }


    private fun part2(): Long {
        return 0
    }
}