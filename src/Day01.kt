fun main() {
    fun getVal(strInput: String) =
        (strInput.first { it.isDigit() }.digitToInt() * 10) + (strInput.findLast { it.isDigit() }?.digitToInt() ?: 0)

    fun part1(input: List<String>): Int {
        return input.sumOf { strInput ->
            getVal(strInput)
        }
    }

    fun String.possible(startingAt: Int): List<String> =
        (3..5).map { len ->
            substring(startingAt, (startingAt + len).coerceAtMost(length))
        }

    fun part2(input: List<String>): Int {
        val wordToDigitMap = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )

        return input.sumOf {strInput ->
            getVal(
                strInput.mapIndexedNotNull{ index, c ->
                    if(c.isDigit()) c
                    else
                        strInput.possible(index).firstNotNullOfOrNull { can ->
                            wordToDigitMap[can]
                        }
                }.joinToString()
            )
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val testInputP2 = readInput("Day01_p2_test")
    check(part2(testInputP2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}


