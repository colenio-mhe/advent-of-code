fun main() {
    data class Point(val x: Int, val y: Int) {
        fun neighbors(): Set<Point> = setOf(
            Point(x - 1, y - 1),
            Point(x, y - 1),
            Point(x + 1, y - 1),
            Point(x - 1, y),
            Point(x + 1, y),
            Point(x - 1, y + 1),
            Point(x, y + 1),
            Point(x + 1, y + 1)
        )
    }

    class Number {
        private val number = mutableListOf<Char>()
        private val locations = mutableSetOf<Point>()
        fun add(c: Char, location: Point) {
            number.add(c)
            locations.addAll(location.neighbors())
        }

        fun isNotEmpty() = number.isNotEmpty()
        fun isAdjacentToAny(points: Set<Point>): Boolean = locations.intersect(points).isNotEmpty()
        fun isAdjacentTo(point: Point): Boolean = point in locations
        fun toInt(): Int = number.joinToString("").toInt()
    }

    fun parse(
        input: List<String>, takeSymbol: (Char) -> Boolean = { it != '.' }
    ): Pair<Set<Number>, Set<Point>> {
        val numbers = mutableSetOf<Number>()
        val symbols = mutableSetOf<Point>()
        var tempNumber = Number()

        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c.isDigit()) {
                    tempNumber.add(c, Point(x, y))
                } else {
                    if (tempNumber.isNotEmpty()) {
                        numbers.add(tempNumber)
                        tempNumber = Number()
                    }
                    if (takeSymbol(c)) {
                        symbols.add(Point(x, y))
                    }
                }
            }
            if (tempNumber.isNotEmpty()) {
                numbers.add(tempNumber)
                tempNumber = Number()
            }
        }
        return Pair(numbers, symbols)
    }

    fun part1(input: List<String>): Int {
        val (numbers, symbols) = parse(input)
        return numbers.filter { number -> number.isAdjacentToAny(symbols) }.sumOf { it.toInt() }
    }

    fun part2(input: List<String>): Int {
        val (numbers, symbols) = parse(input) { it == '*' }
        return symbols.sumOf { symbol ->
            val neighbors = numbers.filter { it.isAdjacentTo(symbol) }
            if (neighbors.size == 2) {
                neighbors.first().toInt() * neighbors.last().toInt()
            } else 0
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val part1 = part1(testInput)
    check(part1 == 4361)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}


