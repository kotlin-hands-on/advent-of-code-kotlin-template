import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun CharSequence.reversedWindowedSequence(size: Int, step: Int = 1, partialWindows: Boolean = false): Sequence<String> =
    reversedWindowedSequence(size, step, partialWindows) { it.toString() }

fun <R> CharSequence.reversedWindowedSequence(
    size: Int,
    step: Int = 1,
    partialWindows: Boolean = false,
    transform: (CharSequence) -> R,
): Sequence<R> {
    checkWindowSizeStep(size, step)
    val windows = (if (partialWindows) indices else 0 until length - size + 1).reversed() step step
    return windows.asSequence().map { it + 1 }.map { endIndex ->
        val start = endIndex - size
        val coercedStart = if (start < 0) 0 else start
        transform(subSequence(coercedStart, endIndex))
    }
}

private fun checkWindowSizeStep(size: Int, step: Int) {
    require(size > 0 && step > 0) {
        if (size != step)
            "Both size $size and step $step must be greater than zero."
        else
            "size $size must be greater than zero."
    }
}
