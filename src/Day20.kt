open class Module(var type: Char, var name: String, list: String) {
    var inputPulse: MutableMap<String, Char>
    var subs: Array<String>

    init {
        subs = list.split(", ".toRegex()).toTypedArray()
        inputPulse = HashMap()
    }

    open fun sendPulses(modules: Map<String, Module>, q: ArrayDeque<Pulse>, curPulse: Char): LongArray {
        val res = longArrayOf(0, 0)
        val outPulse = outPulse()
        for (name in subs) {
            q.add(Pulse(outPulse, this.name, name))
        }
        res[0] = if (outPulse == 'l') subs.size.toLong() else 0L
        res[1] = if (outPulse == 'h') subs.size.toLong() else 0L
        return res
    }

    fun setInputPulse(inP: Char, name: String) {
        inputPulse[name] = inP
    }

    open fun outPulse(): Char = 'l'
    val isConjunction: Boolean
        get() = type == '&'

    companion object {
        fun createModule(t: Char, name: String, list: String): Module {
            if (t == '%') return FlipFlop(name, list) else if (t == '&') return Conjunction(name, list)
            return Module(t, name, list)
        }
    }
}

class FlipFlop(name: String, list: String) : Module('%', name, list) {
    var on = false
    override fun sendPulses(modules: Map<String, Module>, q: ArrayDeque<Pulse>, curPulse: Char): LongArray {
        if (curPulse == 'h') return longArrayOf(0, 0)
        toggle()
        return super.sendPulses(modules, q, curPulse)
    }

    override fun outPulse(): Char = if (on) 'h' else 'l'

    private fun toggle() {
        on = !on
    }
}
class Conjunction(name: String, list: String) : Module('&', name, list) {
    var pubs: MutableList<String> = ArrayList()

    fun addPub(name: String) {
        pubs.add(name)
    }

    override fun outPulse(): Char {
        for (pub in pubs) {
            val p: Char = inputPulse.getOrDefault(pub, 'l')
            if (p == 'l') return 'h'
        }
        return 'l'
    }
}
class Pulse(var type: Char, var from: String, var to: String)

fun readModules(lines: List<String>): Map<String, Module> {
    val res: MutableMap<String, Module> = HashMap<String, Module>()
    for (line in lines) {
        val tmp = line.split(" -> ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val type = tmp[0][0]
        if (type == '%' || type == '&') tmp[0] = tmp[0].substring(1)
        res[tmp[0]] = Module.createModule(type, tmp[0], tmp[1])
    }
    // update input of conjunctions
    for (name in res.keys) {
        val m: Module = res[name]!!
        for (sub in m.subs) {
            val subM: Module? = res[sub]
            if (subM != null && subM.isConjunction) {
                val conM: Conjunction = subM as Conjunction
                conM.addPub(name)
            }
        }
    }
    return res
}
fun main() {
    fun part1(lines: List<String>): Long {
        val modules: Map<String, Module> = readModules(lines)
        var l = 0L
        var h = 0L
        for (i in 0..999) {
            val q: ArrayDeque<Pulse> = ArrayDeque()
            l += 1
            q.add(Pulse('l', "button", "broadcaster"))
            while (!q.isEmpty()) {
                val p: Pulse = q.removeFirst()
                val m: Module = modules[p.to] ?: continue
                m.setInputPulse(p.type, p.from)
                val res: LongArray = m.sendPulses(modules, q, p.type)
                l += res[0]
                h += res[1]
            }
        }
        return l * h
    }

    fun part2(lines: List<String>): Int {
        return lines.size
    }

    val input = readInput("Day20")
    part1(input).println()
    part2(input).println()
}
