fun main() {
    var seek = 0

    class Range(var start: Long, var length: Long) {
        constructor(s: String, l: String) : this(s.toLong(), l.toLong())

        fun isInRange(`val`: Long): Boolean {
            val diff = `val` - start
            return diff in 0 until length
        }

        val end: Long
            get() = start + length - 1

        override fun toString(): String {
            return "[ $start $length ]"
        }
    }

    class MapRange(strs: Array<String>): Comparable<MapRange> {
        var source: Range
        var destination: Range
        var length: Long

        init {
            length = strs[2].toLong()
            destination = Range(strs[0].toLong(), length)
            source = Range(strs[1].toLong(), length)
        }

        fun getMapValue(source: Long): Long {
            val diff: Long = source - this.source.start
            return destination.start + diff
        }

        fun isInSourceRange(source: Long): Boolean {
            return this.source.isInRange(source)
        }

        override operator fun compareTo(other: MapRange): Int {
            return source.start.compareTo(other.source.start)
        }
    }

    fun readRanges(lines: List<String>): List<MapRange> {
        val res: MutableList<MapRange> = mutableListOf()
        while (seek < lines.size) {
            val line = lines[seek]
            if (line.isEmpty()) break
            res.add(MapRange(line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
            seek++
        }
        seek += 2
        return res
    }
    fun findMapValue(v: Long, mapper: List<MapRange>): Long {
        for (range in mapper) {
            if (range.isInSourceRange(v)) {
                return range.getMapValue(v)
            }
        }
        return v
    }
    fun findLocation(seed: Long, mappers: Array<List<MapRange>>): Long {
        var seedLocation = seed
        for (i in 0..6) {
            seedLocation = findMapValue(seedLocation, mappers[i])
        }
        return seedLocation
    }

    fun part1(lines: List<String>): Long {
        seek = 0
        val seeds = lines[seek].split(" ").drop(1).map{ it.toLong() }.toTypedArray()
        seek += 3
        val mappers = Array<List<MapRange>>(7) { readRanges(lines) }
        var ans = Long.MAX_VALUE
        for (seed in seeds) {
            ans = Math.min(ans, findLocation(seed, mappers))
        }

        return ans
    }

    fun getMapValues(range: Range, mapper: List<MapRange>): List<Range> {
        val res: MutableList<Range> = mutableListOf()
        var s: Long = range.start
        var l: Long = range.length
        for (mapRange in mapper) {
            if (l <= 0) break
            if (s < mapRange.source.start) {
                val numEle = Math.min(mapRange.source.start - s, l)
                res.add(Range(s, numEle))
                s = mapRange.source.start
                l -= numEle
            } else if (s <= mapRange.source.end) {
                val start: Long = mapRange.getMapValue(s)
                val numEle = Math.min(mapRange.destination.end - start + 1, l)
                res.add(Range(start, numEle))
                s = mapRange.source.end + 1
                l -= numEle
            }
        }
        if (l > 0) res.add(Range(s, l))
        return res
    }
    fun part2(lines: List<String>): Long {
        seek = 0
        val tmp = lines[seek].split(" ".toRegex()).toTypedArray().drop(1)

        val seeds: MutableList<Range> = mutableListOf()
        for (i in tmp.indices step 2) {
            seeds.add(Range(tmp[i], tmp[i + 1]))
        }
        seek += 3
        val mappers = Array<List<MapRange>>(7) { readRanges(lines).sorted() }
        var ans = Long.MAX_VALUE
        for (seedRange in seeds) {
            var locations: MutableList<Range> = MutableList(1){seedRange}
            for (mapper in mappers) {
                val cur: MutableList<Range> = ArrayList()
                for (range in locations) {
                    cur.addAll(getMapValues(range, mapper))
                }
                locations = cur
            }
            for (range in locations) {
                ans = Math.min(ans, range.start)
            }
        }
        return ans
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
