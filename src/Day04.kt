fun main() {
    val inputs = readInput("day04")
    var res = findXmasHorizontal(inputs)
    res += findXmasHorizontal(transposeMatrix(inputs))
    res += findXmasHorizontal(getDiagonalMatrix(inputs))
    println(res)

    val res2 = findXmasAllDirections(inputs)
    println(res2)

    val res3 = findAllMas(inputs)
    println(res3)
}

fun findXmasAllDirections(inputs: List<String>): Int {
    var i = 0
    var res = 0
    while (i < inputs.size) {
        var j = 0
        while (j < inputs.size) {
            if (inputs[i][j] == 'X') {
                try {
                    if (isXmas(inputs[i][j], inputs[i][j + 1], inputs[i][j + 2], inputs[i][j + 3])) {
                        res++
                    }
                } catch (e: IndexOutOfBoundsException) {

                }
                try {
                    if (isXmas(inputs[i][j], inputs[i][j - 1], inputs[i][j - 2], inputs[i][j - 3])) {
                        res++
                    }
                } catch (e: IndexOutOfBoundsException) {

                }
                try {
                    if (isXmas(inputs[i][j], inputs[i + 1][j], inputs[i + 2][j], inputs[i + 3][j])) {
                        res++
                    }
                } catch (e: IndexOutOfBoundsException) {

                }
                try {
                    if (isXmas(inputs[i][j], inputs[i - 1][j], inputs[i - 2][j], inputs[i - 3][j])) {
                        res++
                    }
                } catch (e: IndexOutOfBoundsException) {

                }
                try {
                    if (isXmas(inputs[i][j], inputs[i + 1][j + 1], inputs[i + 2][j + 2], inputs[i + 3][j + 3])) {
                        res++
                    }
                } catch (e: IndexOutOfBoundsException) {

                }
                try {
                    if (isXmas(inputs[i][j], inputs[i - 1][j + 1], inputs[i - 2][j + 2], inputs[i - 3][j + 3])) {
                        res++
                    }
                } catch (e: IndexOutOfBoundsException) {

                }
                try {
                    if (isXmas(inputs[i][j], inputs[i + 1][j - 1], inputs[i + 2][j - 2], inputs[i + 3][j - 3])) {
                        res++
                    }
                } catch (e: IndexOutOfBoundsException) {

                }
                try {
                    if (isXmas(inputs[i][j], inputs[i - 1][j - 1], inputs[i - 2][j - 2], inputs[i - 3][j - 3])) {
                        res++
                    }
                } catch (e: IndexOutOfBoundsException) {

                }
            }
            j++
        }
        i++
    }
    return res
}

fun isXmas(char1: Char, char2: Char, char3: Char, char4: Char): Boolean {
    return char1 == 'X' && char2 == 'M' && char3 == 'A' && char4 == 'S'
}

fun findAllMas(inputs: List<String>): Int {
    var i = 0
    var res = 0
    while (i < inputs.size) {
        var j = 0
        while (j < inputs.size) {
            if (inputs[i][j] == 'A') {
                try {
                    if (isMas(inputs[i - 1][j - 1], inputs[i + 1][j + 1], inputs[i - 1][j + 1], inputs[i + 1][j - 1])) {
                        res++
                    }
                } catch (e: IndexOutOfBoundsException) {

                }
            }
            j++
        }
        i++
    }
    return res

}

fun isMas(char1: Char, char2: Char, char3: Char, char4: Char): Boolean {
    if (char1 == 'M' && char2 == 'S' || char1 == 'S' && char2 == 'M') {
        if (char3 == 'M' && char4 == 'S' || char3 == 'S' && char4 == 'M') {
            return true
        }
    }
    return false
}

fun findXmasHorizontal(inputs: List<String>): Int {
    var res = 0
    inputs.forEach { input ->
        for (index in 0 until input.length - 3) {
            if (index < input.length - 4) {
                if (input.substring(index, index + 4) == "XMAS" || input.substring(index, index + 4) == "SAMX") {
                    res++
                }
            }
        }
    }
    return res
}

fun transposeMatrix(input: List<String>): List<String> {
    val output = mutableListOf<String>()
    for (i in 0 until input.size) {
        val stringBuilder = StringBuilder()
        for (j in input[i].indices) {
            stringBuilder.append(input[j][i])
        }
        output.add(stringBuilder.toString())
    }
    return output
}

fun getDiagonalMatrix(input: List<String>): List<String> {
    val output = mutableListOf<String>()
    var iterations = 0
    while (iterations < input.size) {
        var i = 0
        var j = iterations
        val strBuilder = StringBuilder()
        while (j > 0) {
            strBuilder.append(input[i][j])
            i++
            j--
        }
        output.add(strBuilder.toString())
        iterations++
    }
    iterations = input.size - 2
    while (iterations >= 0) {
        var i = input.size - 1 - iterations
        var j = iterations
        val strBuilder = StringBuilder()
        while (i < input.size) {
            strBuilder.append(input[i][j])
            i++
            j--
        }
        output.add(strBuilder.toString())
        iterations--
    }
    iterations = input.size - 1
    while (iterations >= 0) {
        var i = iterations
        var j = 0
        val strBuilder = StringBuilder()
        while (i < input.size) {
            strBuilder.append(input[i][j])
            i++
            j++
        }
        output.add(strBuilder.toString())
        iterations--
    }
    iterations = 1
    while (iterations < input.size) {
        var i = 0
        var j = iterations

        val strBuilder = StringBuilder()
        while (j < input.size) {
            strBuilder.append(input[i][j])
            i++
            j++
        }
        output.add(strBuilder.toString())
        iterations++
    }
    return output
}