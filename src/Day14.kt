import java.io.BufferedWriter
import java.io.File

class Day14 : Application {
    var bufferedWriter: BufferedWriter? = null
    var xlim = 0
    var ylim = 0
    var initialPositions = mutableListOf<Pair<Int, Int>>()
    var moves = mutableListOf<Pair<Int, Int>>()
    override fun run(fileName: String): Pair<Long, Long> {
        if (fileName == "day14test") {
            xlim = 7
            ylim = 11
        } else {
            xlim = 103
            ylim = 101
        }
        bufferedWriter = File("src/resources/$fileName-output.txt").bufferedWriter()
        val input = readInput(fileName)
        val inputPos = input.map {
            Pair(
                it.split(" ")[0].split("=")[1].split(",")[1].toInt(),
                it.split(" ")[0].split("=")[1].split(",")[0].toInt()
            )
        }
        initialPositions = inputPos.toMutableList()
        val inputMoves = input.map {
            Pair(
                it.split(" ")[1].split("=")[1].split(",")[1].toInt(),
                it.split(" ")[1].split("=")[1].split(",")[0].toInt()
            )
        }
        moves = inputMoves.toMutableList()
        inputPos.println()
        inputMoves.println()
        val res1 = this.part1()
        val res2 = this.part2()
        bufferedWriter?.close()
        return res1 to res2
    }

    private fun part1(): Long {
        val res = mutableListOf(0, 0, 0, 0)
        initialPositions.zip(moves).forEach {
            val finalPos = move100Times(it.first, it.second)
            when {
                finalPos.first < xlim / 2 && finalPos.second < ylim / 2 -> res[0]++
                finalPos.first > xlim / 2 && finalPos.second < ylim / 2 -> res[1]++
                finalPos.first < xlim / 2 && finalPos.second > ylim / 2 -> res[2]++
                finalPos.first > xlim / 2 && finalPos.second > ylim / 2 -> res[3]++
            }
        }
        res.println()
        return res.fold(1) { acc, i -> acc * (if (i == 0) 1 else i) }
    }

    private fun move100Times(pos: Pair<Int, Int>, move: Pair<Int, Int>): Pair<Int, Int> {
        var newPos = pos
        for (i in 0 until 100) {
            newPos = moveRobot(newPos, move)
        }
        return newPos
    }

    private fun moveRobot(pos: Pair<Int, Int>, move: Pair<Int, Int>): Pair<Int, Int> {
        var x = pos.first + move.first
        if (x >= xlim) x -= xlim
        else if (x < 0) x += xlim
        var y = pos.second + move.second
        if (y >= ylim) y -= ylim
        else if (y < 0) y += ylim
        return Pair(x, y)
    }

//    unable to find a good way to find Christmas tree
//    so run 10000 iterations, and output to a file to look manually
//    can find it by searching "ooooooooooooooooooooo" in the file
    private fun part2(): Long {
        var res = 0L
        var positions = initialPositions
        for (i in 0 until 10000) {
            var notConnected = false
            positions = positions.zip(moves).map {
                moveRobot(it.first, it.second)
            }.toMutableList()
            bufferedWriter?.write("------------------------ Day: ${i + 1} ------------------------")
            bufferedWriter?.newLine()

            positions.draw()
            positions.forEach {
                if (
                    !positions.contains(Pair(it.first + 1, it.second + 1)) &&
                    !positions.contains(Pair(it.first - 1, it.second + 1)) &&
                    !positions.contains(Pair(it.first + 1, it.second - 1)) &&
                    !positions.contains(Pair(it.first - 1, it.second - 1))
                ) notConnected = true
            }
            if (!notConnected) {
                res = i.toLong()
                break
            }
        }
        return res
    }

    private fun MutableList<Pair<Int, Int>>.draw() {
        val map = MutableList(xlim) { MutableList(ylim) { '.' } }
        forEach { map[it.first][it.second] = 'o' }
        val strs = map.map {
            val strBuilder = StringBuilder()
            it.forEach { c ->
                when {
                    c == '.' -> strBuilder.append(' ')
                    else -> strBuilder.append('o')
                }
            }
            strBuilder.toString()
        }
        strs.forEach {
            bufferedWriter?.write(it)
            bufferedWriter?.newLine()
//                .use { bw ->
//                bw?.write(it)
//                bw?.newLine()
//            }
        }
    }
}