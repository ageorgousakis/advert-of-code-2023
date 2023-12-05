fun main() {
    val day = "01"
    val testPart1Result = 142
//    val testPart2Result = 0
    val part1InputResult = 54081
    val part2InputResult = 54649
    val showTestResult = true

    val numbers = mapOf(
            "one" to '1',
            "two" to '2',
            "three" to '3',
            "four" to '4',
            "five" to '5',
            "six" to '6',
            "seven" to '7',
            "eight" to '8',
            "nine" to '9',
    )

    fun String.calibratedValue() =
        first { it.isDigit() }.digitToInt() * 10 + reversed().first { it.isDigit() }.digitToInt()

    fun String.convertWordsToNumbers(): String {
        return mapIndexedNotNull { index, c ->
            if (c.isDigit()) c
            else
                    (3..5).map { len ->
                        substring(index, (index + len).coerceAtMost(length))
                    }.firstNotNullOfOrNull { word -> numbers[word] }
        }.joinToString()
    }

    fun part1(input: List<String>): Int =
        input.sumOf {
            it.calibratedValue()
        }

    fun part2(input: List<String>): Int =
        input.sumOf {
            it.convertWordsToNumbers().calibratedValue()
        }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    part1(testInput).also {
        if (showTestResult) it.println()
        check(it == testPart1Result)
    }
//    part2(testInput).also {
//        if (showTestResult) it.println()
//        check(it == testPart2Result)
//    }

    val input = readInput("Day$day")
    part1(input).also {
        it.println()
        check(it == part1InputResult)
    }
    part2(input).also {
        it.println()
        check(it == part2InputResult)
    }
}
