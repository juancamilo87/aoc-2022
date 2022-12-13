package day_10

import java.io.File
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_10/input.txt"

private val cyclesToCount = listOf(20, 60, 100, 140, 180, 220)

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private fun calculate() {
  val inputFile = File(FILE_PATH)
  var currentCycle = 1
  var x = 1
  var sum = 0
  inputFile.forEachLine { command ->
    if (command == "noop") {
      currentCycle++
      if (currentCycle in cyclesToCount) {
        sum += currentCycle * x
      }
    } else {
      val (_, amount) = command.split(" ")
      if ((currentCycle + 1) in cyclesToCount) {
        sum += (currentCycle + 1) * x
      }
      x += amount.toInt()
      currentCycle += 2
      if (currentCycle in cyclesToCount) {
        sum += currentCycle * x
      }
    }
  }
  println(sum)
}