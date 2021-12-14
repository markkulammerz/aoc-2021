package day10

import readInput
import runFuncWithMeasurement

fun part1(input: List<String>): Int {
    val braceTypes = input.map { matchBraces(it) }
    println(braceTypes)
    val corrupt = braceTypes.filter { it.lineType == LineType.CORRUPT }
    println(corrupt)
    val sumOf = corrupt.sumOf { it.braceType?.points ?: 0 }
    println(sumOf)
    return sumOf
}

fun matchBraces(input: String): Line {
    val openBraces = listOf('(', '[', '{', '<')
    val closeBraces = listOf(')', ']', '}', '>')

    val openB = ArrayDeque<Brace>(input.length)
    val closingB = ArrayDeque<Brace>(input.length)

    input.forEachIndexed { index, c ->
        when {
            openBraces.contains(c) -> openB.add(Brace(c, index))
            closeBraces.contains(c) -> closingB.add(Brace(c, index))
            else -> println("unknown: $index '$c'")
        }
    }

    if (openB.isEmpty()) {
        throw Exception("no open braces found")
    }

    if (closingB.isEmpty()) {
        throw Exception("no closed braces found")
    }

    var isLineIncomplete = false
    var isLineCorrupt = false
    var braceType: BraceType? = null

    while (openB.isNotEmpty()) {
        val currentOpenBrace = openB.removeLast()
        val closingBracesRight = closingB.filter { it.index > currentOpenBrace.index }
        val matchingClosingBrace =
            closingBracesRight.filter { closeBraces.indexOf(it.brace) == openBraces.indexOf(currentOpenBrace.brace) }
        val nearestMatchingClosingBrace = matchingClosingBrace.minByOrNull { it.index - currentOpenBrace.index }
        val nearestClosingBrace = closingBracesRight.minByOrNull { it.index - currentOpenBrace.index }

        if (closingBracesRight.isEmpty()) {
            isLineIncomplete = true
            continue
        }

        if (nearestMatchingClosingBrace != null) {
            closingB.remove(nearestClosingBrace)
        } else if (nearestClosingBrace != null) {
            isLineCorrupt = true
            braceType = nearestClosingBrace.toBraceType()
        } else {
            println("somethings wrong: input: '$input' openB: '$openB' closingB: '$closingB' currentOpenBrace: '$currentOpenBrace' ")
        }
        //
        if (input == "[<(<(<(<{}))><([]([]()") {
            println("input: '$input' currentOpenBrace: '$currentOpenBrace' ")

        }
    }
    if (input == "[<(<(<(<{}))><([]([]()") {
        println("input: '$input' openB: '$openB' closingB: '$closingB' isLineIncomplete: $isLineIncomplete isLineCorrupt: $isLineCorrupt braceType: $braceType")
    }

    return Line(when {
        isLineCorrupt -> LineType.CORRUPT
        isLineIncomplete -> LineType.INCOMPLETE
        else -> LineType.OK
    }, braceType)
}

fun part2(input: List<String>): Int {

    return 0
}

fun main() { // test if implementation meets criteria from the description:
    val inputTest = readInput("day10/input-day10_test")

    check(part1(inputTest) == 26397) //    check(part2(input_test) == 1134)

    // print results for 'real' data input:
    val input = readInput("day10/input-day10")

    runFuncWithMeasurement(listOf(Pair("step1", { part1(input) }), Pair("step2", { part2(input) })))
}

data class Line(val lineType: LineType, val braceType: BraceType?)

data class Brace(val brace: Char, val index: Int) {
    fun toBraceType(): BraceType {
        return when (brace) {
            '(' -> BraceType.BRACE
            '[' -> BraceType.SQUARE_BRACE
            '{' -> BraceType.CURLY_BRACE
            '<' -> BraceType.ANGLE_BRACE
            ')' -> BraceType.BRACE
            ']' -> BraceType.SQUARE_BRACE
            '}' -> BraceType.CURLY_BRACE
            '>' -> BraceType.ANGLE_BRACE
            else -> throw Exception("illegal brace: '$brace' at pos: '$index'")
        }
    }
}

enum class LineType() {
    CORRUPT, INCOMPLETE, OK
}

enum class BraceType(val points: Int) {
    BRACE(3), SQUARE_BRACE(57), CURLY_BRACE(1197), ANGLE_BRACE(25137)
}