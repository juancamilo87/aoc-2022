package day_3

import java.io.File
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_3/input.txt"

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private fun calculate() {
  val itemPriorityMap = createPriorityMap()
  var prioritiesSum = 0
  val itemsFirstElf = mutableSetOf<Char>()
  val itemsSecondElf = mutableSetOf<Char>()
  var previousElfType = 0
  File(FILE_PATH).forEachLine { items ->
    when (previousElfType) {
      0 -> {
        itemsFirstElf.addAll(items.asSequence())
        previousElfType++
      }
      1 -> {
        itemsSecondElf.addAll(items.asSequence())
        previousElfType++
      }
      2 -> {
        val badge =
          items.find { item -> itemsFirstElf.contains(item) && itemsSecondElf.contains(item) }
        prioritiesSum += itemPriorityMap[badge] ?: 0
        itemsFirstElf.clear()
        itemsSecondElf.clear()
        previousElfType = 0
      }
    }
  }
  println(prioritiesSum)
}

private fun createPriorityMap(): Map<Char, Int> {
  val priorityMap = mutableMapOf<Char, Int>()
  for (i in 'a'..'z') {
    priorityMap[i] = i - 'a' + 1
  }
  for (i in 'A'..'Z') {
    priorityMap[i] = i - 'A' + 27
  }
  return priorityMap
}