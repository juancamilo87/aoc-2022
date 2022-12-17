package day_17.part_one

sealed interface Rock {
  companion object {
    fun toRockKey(x: Int, y: Long) = "$x,$y"
    fun createRock(type: RockType, y: Long): Rock {
      return when (type) {
        RockType.LINE -> Line(y = y)
        RockType.CROSS -> Cross(y = y)
        RockType.REVERSE_L -> ReverseL(y = y)
        RockType.V_LINE -> VLine(y = y)
        RockType.SQUARE -> Square(y = y)
      }
    }
  }

  fun drop(occupiedSpace: Set<String>): Rock?
  fun move(jet: Jet, occupiedSpace: Set<String>): Rock
  fun moveAndDropNoCheck(jets: List<Jet>): Rock
  fun getOccupiedSpace(): Set<String>
  fun maxY(): Long
}

enum class RockType {
  LINE,
  CROSS,
  REVERSE_L,
  V_LINE,
  SQUARE
}