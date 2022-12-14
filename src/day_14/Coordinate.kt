package day_14

data class Coordinate(val x: Int, val y: Int) {
  companion object {
    fun parse(coordinateString: String) : Coordinate {
      val (x, y) = coordinateString.split(",").map { it.trim().toInt() }
      return Coordinate(x, y)
    }
  }
}