fun main() {
    fun part1(depthData: List<String>): Int {
        return 0
    }

    fun part2(depthData: List<String>): Int {
        return 0
    }


    // test if implementation meets criteria from the description:
    val testMovements: List<String> = readInput("day2/Day01_test")
    check(part1(testMovements) == 150)


    // print results for 'real' data input:
    val movements: List<String> = readInput("day2/Day01")
    runFuncWithMeasurment(
        listOf(
            Pair("step1_normal", { part1(movements) }),
        )
    )
}
