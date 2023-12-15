fun main() {
    fun readGrid(lines: List<String>): Array<CharArray> {
        return lines.map { it.toCharArray() }.toTypedArray()
    }
    fun copy(source: Array<CharArray>): Array<CharArray> {
        return source.map { it.copyOf() }.toTypedArray()
    }
    fun copy(source: Array<CharArray>, destination: Array<CharArray>) {
        val n = source.size
        val m = source[0].size
        for (i in 0 until n) for (j in 0 until m) destination[i][j] = source[i][j]
    }
    fun conditionSN(i: Int, n: Int, sign: Int): Boolean {
        return if (sign == -1) i > 0 else i < n-1
    }
    fun conditionWE(j: Int, m: Int, sign: Int): Boolean {
        return if (sign == -1) j > 0 else j < m - 1
    }
    fun tiltNS(grid: Array<CharArray>, sign: Int) {
        val n = grid.size
        val m = grid[0].size
        for (j in 0 until m) {
            var i = if (sign == -1) 0 else n - 1
            while (if (sign == -1) i < n else i >= 0) {
                if (grid[i][j] == 'O') {
                    var curI = i
                    while (conditionSN(curI, n, sign) && grid[curI + sign][j] == '.') curI += sign
                    if (curI != i) {
                        grid[i][j] = '.'
                        grid[curI][j] = 'O'
                    }
                }
                i -= sign
            }
        }
    }
    fun tiltWE(grid: Array<CharArray>, sign: Int) {
        val n = grid.size
        val m = grid[0].size
        for (i in 0 until n) {
            var j = if (sign == -1) 0 else m - 1
            while (if (sign == -1) j < m else j >= 0) {
                if (grid[i][j] == 'O') {
                    var curJ = j
                    while (conditionWE(curJ, m, sign) && grid[i][curJ + sign] == '.') curJ += sign
                    if (curJ != j) {
                        grid[i][j] = '.'
                        grid[i][curJ] = 'O'
                    }
                }
                j -= sign
            }
        }
    }
    fun runCycle(grid: Array<CharArray>) {
        tiltNS(grid, -1)
        tiltWE(grid, -1)
        tiltNS(grid, 1)
        tiltWE(grid, 1)
    }
    fun getLoad(grid: Array<CharArray>): Int {
        val n = grid.size
        return grid.withIndex().sumOf { (idx, v) -> (n - idx) * v.map { c -> if (c == 'O') 1 else 0 }.sum() }
    }
    fun part1(lines: List<String>): Int {
        val grid: Array<CharArray> = readGrid(lines)
        tiltNS(grid, -1)
        return getLoad(grid)
    }

    fun part2(lines: List<String>): Int {
        val grid: Array<CharArray> = readGrid(lines)
        var changed = true
        var cnt = 1
        while (changed && cnt <= 1000) {
            val cgrid: Array<CharArray> = copy(grid)
            runCycle(cgrid)
            changed = !grid.contentEquals(cgrid)
            copy(cgrid, grid)
            cnt++
        }
        return getLoad(grid)
    }

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
