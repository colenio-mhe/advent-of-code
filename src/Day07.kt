enum class Type {
    NONE, HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND
}

fun main() {
    // Step 1: Parse Input
    // Step 2: Determine Type
    // Step 3: Determine Rank
    // Step 4: return sumBy(RANK * BID)
    data class Game(
        val hand: String, val bid: Int, val valueMap: Map<Char, Int>
    ) {
        val cards: List<Int>
            get() = hand.map { valueMap[it] ?: it.digitToInt() }
    }

    fun parse(input: List<String>, valueMap: Map<Char, Int>): List<Game> {
        return input.map { line ->
            line.split(" ").let {
                Game(it.first(), it.last().toInt(), valueMap)
            }
        }
    }

    fun checkAnyOfAKind(hand: String, kind: Int) = hand.any { candidate -> hand.count { candidate == it } == kind }

    fun calculateType(hand: String): Type = when (hand.toSet().count()) {
        5 -> Type.HIGH_CARD
        4 -> Type.ONE_PAIR
        3 -> if (checkAnyOfAKind(hand, 3)) Type.THREE_OF_KIND else Type.TWO_PAIR
        2 -> if (checkAnyOfAKind(hand, 4)) Type.FOUR_OF_A_KIND else Type.FULL_HOUSE
        1 -> Type.FIVE_OF_A_KIND
        else -> error("Malformed hand string")
    }

    fun part1(input: List<String>): Int {
        val valueMap = mapOf(
            'T' to 10, 'J' to 11, 'Q' to 12, 'K' to 13, 'A' to 14
        )

        var games = parse(input, valueMap)
        games = games.sortedWith(compareBy({ it.cards[0] },
            { it.cards[1] },
            { it.cards[2] },
            { it.cards[3] },
            { it.cards[4] })).sortedBy { calculateType(it.hand) }

        return games.mapIndexed { index, game -> (index + 1) * game.bid }.sumOf { it }
    }

    fun part2(input: List<String>): Int {
        val valueMap = mapOf(
            'J' to 1, 'T' to 10, 'Q' to 12, 'K' to 13, 'A' to 14
        )

        fun patchHand(hand: String): String =
            if (hand.count { it == 'J' } == 5) hand
            else hand.replace('J', hand.filter { it != 'J' }.groupBy { it }.maxBy { it.value.size }.key)

        var games = parse(input, valueMap)
        games = games.sortedWith(compareBy(
            { it.cards[0] },
            { it.cards[1] },
            { it.cards[2] },
            { it.cards[3] },
            { it.cards[4] }
        )).sortedBy { calculateType(patchHand(it.hand)) }
        return games.mapIndexed { index, game -> (index + 1) * game.bid }.sumOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val part1 = part1(testInput)
    check(part1 == 6440)

    val part2 = part2(testInput)
    check(part2 == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}


