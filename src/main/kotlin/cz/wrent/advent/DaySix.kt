package cz.wrent.advent

fun main() {
    val input = listOf(
        42L to 308L,
        89L to 1170L,
        91L to 1291L,
        89L to 1467L,
    )
    println(daySixPartOne(input))
    val input2 = listOf(
        42899189L to 308117012911467L,
    )
    println(daySixPartOne(input2))
}

fun daySixPartOne(input: List<Pair<Long, Long>>): Long {
    return input.map { (time, max) ->
        val results = (0..time)
            .map { holdTime ->
                val speed = holdTime
                speed * (time - holdTime)
            }
        results.count { it > max }
    }.reduce { acc: Int, i: Int -> acc * i }.toLong()
}

private const val input =
    """Time:        42     89     91     89
Distance:   308   1170   1291   1467"""
