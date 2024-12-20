class Day10 : Application {
    val directions = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))
    override fun run(fileName: String): Pair<Long, Long> {
        val input = readInput(fileName).map { it.map { c -> c.digitToInt() } }
        val res1 = this.part1(input)
        val res2 = this.part2(input)
        return res1 to res2
    }

    private fun part1(input: List<List<Int>>): Long = compute(input, true).map { it.value.size }.sum().toLong()

    private fun part2(input: List<List<Int>>): Long {
        val resMap = compute(input, false)
        return resMap.map { it.value.size }.sum().toLong()
    }

    private fun compute(
        input: List<List<Int>>,
        distinct: Boolean
    ): MutableMap<Pair<Int, Int>, List<Pair<Int, Int>>> {
        val resMap = mutableMapOf<Pair<Int, Int>, List<Pair<Int, Int>>>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == 0) {
                    resMap[Pair(i, j)] =
                        if (distinct) computeTrail(input, Pair(i, j), 0).distinct() else computeTrail(
                            input,
                            Pair(i, j),
                            0
                        )
                }
            }
        }
        return resMap
    }

    private fun computeTrail(
        input: List<List<Int>>,
        coord: Pair<Int, Int>,
        currTarget: Int
    ): List<Pair<Int, Int>> {
        try {
            if (input[coord.first][coord.second] == currTarget) {
                return if (currTarget == 9) {
                    listOf(coord)
                } else {
                    computeTrail(input, getNewDir(coord, 0), currTarget + 1).plus(
                        computeTrail(
                            input,
                            getNewDir(coord, 1),
                            currTarget + 1
                        )
                    ).plus(computeTrail(input, getNewDir(coord, 2), currTarget + 1))
                        .plus(computeTrail(input, getNewDir(coord, 3), currTarget + 1))
                }
            } else {
                return listOf()
            }
        } catch (e: IndexOutOfBoundsException) {
            return listOf()
        }
    }


    private fun getNewDir(coord: Pair<Int, Int>, dir: Int): Pair<Int, Int> =
        Pair(coord.first + directions[dir].first, coord.second + directions[dir].second)
}