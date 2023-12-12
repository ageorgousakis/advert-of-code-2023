const val CONDITION_UNKNOWN = '?'
const val CONDITION_DAMAGED = '#'
const val CONDITION_OPERATIONAL = '.'


fun main() {
    val day = "12"
    val testPart1Result = 21L
    val testPart2Result = 525152L
    val part1Result = 7110L
    val part2Result = 1566786613613L
    val showTestResult = true

//    fun calculateCombinations(springs: List<Char>, damaged: List<Int>): Long {
//        buildMap {
//            fun recursive(springs: List<Char>, damaged: List<Int>): Long = getOrPut(springs to damaged) {
//                when (val damage = damaged.firstOrNull()) {
//                    null -> if (springs.none { it == '#' }) 1 else 0
//                    else -> {
//                        val maxIndex = min(
//                            springs.indexOf('#').takeIf { it >= 0 } ?: Int.MAX_VALUE,
//                            springs.size - damaged.drop(1).sum() - damaged.size + 1 - damage,
//                        )
//                        (0..maxIndex).sumOf { index ->
//                            val piece = springs.subList(index, index + damage)
//                            when (piece.none { it == '.' } && springs.getOrNull(index + damage) != '#') {
//                                true -> recursive(springs.drop(index + damage + 1), damaged.drop(1))
//                                else -> 0
//                            }
//                        }
//                    }
//                }
//            }
//            return recursive(springs, damaged)
//        }
//    }

    fun part1(input: List<String>): Long {
//        return input.sumOf { line ->
//            val (springs, unordered) = line.split(' ')
//            calculateCombinations(springs.toList(), unordered.split(',').map { it.toInt() })
//        }
        return input.map { line ->
            line.split(' ').let { (springs, groups) ->
                val damagedGroups = groups.split(',').map { it.toInt() }
                HotSprings(springs, damagedGroups)
            }
        }.sumOf { it.combinations(0, 0, -1) }
    }

    fun part2(input: List<String>): Long {
        return input.map { line ->
            line.split(' ').let { (springs, groups) ->
                val damagedGroups = groups.split(',').map { it.toInt() }
                HotSprings("$springs?".repeat(5).dropLast(1), List(5) { damagedGroups }.flatten())
            }
        }.sumOf { it.combinations(0, 0, -1) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    part1(testInput).also {
        if (showTestResult) it.println()
        check(it == testPart1Result) { "$it <> $testPart1Result" }
    }
    part2(testInput).also {
        if (showTestResult) it.println()
        check(it == testPart2Result) { "$it <> $testPart2Result" }
    }

    val input = readInput("Day$day")
    part1(input).also {
        it.println()
        check(it == part1Result) { "$it <> $part1Result" }
    }
    part2(input).also {
        it.println()
        check(it == part2Result) { "$it <> $part2Result" }
    }
}

data class HotSprings(val springs: String, val damagedGroups: List<Int>) {
    private val cache = mutableMapOf<Triple<Int, Int, Int>, Long>()

    fun combinations(springIndex: Int, groupIndex: Int, remainingUnknown: Int): Long =
        cache.getOrPut(Triple(springIndex, groupIndex, remainingUnknown)) {
            calculateCombinations(
                springIndex,
                groupIndex,
                remainingUnknown
            )
        }

    private fun calculateCombinations(springIndex: Int, groupIndex: Int, remainingUnknown: Int): Long {
        if (springIndex >= springs.length) return if (remainingUnknown <= 0 && groupIndex == damagedGroups.size) 1 else 0
        return when (val next = springs[springIndex]) {
            CONDITION_OPERATIONAL -> {
                if (remainingUnknown <= 0)
                    combinations(springIndex + 1, groupIndex, -1)
                else 0
            }

            CONDITION_DAMAGED -> when {
                remainingUnknown < 0 ->
                    if (groupIndex >= damagedGroups.size) 0
                    else combinations(springIndex + 1, groupIndex + 1, damagedGroups[groupIndex] - 1)

                remainingUnknown == 0 -> 0
                else -> combinations(springIndex + 1, groupIndex, remainingUnknown - 1)
            }

            CONDITION_UNKNOWN -> when {
                remainingUnknown < 0 ->
                    (if (groupIndex >= damagedGroups.size) 0
                    else combinations(springIndex + 1, groupIndex + 1, damagedGroups[groupIndex] - 1)) +
                            combinations(springIndex + 1, groupIndex, -1)

                remainingUnknown == 0 ->
                    combinations(springIndex + 1, groupIndex, -1)

                else ->
                    combinations(springIndex + 1, groupIndex, remainingUnknown - 1)
            }

            else -> {
                error("Invalid spring: $next")
            }
        }
    }

}
