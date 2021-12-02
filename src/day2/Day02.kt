package day2

import mulitply
import readInput
import runFuncWithMeasurement

fun main() {
    fun part1(movements: List<String>): Int {
        var forwardPosition = 0
        var depthPosition = 0
        for (movement in movements) {
            val (instruction, amount) = movement.split(' ')
            when (instruction) {
                "forward" -> forwardPosition = forwardPosition.plus(amount.toInt())
                "up" -> depthPosition = depthPosition.minus(amount.toInt())
                "down" -> depthPosition = depthPosition.plus(amount.toInt())
                else -> println("cant recognize token")
            }
        }

        return forwardPosition * depthPosition;
    }

    fun part1fast(mov: List<String>): Int {
        val movements = mov.groupBy { it[0] }
        val forward = movements['f']?.sumOf { s -> s[8].toString().toInt() } ?: 0
        val up = movements['u']?.sumOf { s -> s[3].toString().toInt() } ?: 0
        val down = movements['d']?.sumOf { s -> s[5].toString().toInt() } ?: 0

        return forward * (down - up)
    }

    fun part2(movements: List<String>): Int {
        val submarine = Submarine(0, 0, 0)

        for (movement in movements) {
            val (instruction, amount) = movement.split(' ')
            when (instruction) {
                "forward" -> submarine.forward(amount.toInt())
                "up" -> submarine.up(amount.toInt())
                "down" -> submarine.down(amount.toInt())
                else -> println("cant recognize token")
            }
        }

        return submarine.position().mulitply()
    }

    fun part2fast(movements: List<String>): Int {
        val submarine = Submarine(0, 0, 0)

        for (movement in movements) {
            when (movement[0]) {
                'f' -> submarine.forward(movement[8].digitToIntOrNull()!!)
                'u' -> submarine.up(movement[3].digitToIntOrNull()!!)
                'd' -> submarine.down(movement[5].digitToIntOrNull()!!)
                else -> println("cant recognize token")
            }
        }

        return submarine.position().mulitply()
    }


// test if implementation meets criteria from the description:
    val testMovements: List<String> = readInput("day2/input-day2_test")
    check(part1(testMovements) == 150)
    check(part1fast(testMovements) == 150)
    check(part2(testMovements) == 900)


// print results for 'real' data input:
    val movements: List<String> = readInput("day2/input-day2-simon")
    runFuncWithMeasurement(
        listOf(
            Pair("step1", { part1(movements) }),
            Pair("step1fast", { part1fast(movements) }),
            Pair("step2", { part2(movements) }),
        )
    )
}

class Submarine(private var forward: Int = 0, private var depth: Int = 0, private var aim: Int = 0) {
    fun forward(amount: Int) {
        this.forward += amount
        this.depth += amount * aim
    }

    fun up(amount: Int) {
        this.aim -= amount
    }

    fun down(amount: Int) {
        this.aim += amount;
    }

    fun position(): Pair<Int, Int> {
        return Pair(forward, depth)
    }
}
