package cz.wrent.advent

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

class DayThirteenKtTest {

    @Test
    fun testPartOne() {
        val input = """#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#"""
        assertEquals(405, dayThirteenPartOne(input))
    }

    @Test
    fun testPartTwo() {
        val input = """#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#"""
        assertEquals(400, dayThirteenPartTwo(input))
    }
}
