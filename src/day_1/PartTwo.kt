package day_1

import java.io.File
import kotlin.math.max

private const val FILE_PATH = "src/day_1/input.txt"

fun main() {
  val maxCalories = IntArray(3) { -1 }
  var tempCalories = 0
  File(FILE_PATH).forEachLine {
    if (it.isNotEmpty()) {
      tempCalories += it.toInt()
    } else {
      maxCalories.sort()
      maxCalories[0] = max(maxCalories[0], tempCalories)
      tempCalories = 0
    }
  }
  println(maxCalories.sum())
}