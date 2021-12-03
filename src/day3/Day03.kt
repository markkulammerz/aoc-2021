package day3

import readInput
import runFuncWithMeasurement
import toDecimalValue
import java.util.*

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

    fun part1sk(sensorData: List<String>): Int {
        val referenceValue = sensorData.first()
        // assume all have the same length
        val bits = referenceValue.map { BitSet(sensorData.size) }
        for (i in sensorData.indices) {
            val dataLine = sensorData[i]
            for (j in dataLine.indices) {
                val bit = dataLine[j]
                bits[j][i] = bit == '1'
            }
        }

        val gamma = BitSet(referenceValue.length)
        val epsilon = BitSet(referenceValue.length)
        for (i in bits.indices) {
            val bitset = bits[bits.size - 1 - i]
            val trueBits = bitset.cardinality()
            val falseBits = sensorData.size - bitset.cardinality()
            gamma[i] = trueBits > falseBits
            epsilon[i] = falseBits > trueBits
        }

        return (gamma.toLongArray()[0] * epsilon.toLongArray()[0]).toInt()
    }

    fun part2(sensorData: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description:
    val sensorTestData: List<String> = readInput("day3/input-day3_test")
    check(part1(sensorTestData) == 198)
    check(part1sk(sensorTestData) == 198)
    // check(part2(testMovements) == 900)


    // print results for 'real' data input:
    val sensorData: List<String> = readInput("day3/input-day3-simon")
    runFuncWithMeasurement(
        listOf(
            Pair("step1", { part1(sensorData) }),
            Pair("step1sk", { part1sk(sensorData) }),
            Pair("step2", { part2(sensorData) }),
        )
    )
}
