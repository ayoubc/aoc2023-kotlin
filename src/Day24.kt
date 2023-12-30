val xLimit = doubleArrayOf(200000000000000.0, 400000000000000.0)
val yLimit = doubleArrayOf(200000000000000.0, 400000000000000.0)

class V(s: String) {
    var x: Double
    var y: Double
    var z: Double

    init {
        val tmp = s.split(",\\s+".toRegex()).toTypedArray()
        x = tmp[0].toDouble()
        y = tmp[1].toDouble()
        z = tmp[2].toDouble()
    }
}

class Point {
    var x: Double
    var y: Double
    var z: Double
    var v: V
    var a = 0.0
    var b = 0.0
    var c = 0.0

    constructor(coords: String, v: String?) {
        val tmp = coords.split(",\\s+".toRegex()).toTypedArray()
        x = tmp[0].toDouble()
        y = tmp[1].toDouble()
        z = tmp[2].toDouble()
        this.v = V(v!!)
        setParams()
    }

    fun setParams() {
        a = v.y
        b = -1 * v.x
        c = v.y * x - v.x * y
    }

    constructor(x: Double, y: Double, z: Double, v: V) {
        this.x = x
        this.y = y
        this.z = z
        this.v = v
    }

    constructor(args: Array<String>) : this(args[0], args[1])

    fun isIntersect(p: Point): Boolean {
//        https://www.topcoder.com/thrive/articles/Geometry%20Concepts%20part%202:%20%20Line%20Intersection%20and%20its%20Applications
        val det = a * p.b - p.a * b
        if (det == 0.0) return false
        val nx = (p.b * c - b * p.c) / det
        val ny = (a * p.c - p.a * c) / det
        val t1 = (nx - x) / v.x
        val t2 = (nx - p.x) / p.v.x
        return if (t1 < 0 || t2 < 0) false else isValid(nx, ny)
    }

    fun isValid(x: Double, y: Double): Boolean {
        return x >= xLimit[0] && x <= xLimit[1] && y >= yLimit[0] && y <= yLimit[1]
    }
}
fun readPoints(lines: List<String>): List<Point> {
    val res: MutableList<Point> = ArrayList()
    for (line in lines) res.add(Point(line.split("\\s+@\\s+".toRegex()).toTypedArray()))
    return res
}
fun main() {
    fun part1(lines: List<String>): Int {
        val points: List<Point> = readPoints(lines)
        var ans = 0
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val a = points[i]
                val b = points[j]
                if (a.isIntersect(b)) ans++
            }
        }
        return ans
    }

    fun part2(lines: List<String>): Int {
        return lines.size
    }

    val input = readInput("Day24")
    part1(input).println()
    part2(input).println()
}
