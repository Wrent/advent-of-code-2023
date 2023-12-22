package cz.wrent.advent

fun main() {
    println(dayThirteenPartOne(input))
    println(dayThirteenPartTwo(input))
}

fun dayThirteenPartOne(input: String): Int {
    return input.split("\n\n").map { evaluate(it) }.sumOf { it.first + 100 * it.second }
}

fun dayThirteenPartTwo(input: String): Int {
    return input.split("\n\n")
        .mapIndexed { index, input ->
            replaceAndEvaluate(index, input)
        }
        .sumOf { it.first + 100 * it.second }
}

private fun replaceAndEvaluate(index: Int, input: String): Pair<Int, Int> {
    var res = 0 to 0
    var i = 0
    val old = evaluate(input)
    while (true) {
        if (i >= input.length) {
            println("nothing found for $index")
            break
        }
        val char = input[i]
        if (char == '\n') {
            i++
            continue
        }
        val newChar = if (char == '.') '#' else '.'
        val newString = input.substring(0, i) + newChar + input.substring(i + 1)
        res = evaluate(newString, old)
        if (res != 0 to 0) {
            break
        }
        i++
    }
    return res
}

private fun evaluate(input: String, old: Pair<Int, Int>? = null): Pair<Int, Int> {
    val col = findCol(input, old?.first)
    val row = findRow(input, old?.second)

    return col to row
}

private fun findRow(input: String, old: Int? = null): Int {
    val rows = input.split("\n")
    var res = 0
    for (i in 1..<rows.size) {

        if (i == old) {
            continue
        }
        val first = rows.subList(0, i).reversed()
        val second = rows.subList(i, rows.size)

        if (first.size > second.size) {
            if (first.take(second.size) == second) {
                res = i
                break
            }
        } else {
            if (second.take(first.size) == first) {
                res = i
                break
            }
        }
    }
    return res
}

private fun findCol(input: String, old: Int? = null): Int {
    return findRow(input.split("\n").flip().joinToString(separator = "\n"), old)
}

fun List<String>.flip(): List<String> {
    val res = this.first().map { StringBuilder() }
    for (row in 0..<this.size) {
        for (col in 0..<this.first().length) {
            res[col].append(this[row][col])
        }
    }
    return res.map { it.toString() }
}

