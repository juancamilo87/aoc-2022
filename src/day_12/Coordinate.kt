package day_12

data class Coordinate(val x: Int, val y: Int) {
  fun moveUp(): Coordinate? {
    return if (y == 0) null
    else copy(y = y - 1)
  }
  fun moveDown(map: Array<IntArray>) : Coordinate? {
    return if (y == map.size - 1) null
    else copy(y = y + 1)
  }
  fun moveLeft(): Coordinate? {
    return if (x == 0) null
    else copy(x = x - 1)
  }
  fun moveRight(map: Array<IntArray>) : Coordinate? {
    return if (x == map[0].size - 1) null
    else copy(x = x + 1)
  }
}