fun main() {
    val input = readInput("day08")
    val res1 = part1(input)
    println("day08-1: $res1")
    val res2 = part2(input)
    println("day08-2: $res2")
}
private fun part1(input: List<String>): Int {
    val charMap = mutableMapOf<Char, List<Pair<Int, Int>>>()
    input.forEachIndexed { index, s ->
        s.forEachIndexed { idx, c ->
            if (c != '.') {
                charMap[c] = charMap.getOrDefault(c, emptyList()).plus(Pair(index, idx))
            }
        }
    }
    val resList = mutableListOf<Pair<Int, Int>>()
    charMap.forEach { (c, list) ->
        var i = 0
        while (i < list.size - 1) {
            var j = i + 1
            while (j < list.size) {
                val pair1 = Pair(2 * list[i].first - list[j].first, 2 * list[i].second - list[j].second)
                val pair2 = Pair(2 * list[j].first - list[i].first, 2 * list[j].second - list[i].second)
                if (pair1.validateCoords(input.lastIndex, input[0].lastIndex)) resList.add(pair1)
                if (pair2.validateCoords(input.lastIndex, input[0].lastIndex)) resList.add(pair2)
                j++
            }
            i++
        }
    }
    return resList.distinct().size
}

private fun part2(input: List<String>): Int {
    val charMap = mutableMapOf<Char, List<Pair<Int, Int>>>()
    input.forEachIndexed { index, s ->
        s.forEachIndexed { idx, c ->
            if (c != '.') {
                charMap[c] = charMap.getOrDefault(c, emptyList()).plus(Pair(index, idx))
            }
        }
    }
    val resList = mutableListOf<Pair<Int, Int>>()
    charMap.forEach { (c, list) ->
        var i = 0
        while (i < list.size - 1) {
            var j = i + 1
            while (j < list.size) {
//                Check 2 directions in loop
                val diff1 = Pair(list[j].first - list[i].first, list[j].second - list[i].second)
                var continueChecking = true
                var iterations = 0
                while (continueChecking) {
                    val pair = Pair(list[i].first - diff1.first * iterations, list[i].second - diff1.second * iterations)
                    if (pair.validateCoords(input.lastIndex, input[0].lastIndex)) {
                        resList.add(pair)
                        iterations++
                    } else {
                        continueChecking = false
                    }
                }
                iterations = 0
                continueChecking = true
                var diff2 = Pair(list[i].first - list[j].first, list[i].second - list[j].second)
                while (continueChecking) {
                    val pair = Pair(list[j].first - diff2.first * iterations, list[j].second - diff2.second * iterations)
                    if (pair.validateCoords(input.lastIndex, input[0].lastIndex)) {
                        resList.add(pair)
                        iterations++
                    } else {
                        continueChecking = false
                    }
                }
                j++
            }
            i++
        }
    }
    return resList.distinct().size
}

private fun Pair<Int, Int>.validateCoords(xLim: Int, yLim: Int): Boolean {
    return !(this.first < 0 || this.second < 0 || this.first > xLim || this.second > yLim)
}