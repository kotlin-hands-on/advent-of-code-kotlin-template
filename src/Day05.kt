class Node(
    val value: Int,
    val parent: List<Node> = listOf()
)

fun main() {
    val rulesInput = readInput("day05p1").map {
        Pair(it.split('|')[0].toInt(), it.split('|')[1].toInt())
    }
    val pagesInput = readInput("day05p2").map {
        it.split(',').map { it.toInt() }
    }
    val rulesNodeMap = mutableMapOf<Int, Node>()
    rulesInput.forEach {
        val parentNode = rulesNodeMap[it.first] ?: Node(it.first)
        val childNode =
            Node(it.second, (rulesNodeMap[it.second] ?: Node(it.second, mutableListOf())).parent.plus(parentNode))
        rulesNodeMap[it.first] = parentNode
        rulesNodeMap[it.second] = childNode
    }
    val validPages = pagesInput.filter { isValidPage(it, rulesNodeMap) }
    val res1 = validPages.fold(0) { acc, page -> acc + page[page.size / 2] }
    println(res1)
    val invalidPages = pagesInput.filterNot { isValidPage(it, rulesNodeMap) }
    val reorderedPagges = invalidPages.map { reorderPage(it, rulesNodeMap) }
    val res2 = reorderedPagges.fold(0) { acc, page -> acc + page[page.size / 2] }
    res2.println()
}

fun isValidPage(input: List<Int>, map: Map<Int, Node>): Boolean {
    input.forEachIndexed { index, num ->
        val node = map[num]
        if (node != null) {
            for (i in index + 1 until input.size - 1) {
                if (node.parent.map { it.value }.contains(input[i])) return false
            }
        }
    }
    return true
}

fun reorderPage(input: List<Int>, map: Map<Int, Node>): List<Int> {
    val outputList = input.filter{map[it] == null}.toMutableList()
    var numsToReorder = input.filter { map[it] != null }
    while (numsToReorder.isNotEmpty()) {
        val numsToRemove = numsWithoutParentNode(numsToReorder, map)
        outputList.addAll(numsToRemove)
        numsToReorder = numsToReorder.filterNot { numsToRemove.contains(it) }
    }
    return outputList.toList()
}

fun numsWithoutParentNode(input: List<Int>, map: Map<Int, Node>): List<Int> {
    return input.filterNot {
        var hasParent = false
        map[it]!!.parent.forEach { parent ->
            if(input.contains(parent.value)) {
                hasParent = true
            }
        }
        hasParent
    }
}
