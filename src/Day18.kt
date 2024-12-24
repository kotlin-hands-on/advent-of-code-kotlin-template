class Day18 : Application {
    var inputMap = mutableListOf<MutableList<Int>>()
    var dimension = 0
    val directions = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))
    var limit = 0
    override fun run(fileName: String): Pair<Long, Long> {
        if (fileName.contains("test")) {
            dimension = 7
            limit = 12
        } else {
            dimension = 71
            limit = 1024
        }
        val fullInput = readInput(fileName).map { Pair(it.split(',')[1].toInt(), it.split(',')[0].toInt()) }
//        fullInput.forEach(::println)
        val input =
            fullInput.subList(0, limit)
        for (i in 0..<dimension) {
            inputMap.add(MutableList(dimension) { 0 })
        }
        input.forEach { inputMap[it.first][it.second] = -1 }
        val res1 = this.part1()
        val res2 = this.part2(fullInput)
        return res1 to res2
    }

    private fun part1(): Long {
        val initialPos = Pair(0, 0)
        val finalPos = Pair(dimension - 1, dimension - 1)
        computePart1(initialPos, 1)
        return inputMap[finalPos.first][finalPos.second].toLong() - 1
    }

    private fun part2(input: List<Pair<Int, Int>>): Long {
//        idea from https://www.reddit.com/r/adventofcode/comments/1hhiawu/2024_day_18_part_2_visualization_of_my_algorithm/
        inputMap = mutableListOf()
        for (i in 0..<dimension) {
            inputMap.add(MutableList(dimension) { 0 })
        }
//        2 for connecting top & right, 3 for connecting left & bottom, 4 for connecting both
        input.forEach {
            inputMap[it.first][it.second] = computeConnection(it)
            if (inputMap[it.first][it.second] == 4) {
                it.println()
                return 0
            }
            if (inputMap[it.first][it.second] in 2 .. 3) {
                val updateResult = updateNeighbors(it, inputMap[it.first][it.second])
                if (updateResult.first) {
                    it.println()
                    return 0
                }
            }
        }
        return 0
    }

    private fun computePart1(pos: Pair<Int, Int>, score: Int) {
        if (pos.first < 0 || pos.first >= dimension || pos.second < 0 || pos.second >= dimension) return
        if (inputMap[pos.first][pos.second] == -1) return
        if (inputMap[pos.first][pos.second] in 1..score) return
        inputMap[pos.first][pos.second] = score
        if (pos.first == dimension - 1 && pos.second == dimension - 1) return
        computePart1(pos.plus(directions[0]), score + 1)
        computePart1(pos.plus(directions[1]), score + 1)
        computePart1(pos.plus(directions[2]), score + 1)
        computePart1(pos.plus(directions[3]), score + 1)
    }

    private fun computeConnection(coord: Pair<Int, Int>): Int {
        var topRight = false
        var botLeft = false
        if (coord.first == 0 || coord.second == dimension - 1) topRight = true
        if (coord.second == 0 || coord.first == dimension - 1) botLeft = true
        try {
            val next = inputMap[coord.first - 1][coord.second - 1]
            if (next == 2) topRight = true
            if (next == 3) botLeft = true
        } catch(e : IndexOutOfBoundsException){}
        try {
            val next = inputMap[coord.first - 1][coord.second]
            if (next == 2) topRight = true
            if (next == 3) botLeft = true
        } catch(e : IndexOutOfBoundsException){}
        try {
            val next = inputMap[coord.first - 1][coord.second + 1]
            if (next == 2) topRight = true
            if (next == 3) botLeft = true
        } catch(e : IndexOutOfBoundsException){}
        try {
            val next = inputMap[coord.first][coord.second - 1]
            if (next == 2) topRight = true
            if (next == 3) botLeft = true
        } catch(e : IndexOutOfBoundsException){}
        try {
            val next = inputMap[coord.first][coord.second + 1]
            if (next == 2) topRight = true
            if (next == 3) botLeft = true
        } catch(e : IndexOutOfBoundsException){}
        try {
            val next = inputMap[coord.first + 1][coord.second - 1]
            if (next == 2) topRight = true
            if (next == 3) botLeft = true
        } catch(e : IndexOutOfBoundsException){}
        try {
            val next = inputMap[coord.first + 1][coord.second]
            if (next == 2) topRight = true
            if (next == 3) botLeft = true
        } catch(e : IndexOutOfBoundsException){}
        try {
            val next = inputMap[coord.first + 1][coord.second + 1]
            if (next == 2) topRight = true
            if (next == 3) botLeft = true
        } catch(e : IndexOutOfBoundsException){}
        return when {
            topRight && botLeft -> 4
            topRight -> 2
            botLeft -> 3
            else -> 1
        }
    }
    private fun updateNeighbors(coord: Pair<Int, Int>, newVal: Int): Pair<Boolean, Pair<Int, Int>> {
        try {
            if (inputMap[coord.first - 1][coord.second - 1] == 1) {
                inputMap[coord.first - 1][coord.second - 1] = computeConnection(coord.plus(Pair(-1, -1)))
                if (inputMap[coord.first - 1][coord.second - 1] == 4) return Pair(true, coord.plus(Pair(-1, -1)))
                val newRes = updateNeighbors(coord.plus(Pair(-1, -1)), newVal)
                if (newRes.first) return newRes
            }
        } catch(e : IndexOutOfBoundsException){}
        try {
            if (inputMap[coord.first - 1][coord.second] == 1) {
                inputMap[coord.first - 1][coord.second] = computeConnection(coord.plus(Pair(-1, 0)))
                if (inputMap[coord.first - 1][coord.second] == 4) return Pair(true, (coord.plus(Pair(-1, 0))))
                val newRes = updateNeighbors(coord.plus(Pair(-1, 0)), newVal)
                if (newRes.first) return newRes
            }
        } catch(e : IndexOutOfBoundsException){}
        try {
            if (inputMap[coord.first - 1][coord.second + 1] == 1) {
                inputMap[coord.first - 1][coord.second + 1] = computeConnection(coord.plus(Pair(-1, 1)))
                if (inputMap[coord.first - 1][coord.second + 1] == 4) return Pair(true, (coord.plus(Pair(-1, 1))))
                val newRes = updateNeighbors(coord.plus(Pair(-1, 1)), newVal)
                if (newRes.first) return newRes
            }
        } catch(e : IndexOutOfBoundsException){}
        try {
            if (inputMap[coord.first][coord.second - 1] == 1) {
                inputMap[coord.first][coord.second - 1] = computeConnection(coord.plus(Pair(0, -1)))
                if (inputMap[coord.first][coord.second - 1] == 4) return Pair(true, (coord.plus(Pair(0, -1))))
                val newRes = updateNeighbors(coord.plus(Pair(0, -1)), newVal)
                if (newRes.first) return newRes
            }
        } catch(e : IndexOutOfBoundsException){}
        try {
            if (inputMap[coord.first][coord.second + 1] == 1) {
                inputMap[coord.first][coord.second + 1] = computeConnection(coord.plus(Pair(0, 1)))
                if (inputMap[coord.first][coord.second + 1] == 4) return Pair(true, (coord.plus(Pair(0, 1))))
                val newRes = updateNeighbors(coord.plus(Pair(0, 1)), newVal)
                if (newRes.first) return newRes
            }
        } catch(e : IndexOutOfBoundsException){}
        try {
            if (inputMap[coord.first + 1][coord.second - 1] == 1) {
                inputMap[coord.first + 1][coord.second - 1] = computeConnection(coord.plus(Pair(1, -1)))
                if (inputMap[coord.first + 1][coord.second - 1] == 4) return Pair(true, (coord.plus(Pair(1, -1))))
                val newRes = updateNeighbors(coord.plus(Pair(1, -1)), newVal)
                if (newRes.first) return newRes
            }
        } catch(e : IndexOutOfBoundsException){}
        try {
            if (inputMap[coord.first + 1][coord.second] == 1) {
                inputMap[coord.first + 1][coord.second] = computeConnection(coord.plus(Pair(1, 0)))
                if (inputMap[coord.first + 1][coord.second] == 4) return Pair(true, (coord.plus(Pair(1, 0))))
                val newRes = updateNeighbors(coord.plus(Pair(1, 0)), newVal)
                if (newRes.first) return newRes
            }
        } catch(e : IndexOutOfBoundsException){}
        try {
            if (inputMap[coord.first + 1][coord.second + 1] == 1) {
                inputMap[coord.first + 1][coord.second + 1] = computeConnection(coord.plus(Pair(1, 1)))
                if (inputMap[coord.first + 1][coord.second + 1] == 4) return Pair(true, (coord.plus(Pair(1, 1))))
                val newRes = updateNeighbors(coord.plus(Pair(1, 1)), newVal)
                if (newRes.first) return newRes
            }
        } catch(e : IndexOutOfBoundsException){}
        return Pair(false, Pair(0, 0))
    }
}