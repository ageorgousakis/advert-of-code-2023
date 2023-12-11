import kotlin.math.abs

//typealias GalaxyPoint = Pair<Long, Long>
//
//val GalaxyPoint.x get() = first
//val GalaxyPoint.y get() = second
data class  GalaxyPoint(val x: Long, val y: Long)

fun main() {
    val day = "11"
    val testPart1Result = 374L
    val testPart2Result = 8410L
    val part1Result = 10313550L
    val part2Result = 611998089572
    val showTestResult = true

    fun GalaxyPoint.distance(other: GalaxyPoint) =
        abs(other.x - x) + abs(other.y - y)

    fun Iterable<GalaxyPoint>.sumOfDistances() =
        flatMap { a -> map { b -> a to b } }
            .sumOf { (a, b) -> a.distance(b) } / 2

    fun parseInput(input: List<String>, magnitude: Long): List<GalaxyPoint> {
        val rowsToExpand = input.indices.filter { y -> '#' !in input[y] }
        val colsToExpand = input[0].indices.filter { x -> input.none { it[x] == '#' } }

        fun makePoint(x: Int, y: Int, magnitude: Long) = GalaxyPoint(
            x.toLong() + colsToExpand.count { x > it } * (magnitude - 1),
            y.toLong() + rowsToExpand.count { y > it } * (magnitude - 1)
        )

        return input.flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, char -> if (char == '#') makePoint(x, y, magnitude) else null }
        }
    }

    fun part1(input: List<String>): Long =
        parseInput(input, 2).sumOfDistances()

    fun part2(input: List<String>, magnitude: Long): Long =
        parseInput(input, magnitude).sumOfDistances()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    part1(testInput).also {
        if (showTestResult) it.println()
        check(it == testPart1Result) { "$it <> $testPart1Result" }
    }
    part2(testInput, 100).also {
        if (showTestResult) it.println()
        check(it == testPart2Result) { "$it <> $testPart2Result" }
    }

    val input = readInput("Day$day")
    part1(input).also {
        it.println()
        check(it == part1Result) { "$it <> $part1Result" }
    }
    part2(input, 1000000).also {
        it.println()
        check(it == part2Result) { "$it <> $part2Result" }
    }
}
