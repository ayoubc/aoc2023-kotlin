var seek = 0
class Interval {
    var l: Long
    var r: Long

    constructor(): this(1L, 4000L)

    constructor(intv: Interval) : this(intv.l, intv.r)
    constructor(left: Long, right: Long) {
        l = left
        r = right
    }
    fun setLeft(v: Long) {
        l = v
    }
    fun setRight(v: Long) {
        r = v
    }
    fun count(): Long = Math.max(0L, r - l + 1L)
}
class Part {
    var mp: MutableMap<Char, Long> = HashMap()
    var imp: MutableMap<Char, Interval> = HashMap<Char, Interval>()
    val attrs = "xmas"
    constructor() {
        for (c in attrs) imp[c] = Interval()
    }
    constructor(part: Part) {
        imp = HashMap<Char, Interval>()
        for ((key, value) in part.imp) {
            imp[key] = Interval(value)
        }
        mp = HashMap(part.mp)
    }
    constructor(line: String) {
        val parts = line.split(",".toRegex()).toTypedArray()
        for (p in parts) {
            mp[p[0]] = p.substring(2).toLong()
        }
    }
    fun getSum(): Long = mp.values.sum()
    fun getValue(c: Char): Long = mp[c]!!
    fun getInterval(c: Char): Interval = imp[c]!!
    fun setInterval(c: Char, intv: Interval) {
        imp[c] = intv
    }
    fun countCombinations(): Long = imp.values.map { it.count() }.reduce { acc, cur -> acc * cur }
}
fun readWorkFlows(lines: List<String>): Map<String, Array<String>> {
    val res: MutableMap<String, Array<String>> = HashMap()
    for (line in lines) {
        seek++
        if (line.isEmpty()) break
        val parts = line.split("[{}]".toRegex()).toTypedArray()
        res[parts[0]] = parts[1].split(",".toRegex()).toTypedArray()
    }
    return res
}
fun readParts(lines: List<String>): List<Part> {
    val res: MutableList<Part> = ArrayList<Part>()
    while (seek < lines.size) {
        val line = lines[seek]
        res.add(Part(line.substring(1, line.length - 1)))
        seek++
    }
    return res
}
fun isTrue(part: Part, predicate: String): Boolean {
    val value: Long = part.getValue(predicate[0])
    val opt = predicate[1]
    val num = predicate.substring(2).toLong()
    return if (opt == '>') value > num else value < num
}
fun isAccepted(part: Part, workflow: String, workflows: Map<String, Array<String>>): Boolean {
    if (workflow == "A") return true
    if (workflow == "R") return false
    val predicates = workflows[workflow]!!
    var ok = true
    for (predicate in predicates) {
        val tmp = predicate.split(":".toRegex()).toTypedArray()
        if (tmp.size == 2) {
            if (isTrue(part, tmp[0])) {
                ok = ok and isAccepted(part, tmp[1], workflows)
                break
            }
        } else ok = ok and isAccepted(part, tmp[0], workflows)
    }
    return ok
}
fun updateIntervals(predicate: String, part: Part): Part {
    val attr = predicate[0]
    val opt = predicate[1]
    val num = predicate.substring(2).toLong()
    val newP = Part(part)
    val intv: Interval = part.getInterval(attr)
    val newIintv: Interval = newP.getInterval(attr)
    if (opt == '>') {
        intv.setRight(num)
        newIintv.setLeft(num + 1)
    } else {
        intv.setLeft(num)
        newIintv.setRight(num - 1)
    }
    part.setInterval(attr, Interval(intv))
    newP.setInterval(attr, newIintv)
    return newP
}
fun countAccepted(workflow: String, workflows: Map<String, Array<String>>, part: Part): Long {
    if (workflow == "A") return part.countCombinations()
    if (workflow == "R") return 0L
    val predicates = workflows[workflow]!!
    var res = 0L
    for (predicate in predicates) {
        val tmp = predicate.split(":".toRegex()).toTypedArray()
        if (tmp.size == 2) {
            val newP: Part = updateIntervals(tmp[0], part)
            res += countAccepted(tmp[1], workflows, newP)
        }
        else {
            res += countAccepted(tmp[0], workflows, Part(part))
        }
    }
    return res
}
fun main() {
    fun part1(lines: List<String>): Long {
        seek = 0
        val workflows: Map<String, Array<String>> = readWorkFlows(lines)
        val parts: List<Part> = readParts(lines)
        var ans: Long = 0
        for (part in parts) {
            if (isAccepted(part, "in", workflows)) {
                ans += part.getSum()
            }
        }
        return ans
    }
    fun part2(lines: List<String>): Long {
        seek = 0
        val workflows: Map<String, Array<String>> = readWorkFlows(lines)
        return countAccepted("in", workflows, Part())
    }

    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}
