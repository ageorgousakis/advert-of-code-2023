import java.util.*

const val NorthSouth = '|'
const val EastWest = '-'
const val NorthEast = 'L'
const val NorthWest = 'J'
const val SouthWest = '7'
const val SouthEast = 'F'
const val Start = 'S'

data class Position(val y: Int, val x: Int)

infix fun Int.to(other: Int): Position = Position(this, other)

data class Maze(val blocks: List<List<Char>>) {
    private val mazSize = blocks.size

    fun maxPath(): Int {
        val startY = blocks.indexOfFirst { it.contains(Start) }
        val startX = blocks[startY].indexOfFirst { it == Start }
        val queue = PriorityQueue<IndexedValue<Position>>(compareBy { it.index })
        val start = Position(startY, startX)
        queue.add(IndexedValue(0, start))
        var maxPath = -1
        buildSet {
            while (!queue.isEmpty()) {
                val (d, position) = queue.remove()
                if (!add(position)) continue
                val (y, x) = position
                when (blocks[y][x]) {
                    NorthSouth -> {
                        if (y > 0) queue.add(IndexedValue(d + 1, y - 1 to x))
                        if (y + 1 < mazSize) queue.add(IndexedValue(d + 1, y + 1 to x))
                    }

                    EastWest -> {
                        if (x > 0) queue.add(IndexedValue(d + 1, y to x - 1))
                        if (x + 1 < mazSize) queue.add(IndexedValue(d + 1, y to x + 1))
                    }

                    NorthEast -> {
                        if (y > 0) queue.add(IndexedValue(d + 1, y - 1 to x))
                        if (x + 1 < mazSize) queue.add(IndexedValue(d + 1, y to x + 1))
                    }

                    NorthWest -> {
                        if (y > 0) queue.add(IndexedValue(d + 1, y - 1 to x))
                        if (x > 0) queue.add(IndexedValue(d + 1, y to x - 1))
                    }

                    SouthWest -> {
                        if (x > 0) queue.add(IndexedValue(d + 1, y to x - 1))
                        if (y + 1 < mazSize) queue.add(IndexedValue(d + 1, y + 1 to x))
                    }

                    SouthEast -> {
                        if (x + 1 < mazSize) queue.add(IndexedValue(d + 1, y to x + 1))
                        if (y + 1 < mazSize) queue.add(IndexedValue(d + 1, y + 1 to x))
                    }

                    Start -> {
                        if (y > 0 && blocks[y - 1][x] in "${NorthSouth}${SouthWest}${SouthEast}")
                            queue.add(IndexedValue(d + 1, y - 1 to x))
                        if (x > 0 && blocks[y][x - 1] in "${EastWest}${NorthEast}${SouthEast}")
                            queue.add(IndexedValue(d + 1, y to x - 1))
                        if (y < mazSize && blocks[y + 1][x] in "${NorthSouth}${NorthEast}${NorthWest}")
                            queue.add(IndexedValue(d + 1, y + 1 to x))
                        if (x < mazSize && blocks[y][x + 1] in "${EastWest}${NorthWest}${SouthWest}")
                            queue.add(IndexedValue(d + 1, y to x + 1))
                    }

                    else -> continue
                }
                maxPath = d
            }
        }
        return maxPath
    }

}

fun main() {
    val day = "10"
    val testPart1Result = 8
    val testPart2Result = 0
    val part1Result = 6927
    val part2Result = 0
    val showTestResult = true

    fun parseInput(input: List<String>): Maze {
        val blocks = input.map { line ->
            line.toList()
        }
        return Maze(blocks)
    }

    fun part1(input: List<String>): Int {
        val maze = parseInput(input)
        return maze.maxPath()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    part1(testInput).also {
        if (showTestResult) it.println()
//        check(it == testPart1Result) { "$it <> $testPart1Result" }
    }
//    part2(testInput).also {
//        if (showTestResult) it.println()
//        check(it == testPart2Result)  { "$it <> $testPart2Result" }
//    }
//
    val input = readInput("Day$day")
    part1(input).also {
        it.println()
        check(it == part1Result) { "$it <> $part1Result" }
    }
//    part2(input).also {
//        it.println()
//        check(it == part2Result)  { "$it <> $part2Result" }
//    }
}