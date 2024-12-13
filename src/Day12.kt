class Day12 : Application {
    private var inputMap = mutableListOf<MutableList<Char>>()
    override fun run(fileName: String): Pair<Long, Long> {
        val input = readInput(fileName)
        inputMap = input.map { it.toMutableList() }.toMutableList()
        inputMap.println()

        val res1 = this.part1()
        inputMap = input.map { it.toMutableList() }.toMutableList()
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
//        idea from reddit -> count corners instead, corner count == side count
        var res = 0L
        for (i in inputMap.indices) {
            for (j in inputMap[0].indices) {
                if (inputMap[i][j] != '.') {
                    val target = inputMap[i][j]
                    val output = computeWithCorners(inputMap[i][j], Pair(i, j))
                    res += output.first * output.second
                    cleanUpMap()
                }
            }
        }
        return res
    }


    private fun computeWithCorners(target: Char, coord: Pair<Int, Int>): Pair<Int, Int> {
        if (coord.first > inputMap.lastIndex || coord.first < 0 || coord.second > inputMap[0].lastIndex || coord.second < 0) return 0 to 0
        when (inputMap[coord.first][coord.second]) {
            '-' -> return 0 to 0
            target -> {
                inputMap[coord.first][coord.second] = '-'
                return listOf(
                    computeWithCorners(target, Pair(coord.first - 1, coord.second)),
                    computeWithCorners(target, Pair(coord.first, coord.second + 1)),
                    computeWithCorners(target, Pair(coord.first + 1, coord.second)),
                    computeWithCorners(target, Pair(coord.first, coord.second - 1))
                ).fold(Pair(1, computeCorners(target, coord))) { acc, pair ->
                    Pair(
                        acc.first + pair.first,
                        acc.second + pair.second
                    )
                }
            }

            else -> return 0 to 0
        }
    }

    private fun computeCorners(target: Char, coord: Pair<Int, Int>): Int {
        var corners = 0
        var xDiff = false
        var yDiff = false

        if (coord.first == 0) xDiff = true
        else if (inputMap[coord.first - 1][coord.second] != '-' && inputMap[coord.first - 1][coord.second] != target) xDiff =
            true
        if (coord.second == inputMap[0].lastIndex) yDiff = true
        else if (inputMap[coord.first][coord.second + 1] != '-' && inputMap[coord.first][coord.second + 1] != target) yDiff =
            true
        if (xDiff == yDiff) {
            if (xDiff) corners++
            else {
                try {
                    if (inputMap[coord.first - 1][coord.second + 1] != target && inputMap[coord.first - 1][coord.second + 1] != '-') corners++
                } catch (e: IndexOutOfBoundsException) {
                }
            }
        }

        xDiff = false
        yDiff = false
        if (coord.second == inputMap[0].lastIndex) xDiff = true
        else if (inputMap[coord.first][coord.second + 1] != '-' && inputMap[coord.first][coord.second + 1] != target) xDiff =
            true
        if (coord.first == inputMap.lastIndex) yDiff = true
        else if (inputMap[coord.first + 1][coord.second] != '-' && inputMap[coord.first + 1][coord.second] != target) yDiff =
            true
        if (xDiff == yDiff) {
            if (xDiff) corners++
            else {
                try {
                    if (inputMap[coord.first + 1][coord.second + 1] != target && inputMap[coord.first + 1][coord.second + 1] != '-') corners++
                } catch (e: IndexOutOfBoundsException) {
                }
            }
        }

        xDiff = false
        yDiff = false
        if (coord.first == inputMap.lastIndex) xDiff = true
        else if (inputMap[coord.first + 1][coord.second] != '-' && inputMap[coord.first + 1][coord.second] != target) xDiff =
            true
        if (coord.second == 0) yDiff = true
        else if (inputMap[coord.first][coord.second - 1] != '-' && inputMap[coord.first][coord.second - 1] != target) yDiff =
            true
        if (xDiff == yDiff) {
            if (xDiff) corners++
            else {
                try {
                    if (inputMap[coord.first + 1][coord.second - 1] != target && inputMap[coord.first + 1][coord.second - 1] != '-') corners++
                } catch (e: IndexOutOfBoundsException) {
                }
            }
        }

        xDiff = false
        yDiff = false
        if (coord.second == 0) xDiff = true
        else if (inputMap[coord.first][coord.second - 1] != '-' && inputMap[coord.first][coord.second - 1] != target) xDiff =
            true
        if (coord.first == 0) yDiff = true
        else if (inputMap[coord.first - 1][coord.second] != '-' && inputMap[coord.first - 1][coord.second] != target) yDiff =
            true
        if (xDiff == yDiff) {
            if (xDiff) corners++
            else {
                try {
                    if (inputMap[coord.first - 1][coord.second - 1] != target && inputMap[coord.first - 1][coord.second - 1] != '-') corners++
                } catch (e: IndexOutOfBoundsException) {
                }
            }
        }
        return corners
    }
}