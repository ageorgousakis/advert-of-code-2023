val original_marks = "AKQJT"
val ordered_marks = "EDCBA"
val original_marks2 = "AKQTJ"
val ordered_marks2 = "DCBA1"
fun addType(hand: String) = powers2(hand).joinToString("").padEnd(5) + hand
fun powers2(hand: String): List<Int> = hand.toList().sorted().reversed().fold(emptyMap<Char, Int>()) { acc, c ->
    if (c == '1') {
        val highestKeyPair = acc.maxByOrNull { it.value }
        if (highestKeyPair == null) {
            acc + (c to (acc.getOrDefault(c, 0) + 1))
        } else {
            acc + (highestKeyPair.key to highestKeyPair.value + 1)
        }
    } else
        acc + (c to (acc.getOrDefault(c, 0) + 1))
}.values.sorted().reversed()


fun main() {
    val day = "07"
    val testPart1Result: Long = 6440
    val testPart2Result: Long = 5905
    val part1Result: Long = 246424613
    val part2Result: Long = 248256639
    val showTestResult = true

    fun part1(input: List<String>): Long =
        input.map {
            val (hand, bid) = it.split(' ')
            addType(hand.map {
                if (!it.isDigit()) ordered_marks[original_marks.indexOf(it)] else it
            }.joinToString("")) to bid.toLong()
        }.sortedWith { a, b ->
            a.first.compareTo(b.first)
        }.mapIndexed { index, handToBid ->
            println("${handToBid.first} to ${handToBid.second}")
            handToBid.second * (index + 1)
        }.sum()

    fun part2(input: List<String>): Long =
        input.map {
            val (hand, bid) = it.split(" ")
            addType(hand.map {
                if (!it.isDigit()) ordered_marks2[original_marks2.indexOf(it)] else it
            }.joinToString("")) to bid.toLong()
        }.sortedWith { a, b ->
            a.first.compareTo(b.first)
        }.mapIndexed { index, handToBid ->
            println("${handToBid.first} to ${handToBid.second}")
            handToBid.second * (index + 1)
        }.sum()

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
