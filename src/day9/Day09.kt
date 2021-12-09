package day9

import org.jetbrains.kotlinx.multik.api.d2arrayIndices
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.*
import org.jetbrains.kotlinx.multik.ndarray.operations.groupNDArrayBy
import readInput
import runFuncWithMeasurement


fun part1(peilplan: D2Array<Int>): Int {
    val minimums = mutableListOf<Int>()
    val peilkarte = peilplan.asDNArray()
    for (index in peilkarte.multiIndices) {
        val (y, x) = index
        val minimum = checkIfLowPoint(y, x, peilkarte);

        if (minimum > peilkarte[index]) {
            minimums.add(peilkarte[index])
        }
    }

    return minimums.sumOf { it + 1 }
}

fun part2(peilplan: D2Array<Int>): Int {
    val minimums = mutableListOf<IntArray>()
    val peilkarte = peilplan.asDNArray()
    for (index in peilkarte.multiIndices) {
        val (y, x) = index
        val minimum = checkIfLowPoint(y, x, peilkarte);

        if (minimum > peilkarte[index]) {
            minimums.add(index.clone())
        }
    }

    var markerValue = -1

    for (mini in minimums) {
        buildBasin(mini, peilkarte, markerValue)
        markerValue--
    }

    val basins = peilkarte.groupNDArrayBy { it }.filter { it.key < 0 }.values.map { it.size }.sorted().takeLast(3)

    return basins.reduce { a, b -> a * b }
}

fun buildBasin(pos: IntArray, plan: NDArray<Int, DN>, marker: Int) {
    val y = pos[0]
    val x = pos[1]
    val ySize = plan.shape[0]
    val xSize = plan.shape[1]
    val candidates = mutableListOf<IntArray>()

    if (y > 0 && plan[y - 1, x] != marker && plan[y - 1, x] != (9)) {
        candidates.add(intArrayOf(y - 1, x))
    }
    if (x + 1 < xSize && plan[y, x + 1] != marker && plan[y, x + 1] != (9)) {
        candidates.add(intArrayOf(y, x + 1))
    }
    if (y + 1 < ySize && plan[y + 1, x] != marker && plan[y + 1, x] != (9)) {
        candidates.add(intArrayOf(y + 1, x))
    }
    if (x > 0 && plan[y, x - 1] != marker && plan[y, x - 1] != (9)) {
        candidates.add(intArrayOf(y, x - 1))
    }

    for (candidate in candidates) {
        plan[candidate] = marker
    }

    for (candidate in candidates) {
        buildBasin(candidate, plan, marker)
    }
}

fun checkIfLowPoint(y: Int, x: Int, plan: NDArray<Int, DN>): Int {
    val ySize = plan.shape[0]
    val xSize = plan.shape[1]
    val candidates = mutableListOf<Int>()

    if (y > 0) candidates.add(plan[y - 1, x])
    if (x + 1 < xSize) candidates.add(plan[y, x + 1])
    if (y + 1 < ySize) candidates.add(plan[y + 1, x])
    if (x > 0) candidates.add(plan[y, x - 1])
    val min = candidates.minOf { it }
    return min
}


fun main() {
    // test if implementation meets criteria from the description:
    val peilungTest = readInput("day9/input-day9_test")
    val peilplanTest =
        mk.d2arrayIndices(
            peilungTest.size,
            peilungTest[0].length
        ) { y, x -> peilungTest[y][x].toString().toInt() }

    check(part1(peilplanTest) == 15)
    check(part2(peilplanTest) == 1134)

    // print results for 'real' data input:
    val peilung = readInput("day9/input-day9")
    val peilplan =
        mk.d2arrayIndices(
            peilung.size,
            peilung[0].length
        ) { y, x -> peilung[y][x].toString().toInt() }
    runFuncWithMeasurement(
        listOf(
            Pair("step1", { part1(peilplan) }),
            Pair("step2", { part2(peilplan) })
        )
    )
}
