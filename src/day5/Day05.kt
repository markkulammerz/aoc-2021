package day5

import readInput
import runFuncWithMeasurement

fun part1(bingoDate: List<String>): Int {
    return 0
}

fun part2(bingoDate: List<String>): Int {
    return 0
}

fun main() {
    // test if implementation meets criteria from the description:
    val bingoDataTest: List<String> = readInput("day5/input-day5_test")
    check(part1(bingoDataTest) == 5)
    // check(part2(bingoDataTest) == 1924)

    // print results for 'real' data input:
    val bingoData: List<String> = readInput("day5/input-day5")
    runFuncWithMeasurement(
        listOf(
            Pair("step1", { part1(bingoData) }),
            Pair("step2", { part2(bingoData) })
        )
    )
}
