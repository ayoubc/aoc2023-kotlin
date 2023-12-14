fun main() {
    var isPart2 = false
    var seek = 0

    fun readPattern(lines: List<String>): List<CharArray> {
        val res: MutableList<String> = ArrayList()
        while (seek < lines.size) {
            val line = lines[seek]
            seek++
            if (line.isEmpty()) break
            res.add(line)
        }
        return res.map { it.toCharArray() }
    }
    fun getColRefs(pattern: List<CharArray>): Long {
        val r = pattern.size
        val c = pattern[0].size
        var res: Long = 0
        for (j in 0 until c) {
            var left = j
            var right = j + 1
            val len = Math.min(c - j - 1, j + 1)
            if (len == 0) continue
            var diff = 0
            while (left >= j - len + 1 && right <= j + len) {
                for (i in 0 until r) {
                    if (pattern[i][left] != pattern[i][right]) diff++
                }
                left--
                right++
            }
            if (!isPart2 && diff == 0 || isPart2 && diff == 1) res += (j + 1).toLong()
        }
        return res
    }
    fun getRowRefs(pattern: List<CharArray>): Long {
        val r = pattern.size
        val c = pattern[0].size
        var res: Long = 0
        for (i in 0 until r) {
            var up = i
            var down = i + 1
            val len = Math.min(r - i - 1, i + 1)
            if (len == 0) continue
            var diff = 0
            while (up >= i - len + 1 && down <= i + len) {
                for (j in 0 until c) {
                    if (pattern[up][j] != pattern[down][j]) diff++
                }
                up--
                down++
            }
            if (!isPart2 && diff == 0 || isPart2 && diff == 1) res += ((i + 1) * 100).toLong()
        }
        return res
    }
    fun part1(lines: List<String>): Long {
        seek = 0
        var ans: Long = 0
        while (seek < lines.size) {
            val pattern = readPattern(lines)
            val rowRefs: Long = getRowRefs(pattern)
            val colRefs: Long = getColRefs(pattern)

            ans += colRefs + rowRefs
        }
        return ans
    }

    fun part2(lines: List<String>): Long {
        seek = 0
        isPart2 = true
        return part1(lines)
    }
    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
