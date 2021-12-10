package day7

import readInput
import runFuncWithMeasurement
import triangularNumber
import kotlin.math.absoluteValue

fun fuelConsumption(positions: List<Int>, constantConsumptionRate: Boolean): Int {
    val minima = positions.minOf { it }
    val maxima = positions.maxOf { it }
    val fullRange = minima..maxima

    return fullRange.minOf { alignPosition ->
        positions
            .sumOf { position ->
                val relocations = (alignPosition - position).absoluteValue
                if (constantConsumptionRate) relocations else relocations.triangularNumber()
            }
    }
}

fun part1(positions: List<Int>): Int {
    return fuelConsumption(positions, true)
}

fun part2(positions: List<Int>): Int {
    return fuelConsumption(positions, false)
}

fun main() {
    // test if implementation meets criteria from the description:
    val populationStatisticsTest: List<Int> = readInput("day7/input-day7_test")[0].split(",").map { it.toInt() }
    check(part1(populationStatisticsTest) == 37)
    check(part2(populationStatisticsTest) == 168)

    // print results for 'real' data input:
    val populationStatistics: List<Int> = readInput("day7/input-day7-simon")[0].split(",").map { it.toInt() }
    runFuncWithMeasurement(
        listOf(
            Pair("step1", { part1(populationStatistics) }),
            Pair("step2", { part2(populationStatistics) })
        )
    )
}

