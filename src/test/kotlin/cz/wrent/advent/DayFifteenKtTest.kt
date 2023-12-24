package cz.wrent.advent

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.math.exp

class DayFifteenKtTest {

    @Test
    fun testHash() {
        val input = """HASH"""
        assertEquals(52, hash(input))
    }

    @Test
    fun testPartOne() {
        val input = """rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"""
        assertEquals(1320, dayFifteenPartOne(input))
    }

    @Test
    fun testPartTwo() {
        val input = """rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"""
        assertEquals(145, dayFifteenPartTwo(input))
    }
}
