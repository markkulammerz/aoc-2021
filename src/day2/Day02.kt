fun main() {
    fun part1(movements: List<String>): Int {
        var forwardPosition = 0;
        var depthPosition = 0;
        for (movement in movements) {
            val splittedMovement = movement.split(' ');
            when (splittedMovement[0]) {
                "forward" -> forwardPosition = forwardPosition.plus(splittedMovement[1].toInt())
                "up" -> depthPosition = depthPosition.minus(splittedMovement[1].toInt())
                "down" -> depthPosition = depthPosition.plus(splittedMovement[1].toInt())
                else -> println("cant recognize token")
            }
        }

        return forwardPosition * depthPosition;
    }

    fun part2(movements: List<String>): Int {
        return 0
    }


    // test if implementation meets criteria from the description:
    val testMovements: List<String> = readInput("day2/input-day2_test")
    check(part1(testMovements) == 150)
    // check(part2(testMovements) == 900)


    // print results for 'real' data input:
    val movements: List<String> = readInput("day2/input-day2")
    runFuncWithMeasurment(
        listOf(
            Pair("step1", { part1(movements) }),
            Pair("step2", { part2(movements) }),
        )
    )
}
