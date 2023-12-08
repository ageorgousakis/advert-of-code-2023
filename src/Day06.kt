fun main() {
    val day = "06"
    val testPart1Result = 288
    val testPart2Result: Long = 71503
    val part1Result = 3316275
    val part2Result: Long = 27102791
    val showTestResult = false

    val spacesSplitter = "\\s+".toRegex()

    fun part1(input: List<String>): Int {
        val times = input[0].substringAfter(":").trim().split(spacesSplitter).map(String::toInt)
        val distances = input[1].substringAfter(":").trim().split(spacesSplitter).map(String::toInt)
        val races = times.mapIndexed { index, time ->
            time to distances[index]
        }
        return races.map { (time, distance) ->
//            println("Race $time / $distance")
            val from = (1..<time).first { speed ->
                (time - speed) * speed > distance
            }
            time - (from * 2) + 1
        }.fold(1) { acc, count -> acc * count }
    }

    fun part2(input: List<String>): Long {
        val time = input[0].substringAfter(":").filterNot { it.isWhitespace() }.toLong()
        val distance = input[1].substringAfter(":").filterNot { it.isWhitespace() }.toLong()
//        return (1..<time).map { speed ->
//            (time - speed) * speed
//        }.count { it > distance }
        val from = (1..<time).first { speed ->
            (time - speed) * speed > distance
        }
        return time - (from * 2) + 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    part1(testInput).also {
        if (showTestResult) it.println()
        check(it == testPart1Result) { "$it <> $testPart1Result" }
    }
    part2(testInput).also {
        if (showTestResult) it.println()
        check(it == testPart2Result)  { "$it <> $testPart2Result" }
    }

    val input = readInput("Day$day")
    part1(input).also {
        it.println()
        check(it == part1Result) { "$it <> $part1Result" }
    }
    part2(input).also {
        it.println()
        check(it == part2Result)  { "$it <> $part2Result" }
    }
}
