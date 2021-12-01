import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import java.time.Duration
import kotlin.system.measureNanoTime

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)


fun runFuncWithMeasurment(blocks: List<Pair<String, () -> Int>>) {
    val times = mutableListOf<Pair<String, Long>>()
    for (block in blocks) {
        val label = block.first
        var value: Int
        val time = measureNanoTime {
            value = block.second()
        }
        println("Value for $label: $value")
        times.add(Pair(label, time))
    }
    println("------------------------------------")
    times.forEach { println("${it.first}: ${Duration.ofNanos(it.second)}") }
    val min = times.minByOrNull { it.second }
    println("------------------------------------")
    println("fastest was: ${min?.first} in ${min?.second?.let { Duration.ofNanos(it) }}")
}
