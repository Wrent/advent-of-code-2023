package cz.wrent.advent

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

class DaySixKtTest {

    @Test
    fun `day six part one`() {
        val input = listOf(
            7L to 9L,
            15L to 40L,
            30L to 200L,
        )
        assertEquals(288, daySixPartOne(input))
    }

    @Test
    fun `day six part two`() {
        val input = listOf(
            71530L to 940200L,
        )
        assertEquals(71503, daySixPartOne(input))
    }
}
