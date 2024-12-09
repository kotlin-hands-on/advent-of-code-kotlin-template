fun main() {
    var res = 1
    var basePosition = Pair(0, 0)
    val directionDict = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))
    var baseDirectionIndex = 0
    var notStuck = true
    val input = readInput("day06")
    val inputMap = input.mapIndexed { index, it ->
        it.mapIndexed { index2, c ->
            when (c) {
                '.' -> 0
                '#' -> 1
                else -> {
                    basePosition = Pair(index, index2)
                    baseDirectionIndex = getDirection(c)
                    0
                }
            }
        }
    }
    var directionIndex = baseDirectionIndex
    var position = basePosition
    val outputMap = input.map {
        it.map { c ->
            when (c) {
                '.' -> mutableListOf(false, false, false, false)
                '#' -> mutableListOf(false, false, false, false)
                else -> mutableListOf(
                    directionIndex == 0,
                    directionIndex == 1,
                    directionIndex == 2,
                    directionIndex == 3
                )
            }
        }.toMutableList()
    }.toMutableList()
    var maybeStuck = false
    while (notStuck) {
//        println("current position: $position, direction: ${directionDict[directionIndex]}")
        if (outputMap[position.first][position.second] == mutableListOf(false, false, false, false)) {
            res++
        }
        outputMap[position.first][position.second][directionIndex] = true
        val nextPositionCoords = Pair(
            position.first + directionDict[directionIndex].first,
            position.second + directionDict[directionIndex].second
        )
        if (nextPositionCoords.first < 0 || nextPositionCoords.second < 0 || nextPositionCoords.first > inputMap.lastIndex || nextPositionCoords.second > inputMap[0].lastIndex) {
            notStuck = false
        } else {
            val nextPosition = inputMap[nextPositionCoords.first][nextPositionCoords.second]
            if (nextPosition == 1) {
                directionIndex = getNextDirection(directionIndex)
                if (maybeStuck) {
                    notStuck = false
                } else {
                    maybeStuck = true
                }
            } else {
                maybeStuck = false
                position = nextPositionCoords
            }
        }
    }
    println("day06-1: $res")

    val res2 = part2(inputMap, outputMap, basePosition, baseDirectionIndex)
    println("day06-2: $res2")
}

fun dummy() {
//    position = basePosition
//    directionIndex = baseDirectionIndex
//    val distinctObs = mutableListOf<Pair<Int, Int>>()
//    notStuck = true
//    maybeStuck = false
//    val newOutputMap =
//        outputMap.map { it.map { lst -> lst.map { _ -> false }.toMutableList() }.toMutableList() }.toMutableList()
//    while (notStuck) {
//        val nextPositionCoords = Pair(
//            position.first + directionDict[directionIndex].first,
//            position.second + directionDict[directionIndex].second
//        )
//        if (nextPositionCoords.first < 0 || nextPositionCoords.second < 0 || nextPositionCoords.first > inputMap.lastIndex || nextPositionCoords.second > inputMap[0].lastIndex) {
//            notStuck = false
//        } else {
//            newOutputMap[position.first][position.second][directionIndex] = true
//            val nextPosition = inputMap[nextPositionCoords.first][nextPositionCoords.second]
//            if (nextPosition == 1) {
//                directionIndex = getNextDirection(directionIndex)
////                if (maybeStuck) {
////                    notStuck = false
////                } else {
////                    maybeStuck = true
////                }
//            } else {
//                maybeStuck = false
//                if (newOutputMap[nextPositionCoords.first][nextPositionCoords.second][directionIndex] || newOutputMap[position.first][position.second][getNextDirection(
//                        directionIndex
//                    )]
//                ) {
////                    if (!distinctObs.contains(Pair(nextPositionCoords.first, nextPositionCoords.second))) {
//                    distinctObs.add(Pair(nextPositionCoords.first, nextPositionCoords.second))
////                    }
//                } else {
//                    if (findWithoutNewObstacle(
//                            position,
//                            getNextDirection(directionIndex),
//                            inputMap,
//                            newOutputMap
//                        )
//                    ) {
//                        val newPair = Pair(
//                            position.first + directionDict[getNextDirection(directionIndex)].first,
//                            position.second + directionDict[getNextDirection(directionIndex)].second
//                        )
//                        distinctObs.add(newPair)
//                    }
//                }
//                position = nextPositionCoords
//            }
//        }
//    }
//
//    println("day06-2: ${distinctObs.size}")
//    println("distinct: ${distinctObs.distinct().size}")
}

