fun main() {
    val wordToInt = buildMap<String, Char>() {
        put("one", '1')
        put("two", '2')
        put("three", '3')
        put("four", '4')
        put("five", '5')
        put("six", '6')
        put("seven", '7')
        put("eight", '8')
        put("nine", '9')
    }
    fun part1(input: List<String>): Int {
        var res = 0;
        for(s in input) {
            var x = ""
            var last = '0'
            for (i in s.indices) {
                val c: Char = s[i]
                if (c in '0'..'9') {
                    if (x.isEmpty()) x += c
                    last = c
                }
            }
            x += last
            res += Integer.valueOf(x)
        }
        return res
    }

    fun part2(input: List<String>): Int {
        var res = 0;
        for(s in input) {
            var x = ""
            var last = '0'
            for (i in s.indices) {
                val c: Char = s[i]
                if (c in '0'..'9') {
                    if (x.isEmpty()) x += c
                    last = c
                } else {
                    var word = ""
                    for (j in i until s.length) {
                        if (word.length >= 6) break
                        word += s[j]
                        if (wordToInt.containsKey(word)) {
                            if (x.isEmpty()) x += wordToInt[word]
                            last = wordToInt[word]?.toChar() ?:
                            break
                        }
                    }
                }
            }
            x += last
            res += Integer.valueOf(x)
        }
        return res
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
