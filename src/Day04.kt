fun main() {
    fun countWins(line: String): Int {
        var res = 0
        val winning: MutableSet<String> = HashSet()
        val nums = line.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var i = 2
        while (nums[i][0] != '|') winning.add(nums[i++])
        while (i < nums.size) {
            if (winning.contains(nums[i++])) res++
        }
        return res
    }

    fun part1(lines: List<String>): Int {
        var ans = 0
        for (line in lines) {
            val cnt = countWins(line)
            ans += if (cnt == 0) 0 else 1 shl cnt - 1
        }
        return ans
    }

    fun part2(lines: List<String>): Int {
        val occ = Array(lines.size) { 1 }
        for (i in lines.indices) {
            val cnt = countWins(lines[i])
            for (k in 1..Math.min(cnt, lines.size - i - 1)) occ[i + k] += occ[i]
        }
        return occ.sum()
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
