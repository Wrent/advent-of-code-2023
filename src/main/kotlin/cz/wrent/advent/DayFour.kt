package cz.wrent.advent

import java.math.BigInteger

fun main() {
    println(dayFourPartOne(input))
    println(dayFourPartTwo(input))
}

fun dayFourPartOne(input: String): Int {
    return input.split("\n")
        .map { it.split(":")[1] }
        .map { it.split("|").let { it[0] to it[1] } }
        .sumOf { (winning, card) -> evaluate(winning, card) }.toInt()
}

private fun evaluate(winning: String, card: String): BigInteger {
    val w = winning.split(" ").map { it.trim() }.filter { it.isNotBlank() }.map { it.toInt() }.toSet()
    val c = card.split(" ").map { it.trim() }.filter { it.isNotBlank() }.map { it.toInt() }
    val count = c.count { w.contains(it) }
    return if (count == 0) {
        BigInteger.ZERO
    } else {
        (2).toBigInteger().pow(count - 1)
    }
}

private fun evaluateTwo(winning: String, card: String): Int {
    val w = winning.split(" ").map { it.trim() }.filter { it.isNotBlank() }.map { it.toInt() }.toSet()
    val c = card.split(" ").map { it.trim() }.filter { it.isNotBlank() }.map { it.toInt() }
    val count = c.count { w.contains(it) }
    return count
}

fun dayFourPartTwo(input: String): BigInteger {
    val map = mutableMapOf<Int, BigInteger>()
    val split = input.split("\n")
    for (i in 1 .. split.size) {
        map[i] = BigInteger.ONE
    }
    split
        .map { it.split(":").let { it[0].split(" ").last().toInt() to it[1] } }
        .map { (id, rest) -> id to (rest.split("|").let { it[0] to it[1] }) }
        .forEach { (id, rest) ->
                val eval = evaluateTwo(rest.first, rest.second)
                for (i in 1..eval) {
                    map[id + i] = (map[id + i] ?: BigInteger.ZERO).plus(BigInteger.ONE.times(map[id] ?: BigInteger.ONE))
                }
        }
    val calc = map.filter { it.key <= split.size }.entries
    return calc.sumOf { it.value.toBigDecimal() }.toBigInteger()
}

