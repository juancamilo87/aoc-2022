package day_12

import java.io.File

private const val FILE_PATH = "src/day_12/input.txt"

fun main() {
  val inputFile = File(FILE_PATH)
  val (map, start, end) = readFile(inputFile)
  println("shortest path: ${findShortestPath(map, start, end)}")
}

private fun readFile(inputFile: File) : Triple<Array<IntArray>, Coordinate, Coordinate> {
  val resultMap = arrayListOf<IntArray>()
  var y = 0
  var start : Coordinate? = null
  var end : Coordinate? = null
  inputFile.forEachLine { row ->
    val currentRow = IntArray(row.length)
    row.forEachIndexed { x, height ->
      currentRow[x] = when (height) {
        'S' -> {
          start = Coordinate(x, y)
          'a'.code
        }
        'E' -> {
          end = Coordinate(x, y)
          'z'.code
        }
        else -> height.code
      } - 'a'.code
    }
    resultMap.add(currentRow)
    y++
  }
  return Triple(resultMap.toTypedArray(), start!!, end!!)
}

