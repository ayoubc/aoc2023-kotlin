import kotlin.collections.ArrayDeque

var dx = intArrayOf(0, 0, -1, 1)
var dy = intArrayOf(-1, 1, 0, 0)

fun isValid(i: Int, j: Int, n: Int, m: Int): Boolean = i in 0 until n && j in 0 until m

fun readGrid(lines: List<String>): Array<CharArray> = lines.map { it.toCharArray() }.toTypedArray()

fun getStart(grid: Array<CharArray>): IntArray {
    val n = grid.size
    val m = grid[0].size
    val res = IntArray(2)
    outerLoop@ for (i in 0 until n) {
        for (j in 0 until m) {
            if (grid[i][j] == 'S') {
                grid[i][j] = '.'
                res[0] = i
                res[1] = j
                break@outerLoop
            }
        }
    }
    return res
}
fun bfs(si: Int, sj: Int, grid: Array<CharArray>): Array<IntArray> {
    val n = grid.size
    val m = grid[0].size
    val q = ArrayDeque<P>()
    q.add(P(si, sj))
    val dist = Array(n) { IntArray(m) }
    while (!q.isEmpty()) {
        val cur: P = q.removeFirst()
        for (k in 0..3) {
            val I: Int = cur.i + dx[k]
            val J: Int = cur.j + dy[k]
            if (isValid(I, J, n, m) && grid[I][J] == '.' && dist[I][J] == 0) {
                dist[I][J] = dist[cur.i][cur.j] + 1
                q.add(P(I, J))
            }
        }
    }
    return dist
}
fun getMAXN(): Int = if (isPart2) 26501365 else 64

fun main() {
    fun part1(lines: List<String>): Int {
        val grid: Array<CharArray> = readGrid(lines)
        val n = grid.size
        val m = grid[0].size
        val start: IntArray = getStart(grid)
        val si = start[0]
        val sj = start[1]

        val dist: Array<IntArray> = bfs(si, sj, grid)
        var ans = 0
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (dist[i][j] > getMAXN()) continue
                if (si == i && sj == j) ans++ else if (dist[i][j] > 0 && (getMAXN() - dist[i][j]) % 2 == 0) ans++
            }
        }
        return ans
    }

    fun part2(lines: List<String>): Int {
        return lines.size
    }

    val input = readInput("Day21")
    part1(input).println()
    part2(input).println()
}