fun getDirection(c: Char): Int = when (c) {
    '^' -> 0
    '>' -> 1
    'v' -> 2
    '<' -> 3
    else -> -1
}

fun getNextDirection(curr: Int): Int = (curr + 1) % 4


fun findWithoutNewObstacle(
    pos: Pair<Int, Int>,
    dir: Int,
    inputMap: List<List<Int>>,
    outputMap: List<List<List<Boolean>>>
): Boolean {
    val newOutputMap = outputMap.map { it.map { lst -> lst.map { bool -> bool }.toMutableList() }.toMutableList() }
        .toMutableList()
    val directionDict = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))
    var position = pos
    var directionIndex = dir
    var maybeStuck = false
    var notStuck = true
    while (notStuck) {
        val nextPositionCoords = Pair(
            position.first + directionDict[directionIndex].first,
            position.second + directionDict[directionIndex].second
        )
        if (nextPositionCoords.first < 0 || nextPositionCoords.second < 0 || nextPositionCoords.first > inputMap.lastIndex || nextPositionCoords.second > inputMap[0].lastIndex) {
            notStuck = false
        } else {
            newOutputMap[position.first][position.second][directionIndex] = true
            val nextPosition = inputMap[nextPositionCoords.first][nextPositionCoords.second]
            if (nextPosition == 1) {
                directionIndex = getNextDirection(directionIndex)
//                if (maybeStuck) {
//                    notStuck = false
//                } else {
//                    maybeStuck = true
//                }
            } else {
                maybeStuck = false
                if (newOutputMap[nextPositionCoords.first][nextPositionCoords.second][directionIndex]) {
                    return true
                }
                position = nextPositionCoords
            }
        }
    }
    return false
}

fun part2(inputMap: List<List<Int>>, outputMap: MutableList<MutableList<MutableList<Boolean>>>, position: Pair<Int, Int>, directionIndex: Int): Int {
//    val input = inputMap.map { it.toMutableList() }.toMutableList()
    var res = 0
    for (i in inputMap.indices) {
        for (j in inputMap[i].indices) {
            if (inputMap[i][j] == 1) continue
            var toCheck = false
            try {
                if (outputMap[i - 1][j][2]) toCheck = true
            } catch (e: IndexOutOfBoundsException) {
            }
            try {
                if (outputMap[i][j - 1][1]) toCheck = true
            } catch (e: IndexOutOfBoundsException) {
            }
            try {
                if (outputMap[i + 1][j][0]) toCheck = true
            } catch (e: IndexOutOfBoundsException) {
            }
            try {
                if (outputMap[i][j + 1][3]) toCheck = true
            } catch (e: IndexOutOfBoundsException) {
            }
            if (toCheck) {
                if (checkLoop(inputMap, Pair(i, j), position, directionIndex)) res++
            }
        }
    }
    return res
}

fun checkLoop(inputMap: List<List<Int>>, pair: Pair<Int, Int>, pos: Pair<Int, Int>, dir: Int): Boolean {
    val input = inputMap.map { it.toMutableList() }.toMutableList()
    input[pair.first][pair.second] = 1
    val outputMap = inputMap.map { it.map {_ -> mutableListOf(false, false, false, false)}.toMutableList()}.toMutableList()
    val directionDict = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))
    var directionIndex = dir
    var position = pos
    var notStuck = true
    while (notStuck) {
        outputMap[position.first][position.second][directionIndex] = true
        val nextPositionCoords = Pair(
            position.first + directionDict[directionIndex].first,
            position.second + directionDict[directionIndex].second
        )
        try {
            val nextPosition = input[nextPositionCoords.first][nextPositionCoords.second]
            if (nextPosition == 1) {
                directionIndex = getNextDirection(directionIndex)
            } else {
                if (outputMap[nextPositionCoords.first][nextPositionCoords.second][directionIndex]) {
                    return true
                }
                position = nextPositionCoords
            }
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
    return false
}
