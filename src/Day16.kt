class Day16 : Application {
    val directions = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))
    var inputMap = mutableListOf<MutableList<Char>>()
    var scoreMap = mutableListOf<MutableList<MutableList<Long>>>()
    var part2List = mutableListOf<Pair<Int, Int>>()
    override fun run(fileName: String): Pair<Long, Long> {
        val input = readInput(fileName)
        inputMap = input.map { it.toMutableList() }.toMutableList()
        scoreMap = inputMap.map {
            it.map { c ->
                when (c) {
                    '#' -> mutableListOf(-1L, -1L, -1L, -1L)
                    else -> mutableListOf(0L, 0L, 0L, 0L)
                }
            }.toMutableList()
        }.toMutableList()
        val res1 = this.part1()
        val res2 = this.part2(res1, scoreMap[1][input.lastIndex - 1].indexOf(res1))
        return res1 to res2
    }

    private fun part1(): Long {
        val initialPos = Pair(inputMap.lastIndex - 1, 1)
        val initialDir = 1
        val finalPos = Pair(1, inputMap.lastIndex - 1)
        computePart1(initialPos, initialDir, 0)
        return scoreMap[finalPos.first][finalPos.second].filterNot { it <= 0L }.min()
    }

    private fun part2(score: Long, dir: Int): Long {
        val finalPos = Pair(1, inputMap.lastIndex - 1)
        computePart2(finalPos, dir, score)

        return part2List.distinct().size.toLong()
    }

    private fun computePart1(pos: Pair<Int, Int>, dir: Int, currScore: Long) {
        try {
            if (scoreMap[pos.first][pos.second][dir] in 1..<currScore) return
            if (scoreMap[pos.first][pos.second][(dir + 1) % 4] in 1..<currScore - 1000
                || scoreMap[pos.first][pos.second][(dir + 3) % 4] in 1..<currScore - 1000
                || scoreMap[pos.first][pos.second][(dir + 2) % 4] in 1..<currScore - 2000
            ) return
            if (inputMap[pos.first][pos.second] == 'E') {
                scoreMap[pos.first][pos.second][dir] = currScore
            } else {
                if (inputMap[pos.first][pos.second] != '#') {
                    scoreMap[pos.first][pos.second][dir] = currScore
                    computePart1(pos.plus(directions[dir]), dir, currScore + 1)
                    computePart1(pos.plus(directions[(dir + 1) % 4]), (dir + 1) % 4, currScore + 1001)
                    computePart1(pos.plus(directions[(dir + 3) % 4]), (dir + 3) % 4, currScore + 1001)
                }
            }
        } catch (e: IndexOutOfBoundsException) {
        }
    }

    private fun computePart2(pos: Pair<Int, Int>, dir: Int, currScore: Long): Int {
        if (currScore < 0) return -1
        if (currScore == 0L) {
            return if (pos.first == inputMap.lastIndex - 1 && pos.second == 1) 1
            else -1
        }
        var res = 1
        if (scoreMap[pos.first][pos.second][dir] == currScore ||
            scoreMap[pos.first][pos.second][(dir + 3) % 4] == currScore - 1000 ||
            scoreMap[pos.first][pos.second][(dir + 1) % 4] == currScore - 1000
        ) {
            if (scoreMap[pos.first][pos.second][dir] == currScore) {
                val computeRes = computePart2(pos.plus(directions[(dir + 2) % 4]), (dir) % 4, currScore - 1)
                res += computeRes
            }
            if (scoreMap[pos.first][pos.second][(dir + 3) % 4] == currScore - 1000) {
                val computeRes = computePart2(pos.plus(directions[(dir + 1) % 4]), (dir + 3) % 4, currScore - 1001)
                res += computeRes
            }
            if (scoreMap[pos.first][pos.second][(dir + 1) % 4] == currScore - 1000) {
                val computeRes = computePart2(pos.plus(directions[(dir + 3) % 4]), (dir + 1) % 4, currScore - 1001)
                res += computeRes
            }
            part2List.add(pos)
            return res
        } else return -1
    }
}