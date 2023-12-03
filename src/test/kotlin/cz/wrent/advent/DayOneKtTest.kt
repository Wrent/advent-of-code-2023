package cz.wrent.advent

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DayOneKtTest {

    @Test
    fun `day one part one`() {
        val input = """1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet""".trimIndent()
        assertEquals(142, dayOnePartOne(input))
    }

    @Test
    fun `day one part two`() {
        val input = """two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen""".trimIndent()
        assertEquals(281, dayOnePartTwo(input))
    }

    @Test
    fun `test calibration values`() {
        assertEquals(29, getCalibrationValueWithWords("""two1nine"""))
        assertEquals(83, getCalibrationValueWithWords("""eightwothree"""))
        assertEquals(13, getCalibrationValueWithWords("""abcone2threexyz"""))
        assertEquals(24, getCalibrationValueWithWords("""xtwone3four"""))
        assertEquals(42, getCalibrationValueWithWords("""4nineeightseven2"""))
        assertEquals(14, getCalibrationValueWithWords("""zoneight234"""))
        assertEquals(76, getCalibrationValueWithWords("""7pqrstsixteen"""))
    }
}
