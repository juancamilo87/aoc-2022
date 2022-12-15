package day_15

import common.Coordinate
import java.io.File
import kotlin.math.abs
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_15/input.txt"

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private const val ROW = 2000000

private fun calculate() {
  val inputFile = File(FILE_PATH)
  val sensorBeaconPairs = mutableListOf<Pair<Coordinate, Coordinate>>()
  inputFile.forEachLine { line ->
    val (sensor, beacon) = line.split(":")
    val (sensorX, sensorY) = sensor.split(",").map { it.trim().split("=")[1].toInt() }
    val (beaconX, beaconY) = beacon.split(",").map { it.trim().split("=")[1].toInt() }
    sensorBeaconPairs.add(Pair(Coordinate(sensorX, sensorY), Coordinate(beaconX, beaconY)))
  }

  var count = 0
  for (x in -6000000..6000000) { //TODO: Do this better.
    if (sensorBeaconPairs.any { pair ->
        val possibleBeacon = Coordinate(x, ROW)
        pair.second != possibleBeacon && pair.first.distanceTo(pair.second) >= pair.first.distanceTo(
          possibleBeacon
        )
      }) {
      count++
    }
  }

  println("Impossible places: $count")
}

private fun Coordinate.distanceTo(dest: Coordinate): Int {
  return abs(x - dest.x) + abs(y - dest.y)
}