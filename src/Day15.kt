import java.io.BufferedWriter
import java.io.File

class Day15 : Application {
    var input: MutableList<MutableList<Char>> = mutableListOf()
    val directions = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))
    var bufferedWriter: BufferedWriter? = null

    override fun run(fileName: String): Pair<Long, Long> {
        bufferedWriter = File("src/resources/$fileName-output.txt").bufferedWriter()
        val input = readInput(fileName)
        val emptyListIdx = input.indexOf("")
        val inputMap = input.subList(0, emptyListIdx)
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
        val part2Input = inputMap.map {
            val strBuilder = StringBuilder()
            it.forEach { c ->
                when(c) {
                    '.' -> strBuilder.append("..")
                    '#' -> strBuilder.append("##")
                    'O' -> strBuilder.append("[]")
                    '@' -> strBuilder.append("@.")
                }
            }
            strBuilder.toString()
        }
        val newInitialCoord = getInitialCoord(part2Input.map{it})
        part2Input.forEach {
            bufferedWriter!!.write(it)
            bufferedWriter!!.newLine()
        }
        bufferedWriter!!.write("---------------------------")
        bufferedWriter!!.newLine()
        val res2 = this.part2(part2Input, steps, newInitialCoord)

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

    private fun part2(inputMap: List<String>, steps: List<Int>, initialCoord: Pair<Int, Int>): Long {
        var res = 0L
        var coord = initialCoord
        input = inputMap.map { it.toMutableList() }.toMutableList()
        steps.forEach {
            coord = computeSquareStep(directions[it], coord)
            input.forEach {
                val strBuilder = StringBuilder()
                it.forEach { c -> strBuilder.append(c) }
                bufferedWriter!!.write(strBuilder.toString())
                bufferedWriter!!.newLine()
            }
            bufferedWriter!!.write("------------------------")
            bufferedWriter!!.newLine()
        }
        input.forEachIndexed { i, list ->
            list.forEachIndexed { idx, c ->
                if (c == '[') {
                    res += 100 * i + idx
                }
            }
        }
        input.println()
        return res
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

    private fun distToEmptyCoord(dir: Pair<Int, Int>, coord: Pair<Int, Int>, isPart2: Boolean? = null): Int {
        val part2 = isPart2 ?: false
        var res = 0
        var continueSearch = true
        var coords = coord
        while (continueSearch) {
            coords = coords.plus(dir)
            try {
                if (input[coords.first][coords.second] == '.') return ++res
                if (input[coords.first][coords.second] == '#') return -1
                if (!part2 && input[coords.first][coords.second] == 'O') res++
                if (part2 && (input[coords.first][coords.second] == '[' || input[coords.first][coords.second] == ']')) res++
            } catch (e: IndexOutOfBoundsException) {
                return -1
            }
        }
        return -1
    }

    private fun computeSquareStep(dir: Pair<Int, Int>, coord: Pair<Int, Int>): Pair<Int, Int> {
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
            if (dir.first == 0) {
                // horizontal
                val distToEmptyCoord = distToEmptyCoord(dir, coord, true)
                if (distToEmptyCoord == -1) return coord
                input[coord.first][coord.second] = '.'
                input[coord.plus(dir).first][coord.plus(dir).second] = '@'
                for (i in 2 until distToEmptyCoord + 1) {
                    if (i % 2 == 0) {
                        input[coord.first][coord.second + dir.second * i] = if (dir.second == 1) '[' else ']'
                    } else {
                        input[coord.first][coord.second + dir.second * i] = if (dir.second == 1) ']' else '['
                    }
                }
            } else {
                // vertical
                val (distToEmptyCoord, coords) = distToEmptyCoordVertical(dir, coord)
                if (distToEmptyCoord == -1) return coord
                while (coords.isNotEmpty()) {
                    val coordList = coords.removeLast()
                    coordList.forEach {
                        input[it.second.first][it.second.second] = '.'
                        input[it.second.first + dir.first][it.second.second] = it.first
                    }
                }
                return coord.plus(dir)
            }
        }
        return coord.plus(dir)
    }

    private fun distToEmptyCoordVertical(
        dir: Pair<Int, Int>,
        coord: Pair<Int, Int>
    ): Pair<Int, ArrayDeque<List<Pair<Char, Pair<Int, Int>>>>> {
        var res = 0
        val coordsData = ArrayDeque<List<Pair<Char, Pair<Int, Int>>>>()
        coordsData.addLast(listOf(Pair('@', coord)))
        var coords: MutableList<Pair<Int, Int>>
        while (true) {
            var possibleToStopSearching = true
            val newList = mutableListOf<Pair<Char, Pair<Int, Int>>>()
            coords = coordsData.last().map { Pair(it.second.first + dir.first, it.second.second) }.toMutableList()
            try {
                coords.forEach {
                    if (input[it.first][it.second] == '#') return Pair(-1, coordsData)
                    if (input[it.first][it.second] == '[') {
                        possibleToStopSearching = false
                        newList.add(Pair('[', Pair(it.first, it.second)))
                        newList.add(Pair(']', Pair(it.first, it.second + 1)))
                    }
                    if (input[it.first][it.second] == ']') {
                        possibleToStopSearching = false
                        newList.add(Pair(']', Pair(it.first, it.second)))
                        newList.add(Pair('[', Pair(it.first, it.second - 1)))
                    }
                }
                coordsData.addLast(newList.distinct())
                if (possibleToStopSearching) return Pair(res, coordsData)
                res++
            } catch (e: IndexOutOfBoundsException) {
                return Pair(-1, coordsData)
            }
        }
        return Pair(-1, coordsData)
    }
}