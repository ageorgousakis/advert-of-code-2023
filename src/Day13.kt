import kotlin.math.min

fun main() {
    val day = "13"
    val testPart1Result = 405
    val testPart2Result = 400
    val part1Result = 33975
    val part2Result = 0
    val showTestResult = true

    fun scan(grid: List<String>): Int? {
        return generateSequence(1 to emptyList<Int>()) { (start, list) ->
            if (start == -1) null else {
                val newList = if (grid[start] == grid[start - 1]) list + start else list
                val newStart = if (start == grid.lastIndex) -1 else start + 1
                newStart to newList
            }
        }.last().second.firstOrNull { foldIndexPlusOne ->
            val offset = min(grid.size - foldIndexPlusOne, foldIndexPlusOne) - 1
            (1..offset).all { shift ->
                val rightIndex = foldIndexPlusOne + shift
                val leftIndex = foldIndexPlusOne - 1 - shift
                grid[leftIndex] == grid[rightIndex]
            }
        }
    }

    fun strDiff(a: String, b: String) =
        a.zip(b).count { it.first != it.second }

    fun scanDefect(grid: List<String>): Int? {
        return generateSequence(1 to emptyList<Int>()) { (start, list) ->
            if (start == -1) null else {
                val newList = if (strDiff(grid[start], grid[start - 1]) <= 1) list.plusElement(start) else list
                val newStart = if (start == grid.lastIndex) -1 else start + 1
                newStart to newList
            }
        }.last().second.firstOrNull { foldIndexPlusOne ->
            val offset = min(grid.size - foldIndexPlusOne, foldIndexPlusOne) - 1
            (0..offset).sumOf { shift ->
                val rightIndex = foldIndexPlusOne + shift
                val leftIndex = foldIndexPlusOne - 1 - shift
                strDiff(grid[leftIndex], grid[rightIndex])
            } == 1
        }
    }


//    fun List<String>.findReflection(eq: List<String>.(List<String>) -> Boolean): Int {
//        for (i in 1 until lastIndex) {
//            if (subList(0, i).asReversed().eq(subList(i, size))) return i
//        }
//        return 0
//    }
//
//    fun zipEquals(first: List<String>, second: List<String>): Boolean {
//        val n = minOf(first.size, second.size)
//        return first.subList(0, n) == second.subList(0, n)
//    }
//
//    fun zipAlmostEqual(first: List<String>, second: List<String>): Boolean {
//        var almostEqual = false
//        for (i in 0..minOf(first.lastIndex, second.lastIndex)) {
//            val a = first[i]
//            val b = second[i]
//            val delta = (0..maxOf(a.lastIndex, b.lastIndex)).count { a.getOrNull(it) != b.getOrNull(it) }
//            if (delta > 1) return false
//            if (delta == 1) if (almostEqual) return false else almostEqual = true
//        }
//        return almostEqual
//    }

    fun List<String>.transpose() =
        first().indices.map { x ->
            buildString {
                this@transpose.map {
                    append(it[x])
                }
            }
        }

    fun parseInput(input: List<String>) = input.fold(mutableListOf(mutableListOf<String>())) { acc, line ->
        if (line.isBlank())
            acc.add(mutableListOf())
        else
            acc.last().add(line)
        acc
    }

    fun part1(input: List<String>) = parseInput(input).sumOf { puzzle ->
//        println(puzzle)
//        println("*******************")
//        println(puzzle.rotateRight())
//        100 * puzzle.findReflection(::zipEquals) + puzzle.transpose().findReflection(::zipEquals)
        100 * (scan(puzzle) ?: 0) + (scan(puzzle.transpose()) ?: 0)
    }

    fun part2(input: List<String>) = parseInput(input).sumOf { puzzle ->
//        println(puzzle)
//        println("*******************")
//        println(puzzle.rotateRight())
//        100 * puzzle.findReflection(::zipEquals) + puzzle.transpose().findReflection(::zipEquals)
        100 * (scanDefect(puzzle) ?: 0) + (scanDefect(puzzle.transpose()) ?: 0)
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

    val input = readInput("Day${day}")
    part1(input).also {
        it.println()
        check(it == part1Result) { "$it <> $part1Result" }
    }
    part2(input).also {
        it.println()
        check(it == part2Result)  { "$it <> $part2Result" }
    }
}
