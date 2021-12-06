package day6

import readInput
import runFuncWithMeasurement

fun part1(input: List<String>): Int {
    val populationsStatistics = input[0].split(',').map { it.toInt() }
    val population: ArrayDeque<Lanternfish> = ArrayDeque()

    for(initialTimeToReproduce in populationsStatistics){
        val newFisch = Lanternfish(initialTimeToReproduce)
        population.add(newFisch)
    }

    for(day in 0..79){
        population.forEach {
            if(it.oneDayPassed() == Birth.BIRTH){
                population.add(Lanternfish())
            }
        }
    }

    return population.size
}

fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    // test if implementation meets criteria from the description:
    val populationStatisticsTest: List<String> = readInput("day6/input-day6_test")
    check(part1(populationStatisticsTest) == 5934)
    // check(part2(populationStatisticsTest) == 26984457539)

    // print results for 'real' data input:
    val populationStatistics: List<String> = readInput("day6/input-day6")
    runFuncWithMeasurement(
        listOf(
            Pair("step1", { part1(populationStatistics) }),
            Pair("step2", { part2(populationStatistics) })
        )
    )
}

class Lanternfish(var timeToReproduce: Int = 9){
    fun oneDayPassed(): Birth{
        when{
            timeToReproduce >= 1 -> {
                this.timeToReproduce -= 1
                return Birth.NOT_READY
            }
            else -> {
                this.timeToReproduce = 6
                return Birth.BIRTH
            }
        }
    }

    override fun toString(): String {
        return "$timeToReproduce"
    }

}

enum class Birth {
    BIRTH, NOT_READY
}