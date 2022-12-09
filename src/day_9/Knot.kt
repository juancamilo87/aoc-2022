package day_9

import kotlin.math.abs

data class Knot(val x: Int, val y: Int) {
  fun follow(head: Knot) : Knot {
    if (isNeighbor(head)) return this
    val deltaY = head.y - y
    val deltaX = head.x - x
    if (deltaX == 0) return copy(y = y + (deltaY/ abs(deltaY)))
    if (deltaY == 0) return copy(x = x + (deltaX/ abs(deltaX)))
    return copy(x = x + (deltaX / abs(deltaX)), y = y + (deltaY / abs(deltaY)))
  }

  fun move(dir: String): Knot {
    return when(dir) {
      "L" -> copy(x = x - 1)
      "R" -> copy(x = x + 1)
      "D" -> copy(y = y - 1)
      "U" -> copy(y = y + 1)
      else -> throw RuntimeException("Wrong command")
    }
  }

  private fun isNeighbor(knot: Knot) : Boolean {
    return abs(knot.x - x) <= 1 && abs(knot.y - y) <= 1
  }
}