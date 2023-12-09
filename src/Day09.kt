fun main() {
    val day = "09"
    val testPart1Result = 114
    val testPart2Result = 2
    val part1Result = 1584748274
    val part2Result = 1026
    val showTestResult = false

    fun part1(input: List<String>): Int = input.sumOf { line ->
        val sequences = mutableListOf(line.split(' ').map(String::toInt))
        while (sequences.last().any { it != 0 })
            sequences += sequences.last().windowed(2).map { (a, b) -> b - a }
        sequences.sumOf { it.last() }
    }

    fun part2(input: List<String>): Int = input.sumOf { line ->
        val sequences = mutableListOf(line.split(' ').map(String::toInt))
        while (sequences.last().any { it != 0 })
            sequences += sequences.last().windowed(2).map { (a, b) -> b - a }
        sequences.map { it.first() }.reversed().reduce { acc, i -> i - acc }
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
