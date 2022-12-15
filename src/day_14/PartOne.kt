package day_14

import common.Coordinate
import java.io.File
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_14/input.txt"

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private fun calculate() {
  val inputFile = File(FILE_PATH)
  val sandStart = Coordinate(500, 0)

  val rocks = RockManager.readRockPaths(inputFile)
  val lowestRockY = rocks.maxByOrNull { it.y }!!.y

  val sand = mutableSetOf<Coordinate>()

  var sandToAbyss = false
  var sandDropped = 0

  while (!sandToAbyss) {
    var sandCoordinate: Coordinate? = sandStart
    while (sandCoordinate != null && !sandToAbyss) {
      if (sandCoordinate.y == lowestRockY) {
        sandToAbyss = true
      } else {
        val newSand = RockManager.moveSand(sandCoordinate, rocks, sand)
        if (newSand == null) {
          sand.add(sandCoordinate)
        }
        sandCoordinate = newSand
      }
    }
    if (!sandToAbyss) {
      sandDropped++
    }
  }

  println("Sand dropped: $sandDropped")
}