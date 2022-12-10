package day_10

import java.io.File

private const val FILE_PATH = "src/day_10/input.txt"

private val cyclesToCount = listOf(20, 60, 100, 140, 180, 220)

fun main() {
  val inputFile = File(FILE_PATH)
  var currentCycle = 1
  var x = 1
  var sum = 0
  inputFile.forEachLine { command ->
    println("cycle: $currentCycle - x: $x - command: $command")
    if (command == "noop") {
      currentCycle++
      if (currentCycle in cyclesToCount) {
        println("Update sum exact noop")
        sum += currentCycle * x
      }
    } else {
      val (_, amount) = command.split(" ")
      if ((currentCycle + 1) in cyclesToCount) {
        println("Update sum jump")
        sum += (currentCycle + 1) * x
      }
      x += amount.toInt()
      currentCycle += 2
      if (currentCycle in cyclesToCount) {
        println("Update sum exact other")
        sum += currentCycle * x
      }
    }
    println("new Cycle: $currentCycle - new x: $x")
  }
  println(sum)
}