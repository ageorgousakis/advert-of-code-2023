fun main() {
    val day = "07"
    val testPart1Result: Long = 6440
    val testPart2Result: Long = 5905
    val part1Result: Long = 246424613
    val part2Result: Long = 248256639
    val showTestResult = false

    fun scored(hand: String): String = hand.toList().sorted().reversed().fold(emptyMap<Char, Int>()) { acc, c ->
        if (c == '1') {
            val highestKeyPair = acc.maxByOrNull { it.value }
            if (highestKeyPair == null) {
                acc + (c to (acc.getOrDefault(c, 0) + 1))
            } else {
                acc + (highestKeyPair.key to highestKeyPair.value + 1)
            }
        } else
            acc + (c to (acc.getOrDefault(c, 0) + 1))
    }.values.sorted().reversed().joinToString("").padEnd(5) + hand

    fun part1(input: List<String>): Long {
        val original_marks = "AKQJT98765432"
        val ordered_marks = "EDCBA98765432"
        return input.map {
            val (hand, bid) = it.split(' ')
            val orderedHand = hand.map {
                ordered_marks[original_marks.indexOf(it)]
            }.joinToString("")
            scored(orderedHand) to bid.toLong()
        }.sortedWith { a, b ->
            a.first.compareTo(b.first)
        }.mapIndexed { index, handToBid ->
//            println("${handToBid.first} to ${handToBid.second}")
            handToBid.second * (index + 1)
        }.sum()
    }

    fun part2(input: List<String>): Long {
        val original_marks = "AKQTJ98765432"
        val ordered_marks = "DCBA198765432"
        return input.map {
            val (hand, bid) = it.split(" ")
            val orderedHand = hand.map {
                ordered_marks[original_marks.indexOf(it)]
            }.joinToString("")
            scored(orderedHand) to bid.toLong()
        }.sortedWith { a, b ->
            a.first.compareTo(b.first)
        }.mapIndexed { index, handToBid ->
//            println("${handToBid.first} to ${handToBid.second}")
            handToBid.second * (index + 1)
        }.sum()
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
