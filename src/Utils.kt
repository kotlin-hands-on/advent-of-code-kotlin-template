import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

class AdventOfCodeUtils {
//    fun readInput(name: String) = this::class.java.classLoader.getResource(name).readText().trim().lines()
}
    fun readInput(name: String) = Path("src/resources/$name.txt").readText().trim().lines()

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
