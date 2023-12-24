package cz.wrent.advent

fun main() {
    println(dayFourteenPartOne(input))
    println(dayFourteenPartTwo(input, 1000000000, ((1000000000 - 1600) % 28) + 1599))
}

fun dayFourteenPartOne(input: String): Int {
    val map = getMap(input).toMutableMap()
    val rows = input.split("\n")
    val rowSize = rows.size
    val colSize = rows.first().length

    move(rowSize, colSize, map, Direction.N)
    return count(map, rows)
}

fun dayFourteenPartTwo(input: String, cycles: Long, stop: Long? = null): Int {
    val rows = input.split("\n")
    val map = dayFourteenPartTwoMap(input, cycles, stop)

    //1656, 1628, 1600 -> 28 mod
    // (1000000000 - 1600)

    return count(map, rows)
}

fun dayFourteenPartTwoMap(input: String, cycles: Long, stop: Long? = null): Map<Position, Char> {
    val map = getMap(input).toMutableMap()
    val rows = input.split("\n")
    val rowSize = rows.size
    val colSize = rows.first().length

    for (i in 0 until cycles) {
        move(rowSize, colSize, map, Direction.N)
        move(rowSize, colSize, map, Direction.W)
        move(rowSize, colSize, map, Direction.S)
        move(rowSize, colSize, map, Direction.E)
        println("$i: ${count(map, rows)}")
        if (stop == i) {
            break
        }
    }
    return map
}

fun Map<Position, Char>.asString(): String {
    val rowsTemp = this.keys.map { it.y }.toSet().map { mutableListOf<Pair<Int, Char>>() }

    this.entries.forEach { (pos, char) ->
        rowsTemp[pos.y].add(pos.x to char)
    }
    return rowsTemp.map {
        it.sortedBy { it.first }.map { it.second }.joinToString("")
    }.joinToString("\n")
}

private fun count(
    map: Map<Position, Char>,
    rows: List<String>
) = map.entries.filter { it.value == 'O' }.map { weight(it.key, rows.size) }.sum()

private fun move(
    rowSize: Int,
    colSize: Int,
    map: MutableMap<Position, Char>,
    direction: Direction
) {
    var moved = true
    while (moved) {
        moved = false
        for (row in 0..<rowSize) {
            for (col in 0..<colSize) {
                val current = map[Position(row, col)]
                if (current == 'O' && canMove(direction, map, row, col)) {
                    move(direction, map, row, col)
                    moved = true
                }
            }
        }
    }
}

enum class Direction {
    N, W, S, E;
}

private fun canMove(direction: Direction, map: Map<Position, Char>, row: Int, col: Int): Boolean {
    val current = Position(row, col)
    val prev = current.direction(direction)
    return map[prev] == '.'
}

private fun move(direction: Direction, map: MutableMap<Position, Char>, row: Int, col: Int) {
    val current = Position(row, col)
    val prev = current.direction(direction)
    val currentVal = map[current]!!
    val prevVal = map[prev]!!

    map[current] = prevVal
    map[prev] = currentVal
}

// private fun canMoveNorth(rows: List<String>, row: Int, col: Int): Boolean {
//     val prevRow = rows.getOrNull(row - 1) ?: return false
//     val char = prevRow[col]
//     return char == '.'
// }
//
// private fun moveNorth(rows: List<String>, row: Int, col: Int): List<String> {
//     val prevRow = rows[row - 1]
//     val currentRow = rows[row]
//     val newPrevRow = prevRow.substring(0, col) + "O" + prevRow.substring(col + 1)
//     val newRow = currentRow.substring(0, col) + "." + currentRow.substring(col + 1)
//     return rows.subList(0, row - 1) + newPrevRow + newRow + rows.subList(row + 1, prevRow.length)
// }

// private fun rowWeight(row: String, index: Int, size: Int): Int {
//     val cnt = row.count { it == 'O' }
//     return cnt * (size - index)
// }

private fun weight(position: Position, size: Int): Int {
    return size - position.y
}

