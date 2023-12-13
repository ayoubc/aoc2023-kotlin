fun isGood(s: String, brokenInts: List<Int>): Boolean {
    val brokenStr = s.split("\\.+".toRegex()).filter { it.isNotEmpty() }.toTypedArray()
    if (brokenStr.size != brokenInts.size) return false
    for (i in brokenStr.indices) {
        if (brokenStr[i].length != brokenInts[i]) return false
    }
    return true
}
fun countArrangements(idx: Int, origin: String, curS: String, broken: List<Int>): Long {
    if (curS.length == origin.length) {
        return if (isGood(curS, broken)) 1L else 0L
    }
    var res: Long = 0
    if (origin[idx] != '?') res += countArrangements(idx + 1, origin, curS + origin[idx], broken) else {
        res += countArrangements(idx + 1, origin, "$curS.", broken)
        res += countArrangements(idx + 1, origin, "$curS#", broken)
    }
    return res
}

fun main() {
    fun part1(lines: List<String>): Long {
        var ans: Long = 0
        for (line in lines) {
            val tmp = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val broken = tmp[1].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().map { it.toInt() }
            val x: Long = countArrangements(0, tmp[0], "", broken)
            ans += x
        }
        return ans
    }

    fun part2(lines: List<String>): Int {
        return lines.size
    }
    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
