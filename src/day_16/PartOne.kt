package day_16

import java.io.File
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_16/input.txt"

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private fun calculate() {
  val inputFile = File(FILE_PATH)
  val valves = Valve.parseValves(inputFile)
  val startingValve = valves["AA"]!!
  val releasedValves = setOf<String>()
  val calculatedReleasedValues = mutableMapOf<String, Int>()

  val maxReleasePressure =
    releasePressure(startingValve, 30, releasedValves, calculatedReleasedValues)

  println("Max released pressure: $maxReleasePressure")
}

private fun toKey(valve: Valve, minutesLeft: Int, releasedValves: Set<String>): String {
  return "${valve.name}-$minutesLeft-${releasedValves.joinToString(",")}"
}

private fun releasePressure(
  valve: Valve,
  minutesLeft: Int,
  releasedValves: Set<String>,
  calculatedReleasedValues: MutableMap<String, Int>,
): Int {
  val key = toKey(valve, minutesLeft, releasedValves)
  val preCalculatedValue = calculatedReleasedValues[key]
  if (preCalculatedValue != null) {
    return preCalculatedValue
  }
  if (minutesLeft <= 1) return 0
  if (minutesLeft == 2) {
    return if (releasedValves.contains(valve.name)) {
      0
    } else {
      valve.flowRate
    }
  }

  val otherPaths = valve.nextValves.map {
    releasePressure(
      it,
      minutesLeft - 1,
      releasedValves,
      calculatedReleasedValues
    )
  }
  val afterRelease = if (releasedValves.contains(valve.name) || valve.flowRate == 0) {
    0
  } else {
    val releasedValvesIncludingMe =
      releasedValves.toMutableList().apply { add(valve.name) }.sorted().toSet()
    val myPressureReleased = valve.flowRate * (minutesLeft - 1)
    releasePressure(
      valve,
      minutesLeft - 1,
      releasedValvesIncludingMe,
      calculatedReleasedValues
    ) + myPressureReleased
  }

  val maxReleasedPressure = otherPaths.plus(afterRelease).maxOrNull()!!
  calculatedReleasedValues[key] = maxReleasedPressure
  return maxReleasedPressure
}
