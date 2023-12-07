package cz.wrent.advent

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

class DaySevenKtTest {

    @Test
    fun `day seven part one`() {
        val input = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483""".trimIndent()
        assertEquals(6440, daySevenPartOne(input))
    }

    @Test
    fun `day seven part two`() {
        val input = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483""".trimIndent()
        assertEquals(5905, daySevenPartTwo(input))
    }
}
