package day_5

import java.io.File

private const val FILE_PATH = "src/day_5/input.txt"

fun main() {
  val inputFile = File(FILE_PATH)
  val totalStacks = (inputFile.useLines {
    it.firstOrNull { line -> line[0].toString().isBlank() }
  }?.split(" ")?.last()?.toInt()) ?: 0
  val stacks = Array<ArrayDeque<Char>>(totalStacks) { ArrayDeque() }
  var checkingStacks = true
  var checkingMoves = false
  inputFile.forEachLine { items ->
    if (checkingStacks && items[0].toString().isBlank()) {
      checkingStacks = false
      return@forEachLine
    }
    if (!checkingMoves && items.isBlank()) {
      checkingMoves = true
      return@forEachLine
    }
    if (checkingStacks) {
      for (i in 1..totalStacks) {
        val index = i * 4 - 3
        if (items.length < index) break
        if (items[index].toString().isNotBlank()) {
          stacks[i - 1].addFirst(items[index])
        }
      }
      return@forEachLine
    }
    if (checkingMoves) {
      val actions = items.split(" ");
      val (move, from, to) = actions.mapNotNull { it.toIntOrNull() }
      repeat(move) {
        val toMove = stacks[from - 1].removeLast()
        stacks[to - 1].addLast(toMove)
      }
    }
  }
  var result = ""
  stacks.forEach { stack ->
    result += stack.last()
  }
  println(result)
}
