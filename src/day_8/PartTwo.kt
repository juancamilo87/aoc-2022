package day_8

import java.io.File
import kotlin.math.max
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_8/input.txt"

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private fun calculate() {
  val inputFile = File(FILE_PATH)
  val (treesHeight, rows, columns) = readInput(inputFile)
  var maxScore = 0
  for (i in 1 until rows - 1) {
    for (j in 1 until columns - 1) {
      maxScore = max(maxScore, treeScore(treesHeight, i, j, rows, columns))
    }
  }
  println(maxScore)
}

private fun treeScore(treesHeight: Map<String, Int>, row: Int, col: Int, rows: Int, columns: Int) : Int {
  val treeHeight = treesHeight[key(row, col)]!!
  var score = 1
  var visible = true
  var currentRow = row
  var currentScore = 0
  while (visible) {
    currentScore++
    currentRow--
    if (currentRow == 0 || treesHeight[key(currentRow, col)]!! >= treeHeight) {
      visible = false
    }
  }
  score *= currentScore
  currentRow = row
  currentScore = 0
  visible = true
  while (visible) {
    currentScore++
    currentRow++
    if (currentRow == rows - 1 || treesHeight[key(currentRow, col)]!! >= treeHeight) {
      visible = false
    }
  }
  score *= currentScore
  var currentColumn = col
  currentScore = 0
  visible = true
  while (visible) {
    currentScore++
    currentColumn--
    if (currentColumn == 0 || treesHeight[key(row, currentColumn)]!! >= treeHeight) {
      visible = false
    }
  }
  score *= currentScore
  currentColumn = col
  currentScore = 0
  visible = true
  while (visible) {
    currentScore++
    currentColumn++
    if (currentColumn == columns - 1 || treesHeight[key(row, currentColumn)]!! >= treeHeight) {
      visible = false
    }
  }
  score *= currentScore
  return score
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