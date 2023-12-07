import kotlin.streams.toList

fun main() {
    var isPart2 = false
    fun getCharW(c: Char): Int {
        if (c in '2'..'9') return c.code - '0'.code
        if (c == 'T') return 10
        if (c == 'J') {
            return if (isPart2) 1 else 11
        }
        if (c == 'Q') return 12
        return if (c == 'K') 13 else 14
    }

    class Hand(h: String) : Comparable<Hand> {
        var hand: CharArray
        var occ: MutableMap<Char, Int>
        var uniq: MutableList<Char>
        init {
            hand = h.toCharArray()
            occ = HashMap()
            uniq = ArrayList()
            for (c in hand) {
                val k = occ.getOrDefault(c, 0)
                if (k == 0) uniq.add(c)
                occ[c] = k + 1
            }
            uniq.sortBy { c -> occ[c] }
        }
        val isSame: Boolean
            get() = uniq.size == 1
        val isFour: Boolean
            get() = if (uniq.size == 2) { occ[uniq[0]] == 1 && occ[uniq[1]] == 4 } else false
        val isFullHouse: Boolean
            get() = if (uniq.size == 2) { occ[uniq[0]] == 2 && occ[uniq[1]] == 3 } else false
        val isThree: Boolean
            get() = if (uniq.size == 3) { (occ[uniq[0]] == 1) && (occ[uniq[1]] == 1) && (occ[uniq[2]] == 3) } else false
        val isTwoPair: Boolean
            get() = if (uniq.size == 3) { (occ[uniq[0]] == 1) && (occ[uniq[1]] == 2) && (occ[uniq[2]] == 2) } else false
        val isOnePair: Boolean
            get() = uniq.size == 4
        val isHigh: Boolean
            get() = uniq.size == 5
        fun handRank(): Int {
            if (isSame) return 7
            if (isFour) return 6
            if (isFullHouse) return 5
            if (isThree) return 4
            if (isTwoPair) return 3
            return if (isOnePair) 2 else 1
        }
        fun rank(): Int = if (isPart2) rankWithJoker() else handRank()
        fun rankWithJoker(): Int {
            // get best you can with Joker card J
            val occJ = occ.getOrDefault('J', 0)
            if (occJ == 0) return handRank()
            val currentW = handRank()
            if (currentW >= 5) return 7
            if (currentW == 4) return 6
            if (currentW == 3) {
                return if (occJ == 1) 5 else 6
            }
            return if (currentW == 2) 4 else 2
        }
        override operator fun compareTo(o: Hand): Int {
            var c = rank().compareTo(o.rank())
            if (c == 0) {
                for (i in hand.indices) {
                    if (hand[i] != o.hand[i]) {
                        c = getCharW(hand[i]).compareTo(getCharW(o.hand[i]))
                        break
                    }
                }
            }
            return c
        }
    }

    class Bid(s: String, b: String) : Comparable<Bid> {
        var hand: Hand
        var bid: Long
        init {
            hand = Hand(s)
            bid = b.toLong()
        }
        override operator fun compareTo(o: Bid): Int {
            return hand.compareTo(o.hand)
        }
    }

    fun part1(lines: List<String>): Long {
        return lines.stream().map { it ->
            val tmp = it.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            Bid(tmp[0], tmp[1])
        }.sorted().toList().mapIndexed {idx, bid -> (idx + 1) * bid.bid}.sum()
    }

    fun part2(lines: List<String>): Long {
        isPart2 = true
        return part1(lines)

    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
