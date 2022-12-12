package day_12

import java.io.File

private const val FILE_PATH = "src/day_12/input.txt"

fun main() {
  val inputFile = File(FILE_PATH)
  val (map, start, end) = readFile(inputFile)
  println("shortest path: ${start.map { try {
    findShortestPath(map, it, end)
  } catch (e: RuntimeException) {
    Int.MAX_VALUE
  }
  }.minOf { it }}")
}

private fun readFile(inputFile: File) : Triple<Array<IntArray>, List<Coordinate>, Coordinate> {
  val resultMap = arrayListOf<IntArray>()
  var y = 0
  val start = mutableListOf<Coordinate>()
  var end : Coordinate? = null
  inputFile.forEachLine { row ->
    val currentRow = IntArray(row.length)
    row.forEachIndexed { x, height ->
      currentRow[x] = when (height) {
        'S' -> {
          start.add(Coordinate(x, y))
          'a'.code
        }
        'E' -> {
          end = Coordinate(x, y)
          'z'.code
        }
        'a' -> {
          start.add(Coordinate(x, y))
          'a'.code
        }
        else -> height.code
      } - 'a'.code
    }
    resultMap.add(currentRow)
    y++
  }
  return Triple(resultMap.toTypedArray(), start, end!!)
}