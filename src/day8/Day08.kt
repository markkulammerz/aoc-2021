package day8

import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import readInput
import runFuncWithMeasurement

fun part1(positions: List<Int>): Int {
    return 0
}

fun part2(positions: List<Int>): Int {
    return 0
}

fun main() {
    // test if implementation meets criteria from the description:
    val populationStatisticsTest: List<Int> = readInput("day8/input-day8_test")[0].split(",").map { it.toInt() }
    check(part1(populationStatisticsTest) == 37)
    // check(part2(populationStatisticsTest) == 168)

    // print results for 'real' data input:
    val populationStatistics: List<Int> = readInput("day8/input-day8")[0].split(",").map { it.toInt() }
    runFuncWithMeasurement(
        listOf(
            Pair("step1", { part1(populationStatistics) }),
            Pair("step2", { part2(populationStatistics) })
        )
    )
}
