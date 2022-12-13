package day_9

import java.io.File
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_9/input.txt"

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private fun calculate() {
  val inputFile = File(FILE_PATH)
  val visitedPlaces = mutableSetOf<Knot>()
  var head = Knot(0, 0)
  var tail = Knot(0, 0)
  visitedPlaces.add(tail)
  inputFile.forEachLine { command ->
    val (dir, quantity) = command.split(" ")
    repeat(quantity.toInt()) {
      head = head.move(dir)
      tail = tail.follow(head)
      visitedPlaces.add(tail)
    }
  }
  println(visitedPlaces.size)
}