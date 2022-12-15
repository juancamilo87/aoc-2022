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
  val floor = lowestRockY + 2

  val sand = mutableSetOf<Coordinate>()

  var sandMoving = true
  var sandDropped = 0

  while (sandMoving) {
    var sandCoordinate: Coordinate? = sandStart
    while (sandCoordinate != null) {
      val newSand = RockManager.moveSand(sandCoordinate, rocks, sand, floor)
      if (newSand == null) {
        if (sandCoordinate == sandStart) sandMoving = false
        sand.add(sandCoordinate)
      }
      sandCoordinate = newSand
    }
    sandDropped++
  }

  println("Sand dropped: $sandDropped")
}