private const val input =
    """.O..O#....O#..O.....O..#.##.....O#.O.....O.O#..#..##.O###..O.O..O....O.....O..O.......#.#.O....##O.#
..O....O.#O.O.#O...OO.#.#...O..O...##..OOO...OO.O.O....#........O.O..#....O##.#.....O.O#..........O.
....O...OO.#O.O.OO....#.O##O.#O...O.OOO###..O###OOO...OOO..O..##O..#...#......#..O#.O..O.O....OO....
O..O.O..O..O#O......#.OO..O#........###O..#...O...##.OO.....O#.#O.#....O....#.O#...O##O..#......O.O.
....#.O....#..O..##O#.#..#.O.OO...##O..##..O#..#.O#...O....#O.O..#......O.O##.#.#..O#O...O.....#.O..
.....O..OO.#...OO..##.#..O.O....#.......O..O...#.O.O##.O.##.O..O..#O.O.O.#.#.......#OO..O.#..O...O..
..#.#......#..OOO#..O....O..##.O..##.#....#.#......O.#OO...#..O.O.O....#..........O#...##...#.......
..##..O.O..O......#.O.....O...#.....#.OO...#.....##.#OO#....O...O...O....O##O.O#..#.O...O.#.....OO..
.O.OO.#...#O...O#....O.#....#.O#.O..........OO...#..#..OO....O#O#O..#O...........O......##.........O
O....O.O..O.O.##..#......OO##....O.......#..OO........#..#........#..#.O#O.O....O....O.OO#O.#.......
#O.#..#O..OO..O..........O#O..#.#....#.O..OOO..#....OO...........O.......O#.#.O..#..O#........#..#.O
#.#.O...#......#...#...O#.O...#O..O.O#...#O#.#.O...........O#.........OO.O#.......O...O.....O.##.O..
...#..O##O..OO....#..##.O.....O.OOO..O.O...O#........#..#O#O...O#O..#O....#O.O......#..#.O.##...O#.O
#.O....O.O......#O....O...##..OO...##..#..##.##....OO#...##O..OO.....OOO.OO..O....#.O........#.#.OO#
.O.O.O.O.O#.OO.....#...O##.O.O#O....#O.###..O...O..#....#...O.O........O.O#O##..O#..O...#.O..#....O.
..O...O.....#...OO...OO.O.O..OO#....#.OO..#..#........OO..OO#.OO..OO......#...O.#O........O.#O.O.O..
...OO.......##..#....O..OOO.#.#.O.....#......O....O....#.....O..##O#.#.O#...#O##..O..#....###.O..O..
.#.#O.O........O..O##.O....O#...O#.O..#...#....O.#.#.O...O.OO.O...O.OO#..O...#......O....###O.O#.O#O
.#O.O....O.#O.......O..OO.O.....OO...#.#..O.OOO.....#O.#...#.....#..O..#O.#..........##.OOO..#.O.#.O
O#.....O...O.#....#.......#..O..#....O.O#.....#O....#...##....#O..OO.#OO#O#.#...O.......O..O..OO#...
......#O#.#.O..#.O#.....OO.......#........O#O......##...O.....#OOO....#.O.##.........OO#...O##..#OO.
##.....#........#...#..O..........#.OOO......O#.O#...#O.OO.O..O.....#.O#.....O.O..##.#.OOOO#.O....#.
#O..#O...#OO.....O#..OO.O.O.#..O..##.OO...#OOO.#.O....O..O...O.#...O.O.O..#.O...#...OO....O.#....OO.
O..#.#.OOO.......O..#...O#.O........O...#..##O..#....O..O#..##O..O...O...O.....OO.#O#.#..##...#.#...
...........#OO....OO#......##...O.##..#.....OOOO..O...OO.O.O...#.........OO#O..#.#..O..#.#OO#...#...
...O..O...#.....#OO.##...O#.O#O.O.O..OO.#.O.....O.....#...O...#...#...O###.##...#O....##...O.......#
..O.O....#.O........O...#O..#......O.O..#.##OO.O....#O..O.OO..OO.#OO#O..O##.O......#O..#.#.#O##....#
....O.#...O.....O..O..........O...#.O.....O.....#.O....O...O#O..#O....O.O.O..O..#.......O#....#.....
..OO....O.#.O##.#O.O.......#...#.......O.........##.#....###.O..#O..O.O........#..O..#.O..O#...OO...
..#O.O.....O.O....##......#O..O..O..O.#..O#........#O.#.........#......###...............#.O...O#OO#
#....#...OO....O.O...#.OOO.O....O..#...O......OO.#.O...#O#O#..O.#O...O...OO......#....#.OO....#....O
..#O.#..#O.....#.#.O..#.O##O#...#.OOO....#.O....O...O.OO....#O.......#.#.#..O.......O.....O#.#..OO.#
...O......#....#O.##O.O.O....##...#..#.##O.#.#.O#..O.#...#..OO.O#...OO....OO...##...O..O#..O....OO#.
.O..OO#...O..#..O.O..O..O#...#O...#...##..#....#..O..#.#O..O#O.O...O.##..#......O.#..O...O...##.O.#.
.##.O.O.O.#.##..#...O..O#.O#O.OO...##O......##..OOO........#.#.#.O#OO#...O..O#.O....O##.#..O.#O...O.
.#.#..O#.#........O.O##.O.#O#........#.O..O.O...O...#OO.#.........#.OO.#.....OO....##..#.....O......
.........#O........#..#..O#.O.O...OO.O##.....O.O.#O#O.#.#.OO#.O.O...#OO.O#...O..O.O..#.#..O.O......#
..#O...#O##..O..........OO.........O.O#.#O......O.O...O.#.......OO.#..OO..O#.O.O..........#...#O...#
.O.O....#.##...O#...O..O.O...#..#..#OOO..#O.O##...#...O.O.OO.....#O..#OO..O.O.#...OO#OO.O.#.#...O..O
#.#.##.O....#..O#O#.#.O.#O...O..OO......#......#...#.O.....#....#O..O.#.........O.#..O.....###.#..#.
..O#..#OOO...#O.O..O....##O....O.O..O.......OO..#.#....#...O......#.OO.#......OOO#.O.......O..O.O...
#O#.O.O#.....#OO....#O.O.#...........O.#..........##.#..O#.......#..#.......#..##.O.O.O.O.#..O......
.O..O#....O.#...O.O..O..#.#..O.#....#O........#..#.#....O.#OOO......O..#O....O..O#.O#O.O.#..#O..#.#.
.OO....OO....O#O#.#..#...#..#....O#..O..OOO..O.O##...O..##O..##....O.O..#.O.O...O#.O.....O.O...O....
..OO#O..#O.#.......O#..O.....O.O.OO#OOO....OOOO#.OO....#.##.#O.....#.#.OO.O#....OO..O..............#
#..#......##.#O...O....OO.#O.........O.#..O.......##.O...#..O...O#OO........#...O..........OO.#.##..
O#..O....O..O#.O.O......OO..OO......O...O..O.O..#....O#..#...O.OOO.#.O...#O..#.#.#...O...#O.O.#...O.
......#.#..O.OO..O...##..#O.OOO#.#.O..#...O..#..OO.O#............O...#O......##..O.O#.O....O.O...#..
........#..........O...O.#..#.....OO.O#O...#OO.....O#O..#OO#..#..#...O..O#.#.#..O#OOO.......##.O.O..
O.#O...#O..#.OO..OO..O#...#.#OO#...O...O###...OO.#O...O.....#.#...OOO#.#...O#....#O...#O.O.....#..#.
..O...O......#....OOO..#..O.O....O.#O...#O.O..O#O.#..O....#..........#..#O.....#...#..#.O..#...##O..
O...#...O..O#O.OO..##......O.#..O##..OO...O....#O..OOO...O.OO.O..##..#.OO....#........#.##.#......#O
O..#.O......O..#.OOO.......#..........OO..O..##O#.....O..#.O.#..#..O...#.O#.O..O..#O.OO......OOO##..
..O#OO.#.........OO...#.O.O.OO#OOOO#O..O..O.#..#O.##.#O.........O.#...O.#.O....O.O..#O..O.O...OOOOO.
O.OO.#....#.#.......O.O...O#.##.O....O#.O..O.##.#....#.......O#..#...#.#.#..O.....#OO...OO#.#.#.O...
.....O....O.O#.....O.O.O.OO.....#O.#O...O#....###OO#...O.#...OOOO..OO.O....#....O.O....O..##OOO.#.O#
#O.O#OO#.O.O#O#.O#.OO..O#.O...OOO....O.#..O..#..#..#.....OO#O.##O#........OO.......#...O...O.....O..
....##..#..O#OO..#OO#....O.....#O.O..OO.O...#O.O.O....#O......O#.O##.#.......O##.#.#......O#..O#..#.
..OO.OO#O....#...O.O#..O.O..#O..O#O#..#.#.O.O#OO.O.OO...O.O.##..O.O.O..#......O.#.#.O.#O.....#..#.#O
#O...O.....#.#...O...O...OO...O.#.O..OO.....##....OO.....#.O.OO....O......##..O......#O..#.#...O..#.
.......O.....OO.#.......##......O..O.........O.O#.O###..O.#.O.O..OO..O#..OO.O.....#..........O#O#.O.
..#...#O.##..#..O.OO..#.O....#O#.O..O..OO...O.....O#...O.OO#O.#..O.O##O...##...#.........OO.O#.O...O
......OO.#O...#.#.....#O#.O......O#..O...O......#...#.##O.OO..O##O##.....O..O........O.......O....#.
.#.##..#.#..OO#......O.O...O..O.......#O.O#.....O#O..........#.........O.....O#...##......O..O#..OO.
....O.......OO..#.O....O..O......O.O.#....O..##...#O..#.#O..O..#O.....#..O....O..O.....#......##....
...##O.......#...#..O...OO......O....O#.........#O.OO.##.O#.O....#.OO#.#O#O..##..O.O#..OO..#...O.#..
O...#.O.O.O...#.##O.OO.......O.....OO...##..O..O..O.....##OO....#.....O..#.O..OO...O##.#.......#..O.
.#..O.#O.#.O......O#..OO#....O.O#..#.....#..O.OO.#.....OO...#.#.#OO..O.O.OO....#O........O...OO.....
...#.#.#O...O...........#.##..O.#..#..OO..O.O.O.#..#.......#...#.#....O.#.....#.OO#...#O.#..........
.O...O.#O#....#..OO#....#.....OO...#...OO..O.....O..#..........OOO......#....#..O#O##..#..O#..#.##..
.......#.................#.O..##OOO.O###..O##.O#..O......O....O#.##OO......#..O.......OO..O.....O#..
........#OOO....O#....#.O..##......O.#O.......#...O#.......#.#O.......#O....#.#OO..O......O....#.O..
.O.O#.O...#...#O.O.O#...O.O.....O.OO...O........O#...OO#.O..O.#....##O.....#...#O.#.....O...........
.O..O#O..#.OO......OO......#......OOOO..O.....#.#...O#..OO.O.....O#..O.O#.O..O.....#.#.O#......O#O.#
......OOO#O.O.O..........O#.........O.#O#.O.#..#....OOOOOO..O..##.....OO...#.....##.O...O#...O.OO#OO
...O.O......OO...##..##..#.........O....#O#..#O.#O#.O.##..#.O#.......##......#O.#......#.O.....#....
O....#....#.#...O...O.#..O##.....O.....OO..##..OO##..O.#O.OO..#........O....O..O......O.#.#..#...#..
.........#....O#.#..OOOO#O..O.....O.O..#...O...##O...O.......O....O.#OO#....#...#...#.#..#.O..#..O..
O.OO.OOO..O........#.........OO....O.##.#..O.OO.O............O...#O......O#.O.#O.O....#..O.O..#O..O.
OOO##OO.O..#O.O........#...#..#.O.#.O..O.#OO..OO.#.....OO#.....O....#...O..O.#.O#O..#..#.O..O...#.O.
O.......O...O#.OO#.O..#.....OO..O...##OO.O....#...OO#O...O#O...##OO..#..O.O....#.#.........OOO.##.O.
...O.....#.....#..#O#.O..O.........#O.#.#O.....O.OOO......O#..OO.O.......O..O......#O.O..O.O....#..O
.#.O.#..........OO...#...OO..O.......#.........O........O...#...#.O#........#...#.#.....##......O#O.
O.....O..#O.OO#..O..#..#....#OO...O.##OO..#..O...#.............#....#..O.O.#.O.#O.#O.........O..#O.#
.#..O#O.........O#...#O..#.#OO........O...#OO..OO.OO#....#O#...O#......O..O.#...O#O.O..O........#O..
O...#OO......OO#....O.....OO.......#.O..OO....OO......O......##.....#...O.O.OO.#....#.O..O....O#....
.O..O.O..O#.....#O..O.O#......O.....O..#.O.OOO..O..O.#OO.##....#..O.......O..#O.O.O.OO..#....O..O..O
..O.....O...#.#...OO.........#.#........#..OOO....##.......#O..OO.#O.O.O.O.#...##.O.#.#.O.OO........
.O..O...O...###....#....O...O#..#..#.O.O...###.O...#....#..OO....#O.O.OO#.#O.#..#..###.....O.#.O..O.
.#.O.O.OO.....##.#OO....#O...#O..#.O##.#.O.#.O.O#.......O..O.#..O..O...#.OO......OO...#....O#.#.O..O
O#O..#..O..O#....#....O.#O#..O#.O#.O.##..O..O.O.O#.O..O.....O..O#...#.O..#....#....O#...O....O..O...
..#.##.#........#O...OOO...#....#.#..O..O...#...#..#.#..#..OO....O#O#...#O.O.....O........O......OO.
..O.......O.O.#..O#O.#.#..##...O...O.#O..#O..#..O.#O#..#O...#O...#.O.#..#..##.#....#.#.OO.O.......#.
..#....O.......O....#..OO....#.#..#OO.........#.#O.#.O...O.O#O..O#O..#..OO.O......O.O..........##.##
O.OO.....#.O.##..O.O......OO...OO#OO...#.O.O.O#O.#.OO#O..O...O...O...#......#.O.......#.O....O.#..OO
.#.#....O#..#..#O#........OO#O......O....O...#..#O..#...#..#OO.##OO.##..O..#OO...O.#..O#....O..O..O.
O...#.#...O.####.O.O..##O.....#..O.....O....OO.##.OOOOO..O.OOOO.##O.#.O..............O#.O...#.O.O...
O..O.O..#..O.#.......#O.#O#O.O#.OOO.#O#.#......#.O....O...#....OO....##......OO##.O#..O.O.O......O#.
#...O..##.....#O..OO..O#..#......O...#O#.......##...#...O...O##.....O.....#.O.O..#..#.#..##...O###..
.O.O..OOO.....OO.....O...#.O.O..#..O#O..####.....#...#####..OO....#...#O.O.#.O........##..#..#OO...."""
