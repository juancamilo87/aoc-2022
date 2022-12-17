package day_17.part_one

import day_17.part_one.Rock.Companion.toRockKey

// .#.
// ###
// .#.

data class Cross(val x: Int = 2, val y: Long) : Rock {
  override fun drop(occupiedSpace: Set<String>): Rock? {
    if (y == 0L) return null
    if (
      occupiedSpace.contains(toRockKey(x + 1, y - 1)) ||
      occupiedSpace.contains(toRockKey(x, y)) ||
      occupiedSpace.contains(toRockKey(x + 2, y))
    ) {
      return null
    }
    return copy(y = y - 1)
  }

  override fun move(jet: Jet, occupiedSpace: Set<String>): Rock {
    return when (jet) {
      Left -> {
        if (x == 0) this
        else if (
          occupiedSpace.contains(toRockKey(x - 1, y + 1)) ||
          occupiedSpace.contains(toRockKey(x, y + 2)) ||
          occupiedSpace.contains(toRockKey(x, y))
        ) this
        else copy(x = x - 1)
      }
      Right -> {
        if (x == 4) this
        else if (occupiedSpace.contains(toRockKey(x + 3, y + 1)) ||
          occupiedSpace.contains(toRockKey(x + 2, y + 2)) ||
          occupiedSpace.contains(toRockKey(x + 2, y))
        ) this
        else copy(x = x + 1)
      }
    }
  }

  override fun moveAndDropNoCheck(jets: List<Jet>): Rock {
    var newX = x
    jets.forEach { jet ->
      newX = when (jet) {
        Left -> {
          if (newX == 0) newX
          else newX - 1
        }
        Right -> {
          if (newX == 4) newX
          else newX + 1
        }
      }
    }
    return Cross(newX, y - jets.size)
  }

  override fun getOccupiedSpace(): Set<String> {
    val result = mutableSetOf<String>()
    result.add(toRockKey(x + 1, y))
    result.add(toRockKey(x, y + 1))
    result.add(toRockKey(x + 1, y + 1))
    result.add(toRockKey(x + 2, y + 1))
    result.add(toRockKey(x + 1, y + 2))
    return result
  }

  override fun maxY() = y + 2
}