package day_9

import java.io.File

private const val FILE_PATH = "src/day_9/input.txt"

fun main() {
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