package day_3

import java.io.File

private const val FILE_PATH = "src/day_3/input.txt"

fun main() {
  val itemPriorityMap = createPriorityMap()
  var prioritiesSum = 0
  File(FILE_PATH).forEachLine { items ->
    val totalItems = items.length
    val lastIndexFirstCompartment = totalItems / 2 - 1
    val itemsInFirstCompartment = mutableSetOf<Char>()
    itemsInFirstCompartment.addAll(
      items.filterIndexed { index, _ -> index <= lastIndexFirstCompartment }
        .asSequence()
    )
    val wrongItem = items
      .filterIndexed { index, _ -> index > lastIndexFirstCompartment }
      .find { item -> itemsInFirstCompartment.contains(item) }
    prioritiesSum += itemPriorityMap[wrongItem] ?: 0
  }
  println(prioritiesSum)
}

private fun createPriorityMap() : Map<Char, Int> {
  val priorityMap = mutableMapOf<Char, Int>()
  for (i in 'a'..'z') {
    priorityMap[i] = i - 'a' + 1
  }
  for (i in 'A'..'Z') {
    priorityMap[i] = i - 'A' + 27
  }
  return priorityMap
}