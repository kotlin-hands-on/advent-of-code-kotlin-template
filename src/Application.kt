interface Application {
    fun run(fileName: String): Pair<Long, Long>
}

fun main(args: Array<String>) {
    val app: Application = Day10()
    val day = 10
    app.run("day${day.toString().padStart(2, '0')}test").printRes(day, true)
    app.run("day${day.toString().padStart(2, '0')}").printRes(day, false)
}

fun Pair<Long, Long>.printRes(day: Int, isTest: Boolean = false) {
    val dayStr = if(isTest) "day${day.toString().padStart(2, '0')}-test" else "day-${day.toString().padStart(2, '0')}"
    println("${dayStr}-1: $first")
    println("${dayStr}-2: $second")
}