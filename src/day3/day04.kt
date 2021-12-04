package day3

import readInput
import runFuncWithMeasurement
import toDecimalValue
import toNumberChar
import java.util.*

fun main() {
    fun reduceMultipleBitLists(list1: List<Int>, list2: List<Int>): List<Int> {
        return list1.zip(list2).map { it.first + it.second }
    }

    fun part1(sensorData: List<String>): Int {
        val sensorBits = sensorData.map { sensorString -> sensorString.toList().map { it.toString().toInt() } }
        val addedSensorBits = sensorBits.reduce(::reduceMultipleBitLists).map { it.toDouble() / sensorData.size }

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

    fun oxygenCriteria(index: Int, input: List<String>): Boolean {
        var trueBits = 0
        var falseBits = 0
        for (line in input) {
            if (line[index] == '1') {
                trueBits++
            } else {
                falseBits++
            }
        }
        return trueBits >= falseBits
    }

    fun co2Criteria(index: Int, input: List<String>): Boolean {
        var trueBits = 0
        var falseBits = 0
        for (line in input) {
            if (line[index] == '1') {
                trueBits++
            } else {
                falseBits++
            }
        }
        return trueBits < falseBits
    }

    fun filterInput(criteria: Boolean, index: Int, input: List<String>): List<String> {
        val criteriaChar = criteria.toNumberChar()
        return input.filter { it[index] == criteriaChar }
    }

    fun calcRating(sensorData: List<String>, criteriaMethod: (Int, List<String>) -> Boolean): Int {
        var index: Int = 0;
        var data = sensorData
        val width = data[0].length
        while (index < width) {
            val criteriaBit = criteriaMethod(index, data)
            data = filterInput(criteriaBit, index, data)
            index += 1
            if (data.size == 1) {
                break;
            }
        }

        return data.first().toInt(2)
    }

    fun part2(sensorData: List<String>): Int {
        val oxygenRating = calcRating(sensorData, ::oxygenCriteria)
        val co2Rating = calcRating(sensorData, ::co2Criteria)

        return oxygenRating * co2Rating
    }

    // test if implementation meets criteria from the description:
    val sensorTestData: List<String> = readInput("day3/input-day3_test")
    check(part1(sensorTestData) == 198)
    check(part1sk(sensorTestData) == 198)
    check(part2(sensorTestData) == 230)


    // print results for 'real' data input:
    val sensorData: List<String> = readInput("day3/input-day3")
    runFuncWithMeasurement(
        listOf(
            Pair("step1", { part1(sensorData) }),
            Pair("step1sk", { part1sk(sensorData) }),
            Pair("step2", { part2(sensorData) }),
        )
    )
}
