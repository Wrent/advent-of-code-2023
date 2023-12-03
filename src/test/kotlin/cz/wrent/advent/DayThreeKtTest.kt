package cz.wrent.advent

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DayThreeKtTest {

    @Test
    fun `day three part one`() {
        val input = """467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..""".trimIndent()
        assertEquals(4361, dayThreePartOne(input))
    }

    @Test
    fun `day one part two`() {
        val input = """467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..""".trimIndent()
        assertEquals(467835, dayThreePartTwo(input))
    }
}
