package cz.wrent.advent

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

class DayNineKtTest {

    @Test
    fun `day nine part one`() {
        val input = """0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45""".trimIndent()
        assertEquals(114, dayNinePartOne(input))
    }

    @Test
    fun `day nine extrapolate`() {
        assertEquals(18, extrapolate(listOf(0, 3, 6, 9, 12, 15)))
    }

    @Test
    fun `day nine extrapolate two`() {
        assertEquals(5, extrapolateTwo(listOf(10, 13, 16, 21, 30, 45)))
    }
}
