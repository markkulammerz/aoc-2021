package day5

import readInput
import runFuncWithMeasurement

fun convertStringToPairs(input: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    val splitted = input.split(" -> ")
    val startSplitted = splitted[0].split(",")
    val endSplitted = splitted[1].split(",")

    val start = Pair(startSplitted[0].toInt(), startSplitted[1].toInt())
    val end = Pair(endSplitted[0].toInt(), endSplitted[1].toInt())

    return Pair(start, end);
}

fun part1(pipelinePositionData: List<String>): Int {
    val map = Map()

    for (line in pipelinePositionData) {
        val (start, end) = convertStringToPairs(line)
        map.addNewPipeline(start, end)
    }

    return map.getDangerousAreasCount(2)
}

fun part2(pipelinePositionData: List<String>): Int {
    return 0
}

fun main() {
    // test if implementation meets criteria from the description:
    val pipelinePositionDataTest: List<String> = readInput("day5/input-day5_test")
    check(part1(pipelinePositionDataTest) == 5)
    // check(part2(pipelinePositionDataTest) == 1924)

    // print results for 'real' data input:
    val pipelinePositionData: List<String> = readInput("day5/input-day5")
    runFuncWithMeasurement(
        listOf(
            Pair("step1", { part1(pipelinePositionData) }),
            Pair("step2", { part2(pipelinePositionData) })
        )
    )
}

class Map(val mapData: Array<Array<Int>> = Array(1000) { Array(1000) { 0 } }) {
    fun addNewPipeline(start: Pair<Int, Int>, end: Pair<Int, Int>) {
        when {
            start.first == end.first -> {
                val fixedX = start.first
                if (start.second <= end.second) {
                    for (y in start.second..end.second) mapData[y][fixedX] += 1
                } else {
                    for (y in end.second..start.second) mapData[y][fixedX] += 1
                }
            }
            start.second == end.second -> {
                val fixedY = start.second
                if (start.first <= end.first) {
                    for (x in start.first..end.first) mapData[fixedY][x] += 1
                } else {
                    for (x in end.first..start.first) mapData[fixedY][x] += 1
                }
            }
            else -> {
                // Do nothing on diagonal lines
            }
        }
    }

    fun getDangerousAreasCount(dangerLimit: Int): Int {
        var counter = 0
        this.mapData.forEach { counter += it.count { it >= dangerLimit } }

        return counter
    }

    override fun toString(): String {
        return mapData.joinToString(separator = "") { it.joinToString(separator = " ", postfix = "\n") }
    }
}
