fun main() {
    fun part1(input: List<String>): Int {
        val times = input.first().replace("Time:", "").trimStart().split("\\s+".toRegex()).map { it.toInt() }
        val distances = input.last().replace("Distance:", "").trimStart().split("\\s+".toRegex()).map { it.toInt() }

        return times.foldIndexed(1) { i, acc, time ->
            acc * ((1..time).count { it * (time - it) > distances[i] })
        }
    }

    fun part2(input: List<String>): Int {
        val time = input.first().replace("Time:", "").replace(" ", "").toLong()
        val distance = input.last().replace("Distance:", "").replace(" ", "").toLong()
        return (1..time).count { it * (time - it) > distance }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    val part1 = part1(testInput)
    check(part1 == 288)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}


