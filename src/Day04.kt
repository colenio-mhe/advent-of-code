import kotlin.math.pow
fun main() {
    data class Card(val id: Int, private val winningNumbers: Set<Int>, private val numbers: Set<Int>) {
        val score: Int
            get() {
                return 2.0.pow(winningNumbers.intersect(numbers).count().toDouble() - 1).toInt()
            }
        val numberOfWinningMatches: Int
            get() {
                return winningNumbers.intersect(numbers).count()
            }
    }

    fun parse(input: List<String>): List<Card> {
        return buildList {
            input.forEach {card ->
                val id = Regex("Card +(\\d+):").find(card)!!.groups[1]!!.value.toInt()
                val winningNumbers = Regex("Card +\\d+: +((\\d+ +)*)").find(card)!!.groups[1]!!.value.split(" ").filter{it.isNotEmpty()}.map { it.toInt() }.toSet()
                val numbers =  Regex("\\|(( +\\d+)*)").find(card)!!.groups[1]!!.value.split(" ").filter{it.isNotEmpty()}.map { it.toInt() }.toSet()
                this.add(Card(id, winningNumbers, numbers))
            }
        }
    }

    fun part1(input: List<String>): Int {
        val cards = parse(input)
        return cards.sumOf { it.score }
    }

    fun processCard(card: Card, cards: List<Card>): Int {
        if (card.id == cards.count()) { return 1 }

        return cards
            .subList(card.id, card.id + card.numberOfWinningMatches)
            .sumOf { processCard(it, cards) } + 1
    }

    fun part2(input: List<String>): Int {
        val cards = parse(input)
        return cards.sumOf { processCard(it, cards) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val part1 = part1(testInput)
    check(part1 == 13)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}


