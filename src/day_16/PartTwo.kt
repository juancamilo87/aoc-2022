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
    releasePressure(startingValve, startingValve, 26, releasedValves, calculatedReleasedValues)

  println("Max released pressure: $maxReleasePressure")
}

private fun toKey(
  myValve: Valve,
  elephantValve: Valve,
  minutesLeft: Int,
  releasedValves: Set<String>,
): String {
  val valves = listOf(myValve, elephantValve).map { it.name }.sorted()
  return "${valves.joinToString(",")}-$minutesLeft-${releasedValves.joinToString(",")}"
}

private fun releasePressure(
  myValve: Valve,
  elephantValve: Valve,
  minutesLeft: Int,
  releasedValves: Set<String>,
  calculatedReleasedValues: MutableMap<String, Int>,
): Int {
  val key = toKey(myValve, elephantValve, minutesLeft, releasedValves)
  val preCalculatedValue = calculatedReleasedValues[key]
  if (preCalculatedValue != null) {
    return preCalculatedValue
  }
  if (minutesLeft <= 1) return 0
  if (minutesLeft == 2) {
    var possibleRelease = 0
    possibleRelease += if (releasedValves.contains(myValve.name)) {
      0
    } else {
      myValve.flowRate
    }
    if (myValve != elephantValve) {
      possibleRelease += if (releasedValves.contains(elephantValve.name)) {
        0
      } else {
        elephantValve.flowRate
      }
    }
    return possibleRelease
  }

  val skipBoth = myValve.nextValves.map { myNextValve ->
    elephantValve.nextValves.map { elephantNextValve ->
      releasePressure(
        myNextValve,
        elephantNextValve,
        minutesLeft - 1,
        releasedValves,
        calculatedReleasedValues
      )
    }
  }.flatten()

  val skipMy = if (releasedValves.contains(elephantValve.name) || elephantValve.flowRate == 0) {
    emptyList()
  } else {
    myValve.nextValves.map { myNextValve ->
      val releasedValvesIncludingElephant =
        releasedValves.toMutableList().apply { add(elephantValve.name) }.sorted().toSet()
      val elephantValveReleased = elephantValve.flowRate * (minutesLeft - 1)
      releasePressure(
        myNextValve,
        elephantValve,
        minutesLeft - 1,
        releasedValvesIncludingElephant,
        calculatedReleasedValues
      ) + elephantValveReleased
    }
  }

  val skipElephant = if (releasedValves.contains(myValve.name) || myValve.flowRate == 0) {
    emptyList()
  } else {
    elephantValve.nextValves.map { elephantNextValve ->
      val releasedValvesIncludingMy =
        releasedValves.toMutableList().apply { add(myValve.name) }.sorted().toSet()
      val myValveReleased = myValve.flowRate * (minutesLeft - 1)
      releasePressure(
        myValve,
        elephantNextValve,
        minutesLeft - 1,
        releasedValvesIncludingMy,
        calculatedReleasedValues
      ) + myValveReleased
    }
  }

  val noSkipping =
    if (myValve == elephantValve || releasedValves.contains(myValve.name) || myValve.flowRate == 0 || releasedValves.contains(
        elephantValve.name
      ) || elephantValve.flowRate == 0
    ) {
      0
    } else {
      val releasedValvesIncludingBoth = releasedValves.toMutableList()
        .apply {
          add(myValve.name)
          add(elephantValve.name)
        }.sorted().toSet()
      val myValveReleased = myValve.flowRate * (minutesLeft - 1)
      val elephantValveReleased = elephantValve.flowRate * (minutesLeft - 1)
      releasePressure(
        myValve,
        elephantValve,
        minutesLeft - 1,
        releasedValvesIncludingBoth,
        calculatedReleasedValues
      ) + myValveReleased + elephantValveReleased
    }

  val maxReleasedPressure =
    listOf(skipBoth, skipMy, skipElephant).flatten().plus(noSkipping).maxOrNull()!!
  calculatedReleasedValues[key] = maxReleasedPressure
  return maxReleasedPressure
}