fun main() {
    var config = buildMap<String, Int> {
        put("red", 12)
        put("green", 13)
        put("blue", 14)
    }
    fun possibleGame(s: String): Int {
        val arr = s.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for (i in 2 until arr.size step 2) {
            val v: Int = Integer.valueOf(arr[i])
            val color: String = if (i + 1 == arr.size - 1) arr[i + 1] else arr[i + 1]
                .substring(0, arr[i + 1].length - 1)
            if (config[color]!! < v) {
                return 0
            }
        }
        return Integer.valueOf(arr[1].substring(0, arr[1].length - 1))
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { s -> possibleGame(s) }
    }

    fun powerOfGame(s: String): Long {
        val arr = s.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val set: MutableMap<String, Int> = HashMap()
        for (i in 2 until arr.size step 2) {
            val v: Int = Integer.valueOf(arr[i])
            val color: String = if (i + 1 == arr.size - 1) arr[i + 1] else arr[i + 1]
                .substring(0, arr[i + 1].length - 1)
            val prev = set[color] ?: 0
            set[color] = Math.max(v, prev)
        }
        var res: Long = 1
        for (d in set.values) res *= d.toLong()
        return res
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { s -> powerOfGame(s) }
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
