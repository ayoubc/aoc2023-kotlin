fun main() {
    class P(var x: Long, var y: Long)

    fun part1(lines: List<String>): Long {
        val points: MutableList<P> = ArrayList()
        var x: Long = 0
        var y: Long = 0
        var B: Long = 0
        for (line in lines) {
            val parts = line.split(" ".toRegex()).toTypedArray()
            val len = parts[1].toLong()
            B += len
            val direction = parts[0][0]
            if (direction == 'R') x += len
            if (direction == 'L') x -= len
            if (direction == 'U') y -= len
            if (direction == 'D') y += len

            points.add(P(x, y))
        }
        var area: Long = 0
        for (i in points.indices) {
            val j = if (i == 0) points.size - 1 else i - 1
            val p = points[j]
            val q = points[i]
            area += (p.y - q.y) * (p.x + q.x)
        }
        // Pick's Theorem
        // polygon has coordonates as integers
        // area = I + B / 2 - 1 ==> I points stricly inside, B points on polygon edges
        // Pick's Theorem
        // polygon has coordonates as integers
        // area = I + B / 2 - 1 ==> I points stricly inside, B points on polygon edges
        return Math.abs(area) / 2 + B / 2 + 1
    }

    fun part2(lines: List<String>): Long {
        val dirs = charArrayOf('R', 'D', 'L', 'U')
        val newLines: MutableList<String> = ArrayList()
        for (line in lines) {
            val parts = line.split(" ".toRegex()).toTypedArray()
            val color = parts[2].substring(1, parts[2].length - 1)
            val dir = dirs[color[color.length - 1].code - '0'.code]
            val hex = color.substring(1, color.length - 1)
            newLines.add(dir.toString() + " " + hex.toInt(16))
        }
        return part1(newLines)
    }

    val input = readInput("Day18")
    part1(input).println()
    part2(input).println()
}