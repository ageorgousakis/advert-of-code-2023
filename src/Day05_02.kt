import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlin.math.min

fun main() {
    val day = "05"
    val testPart1Result: Long = 35
    val testPart2Result: Long = 46
    val part1Result: Long = 57075758
    val part2Result: Long = 31161857
    val showTestResult = false

    fun parseMappers(input: List<String>): Map<String, Map<LongRange, LongRange>> {
        return input.fold(mutableListOf(mutableListOf<String>())) { acc, line ->
            if (line.isBlank())
                acc.add(mutableListOf())
            else
                acc.last().add(line)
            acc
        }.associate {
            val mappingName = it.first().substringBefore(' ')
            val mapping = it.drop(1).map { line ->
                val rangeSpec = line.split(" ").map { it.toLong() }
                check(rangeSpec.size == 3) { "Expected 3 numbers bit got ${rangeSpec.size}" }
                (rangeSpec[1]..<rangeSpec[1] + rangeSpec[2]) to (rangeSpec[0]..<rangeSpec[0] + rangeSpec[2])
            }.sortedBy { (src) -> src.first }.toMap()
            mappingName to mapping
        }
    }

    fun part1(input: List<String>): Long {
        val seeds = input[0].substringAfter(": ").split(" ").map { it.toLong() }
        input.drop(2)
//        println("Seeds: $seeds")
        val mappers = parseMappers(input)
        return seeds.map { seed ->
            var pos = seed
            mappers.values.forEach { map ->
                pos = map.entries.firstOrNull { pos in it.key }?.let { (src, dest) ->
                    dest.first + (pos - src.first)
                } ?: pos
            }
//            println("$seed to $pos")
            pos
        }.min()
    }

    fun part2(input: List<String>): Long {
        val seedRanges = input[0].substringAfter(": ")
            .split(" ")
            .map { it.toLong() }.chunked(2).map {
                LongRange(it[0], it[0] + it[1])
            }
        input.drop(2)
//        println("Seeds: $seeds")
        val maps = parseMappers(input)

        return runBlocking(Dispatchers.Default) {
            val seedRangesMin = seedRanges.map {
                async {
                    var min: Long = Long.MAX_VALUE
                    for (seed in it) {
                        var pos = seed
                        maps.values.forEach { map ->
                            pos = map.entries.firstOrNull { pos in it.key }?.let { (src, dest) ->
                                dest.first + (pos - src.first)
                            } ?: pos
                        }
                        //            println("$seed to $pos")
                        min = min(pos, min)
                    }
                    min
                }
            }.awaitAll()
            seedRangesMin.min()
        }
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
        check(it == part1Result)  { "$it <> $part1Result" }
    }
    part2(input).also {
        it.println()
        check(it == part2Result)  { "$it <> $part2Result" }
    }
}


