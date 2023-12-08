import kotlin.math.max

fun main() {
    fun parse(input: List<String>): Pair<List<Char>, Map<String, Pair<String, String>>> {
        val directions = input.first().map { it }
        val splitNode = Regex("([A-Z]+)\\s*=\\s*\\(([^,]+),\\s*([^)]+)\\)")
        val nodes = input.drop(2).mapNotNull { line ->
            splitNode.find(line)?.let {
                it.groupValues[1] to (it.groupValues[2] to it.groupValues[3])
            }
        }.toMap()
        return Pair(directions, nodes)
    }

    fun part1(input: List<String>): Int {
        val (directions, nodes) = parse(input)

        var steps = 0
        var currentNode = "AAA"
        while (currentNode != "ZZZ") {
            val currentDirection = directions[steps % directions.size]
            currentNode = when(currentDirection) {
                'L' -> nodes[currentNode]?.first ?: error("Map damaged")
                'R' -> nodes[currentNode]?.second ?: error("Map damaged")
                else -> error("Direction can only be R or L")
            }
            steps++
        }

        return steps
    }

    fun part2(input: List<String>): Long {
        val (directions, nodes) = parse(input)

        fun findLCM(a: Long, b: Long): Long {
            val larger = max(a, b)
            val maxLcm = a * b
            var lcm = larger
            while (lcm <= maxLcm) {
                if (lcm % a == 0L && lcm % b == 0L) {
                    return lcm
                }
                lcm += larger
            }
            return maxLcm
        }

        fun findLCMOfListOfNumbers(numbers: LongArray): Long {
            var result = numbers[0]
            for (i in 1 until numbers.size) {
                result = findLCM(result, numbers[i])
            }
            return result
        }

        var steps = 0L
        var currentNode = nodes.filter { it.key.last() == 'A' }.map { it.key }
        val firstZ = LongArray(currentNode.size)
        while (!firstZ.all { it != 0L }) {
            val currentDirection = directions[(steps % directions.size).toInt()]
            steps++
            currentNode = currentNode.map {
                when(currentDirection) {
                    'L' -> nodes[it]?.first ?: error("Map damaged")
                    'R' -> nodes[it]?.second ?: error("Map damaged")
                    else -> error("Direction can only be R or L")
                }
            }

            currentNode.forEachIndexed{index, s ->
                if (s.last() == 'Z' && firstZ[index] == 0L) firstZ[index] = steps
            }
        }

        return findLCMOfListOfNumbers(firstZ)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val part1 = part1(testInput)
    check(part1 == 6)

    val testInputPart2 = readInput("Day08_test_p2")
    val part2 = part2(testInput)
    check(part2 == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}


