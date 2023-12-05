
fun main() {
    val day = "03"
    val testPart1Result = 4361
    val testPart2Result = 467835
    val part1Result = 530495
    val part2Result = 80253814
    val showTestResult = false
        
    fun part1(input: List<String>): Int =
        input.toEngine()
            .findPartNumbers()
            .sumOf { it.value }

    fun part2(input: List<String>): Int =
        input.toEngine()
            .flatten()
            .findGearParts()
            .sumOf { it.partNumber1 * it.partNumber2 }
    

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

sealed class Element
data class Number(val value: Int, val row: Int, val startColumn: Int, val endColumn: Int) : Element() {
    val horizontalBounds = (startColumn - 1)..(endColumn + 1)
    val verticalBounds = (row - 1)..(row + 1)
}
data class Symbol(val value: Char, val column: Int, val row: Int) : Element() {
    val isGear = value == '*'
    
    fun isInNumberBounds(number: Number) =
        row in number.verticalBounds && column in number.horizontalBounds
}

data class Gear(val partNumber1: Int, val partNumber2: Int)

typealias Elements = List<Element>
typealias Engine = List<Elements>

fun Engine.findPartNumbers() =
    this.windowed(2).flatMap { rows ->
        val symbols = rows.flatten().filterIsInstance<Symbol>()
        rows.flatten().filterIsInstance<Number>()
            .filter { n-> symbols.any { s -> s.isInNumberBounds(n) } }
    }.toSet()

fun Elements.findGearParts(): List<Gear> {
    val parts = this.filterIsInstance<Number>()
    return this.filterIsInstance<Symbol>().filter { it.isGear }.map { s ->
        parts.filter { s.isInNumberBounds(it) }
    }.filter { it.size == 2 }.map {
        Gear(it[0].value, it[1].value)
    }
}

fun List<String>.toEngine(): Engine = mapIndexed { row, line ->
    buildList {
        var numberBuilder = ""
        var numberStartColumn = -1
        "$line.".forEachIndexed { column, c ->
            if (c.isDigit()) {
                numberBuilder += c
                if (numberStartColumn == -1) numberStartColumn = column
            } else {
                if (c != '.') add(Symbol(c, column, row))
                if (numberBuilder.isNotEmpty()) {
                    add(Number(numberBuilder.toInt(), row, numberStartColumn, column - 1))
                    numberBuilder = ""
                    numberStartColumn = -1
                }
            }
        }
    }
}
