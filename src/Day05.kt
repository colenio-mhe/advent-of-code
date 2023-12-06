fun main() {
    fun parseMaps(input: List<String>) =
        input.drop(2).joinToString("\n").split("\n\n").map { section ->
            section.lines().drop(1).associate { row ->
                row.split(" ").map { it.toLong() }.let { (dest, source, length) ->
                    source..(source + length) to dest..(dest + length)
                }
            }
        }

    fun part1(input: List<String>): Long {
        val seeds = input.first().substringAfter(" ").split(" ").map { it.toLong() }
        val maps = parseMaps(input)
        return seeds.minOf { seed ->
            maps.fold(seed) { acc, map ->
                map.entries.firstOrNull { acc in it.key }?.let { (source, dest) -> acc + (dest.first - source.first) } ?: acc
            }
        }
    }

    /**
     * After talking to a friend it seems like this solution is fuzzy and doesn't work for every input :D
     * It fails for unmapped areas
     */
    fun part2(input: List<String>): Long {
        val seeds = input.first().substringAfter(" ").split(" ").map { it.toLong() }.chunked(2).map { it.first()..<it.first() + it.last() }
        val maps = parseMaps(input)

        return seeds.flatMap { seedsRange ->
            maps.fold(listOf(seedsRange)) { locRange, map ->
                locRange.flatMap {acc ->
                    map.entries.mapNotNull { (source, dest) ->
                        (maxOf(source.first, acc.first) to minOf(source.last, acc.last)).let { (start, end) ->
                            if (start <= end) (dest.first - source.first).let { (start + it)..(end + it) } else null
                        }
                    }
                }
            }
        }.minOf { it.first }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val part1 = part1(testInput)
    check(part1 == 35L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}