private const val input =
    """Card   1:  9 32  7 82 10 36 31 12 85 95 |  7 69 23  9 32 22 47 10 95 14 24 71 57 12 31 59 36 68  2 82 38 80 85 21 92
Card   2: 16 35 95 22 59 82 76 60 19 88 | 63 91 16 35 26 82 95 51 53 60 94 59 56 73 28 76 12 44 22 62  8  7 19 38 88
Card   3:  1 88 48 52 70 19 11 78 94 28 | 19 92 70 18 34 78 83  8 82 87  3 97 66 31 63 17 69  4 75 94 52 54 77 24 45
Card   4: 72 14  2 92 65 62 16 28 55 91 | 73  8 35  4  9 86 83 51 47 53  3  7 15 52 96 54 49 88 85 30  6 59 81 33 99
Card   5: 60 61 24 41 30 77 94 50 38 75 | 47 93 82 98 96  5  9 53 17  6 10 21  2 91 80  4 14 71 29 69 62  1  7 87 88
Card   6: 21  4 16 41 58 68 66 22 64 94 | 72 69 60 62 34 21 75 66 38 25 63 17 81 23 71 73 78 64 89 24 26 56 68 79 51
Card   7: 10 90  4 56 14 26  7 23 57 19 | 62 10 60 51 46 34 23 49 44  8 53 50  2 56 21 81 69 89 87 91 16 63 38 36 22
Card   8: 36 27 38 47 16 57 96 31 43 76 | 62 99 87 68 76 44 49 63 47 16 58 81 84 48 45 88 64 34 54 42 72 24 26 12 50
Card   9: 92  9 35  2 26 97 79 88 43 45 | 89 96 62  1 39 98 72 66 28 78 64 68 42 63 15 25 22 60 31 91 69 94 21 81 29
Card  10: 40 17  3  9 47 23 82 90 13 87 | 51  2 36 85 34 27 11 53 71 38  8 57 58 17 24 20 54 23 31 37 35 70  9 83 99
Card  11:  2 21  9 53 45 19 91 74 23 25 | 29 26 91 39 58 18 96 53 31 63 46 21 32 54 22 66 17 25 94 74  4 41 77 35 23
Card  12: 13 84 68 67 99 97 73 49 86 65 | 97 49 35 40 42 80 45 11 94 53 99 29 72 73 23 77 62  3 38 90 83 68 59 20 95
Card  13: 70  9  7 11 95 46 69 91 22 71 | 30 56 31 90 91 69 17 71 25 46 66  9 62 89 70 98 36 57 45 94 21 40 75 95 24
Card  14: 54 37 89 14 18  1 60 34  7 80 | 83 98 11 85 50 35 61 29 18 67 33 63 32 92 68 65 95 56 74 96 27 73 81 47 15
Card  15: 25 82 90 66 50 40 15 43 32 62 | 49 77 96 36 89 70 50 29 26  1 20 71 37 30  9 91  7 52  5 82 51 55 72 38 12
Card  16: 26 46 79 29 52 10 87 75 45 49 | 52 25  2 42 73 44 58 85 10 37  4 17  8 86 95 65 35 31 16 12 99 51 70 80 45
Card  17: 17 96 64 38 73 11 32 24 25 65 | 83 37 25 81 59 18  4 15 98 87  2  1  3 60  5 53 67 46  6 20 79 84 95 50 62
Card  18: 48 50 90 87 40 71 59 61 30 85 | 85 19 13 39 76 55 72 29  5 84 22 91 67 63 74 73 61 90 62 42 30  9 69 87 48
Card  19:  4 99 93 55 22 37 41 75 32 79 | 95  7 65 50 40 49 42 64 89  1 59 33  9 53 20 44 23 98 60 30 39 12 66 84 45
Card  20: 14 34 73 91 30 48 35 44 50  8 | 48 33 35 40 65 29  9 42 18 79 43 89 92 68 75 97 87 69 85 80 98 14  6 25 76
Card  21: 33 15 83 71 94 61 87 54 36 72 | 56 52 83 46 32 99 36 67 12 47 38 13 14  2  7 26 20 63 90 40 37  6 84 42 92
Card  22: 66 45 79 71 92 57 47 43 62  9 | 45 89  9  5 53 84 80 19 77 61 68 40 98 23 56 36  6 60 93  1 11 49 71 52 29
Card  23: 29 53 13  4 76 62 25 94 82 98 | 24 45 85 59 41 11 13 78 54 67 74 21 50 75 16 37 63 86 14 26 92 31 57 33 40
Card  24: 16 66 90 39 24 92 53 76 52 31 | 21  7 57 65 58  2 10 61 35 73 68 95 84 43 12 18 76 71 83 15 82 88 30 55  5
Card  25:  9 45 17 42 64 93 54 33 77 62 | 35 81 82 18 47 92  2 20 95  1  6 58 40 73 43 37 15 48 23 75 26 13 50 63 79
Card  26:  1 99 92 73 57 29 18 97 34  5 | 82 89 61  3 95 39 14 98 15 87 45 48 49 60 22 28 65 16  9 58  4 53 38 56 51
Card  27: 87 45 35 93 62  1 66 91  6  3 | 51 60 96 84 35 91  6 28 32 40  3 57 29 90 73 12 81 89 31 69 97 66 70 36 18
Card  28: 72 89 51 45 10 21 84 23  6 94 | 26 45 23 13 68 51 89 66 34 99 83 70 44 56 72 57 73 94 30 84 61 80 85 29  6
Card  29: 58 80 96 47 13 44 71 38 55 54 | 58 35  3 61 81 96 71 44 43 14 36 54 20 69 63 13 75 28 80 18 73 11 38 55 47
Card  30: 44 17 81 16 13 65 85 60 34 46 | 60 71 72 13 10 51 65 56 34 59 80 75 18 64 35 92 58 50 97 46 81 37 44 16 17
Card  31: 84 55 99 95 42 83 26 33 43 87 | 78 59 45 26 28 94 63 17  2 73 38 84 96 34 99 19 75 65  8 92 58 20 11 82 57
Card  32: 57 67 49 76 15 84 27 39 24 14 | 20 15 21 14 67 97 76 19 17 38 23 41 24 32 27 57 26 81 99 49 34 96  9 84 39
Card  33: 96 76 23 95  5 37  8 62 65 63 | 59 23 67 51 40 68 70 38  8 36 41 53 62 65 96 76 66 57 37  5 46 34 95 19 69
Card  34: 52 13 90 81 94 62 91 40 53 42 | 82 90 35 13 55 95 93 74 44 70 89 87 62  5 80 22 53 94 21 77 54 20 18 52 40
Card  35:  1 75 37 64 17 12 57 18 58  3 | 65 74 89 12 72 64 93 23 51 73 44 80 21  7 35 87 42 82 25 53 71 38 79 43 61
Card  36: 15 49 18 30 20 90 69  3 60 59 | 14 63 53 86  5 62 69 52 21  9 18 48 13 34  2 67 60 55 10 98 15 81 88 83 94
Card  37: 48 39 43 81 40 59 70 22 62 61 | 48 14 79 26 12 56  3 89 66 17 87  9 43 27 73 23 47  1 18 97 96 65 59 42  6
Card  38: 35 94 15 31 22 55  8  2 11 45 | 78 53 41 13 37 64 36 90 26 97 16 99 57 54 20 59 82 24 58 81 14 74 95 93 79
Card  39: 74 25 37  8 53 36 86 68 91 79 | 48 45 24 80 20 49 86 42 47 28 17 22 93 38 35 34 85 21 18 14 97 61 27 41 88
Card  40: 41 16 26 14 79 27 62 55 80 99 | 52 83  5 38 69 11 89  6 35 58  4 74 99 97 63 30 90 33 46 60 95 59 22 76 40
Card  41: 57 77 60 66 35  4 13 89 14 40 | 78  4 97 58  9 65 94 44 89 95 80  7 47 59 37 26 19 76 91 18 45  6 56  3 90
Card  42: 83 19 31 59 78 54 22 16 44 88 | 53 84 90 39 56 29 44 55 58 13 72 80 49  6 28 97 25 18 99 89 40 43 10 33 71
Card  43: 81 94 73 42 85 45 28 61 88 19 | 84 86 36 44 51 40 82 72 39 57 13 53 87 83 38 76 63 35 47 15  2 95 79 26  1
Card  44: 78 28 92 59 51 37 61 55 58 98 | 49  6 30 89 61 98 58 78 31 28 48 92 60 56 59 51 29 14 37 63 38 55 24 42 21
Card  45: 23 59 74  5 81 53  3 93 54 68 | 68 15 38 45 66 61 87 54  3 81 74 43  5 25 59 23 93 78 44 72 53 52 89 97 48
Card  46: 79 17 74 72 84 44  3 59 66 76 |  3 44 95 84 66 79 62 97 63 48 76 10 75 72  6 17 74 94 80 59 30 12 20 83 87
Card  47: 98 18 11 52 66 14 68 89 80 19 | 66 67 23 52 80 89 95 44 60 11 55 34 18 59 19 53 98 82 14 38 63 65 90 68 77
Card  48: 84 64 87 81 54 61 31 57 53 79 | 99 74  9 45 54 14 96 38  7 82  4 17 43 69 62 83 66 81 41 86 55 75 30 10 21
Card  49: 95  4 89 38 67 71 70 75 35 39 | 39 71 22 80 75 43 35 89 17 38  4 29  7 69 94 63 85  6  5 95 70 58 67 50 68
Card  50: 18 59  9 34 83 68 80 21 61 78 | 10 96 19 11 74 15 26  8 65 56 35 79 32 44 55 36 94 95 53 86  3 27 88 67 85
Card  51: 17 79 69 92 39 51 46 81 14 21 | 49 92 48 29 47 74 76 81 69 85 86 98 73 43 24 45 21 84 25 10 17 44  1 31 28
Card  52: 39 31  1 21  7 27 85 59 35 67 | 84 29 91 89 70  5 94 23 21 65 71 12 26 41 15 11 61 73 14 52 76  9 16 24 75
Card  53: 93 21  1 18 54 87 35 98  9 48 | 44 30 79 82 23 43 51 46 34 35  9 75 12 60 77 36 27 86 32 47 85 98 87  1 13
Card  54: 51 95 69 73 66 61  9 28 75 24 | 42 28 87 69 24 19 56 98 57 66 95 73  9 51 61 50 64 75 76 27 86  1 29 30 79
Card  55: 73 99 56  8 10 97 26 64 52  2 | 79 47 80 89  5 53 37 63 41 48 12 96  1 25 84 70  9 76 58 75 72 31 20 17 46
Card  56: 71 43  8 50 95  1  2 77 45 81 | 43 85 24 59 46 15 36 81  3 50  9 45 95 77 71 12 65  1 60  2  8  5  7 20 88
Card  57: 10 76 62 41 58 67 80 39  9 21 | 58 10 42 60 37 27 51  1 25 18 56 23 67 88 62 50 87 80  9 54 41 81 26 21  7
Card  58: 41 80 74 71 10 63 58 26 38 21 | 63 66 80 65 12 68 72 10 20 91 88 44 11 70 36 71  3 61 74  7 21 41 67 43 38
Card  59: 41 60 82 94 90  7 76 69 28 95 | 35 69 77  9 81 65 11 90 19 22 36 75 12 52 98 55 92 79 14 67 47 83 39 74 94
Card  60: 44 82 36 55 94 72 99 70  6 50 | 35 29 95 62 97 11 33 92 19 91 10 54 56 53 98  1 39 16 64 52 73 66 30  4 43
Card  61: 55 19 82 18 24 91 99 36 54 98 | 68 58 99 51 79  1  7 50  4 28 59  8 19 14 10 40 57 34 60 82 88 47 52 80 54
Card  62: 14 20 49  2 94 59 33 72 86 34 | 35 33 54 94  5 14  7  6 60 41 40 38 20 26 72 22 49 13 83 98 36  3 51 82 74
Card  63: 73 78  8 64 52  7 86 30 98 27 | 95  4 48 24 45 72 60 31 25 18 59 90 51 14 50 65  2 16  3 33 94 11 93  6 32
Card  64: 26 71 76 85 70 80 81 61 58 77 | 87 10 24 38  4 40 42 96 47 63 99  7 20 68 78 66 53 12 46 90 75 59 60 88  5
Card  65: 26 37 35 44 45 10 13 85  6  8 | 39 80 93 79 60 89 58 96 44 85 59 94 24 23 48 86 22 42  6  5 25 57 43 90 21
Card  66: 62 45 67 18 36 84 66 90 98 95 |  1 71 68 54 76 48 42 35 19 94 39 69 80 17 41 20 93 59 29 30 15 37 14 12 88
Card  67: 33 10  5 94 72 86 84 80 19 85 | 76 26 61  3 93 17 74 42 23 16 18  2 14 46  1 38 72 13 28 29 91 21 12  7 48
Card  68: 70 73  2 79 88 36 35 13 16  6 | 75 47 30  7 89 78 39 69 96 91 19 83 61  4 10 77 81 44 90 50 29 38 32 53 60
Card  69: 60 20 40 38  6 49  5 85 84 80 | 38 12 59 94 27 15 85 17 65 69 82 36  7 43 47 99  5 87 61 77  4 83 81 90 40
Card  70: 85 96 73 11 22 33  9 61 37 42 | 27 70 96 74  9 75 60 88 22 86 13 63 37 80 83 73 42 85 17 33 35 82 61 65 11
Card  71: 41 47 37 52 73 97 77 69  5 45 | 47 95 78 41 74 54  5 88 63 87 34 18 37 85 26 73 52 50 28 77 97 86  1 69 45
Card  72: 90 88 85 71  5 59 65 52 30 13 |  5 29 68 62 24 12 90 30 85 18 76 25 40 60 77 14 19 33 61 88 70 17 95 73  2
Card  73: 44 98 60 49 51  9 21 88 87 70 | 63  3 46 65 49 70 44  7 74 88 35 45 17 24 51 60 25  9 98 87 21 10 81 83 61
Card  74: 31 23 80 11 13 72 63 57 29 70 | 57 28 70 45  3 94  5 80 14 11  2 29 20 47 72 63 10 31 66 19 79 38 74 13 23
Card  75: 82 54 62  8 30  7 88 20 71 85 | 83 65 74 44 79  3 29 64 51 35 98 56 41 22 27 13 26 34 39 59 24  4 33 14 63
Card  76: 16 24 87 73 17 71 69 26  4 76 | 59 71 32  3 56 64 24 41 66 26 12 36 87 65 73  4 75 16  8 17 57 69 49 46 76
Card  77: 98 57 96 56 39 12 69 37 54 65 | 21 48 87 97 29 27 56 31 18 88  7 79 70 35 64 38 91 15 14 43  3 84 20 85 32
Card  78:  5 62 37 51 73 78 18 64 42 48 | 35 82 85 62 23 66  3 14 19 64 79 18 93 53 69 58 96 63 90 24 41 65 94 40 95
Card  79:  8 73 46 15 99 29 96 34 59 75 | 48 65 57 99 63 33 37 73 79 28 72 56 34  3 25 75 70 95 58 27 43 30 54  8 87
Card  80: 91 81 96 55 44 82 31 23 11 74 | 24 51 96 77 40 28 56 44 54 89 78 38 76 74 17 92  3 23 36 63 80 65 55  7 11
Card  81: 81 77 30 26 93 28 97 10 84 88 | 57 28 36 33 54 11 96 58 18 99 30  1  5 79 12 24 56 93 25 78 10 40 76 84 81
Card  82: 31 67  3 90 28 76 55  6 29 26 | 75 34 82 73 38 17 67 91 86 40 43 45 42 60 37 63 55 87 93 84 58 78 80 20 11
Card  83:  8 97 43 88 62 34 68 50 82 71 |  8 68  2 96 85 36 10 14 35 32 73 16 26 29 67 60 37 89 52 98 74 22 78  1 59
Card  84: 77 97  5 33 12 73 90 57 31 19 | 97 46 48 57  4 10 94 85 59 31 17 60 81 49 62 58 25  8 79 78 50 36 55 51 32
Card  85: 74 85 97 19 76 99 21 47 20 50 | 50 69 72 39 45 26 13  7 92 21 63 58 84  9 94 53 43 81 89 49 62 32 70 82 10
Card  86: 81 96 82 76 97 77 40  3 68 98 |  4 26 69 88 43 15 78 64 79 92 62 30 49 89 37 59 95 63 58 98 75 99 80 51  6
Card  87: 80 73 51 20 41 67 31 66 97 27 | 16 59 75 86 24 83 95 44 85 13 11 77 70 14  2 39 88 89 93 55 52  6 53 94 98
Card  88: 18 38 66 87 56 25 46 63 37 15 | 86 22 17 96 10 49 80 21 15 77 66  3 20 95 36 87 16 74 46 26 79 76 38 43  6
Card  89: 51 55 41  3 20 60 99 70 23 43 | 51 81 99 32 50 96 41 53 80 76 43  3 83 65 20 46 70 60 38 35 57 55  7 74 23
Card  90: 94 19 23 81 44 15 74 73 22 71 | 83 66 54 43 23 55 69 81 85 71 62 96 19 86 78 22 15 58 94 74 44 73 57 17  8
Card  91: 72 77 21 83 82 75  1 56 99 43 | 42 77 56 90 68 96 39 72 83 97 21 17 66 70 49 43 69 63 82 75 47 99 87 11  1
Card  92: 62 58 71 78 38 80 52 94 48 92 | 11  7 56  4 77 59 62 49 14 94 52 80 92 64 71 58 13  1 48 12 78  9 17 38 35
Card  93: 98 45 63 46 48 97 91 29 90 15 | 95 31 41 15 43 80 24 44 75 83 30 87 98 12 27 36 74 16 86 35 33 85 54 94 78
Card  94: 27 60 36 26 76 65 86 89 10 54 | 80 78 95 36 88 82  6 46 73 58 22 40 12 50 76 14 65 26 70 60 54 17 27 89 93
Card  95: 18 62 66 98 24 16 80 58 53 97 | 93  9 15 61 51 19 81  8 21 36  6 71 80 13 52 87  5 37 86 75 68 60 97 54 10
Card  96:  6 55 40 79  3 67 13 96 91 34 | 54 13 63 17 52 72  7 81 82 69  6 91 90 57 14  2  8 74 75 40 96 21  3 38 55
Card  97: 40 33  3 59  8 88 99 14 41 74 | 99 48 41 35 69 64 18 50  3 96 40  8 36 63 17 90 59 33 49 26 88 93 74 27 14
Card  98: 63 44 52 11 32 46 62 19 30  6 | 71 51 48 70 82 44  8 60 92 21 77 62 53 95 31 73 80 96 55 34 86 97 76 88  6
Card  99: 66 46 22 44 94 50 68 59 25 75 | 40 46 56 32 64 89 80 22 60 87 77  9 59 25 75 38 44 48  8 50 94 68 66 72 20
Card 100: 15  8 13 93 80 58 66 10 76 32 | 58 68 34 54 53 79 69 18 71 33 66 13 92 77 93 40 80 94 76 75 15 10 83  8 70
Card 101: 22 25 48 97 63 81  7 84 60 43 | 58 91 10 85 59 76 16 36 96 39 42 50 72 34 86 61 67 11 79 27 83 73 98 57 70
Card 102:  1 35 10 28 59  2 74 45 25 13 | 47 56 90 19 20 95 64 72 88 28 27 18 31  7 55 61 48  2 10 81 70 25 96 73 74
Card 103: 28  5 31 23 76 83 25 94 35 18 | 10 85 72 18 23 60 17 50 30 76 35 83  8 42 65  4 34 84 28 39  5 98 25 94 92
Card 104: 32 45 61 94  5 44  3 15 77 87 | 37 15 84 94 19 33 11 51  1 81 16 61 87 68 74 97 62 88  8 98 45 44 92 70 83
Card 105: 32 89 96 15 67 83  9 64 60 44 | 76 38 75 54 44 67  3 88 89 46 15 40 96 19  7 36 55 64  1 83 78 72 31 18 21
Card 106: 78  7 12 67 54 29 76 66 17 35 | 53 19 78 86 36 33 84 63 16 43 64 46 31 58 66 11 39 38 76 14 49 94 47 67 56
Card 107: 95 91 53 27 12 51 29  1 36  9 | 43 70 74 89 52 12 82 97 96  4 45 37 14 68 15 58 63 51 59 34 50 81  3  6 83
Card 108: 68 79 91 36  9 55 21 81  2 17 | 26 21 63 91 27 45 73 92 29 24 74 57 43 61  4 82 20 50 84 88 54 89 81  8 15
Card 109: 68 37 88 22 26 53 67 43 62 35 | 24 19 46 44 49  7 17 51 21 81 96 30 59  3 72 99 71 58 22 28 60 52 43 65 86
Card 110: 95 66 14 29 71 44 35 73 15 63 |  2 87 82 39 55 92 33 98 51 20 16 89 24 12 64  8 85 41 37 77 42 45 22 48 52
Card 111: 83 21 90 50 91 82 73 19 41 25 | 47 74 77 86 84 33  3 99 30 93 37 98 42 59 24 12 69 36 61 68 55 17 32 53 18
Card 112: 72 29 25 73 15 93  4 36 78 56 | 35 80 62 12 88 59 30 17 51 76 70 64 21 42 79 31 96 83 63 57  1 68 52 53 75
Card 113: 39 52 17 98 34  9 72 53 47 51 | 70 72 17 48 83 98 64  9 15 94 57 68 87 14 27 55 40 38 53  8 47 51 16 28 67
Card 114: 18  1 14 61 17 28 24 34 63  5 | 62 64 68 67  8 88 18 73 28 17 14 34 65 95 47  1 31  5 24 99 61 89 63 42 80
Card 115: 31 76 94 50 65 52 21 53  5 43 | 93 38 97 35  2 82  1 45 65 50 43 59 21 27 94  5 31 53 61 52 10 76 99 44 47
Card 116:  2 13 64 10 14 29 33 55 19  6 | 91 68 22 47  5 65 23 64 20 18 70 21 45  1 42 31 59 17 61 58 30 73 81 14  2
Card 117: 52 22 10 59 25 80 48 28 99 82 | 93 52 25 19 94 80 85 28 46 90 74 65 48 99 22 66 87 49 83 57  3 10 59 78 67
Card 118: 22 98 56 42 95  5 62 50 26 71 | 42 50 56 98 41 92 71 69 60 22 59 64 62 27 30 26 21 87  2 51 40 89 24 95  5
Card 119: 25 61 90 69 15  3 33 14 59 21 | 61 30 25 44 18 90 74 64 33 73 51  3 14 60 45 82 23 69 46 59 27 21 49 15  7
Card 120: 99 83  2 21 85 47 45 34 58 31 | 18 34 47 91 58 97 40 60 71 85 24 45 83 21 10 53 56 99 32 38 77 31  2 79 23
Card 121: 91 63 88  9 66 25 48 94 44 51 | 42 24 19 83 43  9 25 28 18 88 91 35 63 69 66 55  3 14 54 75 80 51 73 94 44
Card 122:  4 32 37 25  8 13  3 67 39  5 | 94 99 11  5 93 60 29 37 45 28  3  8 32 66 98 25 36 97 34 96 80 67 63 71 31
Card 123: 93 89  1 53 15 98 21 26 82 42 | 17 25 40 63 15 21 28 42 30 46 13 53 81 74 93 85 98 57 39 65 60 38 12 36 92
Card 124: 14 12 90 59 56 54 94 80 51 63 |  5  7 14 33 86 94 34  3 80 25 40 56 90 65 63 59 71 67 54 12 82 66 46 51 44
Card 125: 52 37 74 28  4 96 92 40  3 64 | 23 44 64 71 22 34 35 17 10 74 93 37 40 42 52 96 53 91 92 70 28  4 75  3 12
Card 126: 40 70 12 23 61 99 47 96 77 24 | 58 32 96 66 44 21 83 84 43 11 94 13 99 62 87  1 31 10 71 53 39 14 95 97 56
Card 127:  9 40 39  4 19  3 25 96  5 95 | 86  9 31 25 14 19 40 55  5 84 66  4 28  3 88 95 27  1 34 52 97 29 12 96 15
Card 128: 56 35  9 95 93 66 38 85 39 65 | 92 14 12 79 86 95 66  1 38 93  6 94 77  2 34 62 56 87 46 39 65 72 40 10 45
Card 129: 77 19 10 24 25 68 67  5 38 29 | 73 59 50 39 37 24 58 56 70 72 74  3 20 66 26 92 71 29 83 15 96 79  2 28 27
Card 130: 19 42 20 87 76 41 83 47 99 51 | 85 27 63  1 55  9 49 28 25 14 76 51 36 34 53 57 73 12 58 66 78 16 22 84 26
Card 131: 33 82 49 90 74 24 53 48 12 51 | 48 29 52 12 31 99 81 33 80 76 37 32 96 28  8 51 79 56 26 62 53 10 15 42 58
Card 132: 51 90 31 49 77 64 20 76 91 45 | 77 93 27 16 45 53 57 84 63 42 25 44  4 64 71  8 70 66 95 34 23 85 35 31 87
Card 133: 76 84  3 16 25 79 35  8 50 60 | 32 97  7 51 49 12 37 54 59 60 27 87 40 64 38 78  8 53 43 39 81 66 68 44 18
Card 134: 77 36 47 40 44 14 94 91 39  5 | 95 25 45 99 59 27 64 12 15  9 22  4 50 62 73 39 34  6  8 72 56 96 89 90 35
Card 135: 33 49 25 71 39 75 30 16 46 55 | 94 82 64 10 16 73 79 68  4 12  1 87  6 53 34 98 66 99 78 59 58  2 36 77 52
Card 136: 53 22 58 94 49 25  6 64 69 73 | 42 23  3 76 77 83  8  1 89  2 33 78 46 12 34 95 26 96 41 93 97 35  5 82 55
Card 137: 64 25 86 72  6 39 48 95 73 62 | 52  4 29 18 11 84 79 87 19 32 96 71 61 35 17 15 44 27 68 70 98 45 22 51 85
Card 138: 60 30 89 73 76 11 23 90 86 80 | 11 89 23 86 66  5 80 13 67 73 59 96 90 76 49 20 84 30 82 29 72 87 63 60 92
Card 139: 82  6 17 76 72  5 70 45 90  7 | 12 50 25 41  9 16 29 20 63 70 28 10 53 90 76  5  7 82 17 15 45 93 72 97  6
Card 140:  3 88 95 62 81 35 92 16 21 87 | 92 42 18 35 21 12 30 25 27 17 87 71 81 95 88 40 16 29 69 70 14 62  3  2 31
Card 141:  2 36  5 53 22 30 40 94 84 52 | 86 67 38 32 90 33 18 26 13  2 96 49 25 74 83 31 54 42 52 36 97 55 63 44 47
Card 142: 84 77 81 89 56 61 39  2 22  7 | 79 40 62 91 39 20 13 80 81 63  7 22 84 77  6  5  2 42 89 61 53 52 56 75 88
Card 143: 90 86  6 75 67 76 18 41 36 55 | 25  7 30 21 40 65 47 42 77 34 53 60 97 10 49 39 45 57  8 94 83 93 32 80 98
Card 144: 26  5 82 70 75 88 53 47 29 93 | 23 40 49 74 62 93 60 75 86 98 53 45 47 29 97 67  8 88  4  5 26 37 71 82 70
Card 145:  8 62 96 13 21 82 42 54 41 61 | 87 13 23 96 29 27  6 14 53 75  5 24 31 60 90 12 82 51 64 65 70 58  9 74 47
Card 146: 57 20 68 46 52 36 49 48 14 34 | 52 45 92 33 49 57 61 54 44 18 47 59 46 34 63 32 65 53 50 14 20 80 38 42 15
Card 147: 47 86 79 34 64 91 57 21  1 89 | 14 79 31 91 44 11 89 75 40 97 81 92 63 73 21 49 93 86 64 22 35  8 13 99 57
Card 148: 83 62 84 25 96 13 30 99 24 82 | 64  2 65 63 60 98 91 76 81 94 23 22 88 85 28 92 15 38 35 72 52 32 47 69 31
Card 149: 62 32 13 59 96 78 11 73 34 52 | 89 73 13  1 52 24 71 83 53 97 62 85 78 20 17 11 59 96 74 19 32 29 34 43  9
Card 150: 76 14 58 69  8 21 49 60 29  6 | 29 86 82 88  3 91 72 71 55 57 51 95  9 61 12 79 23 33 19 20 50 37 62 30  4
Card 151: 68 93 22 66 81 77 16 75 47 34 | 47 76 34 52 25 68  9 26 29 66 37 57 10 54 99 46 77 95 81 93 53 13 97 22  7
Card 152:  2  9 24 70 11 42 44 98 79 27 | 18 72 91 78 44 77 35 17 79 21  9  5  4 98 43 54 70 42  2  6 76 11 24 68 27
Card 153: 36 28 10  9 69 25 87 50 77 11 |  7 54 25 40 85 33 15 20 87 71 96 99 77 53 94  9 67 28 69 32 26 18 63 29 43
Card 154: 41 15 97  7 50 92  9 66 20  6 | 91 53  3 15 66 92 72 67 85  9 20  1 28 32 14 95 81 34 79  8  7 59 82 52  6
Card 155:  8  1 15 14 44 81 89 37 55 43 | 76 62 83 79 37 24 56 30 34 58 45 64 23 41 84 71 14 22 60 27 18  9 42 54 85
Card 156:  1 44 85 92  7 35 52 50 72 26 | 62 69 85 60 80 73 94 37 12 63 99 70 17 51 23  8 95 29 32 55 47 41  6 36 25
Card 157: 12 49 19 14 28 64 17 76 34  8 | 33 87 95 61 84 55 86 19 92 44  3 42 24 96 94 36 38 13 10 51 15 81 27 75 67
Card 158: 29 88 66 19 41 57 52 30 46 47 | 16  1 36 91  8 70 27  3 38 32 89 84 90 31 88 49 60 22 18  9 62 67 47  7 34
Card 159: 99 87  7 36 67 23 14 92 52 82 | 93 41 76 15  8 63 50 40 30 85 92 48  1 99 87 53 46 70 34  5 44 79 16 35 17
Card 160: 40 35 56 38 93 47 42 72 80 79 | 77 90 83  4 65 61 27 41 21 13 25 68 34 11 84 40 94 42 69 64 14 70 58 97 63
Card 161: 57 54 92 91 51 37 93 55 59 41 |  2 78 35 85 23 41 74 33  8 21 72 94 63 90 95 64 71 12 65 27 38 18 19  9 60
Card 162: 51 37  4 45 15 59 71 23 61 77 | 72 81 19 35 53 60 11 93 54  2 31 70 40 28 57 63  6 46 89 96 30 36 12 20 29
Card 163: 52 53  7 23 29 89 86 43 97 77 | 79 18 34 45 80 61 30 41 68  7 52 89  8 49 29 86 67 17 81 98 97 78 26 48 70
Card 164: 98 61 60  4  5 28 70 37 41 10 | 22 58 68 41 98 53 30 99 60 61 17 12  4 25 49 10 70 92  5 83 36 28 13 37 56
Card 165: 16 12 15 73 22 31 20 63 42 95 | 26 53 40 17 97 15  8 25 78 46 44 45 54 39 61 23 51 11 90 95  2 35 68 24 18
Card 166: 14 89 75  8 90 29 18 27 64 19 | 74 76  7 75 23 72 45 54 59 90 80 44 29 27 20 64 12 89 14 52 79  8 83 11 15
Card 167: 16 78 33 67 66 63 69 59 36 94 | 89 70 42 47 92 34 14 69 37 83 19 25 57 36  3 44 77 26 73 85 18 59 13 65 76
Card 168: 33 76 58 94 34 84 79 69 60  2 | 49 54 80 51 68 84 71 17 44 12 82 69  4 88 76  8 98 34 93  5 52 95 13 75 39
Card 169:  2 63 48 44 25 75 51 36 29 52 | 24 84 56 37 17 60 74 36 77 48 62 32 16  2 80 41 15 35 88 72 30 58 20 49 12
Card 170: 98 14 67 30  5 15 89 28 74 12 | 99 88  7 35 19 27 81 50 70 97 98 61  2 67 92 46 75 30 53  4 59  1 74 26 45
Card 171:  4 26 44 61  5 75 76 20 56 33 | 34 97 62 70 83 35 23 78 13  3 86 58 56 65 72 59 76 20 39 32 36 71 33 30 46
Card 172: 47 15 51  8 77 74  7 41 30 35 |  8 30 45 15 99 80 75 52 74 28 49 81 16  2 66 62 32 10 69 65 92 77 38 73 13
Card 173: 65 57 60 74 69 55 21 59 10 50 | 32 28 78 16 17 79 67 76 35 66  9 63 80 58 62 20 89 88 92 26 64 10 12 97  3
Card 174: 40 66 82 30 61 27 78 54  8 48 | 73 45 28 49 52 91 25 20 55 34 33  7 23  4  5 56 74 44  9 75  2 24 71  3 70
Card 175: 49 42  2 59 41 38 32 83 89 23 | 25 57 90 46 99 54 67 40 73 87  9 27 82 58 53 63 22  7 13 37 52 68 15 81 88
Card 176: 22 81 29 71 28  4 94 32 19 98 | 53 47 84 63 45 73  7 79 52 31 75 49 55 21 96 38 23 58  1 15 40 83  2 90 41
Card 177: 44 43 42 73 56 74 64 61 29 89 | 91 15 96 77 65 23 84 20 94 45 95  6 33 13 63 50 53 18 12 24 41 47 54  9 14
Card 178: 12 16 77 20 89 41 55 94 13 50 | 78 36 67 75 55 12 20 28 94 63 45 81 53 26 43 41 16 89 50 68 77 22 32 62 13
Card 179: 41 64 96 46 85  5 11 79 89 51 | 98 62 92 55 49 93 90 91 41 64 94 17 48 46 70 31 51 74 34 33 75 12 28 35 84
Card 180: 27 13 89 84 45 16 77 86 72 83 | 55 89 12 83  5 37 38 53 98 77 16 27 22 78 45 87 35 64 68 93 41 84 13 34 88
Card 181: 79 31 12 61 49 11 68 56 78 54 | 58 23 21 59 62 72 69 17 12 34 68 87 26 98 67 16  1  3 11 38 31 78 89 27 91
Card 182: 52 55  4 31 57  5 23 66 78 68 | 52 31 87 50 98 83 66  4 26 25 68 63 14 70 54 29  7 22 85 42 17 27 67 32  5
Card 183: 71  9 31 84 59 32 74 26 85 36 |  5  9 58 26 50 74 80 37 59 16 44 27 98 11 21 48 43 77 57 62 33 86 24 28 63
Card 184: 47 16 69 11 99 34 79 65 49  9 | 92 97 64 83 49 73 17 36 29 46  1 15 78 25 58 81 51 23 84 39 60 91 67  3 56
Card 185: 84 21  9 64 45 19 51 90 91 76 | 91 83 76 26  8  6 21 70 32 42 11 51 19 39  9 69  4 67 82 49 61 68 64 45 57
Card 186: 69  5 36 53 16 65 64 32 62 50 |  8  4  2 92 18 13 33  9 42 78 59 25 68 79 19 28 38 32 16 36 71 69 65 63 17
Card 187: 37 58 19 57 26 21 55 92 68 50 | 95 61 58 35  6 55 31 45 76 23 22 96 18 30 86 15 94 70 53 13 80 26 68 87 36
Card 188: 46 30 47 87 12 68 84 51 90 42 | 71 61 95 42 60 14 22 84 83 48 23 20 27 69 47 18 85 94 96 35 66 93 87  5 50
Card 189: 36 31  8 73 83 14 39 90 67 74 | 89 21 37 74 36 19  8 14 53 32 17 39 23 96 95 38 46 78 90  3 35 52 26 56 84
Card 190: 41 75 39 18 97 85 30 24 83 40 | 81 95 22 33 97 40 98 48 30 47 37 39 75 78 46 27 59 85 82 41  6 56 24 12 29
Card 191: 39 29 11 97 41 63 57 87 10 53 | 14 68 22 52 75 35 97 42 13 38 54 23 24  3 71 20  4 43 86 56 34 70 51 94 47
Card 192: 18 88 98 45 41 71 32 87 29 99 | 99 85 91  7 21 86  5 12 87 35 95 94 65 57 17 58 39 78 22 49 32  2  8 19 97
Card 193:  5 31 29 36 40 65 95 74  9 69 | 79 64 74 44  9 95 73 65 70 18 48 58 33 22 88 81 26 57 67 46 42 11 30  5 15
Card 194: 45 87 50 51 64 69 13 83 40 52 | 54 82 76 85 32 61 70 81 44 33  9 77 99 23 39 60 19 35 31 52 65 78 79 34 95
Card 195: 48 35 73 17 39 44 91 70 45 66 | 76  3 23 30 15 20 22 40 25  8 90 75 24 42 21 46 10  7 77 72 29 64 55 49 28
Card 196: 45 95 66 40 83 68 96 84 15 89 | 51 86 89 90 70 88 36 53 14 18 22 74 46 17 56  1  9 65 87 24 30 32 38 66 92
Card 197: 92 25  8 82 33  4 52 55 95 83 | 75 50 53 28 14 76 61 65 90 29 81 39 27 33 15 56 24 97 80 40 70 13 21 78 37
Card 198: 30 94 70 37 58 66  8 78 71 18 | 97 64 16 13 77 57 87 74 54 92  1 52 35 23 82  9 72 22 80 27 75 10 15 20 43
Card 199: 20 30 64 54 88 32 16 59 43 48 | 88  2 11 27 21 52 68 99 75 80 84 62 23 37 56 82 89  8  1 24 48 98  7 15 72
Card 200: 86 18 84 48 80 11 30 72  1 88 | 16 48 55 32 41 51 18  2 76 72 81 84 58 22 88 27 11 89  8 66 30 53 93 20 19
Card 201: 35 12 66 89 38 67 14 64 51  7 |  6 88 40 49 86 96 47 67 70 71 23  7 32 42 12 51 30 81 20 74 28 34 35 14 15
Card 202: 89 13 72 16 67 26 37 46 66 33 | 56 95 63 93 67 97 44 74 83 60 36 78 30 45 87 24 90 70 99 88 80 48 82 68 54
Card 203: 94 55 82 24 41 48 28 14 42 80 | 28 22 78 14 80 41 42 58 48 13 44 57 61 55 15 47 53 11 21 19 29 82 63 65 94
Card 204: 54 66 41 71  2 91 73 85 78 69 | 24 82 41 38 45 91  4 68 77 18 54 78 85 73 71  2 69 44 76 80 63 89  9 34 66
Card 205: 76  2 81 21 16 64 10  6 30 45 | 72 38  8 84 64 21 81 45 69  3 22  5 60 23 18 63 74  9  6 29 82  4 46 30 16
Card 206: 62 17 63 99  1 31 48 89 22 53 | 28 74 78 47 48 97 73 31 68 23 42 14  2 99 49 37 39  8 35 64 66 92 44 54 11
Card 207: 69 76 21 35 49 91 77 75 72 53 | 88 57 58 16 91 84 21 99 53 70 19 29  3 56 55 22 49 41  1 72 10 35  9 20 60
Card 208: 33 97 67 76 69 63 13 41 54 21 | 84 69  9 11 21 50 12 45 97 67 54 42 18 63 62 81 96 33 31 95 26 48 17 68 76
Card 209: 66 35  6 71 82 16 14 97 68 50 | 58 50 82 71 97 90 96 49 16 84 91 21 98 63 76 31 65 51  1 80 52 30 47 93 33
Card 210: 56 53 62 97 78 68 27 16 50 10 | 48 71 80 87  8 39 22  9 53 58 54  1 11 14 96  6 41 65 37 34 98 17 85 24 20
Card 211: 52  6 85 59 80 96 77 26 65 36 | 38 77 86 29 98 27 58 51  8 75 87 50 63  2 82  1 41 92 97 53 33 14 16 93 49
Card 212: 82 69 16 77 49 12 19 29 30 31 | 42 81 22 94 32 78 61 29 11  1 64 87 19 57 88 82 24  8  7 46  5 28 30 77 26
Card 213: 71 48 25 24 37 40 77 88 44 74 | 51 92 62 34 33 93 54 78  6 25 10 96 70 63 81 82 85 20 48 12 99 40 86  8 58
Card 214: 12 43 97 95 19 39 98 13 41 93 | 69 89 10 36 50 20 51 33 67 88 73 59 81 29 17 34 85 28 92 55  5 63 79 72 52
Card 215: 21 31 62 69 74 97 40 45 20 35 | 25 18  1 52 86 84 68 44 15 47 91 99 57 87 98 17 66 56 73 42 33 93 30  8 95
Card 216: 94 71 33 51 86 70 60 78 12 17 | 98 68 80 56 47 53 41 55 70 37 46 43  8 22 74 48 62  9 10 65  7 60 39 29 97
Card 217: 70 98 21 38 77 68 67 39 45 72 | 63 57 37 21 94 64  8 96 69 80 84 25 71 26 83 99 81 31 48 42 41 73 54 60 22
Card 218: 92 44 79 17 16 34 55 78 19  9 | 52 39 85 98 93 46 21 91 20 45  1 89 66 27  4 88 99 41 86 72 38 40 84 81 69"""
