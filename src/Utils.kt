import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import java.time.Duration
import java.time.temporal.ChronoUnit
import kotlin.system.measureNanoTime

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun Pair<Int, Int>.mulitply(): Int = first * second

fun List<Int>.toDecimalValue(): Int {
    return Integer.parseInt(this.joinToString(""), 2)
}

fun Int.triangularNumber(): Int {
    return ((this.toLong() * (this.toLong() + 1)) / 2).toInt()
}

fun Boolean.toNumberChar(): Char {
    return if (this) '1' else '0'
}

fun runFuncWithMeasurement(blocks: List<Pair<String, () -> Number>>) {
    val times = mutableListOf<Pair<String, Long>>()
    for (block in blocks) {
        val label = block.first
        var value: Number
        val time = measureNanoTime {
            value = block.second()
        }
        println("Value for $label: $value")
        times.add(Pair(label, time))
    }
    println("------------------------------------")
    times.forEach { println("${it.first}: ${Duration.of(it.second, ChronoUnit.NANOS)}") }
    val min = times.minByOrNull { it.second }
    println("------------------------------------")
    println("fastest was: ${min?.first} in ${min?.second?.let { Duration.ofNanos(it) }}")
}
