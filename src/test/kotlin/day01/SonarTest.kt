package day01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SonarTest {

    private val sonar: Sonar = Sonar()

    @Test
    internal fun `Computes Increments for Sliding Windows of 1`() {
        val values = listOf(10, 20, 30, 10).map { it.toString() }
        val increments = sonar.countSlidingWindowIncrements(values, 1)
        assertEquals(2, increments)
    }

    @Test
    internal fun `Computes Increments for Sliding Window of 2`() {
        val values = listOf(10, 20, 30, 10).map { it.toString() }
        val increments = sonar.countSlidingWindowIncrements(values, 2)
        assertEquals(1, increments)
    }
}
