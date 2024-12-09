class Day09 : Application {
    override fun run(fileName: String): Pair<Long, Long> {
        val input = readInput(fileName)[0]
        val res1 = this.part1(input)
        val res2 = this.part2(input)
        return res1 to res2
    }

    private fun part1(input: String): Long {
        val initialStr = computeStr(input)
//        println("Initial string: $initialStr")
        val finalStr = rearrange(initialStr)
//        println("Final string: $finalStr")
        val checkSum = computeChecksum(finalStr)
        return checkSum
    }

    private fun part2(input: String): Long {
        val initialList = computeListOfPairs(input)
//        "Initial List: $initialList".println()
        val rearrangedList = rearrangeList(initialList)
//        "Rearranged List: $rearrangedList".println()
        val finalList = getListOfNums(rearrangedList)
//        "Final List: $finalList".println()
        val checkSum = computeChecksum(finalList)
        return checkSum
    }

    private fun computeStr(input: String): List<Int> {
        val resList = mutableListOf<Int>()
        var digit = 0
        input.forEachIndexed { index, c ->
            val numOfDigits = c.digitToInt()
            if (index % 2 == 0) {
                for (i in 0 until numOfDigits) {
                    resList.add(digit)
                }
//                digit = (digit + 1) % 10
                digit++
            } else {
                for (i in 0 until numOfDigits) {
                    resList.add(-1)
                }
            }
        }
        return resList
    }

    private fun rearrange(input: List<Int>): List<Int> {
        var lastIdx = input.lastIndex
        var firstIdx = 0
        val resList = mutableListOf<Int>()
        while (lastIdx >= firstIdx) {
            if (input[firstIdx] == -1) {
                while (input[lastIdx] == -1) {
                    if (lastIdx <= firstIdx) break
                    lastIdx--
                }
                resList.add(input[lastIdx--])
            } else {
                resList.add(input[firstIdx])
            }
            firstIdx++
        }
        return resList
    }

    private fun computeChecksum(input: List<Int>): Long {
        var sum = 0L
        input.forEachIndexed { index, c ->
            if (c != -1) sum += index * c.toLong()
        }
        return sum
    }

//    return list of pairs of int to int, first is number of digits, second is id
    private fun computeListOfPairs(input: String): MutableList<Pair<Int, Int>> {
        val resList = mutableListOf<Pair<Int, Int>>()
        var digit = 0
        input.forEachIndexed { index, c ->
            if (index % 2 == 0) {
                resList.add(Pair(c.digitToInt(), digit++))
            } else resList.add(Pair(c.digitToInt(), -1))
        }
        return resList
    }

    private fun rearrangeList(input: MutableList<Pair<Int, Int>>): List<Pair<Int, Int>> {
        var lastIdx = input.lastIndex
        while (lastIdx >= 0) {
            if (input[lastIdx].second != -1) {
                var firstIdx = 0
                while (firstIdx < lastIdx) {
                    if (input[firstIdx].second == -1) {
                        if (input[firstIdx].first >= input[lastIdx].first) {
                            val temp = input[lastIdx]
                            input[lastIdx] = Pair(temp.first, -1)
                            input[firstIdx] = Pair(input[firstIdx].first - temp.first, -1)
                            input.add(firstIdx, temp)
                            lastIdx++
                            break
                        }
                    }
                    firstIdx++
                }
            }
            lastIdx--
        }
        return input
    }

    private fun getListOfNums(input: List<Pair<Int, Int>>): List<Int> {
        val resList = mutableListOf<Int>()
        input.forEach {
            if (it.second == -1) {
                for (i in 0 until it.first) {
                    resList.add(0)
                }
            } else {
                for (i in 0 until it.first) {
                    resList.add(it.second)
                }
            }
        }
        return resList
    }
}