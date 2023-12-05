fun main() {
    val day = "02"
    val testPart1Result = 8
    val testPart2Result = 2286
    val part1Result = 2810
    val part2Result = 69110
    val showTestResult = false

    val possibleRedCubes = 12
    val possibleGreenCubes = 13
    val possibleBlueCubes = 14

    data class CubeSet(val red: Int, val green: Int, val blue: Int) {
        val isPossible = red <= possibleRedCubes && green <= possibleGreenCubes && blue <= possibleBlueCubes
    }

    data class Game(val number: Int, val cubeSets: List<CubeSet>) {
        val isPossible: Boolean = cubeSets.all { it.isPossible }
        val power = cubeSets.maxOf { it.red } * cubeSets.maxOf { it.green } * cubeSets.maxOf { it.blue }
    }

    val setRegEx = "(\\d+) (\\w+)".toRegex()

    fun String.toCubeSet(): CubeSet {
        var red = 0
        var green = 0
        var blue = 0
        split(",").forEach {
            val cubes = setRegEx.matchEntire(it.trim())!!.groupValues[1].toInt()
            val color = setRegEx.matchEntire(it.trim())!!.groupValues[2]
            when (color) {
                "red" -> red = cubes
                "green" -> green = cubes
                "blue" -> blue = cubes
            }
        }
        return CubeSet(red, green, blue)
    }

    fun String.toGame(): Game {
        val number = substringBefore(":").removePrefix("Game").trim().toInt()
        val cubeSets = substringAfter(": ").split(";").map { it.toCubeSet() }
        return Game(number, cubeSets)
    }

    fun part1(input: List<String>): Int =
        input.map { it.toGame() }.filter { it.isPossible }.sumOf { it.number }


    fun part2(input: List<String>): Int =
        input.sumOf { it.toGame().power }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    part1(testInput).also {
        if (showTestResult) it.println()
        check(it == testPart1Result)
    }
    part2(testInput).also {
        if (showTestResult) it.println()
        check(it == testPart2Result)
    }
    
    val input = readInput("Day$day")
    part1(input).also {
        it.println()
        check(it == part1Result)
    }
    part2(input).also {
        it.println()
        check(it == part2Result)
    }
}
