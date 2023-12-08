
@JvmInline
value class Mapper(private val mappings: List<Pair<LongRange, LongRange>>) {
    operator fun get(n: Long) = mappings.firstOrNull { (src) -> n in src }
        ?.let { (src, dst) -> n - src.first + dst.first }
        ?: n

    operator fun get(r: LongRange) = sequence {
        var unmapped = r
        for ((src, dst) in mappings) {
            if (unmapped.first > src.last) continue
            // outside of range
            if (unmapped.last < src.first) {
                yield(unmapped)
                return@sequence
            }
            if (unmapped.first < src.first) {
                yield(unmapped.first..<src.first)
                unmapped = src.first..unmapped.last
            }

            // ends in range (and starts in range from above)
            if(unmapped.last in src) {
                val first = unmapped.first - src.first + dst.first
                val last = unmapped.last - src.first + dst.first
                yield(first..last)
                return@sequence
            }
            // starts in range (doesn't end in range)
            if (unmapped.first in src) {
                val first = unmapped.first - src.first + dst.first
                val last = dst.last
                yield(first..last)
                unmapped = src.last + 1..unmapped.last
            }
        }
        if(!unmapped.isEmpty())yield(unmapped)
    }
}

fun main() {
    val day = "05"
    val testPart1Result: Long = 35
    val testPart2Result: Long = 46
    val part1Result: Long = 57075758
    val part2Result: Long = 31161857
    val showTestResult = true

    fun parseMappers(input: List<String>): List<Pair<String, Mapper>> {
        return input.fold(mutableListOf(mutableListOf<String>())) { acc, line ->
            if (line.isBlank())
                acc.add(mutableListOf())
            else
                acc.last().add(line)
            acc
        }.map {
            val mappingName = it.first().substringBefore(' ')
            val mapper = Mapper(it.drop(1).map {
                val (dst, src, len) = it.split(' ').map(String::toLong)
                (src..<src + len) to (dst..<dst + len)
            }.sortedBy { (src) -> src.first })
            mappingName to mapper
        }
    }

    fun part1(input: List<String>): Long {
        val seeds = input[0].substringAfter(": ").split(" ").map { it.toLong() }
        input.drop(2)
//        println("Seeds: $seeds")
        val mappers = parseMappers(input)
        return seeds.minOf {
            mappers.fold(it) { acc, (_, mapper) -> mapper[acc] }
        }
    }

    fun part2(input: List<String>): Long {
        val seedRanges = input[0].substringAfter(": ")
            .split(" ")
            .map { it.toLong() }.chunked(2).map {
                LongRange(it[0], it[0] + it[1])
            }
        input.drop(2)
//        println("Seeds: $seeds")
        val mappers = parseMappers(input)
        return mappers.fold(seedRanges) { acc, (_, mapper) ->
            acc.flatMap { mapper[it] }
        }.minOf { it.first }
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


