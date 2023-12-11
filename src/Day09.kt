fun main() {

    fun printNumberLine(numbers: List<Int>) {
        numbers.forEach {
            print("$it\t")
        }
        println()
    }

    fun part1(input: List<String>): Int {
        val parsed = input.map { lines -> lines.split(" ").map { it.toInt() } }

        fun getPredictedValue(row: List<Int>): Int {
            if (row.all { it == 0 }) return 0

            val nextLine = row.mapIndexedNotNull { index, entry ->
                if (row.size > index + 1) row[index + 1] - entry
                else null
            }

            return row.last() + getPredictedValue(nextLine)
        }

        return parsed.map { row ->
           getPredictedValue(row)
        }.sumOf{it}
    }

    fun part2(input: List<String>): Int {
        val parsed = input.map { lines -> lines.split(" ").map { it.toInt() } }

        fun getPredictedValue(row: List<Int>): Int {
            if (row.all { it == 0 }) return 0

            val nextLine = row.mapIndexedNotNull { index, entry ->
                if (row.size > index + 1) row[index + 1] - entry
                else null
            }

            return row.first() - getPredictedValue(nextLine)
        }

        return parsed.map { row ->
            getPredictedValue(row)
        }.sumOf{it}
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    val part1 = part1(testInput)
    check(part1 == 114)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}


