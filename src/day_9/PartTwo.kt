package day_9

import java.io.File

private const val FILE_PATH = "src/day_9/input.txt"

fun main() {
  val inputFile = File(FILE_PATH)
  val visitedPlaces = mutableSetOf<Knot>()
  val rope = MutableList(10) { Knot(0, 0) }
  visitedPlaces.add(rope[9])
  inputFile.forEachLine { command ->
    val (dir, quantity) = command.split(" ")
    repeat(quantity.toInt()) {
      rope[0] = rope[0].move(dir)
      for (i in 1..9) {
        rope[i] = rope[i].follow(rope[i - 1])
      }
      visitedPlaces.add(rope[9])
    }
  }
  println(visitedPlaces.size)
}