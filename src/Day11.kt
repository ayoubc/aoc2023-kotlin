import kotlin.math.abs

var isPart2: Boolean = false

class P(var i: Int, var j: Int) {
    fun getDistFrom(o: P): Int = abs(i - o.i) + abs(j - o.j)
}

fun getExpansion(): Int = if (isPart2) 1000000 else 2

fun getEmptyRowAndCol(lines: List<String>): Array<ArrayList<Int>> {
    val n = lines.size
    val m = lines[0].length
    var res = Array<ArrayList<Int>>(2) { ArrayList() }
    for (i in 0 until n) {
        var isEmpty = true
        for (j in 0 until m) {
            if (lines[i][j] != '.') {
                isEmpty = false
                break
            }
        }
        if(isEmpty) res[0].add(i)
    }
    for (j in 0 until m) {
        var isEmpty = true
        for (i in 0 until n) {
            if (lines[i][j] != '.') {
                isEmpty = false
                break
            }
        }
        if(isEmpty) res[1].add(j)
    }
    return res
}
fun part1(lines: List<String>): Long {
    val n = lines.size
    val m = lines[0].length
    val galaxies: ArrayList<P> = ArrayList()
    for (i in 0 until  n) {
        for (j in 0 until m) {
            if (lines[i][j] == '#') galaxies.add(P(i, j))
        }
    }
    var ans = 0L
    var emptyRowAndCol: Array<ArrayList<Int>> = getEmptyRowAndCol(lines)
    for (i in galaxies.indices) {
        for (j in i + 1 until galaxies.size) {
            val g1 = galaxies[i]
            val g2 = galaxies[j]
            ans += g1.getDistFrom(g2).toLong()
            for (r in emptyRowAndCol[0]) {
                if (r >= Math.min(g1.i, g2.i) && r <= Math.max(g1.i, g2.i)) ans += getExpansion() - 1
            }
            for (c in emptyRowAndCol[1]) {
                if (c >= Math.min(g1.j, g2.j) && c <= Math.max(g1.j, g2.j)) ans += getExpansion() - 1
            }
        }
    }
    return ans
}

fun part2(lines: List<String>): Long {
    isPart2 = true
    return part1(lines)
}

fun main() {
    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
