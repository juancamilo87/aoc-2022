package day_15

import java.io.File
import java.math.BigInteger
import kotlin.math.abs
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_15/input.txt"

private const val SAMPLE_MAX = 20
private const val INPUT_MAX = 4000000

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private fun calculate() {
  val inputFile = File(FILE_PATH)
  val sensorBeaconPairs = mutableListOf<Triple<Coordinate, Coordinate, Int>>()

  inputFile.forEachLine { line ->
    val (sensor, beacon) = line.split(":")
    val (sensorX, sensorY) = sensor.split(",").map { it.trim().split("=")[1].toInt() }
    val (beaconX, beaconY) = beacon.split(",").map { it.trim().split("=")[1].toInt() }
    val sensorCoordinate = Coordinate(sensorX, sensorY)
    val beaconCoordinate = Coordinate(beaconX, beaconY)
    val distance = sensorCoordinate.distanceTo(beaconCoordinate)
    sensorBeaconPairs.add(Triple(sensorCoordinate, beaconCoordinate, distance))
  }
  var x = 0
  while (x <= INPUT_MAX) {
    var y = 0
    while (y <= INPUT_MAX) {
      val possibleCoordinate = Coordinate(x, y)
      var step = 1
      val farther = sensorBeaconPairs.firstOrNull {
        val distanceToPossible = it.first.distanceTo(possibleCoordinate)
        step = it.third - distanceToPossible + 1
        it.third >= distanceToPossible
      }
      if (farther == null) {
        println("Beacon place: $possibleCoordinate with tuning frequency ${possibleCoordinate.tuningFrequency()}")
        return
      }
      y += step
    }
    x++
  }
  println("Failed")
}

private fun Coordinate.distanceTo(dest: Coordinate): Int {
  return abs(x - dest.x) + abs(y - dest.y)
}

private fun Coordinate.tuningFrequency(): BigInteger {
  return x.toBigInteger().multiply(4000000.toBigInteger()) + y.toBigInteger()
}