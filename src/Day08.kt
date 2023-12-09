import java.math.BigInteger

typealias Node = Pair<String, String>
typealias Tree = Map<String, Node>

data class Network(val directions: List<Char>, val nodes: Tree)
fun main() {
    val day = "08"
    val testPart1Result = 2
    val testPart1Result2 = 6
    val testPart2Result: BigInteger = BigInteger.valueOf(6)
    val part1Result = 16043
    val part2Result: BigInteger = BigInteger.ZERO
    val showTestResult = true


    val lineRegex = """(\w{3}) = \((\w{3}), (\w{3})\)""".toRegex()

    fun parseInput(input: List<String>): Network {
        val directions = input[0].toList()
        val nodes = mutableMapOf<String, Pair<String, String>>()

        input.drop(2).map { line ->
            val (startNode, leftNode, rightNode) = lineRegex.matchEntire(line)?.destructured ?: error("Invalid line $line input!")
            nodes[startNode] = leftNode to rightNode
        }
        return Network(directions, nodes)
    }

    fun stepsToEnd(start: String, directions: List<Char>, nodes: Tree): Int {
        var currentNode = start
        var steps = 0
        while (true) {
            for(direction in directions) {
                currentNode = if (direction == 'L')
                    nodes[currentNode]!!.first
                else
                    nodes[currentNode]!!.second
                steps++
                if (currentNode.endsWith("Z"))
                    return steps
            }
        }
    }

    fun part1(input: List<String>): Int {
        val network = parseInput(input)
        var currentNode = "AAA"
        var directionIndex = 0
        var steps = 0
        while (currentNode != "ZZZ") {
            val direction = network.directions[directionIndex]
            currentNode = if (direction == 'L')
                network.nodes[currentNode]!!.first
            else
                network.nodes[currentNode]!!.second
            steps++
            directionIndex = if (directionIndex + 1 >= network.directions.size) 0 else directionIndex + 1
        }
        return steps
    }

    fun part2(input: List<String>): BigInteger {
        val network = parseInput(input)
        var currentNodes = network.nodes.keys.filter { it.endsWith("A") }
        val stepsToEnd = currentNodes.map {
            stepsToEnd(it, network.directions, network.nodes)
        }
        val lcm = stepsToEnd.map { BigInteger.valueOf(it.toLong()) }.reduce { acc, i ->
            acc * i / acc.gcd(i)
        }
        return lcm
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    part1(testInput).also {
        if (showTestResult) it.println()
        check(it == testPart1Result) { "$it <> $testPart1Result" }
    }
    val testInput2 = readInput("Day${day}_test2")
    part1(testInput2).also {
        if (showTestResult) it.println()
        check(it == testPart1Result2) { "$it <> $testPart1Result2" }
    }
    val testInput3 = readInput("Day${day}_test3")
    part2(testInput3).also {
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