private const val input =
    """...##......##.#
.#....#..##..#.
#.####.##.#....
##....##.#.#...
#.####.#######.
........#.#####
##.#..##....#.#
#.#..#.#.###...
#.#..#.#.###...

#.#.#.#.#
###.#.#.#
.##.#####
.##.#####
###.#.#.#
#.#.#.#.#
.#.##.#.#
#.#..#..#
..###.#..
#..#.#.#.
##.#...#.

##.....#...
##.....#...
#..###....#
##.#..#.##.
#..#####..#
#.#....#.#.
.##.##....#
.###..###.#
...#.......
....#.#.###
#......#.##
...#.##....
...#.##....
#......#.##
....#.#.###
...#.......
.###..#####

..####....#
..####....#
..#....#.##
.....#.#..#
#.#...##...
###.###.##.
..#..#...##
..#..#...##
###.###.##.
#.#...##...
.....#.#..#
..#.#..#.##
..####....#

#.#####..
#..#.#.#.
.##.#..#.
.##...##.
.....#.##
........#
........#

..#.###.##..#
.##.###.##..#
.#...#.#.#.##
.#...#.#.#.##
.##.###.##..#
..#.###.##..#
...#.#####.#.
##.#...######
..#..#..##.#.
#..####.###.#
.##..#....###

####.##....
....##.##.#
...##...##.
##..#..#..#
##..#....#.
##..#....#.
##..#..#..#
...##...##.
....##.##.#
####.##....
##..#####..
#..#....#..
...#.....##
....#####..
########...
#######.#.#
####.#.#...

..##.......
#..##.#..#.
.##....##..
.#####....#
#######..##
.#...##..##
..####.##.#
#####..##..
##.....##..
##.....##..
.####..##..

#.#..###.#.##.#
##.##.###.#..#.
##...#..#......
.##...##...##..
##...###.#.##.#
.##...#..######
.#.###.....##..
...###....#..#.
#..##..##.#..#.
#..##..##.#..#.
...###....#..#.
.#.###.....##..
.##......######

#.####.
#..##..
##.##.#
...##..
#.#..#.
#####.#
..#..#.
..#..#.
#####.#
#.#..#.
...##..
##.##.#
#..##..
#.####.
..####.

..##.####.#..#.##
..##..###.#..#.##
....###..#.##.#..
......##.######.#
#.#.#.#..#....#..
##..###...####...
#..##..#.#....#.#

##.##..##
##...##.#
####.#...
####.#...
##...##.#
##.##..##
.####.##.
.##.#...#
....#....
#.#..#...
#.#..#..#

##.##.#..#.
.#.###....#
#.#####..##
#.#####..##
#....#.....
##..#######
.####......
##.#.######
##.#.######
.####......
##..#######

####.##..
#..##..##
.....##..
#..######
####.##.#
####....#
.##..##..
####....#
#..##..##
.##.#..#.
#..#.##.#
.##......
....####.
.##..##..
.....##..

....##..#.#....
....##..#.#....
#####.#.#..###.
.##.#.###.####.
#.....#..#..###
.##....#.#....#
.##.##.#..##..#

.##....##.##..#
.##.#.#..#...#.
#..#.#######...
#..####.##.#..#
#.##.##.##..#.#
#..#..#.##..###
#..#..#.##..###

######...##
.#####...##
#.#####..##
#.#.#.#.#..
###.#..#...
..#.....##.
#....##.##.
#....##.##.
..#.....##.
###.#..#...
#.#.#.#.#..

...##.....#
.##...##...
.##...##...
.##...##...
.##...##...
...##.....#
.#######.#.
#...#..##..
.....##.#.#
.#.#.#.###.
.#.#.#.###.
.....##.#.#
#...#..##..
.##.####.#.
...##.....#

.##.##.
.##...#
....###
#..#...
#..#.##
.....##
#..##..
.#...##
#..#.##
.##.##.
.##..##
.##..##
.##.##.

.##.###........
##.#.####.##.##
.####...#.##.#.
#.##......##...
...###...####..
.#.......#..#..
.#.#..#...##...

##..##.##..#.
.####......#.
......#.#####
.####...#.#..
.#..#.#.##..#
#.##.##.#.##.
.#..#..#..##.
######.#.#...
#....###...#.
#....###...#.
######.#.#...
.#..#..##.##.
#.##.##.#.##.

###..####
##.##.###
.######..
...##....
...##....
.#....#..
##....###
.##..##..
##....###
###..##..
#.#..#.##

..##..##.
.#.#.##.#
.###.##.#
..##..##.
..#.#.#..
#.##....#
..####..#
###..#.##
#...##...
#...##...
###..#.##

####.##.#######
#...####...####
##.######.#####
##.######.##...
..#......#...##
###.####.######
####...######..
#..#.##.#..####
#.##.##.##.##..
.##.####.##.#..
#....##....####
..#.#..#.#...##
##..####..##...

...#.#.....##..
####.#.#.......
#.#....##..##..
..#..#.....##..
.##.#...#..##..
#.#..##........
...##..########
#######.#..##..
##.####..######
###...###.####.
#........######
.#.##..........
..##..#.###..##
.#.#.#..#..##..
.....###.##..##
#...#.##.......
##..##..#......

#..###.....##
...#...#...##
##.#.##.....#
..###..###.##
.###..##...##
###.#######..
#.###...##..#
#..##...##..#
###.#######..
.###..##...##
..###..###.##
##.#.##.....#
...#...#...##
#..###.....##
.#.##.##.##..
.#.##.##.##..
#..###.....##

##...##.#..#.
##...##......
###..##..##..
###.#........
....#########
....#########
###.#........
###..##..##..
#....##......
##...##.#..#.
##...#..####.
..#.##.......
#.##.########
#.#.##.##..##
##...#..####.

.#.##....##.##.
#####.####..#.#
.#....##..###.#
.#...#######..#
..##.##.#....#.
...#.#..##.#..#
...#.#..##.#..#
..##.##.#....#.
.#.#.#######..#
.#....##..###.#
#####.####..#.#
.#.##....##.##.
...#.#.##.##...
..#.#.#.#.#.###
##...#.#.....##
##...#.#.....##
..#.#.#.#.#.###

..#....#....##...
##..##..#########
.#......#.##..##.
###.##.###..##..#
##############.##
.########.#.##.#.
#..####..#.####.#
...#..#..........
#.######.##.##.##
.##....##...##...
#..####..#..##..#

#.##.#####.##..#.
#....#.###...##..
#....#.##..###..#
#....#.##..###...
#....#.###...##..
#.##.#####.##..#.
#######..#.####..
.........#..#....
########.#...#.##
#....#.###..#.##.
##..###..#.#..###
#....#...###.....
.####..#.#.#.....
######..##....###
######.###...#.#.

.#..#.########.#.
.####..######..#.
#....##......##..
.####.#..##..#.##
##..#####..#####.
######..#..#..###
#########..######
.####...#..#...##
######...##...###
#....#..#..#..#..
......###..###...
.#..#.#.####.#.#.
#.##.##.####.##.#
.####.#......#.##
##..##.##..##.##.

.##.#.#..
#.###....
###......
...#.####
##...##..
##..#..##
###.#....
###.#....
##..#..##
##...##..
...#.####
###......
#.###.#..
.##.#.#..
.######..

####....##.##.#
..##.##..##.#.#
..##.##..##.#.#
####..#.##.##.#
..##.#........#
#.....##....###
#.#..##...#....
.###..#..##.##.
#..#.#.#..##.#.
##.####.#####..
..##..#.##...##
#.##...##.#.#.#
#..#.##..###..#
#.##.######...#
#.##.######...#

##.##.##.
..####...
..####..#
########.
...##...#
..####..#
..####..#
.#....#..
..####...
##.##.###
###..####
..####..#
........#
##....###
##....###
..#..#..#
..####...

..########.
..########.
#.#..###..#
.#...#.....
..#...#....
.#.##..#..#
.#.....#..#
.....###...
#..####.##.
#..####.##.
.....###...
.#.....#.##
.#.##..#..#
..#...#....
.#...#.....
#.#..###..#
..########.

....##....#
..###.#..#.
.###.######
.###.######
..###.#..#.
....##....#
##..##.##.#
.###..#..#.
##...#.##.#
.##.#######
..#.##.....
.###.......
#######..##
##.#.##..##
#..##.####.

..###..#......#
##..#.##.#..#.#
...#.##..#..#..
..###.####..###
#####.#..####..
...#..###.##.##
..#.#.###..#.##
##..#..########
..##.#.########
.....###..##..#
..#.##.#......#
####.#.##.##.##
########.#..#.#
..####.##.##.##
..#.##..#....#.

#.####.#.####.#
..#..#...##.##.
.##..##.#......
.##..##.#......
..#..#..###.##.
#.####.#.####.#
########....##.
.#.##.#..##..##
.#.##.#.#.##..#
#..##..#.##...#
............#.#
##....###.....#
#.#..#.#.###.##

..######......#
.########.##.##
..#.##.#......#
..#.##.#......#
#.#....#.#..#.#
##.####.######.
###...####..###

...#.#.##..#...##
..#.#.#.########.
....#....##.#....
##.......#.....##
##...##...#.#..##
.#.#####.#....#..
.....#####.##.#..
##.#....#..###...
.#.#.##...####.##
.....######..##..
#.##..#....#.##..
##.....#...###...
#.##..#..#.##..##
#.##..#..#.##..##
##.....#...###...

#.###..##
.#.#....#
##.#..#..
.##.#..##
####.....
.###.....
.#...#.##
..#...#..
..#.#####
.#.#.....
.#.#.....
..#.#####
..#...#..
.#...#.##
.###.....

..##...#.##.#...#
#.#.##...##...##.
##.....#....#....
..#.##........##.
###.##.#.##.#.##.
##..##..#..#..##.
########....#####
.#.....##..##....
###.##.##..##.##.

##..#.##....#
.##..###....#
#.#.#....##..
#.##.#..####.
#.#.#.##....#
#..####......
#..####......
#.#.#.##....#
#.##.#..####.
#.#.##...##..
.##..###....#

...###....#
...###....#
..##..#.#..
..#.....#..
.........#.
###..#.##.#
##.#..#.###
.#.........
###.##..#..
..#.##.####
##...###.##
##..#...#..
##.#.##.##.
....#..##..
...#.#####.

.####..#.##..##..
.###....###..##..
#.#.#..#.#.#....#
#####..#####.##.#
##.##..##.##.##.#
###......####..##
##.#.##.#.##.##.#

.#....##.#....#
.#.##.#.#####..
###..#.###.###.
#...#.#.#..###.
.##.##..#....#.
###.#.#.#.##..#
###..#..#..#.#.
###..#..#..#.#.
###.#.#.#.##..#
.##.##..#....#.
#...#.#.#..###.
###..#.###.###.
.#.##.#.#####..
.#....##.#...##
.#....##.#...##

###...#..#.#.#.
.....#.####.#.#
####..#.#..#.##
#.#..##.###.###
##.#####.###.##
##.#####.###.##
#.#..##.###.###
###...#.#..#.##
.....#.####.#.#
###...#..#.#.#.
...#.###.#...##
.##.#......####
.##.#......####
...#.###.#...##
###...#..#.#.#.

..######..#
###.##.####
##.####.##.
.##....##.#
##.####.###
...#..#...#
###....###.
..#.##.#..#
..##..##...
..##..##...
##..##..##.
...#..#....
###.##.####
##.####.##.
....##....#

##.#....#.#
..#......#.
##...##...#
##........#
##.######.#
..##.##.###
...######..
....#..#...
..#.####.#.
..#.#..#.#.
###.####.##
...#.##.#..
##..#..#..#
..#..##..#.
##.######.#

###.##..#
.#####..#
....#....
###...##.
##.##.##.
####.....
..###....

##.##.##.##
##.########
...#......#
####..##..#
##..#....#.
###.######.
##.##....##
##.#..##..#
##.#.####.#
##..#....#.
######..###
##.........
###..####..
..#.#.##.#.
###..#..#..
...###.####
#####.##.##

.#..######..#..#.
#..###..###..##..
#...######...##..
..###....#.#....#
#....####....##..
.##..####..##..##
.#.##....##.#..#.
###.#.##.#.######
##..##..##..####.
#.#..#..#..#.##.#
...#..##..#......

##.#..#
###...#
.###.##
.###.##
###...#
##.#..#
..#..##
##..#..
.#.....
.#.....
##..#..
#.#..##
##.#..#

####.#.
..#...#
##...#.
..#..##
###.##.
....#..
##.####
##....#
..#####
..#####
##....#
##.####
....#..
###.##.
..#..##
##...#.
..#.#.#

..##.###.##.###
#..#.##.####.##
##.##..######..
###..##.####.##
.####..........
..##..########.
.##..##......##
.##..##......##
..##..########.
.####..........
###..##.####.##
##.##..######..
#..#.##.####.##
..######.##.###
#....#.#.##.#.#

#####..
##.#.##
..#####
.##..##
..#..##
...#.##
##.##..
.####..
##.....
.#..#..
.#..#..
##.....
..###..
##.##..
...#.##

..####.##.#
.#.##.#..#.
.#....#....
.######...#
##.##.##...
.######...#
.#....#.##.
###..###..#
##....#####
..#..#...#.
..#..#...#.

###..#.
...###.
.##.###
.#####.
##..###
.###.##
.#..#..
..###..
..###..
.#..#..
####.##
##..###
.#####.
.##.###
...###.
###..#.
###..#.

..#######
...######
...##....
##.#..###
..#.#.###
.##....##
######...
#..######
.#.####..
.#..##...
..###....
.#####.##
##.###...

...##....##
...##....##
#.####.#...
.#.##.#.##.
.#....#.###
.######..#.
.........##
..####....#
.#....#...#
...##...#.#
##.##.####.
#..##.##...
#.#..#.##..

######...#....#
..##...........
..##..#.##.##.#
##..####.#.##.#
#.##.##.#.####.
.####..##.####.
########.######
#.##.##.###..##
.#..#..#.#.##.#
.####.#.#......
#.##.#...####.#

#...#.###
.###..##.
..##.#.##
.##.#.#..
..#.##.#.
.#..#..#.
.#.#.#.##
.#.###.##
.#..#..#.
.#..#..#.
.#.###.##
.#.#.#.##
.#..#..#.

....#.##..###
#######.#.#..
##.#..##.....
####.#.#.#.##
#####......##
...###.###.#.
......####.##

.#..###..###..#..
.#.#..#..#..#.#..
.#..##.##.##..#..
.......##........
##.##.#..#.##.###
###..######..####
##.##.#.##.##.###
#.#..######..#.##
..#...####...#...

#....#....#
.####....#.
#....##.#..
#....####..
.####....#.
#....#....#
.####....##

########.
##.##.###
...##...#
..####..#
#.#..#.#.
##....###
##....###
###..###.
###..###.
...##...#
...##...#
..####...
........#

#.###..##
##...##..
#.#......
#....##..
#....##..
..#.####.
..#......

..#..###...#.
..#..###...#.
...##...##.##
##....##...#.
##.##.##.#..#
...######...#
..#..#...#.##
##...##..#...
#..#.##..#..#
....#.#.#.##.
###..#.#.##.#

#..#...
...##.#
.#.....
..#.#.#
..#.#.#
.#.....
...####
#..#...
#.#.#.#
#.#.#.#
#..#...
...####
.#.....

..##.####.##...##
..##.####.##...##
..#..#..#..#..##.
.##.##..##.##.#..
#...##..##...#.#.
#.##......##.#.#.
.##..#..#..##.#..
#...##..#....####
######..#######.#
.###.####.###..#.
.##..####..##.#..

##.#.######..#.##
.##..#..#.###.#..
#####..#####...##
#####..#####...##
.##..#..#.###.#..
##.#.######..#.##
.##.###....###.##
.##..####..#....#
.##.####.#.#.####
..#..#.#...####..
.###...######.###

...#.#.###.
...#.#.###.
###...##.##
......##..#
#.#..#..#..
.#.#..#...#
##.##..##..
#..#.#..##.
#..###..#..
#.####..#..
#..#.#..##.

#.#....#...
.#.###.###.
..##.##...#
.#..###..##
..#...#..##
..#...#..##
.#..###..##
..##.##...#
.#.##..###.
#.#....#...
...#####...
...#####...
#.#....#...
.#.##..###.
..##.##...#

#.#####..#.#.#.#.
##..#..#.##.##..#
..##......#...#..
####...###.#.#.#.
...#.#.#....#####
....#....##.##.#.
....#....##.##.#.
#..#.#.#....#####
####...###.#.#.#.
..##......#...#..
##..#..#.##.##..#
#.#####..#.#.#.#.
#.#####..#.#.#.#.

#.#..##..##.#
##..#...###..
#.##.#....#..
#.##.#....#..
##..#...###..
#.#..##..#..#
##.##.#..#..#
...#######.#.
.###.##....##
.#.#....#...#
.##.##..##...
###.#.#.#.###
###.#...###..
.#.###.....#.
#.#.####..##.
#.#.####..##.
.#.###.....#.

.##..#.####..
.#.#.#.######
#.###..###...
#..##.#.#####
###..#.#.#.#.
#.##.#.##.###
...#.###.####
##.#.##....##
##.#.##.#..##
.#.#..####.##
##.###..#..#.
##.###..#..#.
.#.#..####.##
##.#.##.#..##
##.#.##....##

#...##..#
#####....
#####....
#...##..#
.#....###
.#.#..###
#...##..#

#......##
#......#.
.##..##..
.##..##..
#......##
###..####
#......##
#.#..#.##
##.##.###
..#..#..#
........#
..####...
.#.##.#..
........#
........#
.#.##.#..
.######..

#........#..#..#.
##..##..##..##..#
#..####..####..##
#.#....#.#..#.#..
.#......#.##.#...
.##....##.##.##..
##.#..#.######.#.
###.##.########.#
..#....#..##..#..

..........###..##
####..#######.#..
##.#..#.###....##
#........#.###...
####..####.#...##
.##....##.##.#...
##.####.##..###..
###.##.###...##..
##.####.##.##.###
.#..##..#.###..##
##..##..###.##...
.########.#.##.##
.#.####.#...###..
.###..###.#######
#.#.##.#.##...#.#
..#.##.#..##.#.##
#........###.##..

..#.###
.#.....
..#.##.
..#.##.
.#.....
..#.###
#.####.
.##...#
.#.###.
.#.###.
.#....#

#..###.
####..#
####..#
#..###.
.##.##.
###.###
...####
...####
###.###
.#..##.
#..###.

#....#...#...
#....#.####.#
#....#.####.#
#....#...#...
......######.
.####.#..##.#
#######..#.##
.##.#..##.#.#
#....######.#

..#....#.##..##
.#....####.##..
.#.##...#######
#.##..###......
#.#.....#######
##.#.....######
###...##...##..
..##..##.#....#
###.##...#....#
..###.##.......
#.##...##......
.##...###.#..#.
.##...###.#..#.
#.##...##......
..###.##.......
###.##...#....#
..##..##.#....#

....#...#.#.#..
##...#.#.#.....
.####.##.......
.......#.#.##.#
#..#..#.#..#.#.
#..##.#.#..#.#.
.......#.#.##.#
.####.##.......
##...#.#.#.....
....#...#.#.#..
....#...#.#.#..

#..####..#..#
#..#..#..####
#..#.....#..#
##..##..#####
#..####..#..#
##..##..##..#
.###..###....

..#....##
...##..#.
...######
##.#.#..#
######.#.
......#.#
#####.##.
...##....
##.##.##.
...##....
##..#####
##..#####
...##..#.
##.##.##.
...##....
#####.##.
......#.#

####.###.
####..#..
####..#..
####.###.
#..#.####
......##.
#..#.##..
....#.##.
#..#..###
##.##..##
.##......

###.#.#.#
###.#.#.#
...#.#.##
#.##..#..
#.###.##.
#.####.#.
##.######
#.##..#..
#.##..#..
##.######
#.####.#.
#.######.
#.##..#..
...#.#.##
###.#.#.#

...#.#.##.#
...#.#.##.#
#.#.#.#...#
#.###...##.
#..##.##.##
##..####.#.
##..####.#.
#..##.##.##
#.####..##.

#.#.#.##.###.
..##.#..##...
..##.#..##...
..#.#.##.###.
.#.#.##....#.
..#.#.###.#.#
..#.#.###.#.#

...#......#..
##...#..#...#
###..#..#..##
...#.####.#..
..##.#..#.##.
....######...
....##...#...

....#.##.########
.####.##.##......
..##..###.#.#.#.#
########....#.##.
..##......#.#.##.
.#..#...#..###.##
.#..#...#..###.##

##...#..###
..##....###
....#..#...
#####..#...
##.#..####.
##.#..####.
#####..#..#

#.###..##.###..
#....#..##.####
#.#####.##.#...
..#####.....#..
###.####..#####
##.#.....#.#...
#.#....##..####
##..#...#..##..
.#...#.#..##.##
##..#.##.##.#..
#.#.#####.#....
##..##.#..#....
.#..##.....##..
#..#...#...#...
#.##...#...#...
..##.#....#.###
..##.#...##.###

##..###..#.####
.###.#.##..####
###...#..#.....
.........##.##.
..#....##.#.##.
###...#...#....
..#..#....##..#
#.....#.##.....
#...#...#......
.#..##..###....
.#.###..###....

.##.#..#.....
########.....
###..#...####
####.#.##.##.
......#.#.##.
#..#.##.#.##.
####.##.#####
.....#..#####
.##..##.#.##.
.##...##.####
.....##.#####
.##....###..#
.##.#.#...##.
.##.#.#......
#..#..##..##.
....##.......
####.#..#....

.###.##.#
.###.##.#
##.#.....
####.##.#
##...####
....###..
.#....#..
#..#.####
#..#.####
.#...##..
....###..
##...####
####.##.#
##.#.....
.###.##.#

...#.######
...#.######
#.#.#.#....
###...##...
##.########
...#.#.....
...##.#.##.
.#...###..#
..####.####
.#.#.#.....
#......####

######...#...
#.##.##.#####
######...#...
.#..#..#####.
......###..#.
..##....#.#..
..##..#.##.##
..##..#.##.##
..##....#.#..
......###..#.
.#..#..#####.
######...#...
#.##.#..#####
######...#...
#.##.#.#..###"""
