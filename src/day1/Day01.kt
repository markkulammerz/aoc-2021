package day1

import readInput
import runFuncWithMeasurment
import java.util.concurrent.ThreadLocalRandom

fun main() {
    fun part1(depthData: List<Int>): Int {
        var measurementIncreases: Int = 0
        for (index in depthData.indices) {
            if (index >= 1) {
                val depthDifference = depthData[index] - depthData[index - 1]
                if (depthDifference > 0) {
                    measurementIncreases = measurementIncreases.inc()
                }
            }
        }
        return measurementIncreases
    }

    fun part2Normal(depthData: List<Int>): Int {
        fun calculateMeasurementSlidingWindow(startIndex: Int, depthData: List<Int>): Int {
            return depthData[startIndex] + depthData[startIndex + 1] + depthData[startIndex + 2]
        }

        var measurementIncreases: Int = 0
        for (index in depthData.indices) {
            if (index + 3 <= depthData.size - 1) {
                val firstSlidingWindowValue: Int = calculateMeasurementSlidingWindow(index, depthData)
                val secondSlidingWindowValue: Int = calculateMeasurementSlidingWindow(index + 1, depthData)
                val depthDifference: Int = secondSlidingWindowValue - firstSlidingWindowValue

                if (depthDifference > 0) {
                    measurementIncreases = measurementIncreases.inc()
                }
            }
        }
        return measurementIncreases
    }

    fun part2Short(depthData: List<Int>): Int {
        return depthData.asSequence().windowed(3).map { it.sum() }.windowed(2).count { it[1] > it[0] }
    }

    fun part2Speedy(depthData: List<Int>): Int {
        var measurementIncreases: Int = 0
        for (index in depthData.indices) {
            if (index + 3 >= depthData.size) {
                break
            }
            if (depthData[index] < depthData[index + 3]) {
                measurementIncreases++
            }
        }
        return measurementIncreases
    }

    // test if implementation meets criteria from the description:
    val testDepthData: List<Int> = readInput("day1/input-day1_test").map(String::toInt)
    check(part1(testDepthData) == 7)
    check(part2Normal(testDepthData) == 5)
    check(part2Short(testDepthData) == 5)
    check(part2Speedy(testDepthData) == 5)

    // print results for 'real' data input:
    val realDepthData: List<Int> = readInput("day1/input-day1").map(String::toInt)
    runFuncWithMeasurment(
        listOf(
            Pair("step1_normal", { part1(realDepthData) }),
            Pair("step2_normal", { part2Normal(realDepthData) }),
            Pair("step2_short", { part2Short(realDepthData) }),
            Pair("step2_speedy", { part2Speedy(realDepthData) })
        )
    )

    println("-----------------------------------------------")
    println("---------- generating random terrain ----------")
    var lastDepth = 173
    val depths = mutableListOf<Int>()
    for (index in 0..100_000_000) {
        lastDepth += ThreadLocalRandom.current().nextInt(-15, 20)
        depths.add(lastDepth)
    }
    println("-----------------------------------------------")
    runFuncWithMeasurment(
        listOf(
            Pair("step1_normal", { part1(depths) }),
            Pair("step2_normal", { part2Normal(depths) }),
            Pair("step2_short", { part2Short(depths) }),
            Pair("step2_speedy", { part2Speedy(depths) })
        )
    )
}
