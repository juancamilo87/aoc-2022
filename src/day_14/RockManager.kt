package day_14

import java.io.File

object RockManager {
  fun readRockPaths(inputFile: File): Set<Coordinate> {
    val rocks = mutableSetOf<Coordinate>()
    inputFile.forEachLine { line ->
      val rockCoordinates = line.split("->").map { Coordinate.parse(it) }
      rockCoordinates.forEachIndexed { index, coordinate ->
        rocks.add(coordinate)
        if (index != rockCoordinates.lastIndex) {
          val nextCoordinate = rockCoordinates[index + 1]
          val rocksBetween = rocksBetween(coordinate, nextCoordinate)
          rocks.addAll(rocksBetween)
        }
      }
    }
    return rocks
  }

  private fun rocksBetween(a: Coordinate, b: Coordinate): Set<Coordinate> {
    val result = mutableSetOf<Coordinate>()
    val (start, end) = listOf(a, b).sortedBy { it.x }.sortedBy { it.y }

    if (start.x == end.x) {
      for (y in start.y..end.y) {
        result.add(Coordinate(start.x, y))
      }
    }
    if (start.y == end.y) {
      for (x in start.x..end.x) {
        result.add(Coordinate(x, start.y))
      }
    }
    return result
  }

  fun moveSand(
    sandCoordinate: Coordinate,
    rocks: Set<Coordinate>,
    sand: Set<Coordinate>,
    floor: Int = -1,
  ): Coordinate? {
    val lowerCoordinate = sandCoordinate.copy(y = sandCoordinate.y + 1)
    val lowerLeftCoordinate = lowerCoordinate.copy(x = lowerCoordinate.x - 1)
    val lowerRightCoordinate = lowerCoordinate.copy(x = lowerCoordinate.x + 1)
    return if (lowerCoordinate.y == floor) null
    else if (!rocks.contains(lowerCoordinate) && !sand.contains(lowerCoordinate)) lowerCoordinate
    else if (!rocks.contains(lowerLeftCoordinate) && !sand.contains(lowerLeftCoordinate)) lowerLeftCoordinate
    else if (!rocks.contains(lowerRightCoordinate) && !sand.contains(lowerRightCoordinate)) lowerRightCoordinate
    else null
  }
}