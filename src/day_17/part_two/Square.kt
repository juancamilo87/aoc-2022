package day_17.part_two

// ##
// ##

data class Square(val shape: IntArray, val x: Int, val y: Long, private val originalY: Long) :
  Rock {

  constructor(x: Int = 2, y: Long) : this(
    listOf(
      96 shr x,
      96 shr x
    ).toIntArray(),
    x,
    y,
    y
  )

  constructor(x: Int = 2, y: Long, originalY: Long) : this(
    listOf(
      96 shr x,
      96 shr x
    ).toIntArray(),
    x,
    y,
    originalY
  )

  override fun getOriginalY(): Long {
    return originalY
  }

  override fun drop(occupiedSpace: ArrayDeque<Int>): Rock? {
    if (y == 0L) return null
    val line = Rock.getLine(occupiedSpace, originalY, y - 1)
    if (
      line and shape[0] != 0
    ) {
      return null
    }
    return copy(y = y - 1)
  }

  override fun move(jet: Char, occupiedSpace: ArrayDeque<Int>): Rock {
    val lines = listOf(
      Rock.getLine(occupiedSpace, originalY, y),
      Rock.getLine(occupiedSpace, originalY, y + 1)
    )
    return when (jet) {
      '<' -> {
        if (x == 0) this
        else {
          val newShape = shape.map { it shl 1 }
          if (
            lines[0] and newShape[0] != 0 ||
            lines[1] and newShape[1] != 0
          ) this
          else copy(shape = newShape.toIntArray(), x = x - 1)
        }
      }
      '>' -> {
        if (x == 5) this
        else {
          val newShape = shape.map { it shr 1 }
          if (
            lines[0] and newShape[0] != 0 ||
            lines[1] and newShape[1] != 0
          ) this
          else copy(shape = newShape.toIntArray(), x = x + 1)
        }
      }
      else -> throw RuntimeException("Alg is wrong")
    }
  }

  override fun moveAndDropNoCheck(jets: List<Char>): Rock {
    var newX = x
    jets.forEach { jet ->
      newX = when (jet) {
        '<' -> {
          if (newX == 0) newX
          else newX - 1
        }
        '>' -> {
          if (newX == 5) newX
          else newX + 1
        }
        else -> throw RuntimeException("Alg is wrong")
      }
    }
    return Square(newX, y - (jets.size - 1), originalY)
  }

  override fun getOccupiedSpace(): Map<Long, Int> {
    return shape.mapIndexed { index, line -> (y + index) to line }.toMap()
  }

  override fun maxY() = y + 1
}