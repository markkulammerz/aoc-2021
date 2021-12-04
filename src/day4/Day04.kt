package day4

import readInput
import runFuncWithMeasurement

fun main() {

    fun part1(sensorData: List<String>): Int {
        return 0
    }

    fun part2(sensorData: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description:
    val sensorTestData: List<String> = readInput("day4/input-day4_test")
    check(part1(sensorTestData) == 6592)
    // check(part2(sensorTestData) == 230)


    // print results for 'real' data input:
    val sensorData: List<String> = readInput("day4/input-day4")
    runFuncWithMeasurement(
        listOf(
            Pair("step1", { part1(sensorData) }),
            Pair("step2", { part2(sensorData) }),
        )
    )
}
