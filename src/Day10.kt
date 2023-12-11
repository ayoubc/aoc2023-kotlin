fun main() {
    val dx = intArrayOf(0, 0, 1, -1)
    val dy = intArrayOf(1, -1, 0, 0)
    class P(var i: Int, var j: Int)

    fun connectedDir(c: Char): Char {
        if (c == 's') return 'n'
        if (c == 'n') return 's'
        return if (c == 'e') 'w' else 'e'
    }
    class Pipe(var symbol: Char, vararg args: Char) {
        var dirs: CharArray
        var dx: IntArray
        var dy: IntArray

        init {
            dirs = args.clone()
            val res = getNiegbour(symbol)
            this.dx = res[0]
            this.dy = res[1]
        }

        private fun getNiegbour(c: Char): Array<IntArray> {
            var ans = arrayOf(intArrayOf(0, 0), intArrayOf(0, 0))
            when (c) {
                '|' -> ans = arrayOf(intArrayOf(-1, 1), intArrayOf(0, 0))
                '-' -> ans = arrayOf(intArrayOf(0, 0), intArrayOf(-1, 1))
                'L' -> ans = arrayOf(intArrayOf(-1, 0), intArrayOf(0, 1))
                'J' -> ans = arrayOf(intArrayOf(-1, 0), intArrayOf(0, -1))
                '7' -> ans = arrayOf(intArrayOf(1, 0), intArrayOf(0, -1))
                'F' -> ans = arrayOf(intArrayOf(1, 0), intArrayOf(0, 1))
            }
            return ans
        }

        fun canConnect(p: Pipe, k: Int): Boolean {
            val dir: Char = connectedDir(getDir(k))
            for (c in p.dirs) {
                if (c == dir) return true
            }
            return false
        }

        fun getDir(k: Int): Char {
            return if (this.dx[k] == 0) {
                if (this.dy[k] == 1) 'e' else 'w'
            } else {
                if (this.dx[k] == 1) 's' else 'n'
            }
        }
    }
    val pipeMap: Map<Char, Pipe> = buildMap {
        put('|', Pipe('|', 'n', 's'))
        put('-', Pipe('-', 'e', 'w'))
        put('L', Pipe('L', 'n', 'e'))
        put('J', Pipe('J', 'n', 'w'))
        put('7', Pipe('7', 'w', 's'))
        put('F', Pipe('F', 'e', 's'))
    }
    fun isValid(i: Int, j: Int, n: Int, m: Int): Boolean {
        return (i in 0 until n) && (j in 0 until m)
    }

    fun canConnectFromS(k: Int, p: Pipe): Boolean {
        var dir: Char
        if (dx[k] == 0) {
            dir = if (dy[k] == 1) 'e' else 'w'
        } else {
            dir = if (dx[k] == 1) 's' else 'n'
        }
        dir = connectedDir(dir)
        for (c in p.dirs) {
            if (c == dir) return true
        }
        return false
    }
    fun bfs(grid: Array<CharArray>, n: Int, m: Int, dist: Array<IntArray>) {
        val vis = Array(n) { BooleanArray(m) }
        val q: ArrayDeque<P> = ArrayDeque()
        var start = P(0 ,0)
        for (i in 0 until n) {
            var found = false
            for (j in 0 until m) {
                if (grid[i][j] == 'S') {
                    start = P(i, j)
                    vis[i][j] = true
                    found = true
                    break
                }
            }
            if (found) break
        }
        for (k in 0..3) {
            val i: Int = start.i + dx[k]
            val j: Int = start.j + dy[k]
            if (isValid(i, j, n, m) && grid[i][j] != '.') {
                val pipe: Pipe = pipeMap[grid[i][j]]!!
                if (canConnectFromS(k, pipe)) {
                    vis[i][j] = true
                    dist[i][j] = 1
                    q.add(P(i, j))
                }
            }
        }
        while (!q.isEmpty()) {
            val cur: P = q.removeFirst()
            val pipe: Pipe = pipeMap[grid[cur.i][cur.j]]!!
            for (k in 0..1) {
                val I: Int = cur.i + pipe.dx[k]
                val J: Int = cur.j + pipe.dy[k]
                if (isValid(I, J, n, m) && grid[I][J] != '.' && !vis[I][J]) {
                    val next: Pipe = pipeMap[grid[I][J]]!!
                    if (pipe.canConnect(next, k)) {
                        vis[I][J] = true
                        dist[I][J] = dist[cur.i][cur.j] + 1
                        q.add(P(I, J))
                    }
                }
            }
        }
    }

    fun getGrid(lines: List<String>): Array<CharArray> {
        val n = lines.size
        val m = lines[0].length
        val grid = Array(n) { CharArray(m) }
        var i = 0
        for (line in lines) grid[i++] = line.toCharArray()
        return grid
    }

    fun part1(lines: List<String>): Int {
        val grid: Array<CharArray> = getGrid(lines)
        val n = grid.size
        val m = grid[0].size
        val dist = Array(n) { IntArray(m) }
        bfs(grid, n, m, dist)
        return dist.map { it.max() }.max()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
