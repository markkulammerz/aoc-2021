package day1

import readInput
import runFuncWithMeasurement
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.system.measureNanoTime

fun main() {
    fun part1(depthData: IntArray): Int {
        var measurementIncreases = 0
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

    fun part2Normal(depthData: IntArray): Int {
        fun calculateMeasurementSlidingWindow(startIndex: Int, depthData: IntArray): Int {
            return depthData[startIndex] + depthData[startIndex + 1] + depthData[startIndex + 2]
        }

        var measurementIncreases = 0
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

    fun part2Short(depthData: IntArray): Int {
        return depthData.asSequence().windowed(3) { it.sum() }.zipWithNext { i1, i2 -> i2 > i1 }.count { it }
    }

    fun part2Speedy(depthData: IntArray): Int {
        var measurementIncreases = 0
        for (index in depthData.indices) {
            if (index + 3 >= depthData.size) {
                break
            }
            if (depthData[index + 3] > depthData[index]) {
                measurementIncreases++
            }
        }
        return measurementIncreases
    }

    // test if implementation meets criteria from the description:
    val testDepthData = readInput("day1/input-day1_test").map(String::toInt).toIntArray()
    check(part1(testDepthData) == 7)
    check(part2Normal(testDepthData) == 5)
    println(part2Short(testDepthData))
    check(part2Short(testDepthData) == 5)
    check(part2Speedy(testDepthData) == 5)

    // print results for 'real' data input:
    val realDepthData = readInput("day1/input-day1").map(String::toInt).toIntArray()
    runFuncWithMeasurement(
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
    val terrainSize = 2_100_000_000
    val depths = IntArray(terrainSize + 1)
    val sr = SplittableRandom(173)
    val rt = Runtime.getRuntime()
    val miB = 1024 * 1024

    val randomTime = measureNanoTime {
        for (index in 0..terrainSize) {
            lastDepth += sr.nextInt(-15, 20)
            depths[index] = (lastDepth)
        }
    }
    println("terrain creation time: ${Duration.of(randomTime, ChronoUnit.NANOS)}")

    println("***** Heap utilization statistics [MiB] *****\n")
    println("Total Memory: ${rt.totalMemory() / miB}")
    println("Free Memory: ${rt.freeMemory() / miB}")
    println("Used Memory: ${(rt.totalMemory() - rt.freeMemory()) / miB}")
    println("Max Memory: ${rt.maxMemory() / miB}")

    println("-----------------------------------------------")
    runFuncWithMeasurement(
        listOf(
            Pair("step1_normal", { part1(depths) }),
            Pair("step2_normal", { part2Normal(depths) }),
            Pair("step2_speedy", { part2Speedy(depths) }),
            Pair("step2_short", { part2Short(depths) }),
        )
    )
}
