package day_8

import java.io.File
import kotlin.math.max

private const val FILE_PATH = "src/day_8/input.txt"

fun main() {
  val inputFile = File(FILE_PATH)
  val (treesHeight, rows, columns) = readInput(inputFile)
  val visibleTrees = mutableSetOf<String>()
  // Add edges
  for (i in 0 until rows) {
    visibleTrees.add(key(i, 0))
    visibleTrees.add(key(i, columns - 1))
  }
  for (j in 0 until columns) {
    visibleTrees.add(key(0, j))
    visibleTrees.add(key(rows - 1, j))
  }
  // Check visibility from left
  for (i in 1 until rows - 1) {
    var previousTallestTree = treesHeight[key(i, 0)]!!
    for (j in 1 until columns - 1) {
      previousTallestTree = addTreeIfVisible(treesHeight, previousTallestTree, visibleTrees, i,  j)
    }
  }

  // Check visibility from right
  for (i in rows - 2 downTo 1) {
    var previousTallestTree = treesHeight[key(i, columns - 1)]!!
    for (j in columns - 2 downTo 1) {
      previousTallestTree = addTreeIfVisible(treesHeight, previousTallestTree, visibleTrees, i,  j)
    }
  }

  // Check visibility from top
  for (j in 1 until columns - 1) {
    var previousTallestTree = treesHeight[key(0, j)]!!
    for (i in 1 until rows - 1) {
      previousTallestTree = addTreeIfVisible(treesHeight, previousTallestTree, visibleTrees, i,  j)
    }
  }

  // Check visibility from bottom
  for (j in columns - 2 downTo 1) {
    var previousTallestTree = treesHeight[key(rows - 1, j)]!!
    for (i in rows - 2 downTo 1) {
      previousTallestTree = addTreeIfVisible(treesHeight, previousTallestTree, visibleTrees, i,  j)
    }
  }
  println(visibleTrees.size)
}

private fun addTreeIfVisible(treesHeight: Map<String, Int>, previousTallestTree: Int, visibleTrees: MutableSet<String>, i: Int, j: Int) : Int {
  val key = key(i, j)
  val height = treesHeight[key]!!
  if (previousTallestTree < height) {
    visibleTrees.add(key)
    return max(previousTallestTree, height)
  }
  return previousTallestTree
}

private fun readInput(inputFile: File): Triple<Map<String, Int>, Int, Int> {
  var row = 0
  val result = mutableMapOf<String, Int>()
  var column = 0
  inputFile.forEachLine { line ->
    column = 0
    line.forEach { height ->
      result[key(row,column)] = height.digitToInt()
      column++
    }
    row++
  }
  return Triple(result, row, column)
}

private fun key(row: Int, column: Int) = "$row-$column"