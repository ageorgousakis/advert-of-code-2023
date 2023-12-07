import kotlin.math.pow

fun main() {
    val day = "04"
    val testPart1Result = 13
    val testPart2Result = 30
    val part1Result = 25571
    val part2Result = 8805731
    val showTestResult = true

    fun String.splitNumbers() = split(" ").filter { it.isNotBlank() }.map { it.toInt() }

    fun part1(input: List<String>): Int =
            input.map { line ->
                line.split(":", " | ").let {
                    val winningNumbers = it[1].splitNumbers()
                    val playedNumbers = it[2].splitNumbers()
                    val matchedNumbers = winningNumbers.count { it in playedNumbers }
                    if (matchedNumbers > 0) 2.0.pow(matchedNumbers - 1).toInt() else 0
                }
            }
                    //            .onEach(::println)
                    .sum()


    fun part2(input: List<String>): Int =
            input.map { line ->
                line.split(":", " | ").let {
                    val winningNumbers = it[1].splitNumbers()
                    val playedNumbers = it[2].splitNumbers()
                    winningNumbers.count { it in playedNumbers }
                }
            }.let { winningCounts ->
                val cardCounts = MutableList(winningCounts.size) { 1 }
                winningCounts.mapIndexed { index: Int, count: Int ->
                    (1..count).forEach {
                        cardCounts[index + it] += cardCounts[index]
                    }
                }
                cardCounts
            }
//            .onEach(::println)
                    .sum()

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
