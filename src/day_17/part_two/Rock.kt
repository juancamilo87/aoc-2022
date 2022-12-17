package day_17.part_two

sealed interface Rock {
  companion object {
    fun createRock(type: RockType, y: Long): Rock {
      return when (type) {
        RockType.LINE -> Line(y = y)
        RockType.CROSS -> Cross(y = y)
        RockType.REVERSE_L -> ReverseL(y = y)
        RockType.V_LINE -> VLine(y = y)
        RockType.SQUARE -> Square(y = y)
      }
    }

    fun getLine(occupiedSpace: ArrayDeque<Int>, originalY: Long, index: Long): Int {
      val start = originalY - 4
      val newIndex = (index - start).toInt() * (-1)
      if (newIndex < 0) {
        return 0
      }
      return occupiedSpace[newIndex]
    }

    fun addLine(occupiedSpace: ArrayDeque<Int>, originalY: Long, index: Long, value: Int) {
      val start = originalY - 4
      val newIndex = (index - start).toInt() * (-1)
      if (newIndex < 0) {
        occupiedSpace.addFirst(value)
        if (occupiedSpace.size > 100) occupiedSpace.removeLast()
      } else {
        occupiedSpace[newIndex] = occupiedSpace[newIndex] or value
      }
    }
  }

  fun getOriginalY(): Long

  fun drop(occupiedSpace: ArrayDeque<Int>): Rock?
  fun move(jet: Char, occupiedSpace: ArrayDeque<Int>): Rock
  fun moveAndDropNoCheck(jets: List<Char>): Rock
  fun getOccupiedSpace(): Map<Long, Int>
  fun maxY(): Long
}

enum class RockType {
  LINE,
  CROSS,
  REVERSE_L,
  V_LINE,
  SQUARE
}