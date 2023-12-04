data class Cubes(val red: Int, val green: Int, val blue: Int) {
    fun checkRule(givenRule: Cubes): Boolean =
        this.let {
            it.red <= givenRule.red && it.green <= givenRule.green &&  it.blue <= givenRule.blue
        }
}

fun main() {
    fun listOfGames(input: String): List<Cubes>  =
        input.split(";").map { gameString ->
            val components = gameString.trim().split(", ")

            val red = components.find { it.endsWith("red") }?.split(" ")?.get(0)?.toIntOrNull() ?: 0
            val green = components.find { it.endsWith("green") }?.split(" ")?.get(0)?.toIntOrNull() ?: 0
            val blue = components.find { it.endsWith("blue") }?.split(" ")?.get(0)?.toIntOrNull() ?: 0

            Cubes(red, green, blue)
        }

    fun mapOfGames(input: List<String>): Map<Int, List<Cubes>> = input.associate {
        it.replace("Game", "").substringBefore(":").trim().toInt() to listOfGames(it.replace(Regex("Game \\d+:"), "").trim())
    }

    fun part1(input: List<String>): Int {
        val givenGame = Cubes(12,13,14)
        val games = mapOfGames(input)

        return games.map {
            when(it.value.all {cubes -> cubes.checkRule(givenGame) }) {
                true -> it.key
                false -> 0
            }
        }.sumOf { it }
    }

    fun part2(input: List<String>): Int {
        val games = mapOfGames(input)

        return games.map { game ->
            val red = game.value.maxOf { it.red }
            val green = game.value.maxOf { it.green }
            val blue = game.value.maxOf { it.blue }

            red*green*blue
        }.sumOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val part1 = part1(testInput)
    check(part1 == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}


