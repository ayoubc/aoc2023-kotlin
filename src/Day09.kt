fun main() {
    var isPart2 = false
    fun extrapolate(line: String): Long {
        var diffs: List<Long> = line.split(" ".toRegex()).map { it.toLong() }
        val elements: MutableList<Long> = ArrayList()
        var stop = false
        while (!stop) {
            elements.add(if (isPart2) diffs[0] else diffs[diffs.size - 1])
            val cur: MutableList<Long> = ArrayList()
            var cnt = 0
            for (i in 1 until diffs.size) {
                val diff = diffs[i] - diffs[i - 1]
                if (diff == 0L) cnt++
                cur.add(diff)
            }
            stop = cnt == cur.size
            diffs = cur
        }
        var ans: Long = 0
        if (isPart2) for (i in elements.indices.reversed()) ans = elements[i] - ans else ans = elements.sum()
        return ans
    }

    fun part1(lines: List<String>): Long {
        isPart2 = false
        return lines.map { extrapolate(it) }.sum()
    }

    fun part2(lines: List<String>): Long {
        isPart2 = true
        return lines.map { extrapolate(it) }.sum()
    }

    val input = readInput("Day09")
    part1(input).println() //2005352194
    part2(input).println()
}
