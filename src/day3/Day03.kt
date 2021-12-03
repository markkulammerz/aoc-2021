package day3

import readInput
import runFuncWithMeasurement
import toDecimalValue

fun main() {
    fun reduceMultipleListBits(list1: List<Int>, list2: List<Int>): List<Int> {
        return list1.zip(list2).map { it.first + it.second }
    }

    fun part1(sensorData: List<String>): Int {
        val sensorBits = sensorData.map { sensorString -> sensorString.toList().map { it.toString().toInt() } }
        val addedSensorBits = sensorBits.reduce(::reduceMultipleListBits).map { it.toDouble() / sensorData.size }

        val gammaRate = addedSensorBits.map { if (it >= 0.5) 1 else 0 }.toDecimalValue()
        val epsilonRate = addedSensorBits.map { if (it < 0.5) 1 else 0 }.toDecimalValue()

        return gammaRate * epsilonRate
    }

    fun part2(sensorData: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description:
    val sensorTestData: List<String> = readInput("day3/input-day3_test")
    check(part1(sensorTestData) == 198)
    // check(part2(testMovements) == 900)


    // print results for 'real' data input:
    val sensorData: List<String> = readInput("day3/input-day3")
    runFuncWithMeasurement(
        listOf(
            Pair("step1", { part1(sensorData) }),
            Pair("step2", { part2(sensorData) }),
        )
    )
}
