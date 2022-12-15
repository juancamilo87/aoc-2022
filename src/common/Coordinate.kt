package common

data class Coordinate(val x: Int, val y: Int) {
  companion object {
    fun parse(coordinateString: String): Coordinate {
      val (x, y) = coordinateString.split(",").map { it.trim().toInt() }
      return Coordinate(x, y)
    }
  }

  fun moveUp() = copy(y = y - 1)

  fun moveDown() = copy(y = y + 1)

  fun moveLeft() = copy(x = x - 1)

  fun moveRight() = copy(x = x + 1)
}