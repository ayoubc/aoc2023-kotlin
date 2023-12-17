fun main() {
    val dx = intArrayOf(0, 0, -1, 1)
    val dy = intArrayOf(-1, 1, 0, 0)
    fun readGrid(lines: List<String>): Array<CharArray> {
        return lines.map { it.toCharArray() }.toTypedArray()
    }

    class Tile(var i: Int, var j: Int, var type: Char, var dIn: Char) {
        val dirs = "lrud"
        fun getNext(grid: Array<CharArray>): List<Tile> {
            val res: MutableList<Tile> = ArrayList()
            val outs = getOutDirection()
            val n = grid.size
            val m = grid[0].size
            for (c in outs) {
                val k = dirs.indexOf(c)
                val I: Int = i + dx[k]
                val J: Int = j + dy[k]
                if (isValid(I, J, n, m)) res.add(Tile(I, J, grid[I][J], getOpposite(c)))
            }
            return res
        }

        fun isValid(i: Int, j: Int, n: Int, m: Int): Boolean = (i in 0 until n) && (j in 0 until m)

        fun getOpposite(c: Char): Char {
            if (c == 'l') return 'r'
            if (c == 'r') return 'l'
            return if (c == 'u') 'd' else 'u'
        }

        fun getOutDirection(): CharArray {
            var res = ""
            if (type == '.') {
                if (dIn == 'r') res += 'l'
                if (dIn == 'l') res += 'r'
                if (dIn == 'u') res += 'd'
                if (dIn == 'd') res += 'u'
            }
            if (type == '\\') {
                if (dIn == 'r') res += 'u'
                if (dIn == 'l') res += 'd'
                if (dIn == 'u') res += 'r'
                if (dIn == 'd') res += 'l'
            }
            if (type == '|') {
                if (dIn == 'r' || dIn == 'l') res += "ud"
                if (dIn == 'u') res += 'd'
                if (dIn == 'd') res += 'u'
            }
            if (type == '-') {
                if (dIn == 'u' || dIn == 'd') res += "lr"
                if (dIn == 'l') res += 'r'
                if (dIn == 'r') res += 'l'
            }
            if (type == '/') {
                if (dIn == 'r') res += 'd'
                if (dIn == 'l') res += 'u'
                if (dIn == 'u') res += 'l'
                if (dIn == 'd') res += 'r'
            }
            return res.toCharArray()
        }
    }

    fun bfs(si: Int, sj: Int, sIn: Char, grid: Array<CharArray>): Long {
        val n = grid.size
        val m = grid[0].size
        val vis = Array(n) { IntArray(m) }
        val q = ArrayDeque<Tile>()
        q.add(Tile(si, sj, grid[si][sj], sIn))
        vis[si][sj] = 1
        while (!q.isEmpty()) {
            val cur = q.removeFirst()
            val nexts = cur.getNext(grid)
            for (t in nexts) {
                if (vis[t.i][t.j] < n) {
                    vis[t.i][t.j]++
                    q.add(t)
                }
            }
        }
        return vis.sumOf { it.map { x -> if (x > 0) 1 else 0 }.sum() }.toLong()
    }
    fun part1(lines: List<String>): Long {
        val grid = readGrid(lines)
        return bfs(0, 0, 'l', grid)
    }

    fun part2(lines: List<String>): Long {
        val grid: Array<CharArray> = readGrid(lines)
        val n = grid.size
        val m = grid[0].size
        val sJIn = charArrayOf('l', 'r')
        val sIIn = charArrayOf('d', 'u')
        val sI = intArrayOf(0, n - 1)
        val sJ = intArrayOf(0, m - 1)
        var ans: Long = 0
        for (k in 0..1) {
            for (i in 0 until n) {
                val res: Long = bfs(i, sJ[k], sJIn[k], grid)
                ans = Math.max(ans, res)
            }
            for (j in 0 until m) {
                val res: Long = bfs(sI[k], j, sIIn[k], grid)
                ans = Math.max(ans, res)
            }
        }
        return ans
    }
    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}