fun main() {
    val day = "14"
    val testPart1Result = 136
    val testPart2Result = 64
    val part1Result = 111979
    val part2Result = 0
    val showTestResult = true

    fun parseInput(input: List<String>): List<String> =
        input[0].indices.map { i ->
            input.map { it[i] }.joinToString("")
        }

    fun part1(input: List<String>): Int {
        val lines = parseInput(input)
        val lavaComparator = Comparator { a: Char, b: Char ->
            when (a) {
                b -> 0
                'O' -> -1
                else -> 1
            }
        }

        return lines.sumOf { line ->
            line.split('#').joinToString("#") { chunk ->
                chunk.toList().sortedWith(lavaComparator).joinToString("")
            }.reversed().mapIndexed { index, c -> if (c == 'O') index + 1 else 0 }.sum()
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    part1(testInput).also {
        if (showTestResult) it.println()
        check(it == testPart1Result)  { "$it <> $testPart1Result" }
    }
//    part2(testInput).also {
//        if (showTestResult) it.println()
//        check(it == testPart2Result)  { "$it <> $testPart2Result" }
//    }
//
    val input = readInput("Day$day")
    part1(input).also {
        it.println()
        check(it == part1Result)  { "$it <> $part1Result" }
    }
//    part2(input).also {
//        it.println()
//        check(it == part2Result)  { "$it <> $part2Result" }
//    }
}
