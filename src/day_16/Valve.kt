package day_16

import java.io.File

class Valve (val name: String, var flowRate: Int = -1, val nextValves: MutableSet<Valve> = mutableSetOf()) {

  companion object {
    fun parseValves(inputFile: File) : Map<String, Valve> {
      val valves = mutableMapOf<String, Valve>()
      inputFile.forEachLine { line ->
        val (start, leadsTo) = line.split(";")
        val valve = with(start.split(" ")) {
          val flowRate = this[4].split("=")[1].toInt()
          var newValve = valves[this[1]]
          if (newValve != null) {
            newValve.flowRate = flowRate
          } else {
            newValve = Valve(this[1], flowRate)
            valves[newValve.name] = newValve
          }
          newValve
        }
        val nextValves = leadsTo.trim()
          .replace(",", "")
          .split(" ")
          .drop(4)
          .map {
            valves[it] ?: Valve(it).apply { valves[it] = this }
          }
        valve.nextValves.addAll(nextValves)
      }
      return valves
    }
  }

  override fun equals(other: Any?): Boolean {
    if (other != null && other is Valve) {
      return name == other.name
    }
    return super.equals(other)
  }

  override fun hashCode(): Int {
    return name.hashCode()
  }

  override fun toString(): String {
    return "Valve: $name, FlowRate: $flowRate, to: ${nextValves.joinToString { it.name }}"
  }
}