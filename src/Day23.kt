class Tile(var i: Int, var j: Int, var tile: Char, var dist: Int) {
    fun getNexts(grid: Array<CharArray>): List<Tile> {
        val n = grid.size
        val m = grid[0].size
        val res: MutableList<Tile> = ArrayList()
        for (k in 0..3) {
            val I: Int = i + dx[k]
            val J: Int = j + dy[k]
            if (isValid(I, J, n, m) && grid[I][J] != '#') {
                if (!isPart2) {
                    if (tile == '.') {
                        if (dx[k] == 1 && grid[I][J] == '^'
                            || dx[k] == -1 && grid[I][J] == 'v'
                            || dy[k] == 1 && grid[I][J] == '<'
                            || dy[k] == -1 && grid[I][J] == '>') continue
                    }
                    if (tile == '>' && (dy[k] != 1 || grid[I][J] == '<')
                        || tile == '<' && (dy[k] != -1 || grid[I][J] == '>')
                        || tile == '^' && (dx[k] != -1 || grid[I][J] == 'v')
                        || tile == 'v' && (dx[k] != 1 || grid[I][J] == '^')) continue
                }
                res.add(Tile(I, J, grid[I][J], dist + 1))
            }
        }
        return res
    }
}
var ans = 0
var isPart2 = false
fun main() {
    fun backtrack(s: Tile, ei: Int, ej: Int, grid: Array<CharArray>, vis: Array<BooleanArray>) {
        if (s.i == ei && s.j == ej) {
            ans = Math.max(ans, s.dist)
            return
        }
        val nexts: List<Tile> = s.getNexts(grid)
        for (p in nexts) {
            if (!vis[p.i][p.j]) {
                vis[p.i][p.j] = true
                backtrack(p, ei, ej, grid, vis)
                vis[p.i][p.j] = false
            }
        }
    }
    fun part1(lines: List<String>): Int {
        val grid: Array<CharArray> = readGrid(lines)
        val n = grid.size
        val m = grid[0].size
        val si = 0
        val sj = 1
        val ei = n - 1
        val ej = m - 2
        ans = 0
        val vis = Array(n) { BooleanArray(m) }
        backtrack(Tile(si, sj, '.', 0), ei, ej, grid, vis)
        return ans
    }

    fun part2(lines: List<String>): Int {
        // will take sometime around 11 min
        // TODO: we can use edge contraction to accelerate the search by reducing options
        // consider tile with more than 3 adjacent as the only vertices of the graph
        isPart2 = true
        return part1(lines)
    }

    val input = readInput("Day23")
    part1(input).println()
    part2(input).println()
}