package day_4

import java.io.File
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_4/input.txt"

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private fun calculate() {
  var overlappingPairsCount = 0
  File(FILE_PATH).forEachLine { items ->
    val (first, second) = items.split(",")
    if (rangeOverlaps(first, second)) {
      overlappingPairsCount++
    }
  }
  println(overlappingPairsCount)
}

private fun rangeOverlaps(first: String, second: String) : Boolean {
  val (firstStart, firstEnd) = first.split("-").map { it.toInt() }
  val (secondStart, secondEnd) = second.split("-").map { it.toInt() }
  if (secondStart in firstStart..firstEnd) return true
  if (firstStart in secondStart .. secondEnd) return true
  return false
}
