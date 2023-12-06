fun main() {

    fun part1(lines: List<String>): Long {
        val times: List<Long> = lines[0].split("\\s+".toRegex()).drop(1).map { it.toLong() }
        val distances: List<Long> = lines[1].split("\\s+".toRegex()).drop(1).map { it.toLong() }
        var ans: Long = 1
        for (i in times.indices) {
            var cnt = 0L
            for (j in 1 until times[i]) {
                cnt += if (j * (times[i] - j) > distances[i]) 1 else 0
            }
            if (cnt > 0) ans *= cnt
        }
        return ans
    }

    fun part2(lines: List<String>): Long {
        val time: Long = lines[0].split("\\s+".toRegex()).drop(1).joinToString("").toLong()
        val distance: Long = lines[1].split("\\s+".toRegex()).drop(1).joinToString("").toLong()

        var ans: Long = 0
        for (j in 1 until time) {
            ans += (if (j * (time - j) > distance) 1 else 0)
        }
        return ans
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
