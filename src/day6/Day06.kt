package day6

import readInput
import runFuncWithMeasurement

fun part1(input: List<String>): Long {
    val populationsStatistics = input[0].split(',').map { it.toInt() }
    val population = Population(populationsStatistics)
    for (day in 0..79) {
        population.nextDay()
    }

    return population.populationSize()
}

fun part2(input: List<String>): Long {
    val populationsStatistics = input[0].split(',').map { it.toInt() }
    val population = Population(populationsStatistics)
    for (day in 0..255) {
        population.nextDay()
    }

    return population.populationSize()
}

fun main() {
    // test if implementation meets criteria from the description:
    val populationStatisticsTest: List<String> = readInput("day6/input-day6_test")
    check(part1(populationStatisticsTest) == 5934L)
    check(part2(populationStatisticsTest) == 26_984_457_539)

    // print results for 'real' data input:
    val populationStatistics: List<String> = readInput("day6/input-day6")
    runFuncWithMeasurement(
        listOf(
            Pair("step1", { part1(populationStatistics) }),
            Pair("step2", { part2(populationStatistics) })
        )
    )
}

class Population(val population: List<Int>) {
    val fishies: LongArray = LongArray(9) { index -> population.filter { it == index }.size.toLong() }

    fun nextDay() {
        val birthing = fishies[0]

        for (index in 0..7) {
            fishies[index] = fishies[index + 1]
            if (index == 6) {
                fishies[index] += birthing

            }
        }
        fishies[8] = birthing
    }

    fun populationSize(): Long {
        return fishies.sum()
    }
}
