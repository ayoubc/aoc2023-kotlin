fun main() {
    var isPart2 = false

    fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

    fun lcm(a: Long, b: Long): Long = a * (b / gcd(a, b))

    class P(args: Array<String>) {
        var left: String
        var right: String

        init {
            left = args[0]
            right = args[1]
        }
        fun getAttr(c: Char): String = if (c == 'L') left else right

    }
    fun readMap(lines: List<String>): Map<String, P> {
        val res: MutableMap<String, P> = HashMap<String, P>()
        for (i in 2 until lines.size) {
            val strs = lines[i].split(" = ".toRegex()).toTypedArray()
            res[strs[0]] =
                P(strs[1].substring(1, strs[1].length - 1).split(", ".toRegex()).toTypedArray())
        }
        return res
    }
    fun stopCondition(start: String): Boolean {
        return if (isPart2) start.endsWith("Z") else start == "ZZZ"
    }
    fun getStepsToEnd(start: String, instructions: CharArray, map: Map<String, P>): Long {
        var current = start
        var i = 0
        var ans = 0L
        while (!stopCondition(current)) {
            current = map[current]!!.getAttr(instructions[i])
            i = (i + 1) % instructions.size
            ans++
        }
        return ans
    }
    fun part1(lines: List<String>): Long {
        val instructions: CharArray = lines[0].toCharArray()
        val map: Map<String, P> = readMap(lines)
        return getStepsToEnd("AAA", instructions, map)
    }
    fun part2(lines: List<String>): Long {
        isPart2 = true
        val instructions: CharArray = lines[0].toCharArray()
        val map: Map<String, P> = readMap(lines)
        return map.keys
            .filter { it.endsWith("A") }
            .map { getStepsToEnd(it, instructions, map) }
            .reduce{acc, cur -> lcm(acc, cur)}
    }
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
