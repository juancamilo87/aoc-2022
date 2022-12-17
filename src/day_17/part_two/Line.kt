package day_17.part_two

// ####

data class Line(val shape: Int, val x: Int, val y: Long, private val originalY: Long) : Rock {

  constructor (x: Int = 2, y: Long) : this(120 shr x, x, y, y)
  constructor (x: Int = 2, y: Long, originalY: Long) : this(120 shr x, x, y, originalY)

  override fun getOriginalY(): Long {
    return originalY
  }

  override fun drop(occupiedSpace: ArrayDeque<Int>): Rock? {
    if (y == 0L) return null
    val line = Rock.getLine(occupiedSpace, originalY, y - 1)
    if (line and shape != 0) {
      return null
    }
    return copy(y = y - 1)
  }

  override fun move(jet: Char, occupiedSpace: ArrayDeque<Int>): Rock {
    return when (jet) {
      '<' -> {
        if (x == 0) this
        else {
          val newShape = shape shl 1
          if (Rock.getLine(occupiedSpace, originalY, y) and newShape != 0) this
          else copy(shape = newShape, x = x - 1)
        }
      }
      '>' -> {
        if (x == 3) this
        else {
          val newShape = shape shr 1
          if (Rock.getLine(occupiedSpace, originalY, y) and newShape != 0) this
          else copy(shape = newShape, x = x + 1)
        }
      }
      else -> throw RuntimeException("Alg is wrong")
    }
  }

  override fun moveAndDropNoCheck(jets: List<Char>): Rock {
    var newX = x
    jets.forEach { jet ->
      // println(jet)
      newX = when (jet) {
        '<' -> {
          if (newX == 0) newX
          else newX - 1
        }
        '>' -> {
          if (newX == 3) newX
          else newX + 1
        }
        else -> throw RuntimeException("Alg is wrong")
      }
    }
    return Line(newX, y - (jets.size - 1), originalY)
  }

  override fun getOccupiedSpace(): Map<Long, Int> {
    return mapOf(y to shape)
  }

  override fun maxY() = y
}