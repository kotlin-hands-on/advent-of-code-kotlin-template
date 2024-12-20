class Day15 : Application {
    var input: MutableList<MutableList<Char>> = mutableListOf()
    val directions = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))

    override fun run(fileName: String): Pair<Long, Long> {
        val input = readInput(fileName)
        val emptyListIdx = input.indexOf("")
        val inputMap = input.subList(0, emptyListIdx)
        inputMap.println()
        val steps = input.subList(emptyListIdx + 1, input.size).fold(StringBuilder()) { acc, s -> acc.append(s) }
            .toString().toList().map {
                when (it) {
                    '^' -> 0
                    '>' -> 1
                    'v' -> 2
                    else -> 3
                }
            }
        val initialCoord = getInitialCoord(inputMap)
        val res1 = this.part1(inputMap, steps, initialCoord)
        val part2Input = input.map {
            it.fold(StringBuilder()) { strBuilder, c ->
                when (c) {
                    '.' -> strBuilder.append("..")
                    '@' -> strBuilder.append("@.")
                    'O' -> strBuilder.append("[]")
                    '#' -> strBuilder.append("##")
                    else -> strBuilder
                }
            }.toMutableList()
        }
        part2Input.println()
        val res2 = this.part2()
        return res1 to res2
    }

    private fun part1(inputMap: List<String>, steps: List<Int>, initialCoord: Pair<Int, Int>): Long {
        var res = 0L
        var coord = initialCoord
        input = inputMap.map { it.toMutableList() }.toMutableList()
        steps.forEach { n ->
            coord = computeStep(directions[n], coord)
        }
        input.forEachIndexed { i, list ->
            list.forEachIndexed { idx, c ->
                if (c == 'O') {
                    res += 100 * i + idx
                }
            }
        }
        return res
    }

    private fun part2(): Long {
        return 0
    }

    private fun getInitialCoord(inputMap: List<String>): Pair<Int, Int> {
        inputMap.forEachIndexed { i, s ->
            s.forEachIndexed { j, c ->
                if (c == '@') return Pair(i, j)
            }
        }
        return Pair(0, 0)
    }

    private fun computeStep(dir: Pair<Int, Int>, coord: Pair<Int, Int>): Pair<Int, Int> {
        val isNextCoordEmpty = try {
            input[coord.first + dir.first][coord.second + dir.second] == '.'
        } catch (e: IndexOutOfBoundsException) {
            return coord
        }
        if (isNextCoordEmpty) {
            input[coord.first][coord.second] = '.'
            input[coord.first + dir.first][coord.second + dir.second] = '@'
            return Pair(coord.first + dir.first, coord.second + dir.second)
        } else {
            if (input[coord.first + dir.first][coord.second + dir.second] == '#') return coord
            val distToEmptyCoord = distToEmptyCoord(dir, coord)
            if (distToEmptyCoord == -1) return coord
            else {
                input[coord.first + dir.first * distToEmptyCoord][coord.second + dir.second * distToEmptyCoord] =
                    'O'
                input[coord.plus(dir).first][coord.plus(dir).second] = '@'
                input[coord.first][coord.second] = '.'
                return coord.plus(dir)
            }
        }
    }

    private fun distToEmptyCoord(dir: Pair<Int, Int>, coord: Pair<Int, Int>): Int {
        var res = 0
        var continueSearch = true
        var coords = coord
        while (continueSearch) {
            coords = coords.plus(dir)
            try {
                if (input[coords.first][coords.second] == '.') return ++res
                if (input[coords.first][coords.second] == '#') return -1
                if (input[coords.first][coords.second] == 'O') res++
            } catch (e: IndexOutOfBoundsException) {
                return -1
            }
        }
        return -1
    }
}