fun main() {
    val dx = intArrayOf(-1, -1, -1, 0, 1, 1, 1, 0)
    val dy = intArrayOf(-1, 0, 1, 1, 1, 0, -1, -1)
    fun isSymbol(c: Char): Boolean {
        return c != '.' && (c < '0' || c > '9')
    }

    fun isGear(c: Char): Boolean {
        return c == '*'
    }

    fun isDigit(c: Char): Boolean {
        return c in '0'..'9'
    }

    fun isValid(i: Int, j: Int, r: Int, c: Int): Boolean {
        return (i in 0 until r) && (j in 0 until c)
    }

    class P(var i: Int, var j: Int)

    class G {
        var p: Long = 1
        var cnt = 0
        var seen = 0

        val ratio: Long
            get() = if (cnt == 2) p else 0L
    }

    fun part1(grid: List<String>): Long {
        val r: Int = grid.size
        val c: Int = grid[0].length
        var ans: Long = 0
        for (i in 0 until r) {
            var num = ""
            var ok = false
            for (j in 0 until c) {
                val d = grid[i][j]
                if (isDigit(d)) {
                    if (!ok) {
                        for (k in 0..7) {
                            val I: Int = i + dx[k]
                            val J: Int = j + dy[k]
                            if (isValid(I, J, r, c) && isSymbol(grid[I][J])) {
                                ok = true
                                break
                            }
                        }
                    }
                    num += d
                } else {
                    if (ok) {
                        ans += num.toLong()
                    }
                    ok = false
                    num = ""
                }
            }
            if (num.isNotEmpty() && ok) ans += num.toLong()
        }

        return ans
    }

    fun part2(grid: List<String>): Long {
        val r: Int = grid.size
        val c: Int = grid[0].length
        var ans: Long = 0L
        val gears: Array<Array<G?>> = Array<Array<G?>>(r) { arrayOfNulls<G>(c) }
        for (i in 0 until r) {
            var num = ""
            var current: MutableList<P> = ArrayList<P>()
            for (j in 0 until c) {
                val d = grid[i][j]
                if (isDigit(d)) {
                    for (k in 0..7) {
                        val I: Int = i + dx[k]
                        val J: Int = j + dy[k]
                        if (isValid(I, J, r, c) && isGear(grid[I][J])) {
                            if (gears[I][J] == null) gears[I][J] = G()
                            if (gears[I][J]?.seen == 1) continue
                            current.add(P(I, J))
                            gears[I][J]?.seen = 1
                        }
                    }
                    num += d
                } else if (num.isNotEmpty()) {
                    val curNum = num.toLong()
                    for (p in current) {
                        gears[p.i][p.j]?.seen = 0
                        if (gears[p.i][p.j]?.cnt == -1) continue
                        if (gears[p.i][p.j]!!.cnt > 2) {
                            gears[p.i][p.j]?.cnt = -1
                            continue
                        }
                        gears[p.i][p.j]!!.cnt++
                        gears[p.i][p.j]!!.p *= curNum
                    }
                    current = ArrayList<P>()
                    num = ""
                }
            }
            if (num.isNotEmpty()) {
                val curNum = num.toLong()
                for (p in current) {
                    gears[p.i][p.j]?.seen = 0
                    if (gears[p.i][p.j]?.cnt == -1) continue
                    if (gears[p.i][p.j]!!.cnt > 2) {
                        gears[p.i][p.j]!!.cnt = -1
                        continue
                    }
                    gears[p.i][p.j]!!.cnt++
                    gears[p.i][p.j]!!.p *= curNum
                }
            }
        }
        for (row in gears) {
            for (g in row) {
                if (g == null) continue
                ans += g.ratio
            }
        }

        return ans
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
