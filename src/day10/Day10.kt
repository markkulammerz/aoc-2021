package day10

import readInput
import runFuncWithMeasurement


fun part1(input: List<String>): Int {
    val braceTypes = input.map { findIllegalBrace(it) }
    println(braceTypes)
    return braceTypes.sumOf { it?.points ?: 0 }
}

fun findIllegalBrace(input: String): BraceType? {
    val openBraces = listOf('(', '[', '{', '<')
    val closeBraces = listOf(')', ']', '}', '>')
    val mutableInput = input.toList().toMutableList()

    for (i in input.length - 1 downTo 0) {
        val brace = input[i]
        val braceIndex = openBraces.indexOf(brace)
        if (braceIndex < 0) { // no opening brace
            continue
        }
        var foundMatch = false
        for (j in i until mutableInput.size) {
            val possibleClose = input[j]
            val possibleCloseIndex = closeBraces.indexOf(possibleClose)
            if (possibleCloseIndex < 0) {
                // opening brace
                continue
            } else if (possibleCloseIndex == braceIndex) {
                mutableInput.removeAt(j)
                mutableInput.removeAt(i)
                foundMatch = true
                break
            }
        }
        if (!foundMatch && i + 1 < mutableInput.size) {
            val nextChar = mutableInput[i + 1]
            if (closeBraces.contains(nextChar)) {
                return when (nextChar) {
                    ')' -> BraceType.BRACE
                    ']' -> BraceType.SQUARE_BRACE
                    '}' -> BraceType.CURLY_BRACE
                    '>' -> BraceType.ANGLE_BRACE
                    else -> null
                }
            }

        }
    }
    return null
}

fun part2(input: List<String>): Int {


    return 0
}


fun main() {
    // test if implementation meets criteria from the description:
    val input_test = readInput("day10/input-day10_test")

    check(part1(input_test) == 26397)
//    check(part2(input_test) == 1134)

    // print results for 'real' data input:
    val input = readInput("day10/input-day10")

    runFuncWithMeasurement(
        listOf(
            Pair("step1", { part1(input) }),
            Pair("step2", { part2(input) })
        )
    )
}

enum class BraceType(val points: Int) {
    BRACE(3),
    SQUARE_BRACE(57),
    CURLY_BRACE(1197),
    ANGLE_BRACE(25137)

}