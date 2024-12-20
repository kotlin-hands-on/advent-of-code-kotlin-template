import kotlin.math.min

data class ClawMachine(
    val ax: Int,
    val ay: Int,
    val bx: Int,
    val by: Int,
    val px: Long,
    val py: Long
) {
    override fun toString(): String {
        return "ax: $ax, ay: $ay, bx: $bx, by: $by, px: $px, py: $py"
    }
}

class Day13 : Application {
    override fun run(fileName: String): Pair<Long, Long> {
        val input = readInput(fileName).filterNot { it == "" }
        val clawMachineInput =
            input.filter { it.startsWith("Button A") }.zip(input.filter { it.startsWith("Button B") })
                .zip(input.filter { it.startsWith("Prize") }).map {
                    ClawMachine(
                        ax = it.first.first.split(",")[0].split("+")[1].toInt(),
                        ay = it.first.first.split(",")[1].split("+")[1].toInt(),
                        bx = it.first.second.split(",")[0].split("+")[1].toInt(),
                        by = it.first.second.split(",")[1].split("+")[1].toInt(),
                        px = it.second.split(",")[0].split("=")[1].toLong(),
                        py = it.second.split(",")[1].split("=")[1].toLong(),
                    )
                }
//        clawMachineInput.forEach(::println)
        val res1 = this.part1(clawMachineInput)
        "-----------".println()
        val res2 = this.part2(clawMachineInput)
        return res1 to res2
    }

    private fun part1(input: List<ClawMachine>): Long {
        var res = 0L
        input.forEachIndexed { index, it ->
            val singleRes = computeMinCost(it, index)
            if (singleRes > 0) res += singleRes
        }
        return res
    }

    private fun computeMinCost(input: ClawMachine, index: Int): Long {
        var cost = -1L
        var res = Pair(0L, 0L)
        for (i in 0L until input.px / input.ax + 1) {
            if (input.px < (input.ax * i) || input.py < (input.ay * i)) break
            if ((input.px - (input.ax * i)) % input.bx == 0L && (input.py - (input.ay * i)) % input.by == 0L
                && (input.px - (input.ax * i)) / input.bx == (input.py - (input.ay * i)) / input.by
            ) {

                val newCost = computeCost(i, (input.px - (input.ax * i)) / input.bx)
                if (cost != -1L) {
                    if (cost > newCost) {
                        res = Pair(i, (input.px - (input.ax * i)) / input.bx)
                    }
                    cost = min(cost, newCost)
                } else {
                    cost = newCost
                    res = Pair(i, (input.px - (input.ax * i)) / input.bx)
                }
            }
        }
        "$index-$res".println()
        return cost
    }

    private fun computeCost(a: Long, b: Long) = a * 3 + b

    private fun computeWithFormula(input: ClawMachine, index: Int): Long {
        if ((input.ax * input.by - input.ay * input.bx) == 0) return -1
        val computedValues = Pair(
            (input.px * input.by - input.bx * input.py) / (input.ax * input.by - input.ay * input.bx),
            (input.ax * input.py - input.ay * input.px) / (input.ax * input.by - input.ay * input.bx)
        )
        if (computedValues.first < 0 || computedValues.second < 0) return -1
//        if (computedValues.first > 100 || computedValues.second > 100) return -1
        if (computedValues.first * input.ax + computedValues.second * input.bx != input.px ||
            computedValues.first * input.ay + computedValues.second * input.by != input.py) return -1
        "$index-$computedValues".println()
        return computeCost(
            a = computedValues.first,
            b = computedValues.second,
        )
    }

    private fun part2(input: List<ClawMachine>): Long {
//        return input.fold(0L) {acc, i -> acc + computeWithFormula(i)}
        var res = 0L
        input.forEachIndexed { index, it ->
            val singleRes = computeWithFormula(
                ClawMachine(
                    ax = it.ax,
                    ay = it.ay,
                    bx = it.bx,
                    by = it.by,
                    px = it.px + 10000000000000,
                    py = it.py + 10000000000000
                ), index)
//            val singleRes = computeWithFormula(it, index)
            if (singleRes > 0) res += singleRes
        }
        return res
    }

}