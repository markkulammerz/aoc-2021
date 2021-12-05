package day4

import readInput
import runFuncWithMeasurement

fun part1(bingoDate: List<String>): Int {
    val (calledNumbers, bingoBoards) = generateBingoBoards(bingoDate)

    while (calledNumbers.isNotEmpty() && bingoBoards.any { !it.hasWon }) {
        val calledNumber = calledNumbers.removeFirst()

        for (board in bingoBoards) {
            board.numberCalled(calledNumber)

            if (board.hasWon) {
                return board.score
            }
        }
    }

    return 0
}

fun part2(bingoDate: List<String>): Int {
    val (calledNumbers, bingoBoards) = generateBingoBoards(bingoDate)

    while (calledNumbers.isNotEmpty() && !bingoBoards.isEmpty()) {
        val calledNumber = calledNumbers.removeFirst()
        val boardsToRemove = mutableListOf<BingoBoard>()

        for (board in bingoBoards) {
            board.numberCalled(calledNumber)

            if (board.hasWon && bingoBoards.size > 1) {
                boardsToRemove.add(board)
            }
        }
        bingoBoards.removeAll(boardsToRemove)

        if (bingoBoards.size == 1 && bingoBoards.first().hasWon) {
            return bingoBoards.first().score
        }
    }
    return 0
}

fun main() {
    // test if implementation meets criteria from the description:
    val bingoDataTest: List<String> = readInput("day4/input-day4_test")
    check(part1(bingoDataTest) == 4512)
    check(part2(bingoDataTest) == 1924)

    // print results for 'real' data input:
    val bingoData: List<String> = readInput("day4/input-day4")
    runFuncWithMeasurement(
        listOf(
            Pair("step1", { part1(bingoData) }),
            Pair("step2", { part2(bingoData) })
        )
    )
}

private fun generateBingoBoards(bingoData: List<String>): Pair<ArrayDeque<Int>, ArrayDeque<BingoBoard>> {
    val calledNumbers = ArrayDeque(bingoData.first().split(",").map { it.toInt() })

    val bingoBoards = ArrayDeque<BingoBoard>()
    var actualBingoBoardList = ArrayDeque<List<Int>>()

    for (line in bingoData.drop(1)) {
        if (line.isNotBlank()) {
            actualBingoBoardList.add(line.trim().split(' ').filter { it.isNotBlank() }.map { it.toInt() })
        } else {
            if (!actualBingoBoardList.isEmpty()) {
                bingoBoards.add(BingoBoard(actualBingoBoardList))
            }
            actualBingoBoardList = ArrayDeque<List<Int>>()
        }
    }
    bingoBoards.add(BingoBoard(actualBingoBoardList))
    return Pair(calledNumbers, bingoBoards)
}

class BingoBoard(boardData: List<List<Int>>) {

    private var state: Array<Array<BingoSquare>>
    public val hasWon: Boolean
        get() {
            return state.any { check(it) } || state.indices.any { check(produceColumn(it)) }
        }
    public val score: Int
        get() {
            return state.sumOf { arrayOfBingoSquares ->
                arrayOfBingoSquares.filter { !it.isMarked }.sumOf { it.value }
            } * lastNumberCalled
        }
    private var lastNumberCalled = 0

    init {
        state = Array(5) { row -> Array(5) { col -> BingoSquare(boardData[row][col], false) } }
    }

    fun numberCalled(number: Int) {
        lastNumberCalled = number
        state.forEach {
            it.forEach { square ->
                if (square.value == number) {
                    square.mark()
                }
            }
        }
    }

    private fun check(line: Array<BingoSquare>): Boolean {
        return line.all { it.isMarked }
    }


    private fun produceColumn(index: Int): Array<BingoSquare> {
        return Array(5) { row -> state[row][index] }
    }

    override fun toString(): String {
        return "BingoBoard(state=${state.contentToString()}, hasWon=$hasWon)"
    }


}

data class BingoSquare(val value: Int, var isMarked: Boolean) {
    fun mark() {
        this.isMarked = true
    }
}