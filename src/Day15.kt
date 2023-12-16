fun main() {
    //fun hash(s: String): Int = s.map { it.code }.reduce() {acc, cur -> ((acc + cur) * 17) % 256}
    fun hash(s: String): Int {
        var res = 0
        for (c in s) {
            res = ((res + c.code) * 17) % 256
        }
        return res
    }
    fun String.label(): String {
        if (this.endsWith('-')) {
            return this.substring(0, this.length-1)
        }
        return this.substring(0, this.length-2)
    }
    fun String.equalLabel(o: String): Boolean {
        return this.label() == o.label()
    }
    fun String.getNumber(): Int {
        return this[this.length - 1].code - '0'.code
    }
    fun part1(lines: List<String>): Int {
        return lines[0].split(",").sumOf { hash(it) }
    }

    fun part2(lines: List<String>): Int {
        val boxes = Array<ArrayList<String>>(256) { ArrayList() }

        lines[0].split(",".toRegex()).forEach() {
            val h: Int = hash(it.label())
            val idx = boxes[h].indexOfFirst { s -> s.equalLabel(it) }
            if (it.endsWith("-")) {
                if (idx != -1) boxes[h].removeAt(idx)
            }
            else {
                if (idx != -1) boxes[h][idx] = it else boxes[h].add(it)
            }
        }
        return boxes.withIndex()
            .sumOf {(i, box) -> box.withIndex()
                .sumOf { (j, s) -> (i + 1) * (j + 1) * (s!![s.length - 1].code - '0'.code)}}
    }

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}