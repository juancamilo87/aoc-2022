package day_4

import java.io.File
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_4/input.txt"

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private fun calculate() {
  var irrelevantSectionCount = 0
  File(FILE_PATH).forEachLine { items ->
    val (first, second) = items.split(",")
    if (rangeContainedInAnotherRange(first, second)) {
      irrelevantSectionCount++
    }
  }
  println(irrelevantSectionCount)
}

private fun rangeContainedInAnotherRange(first: String, second: String) : Boolean {
  val (firstStart, firstEnd) = first.split("-").map { it.toInt() }
  val (secondStart, secondEnd) = second.split("-").map { it.toInt() }
  if (firstStart <= secondStart && firstEnd >= secondEnd) return true
  if (secondStart <= firstStart && secondEnd >= firstEnd) return true
  return false
}
