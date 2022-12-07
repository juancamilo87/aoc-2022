package day_1

import java.io.File
import kotlin.math.max

private const val FILE_PATH = "src/day_1/input.txt"

fun main() {
  var maxCalories = -1
  var tempCalories = 0
  File(FILE_PATH).forEachLine {
    if (it.isNotEmpty()) {
      tempCalories += it.toInt()
    } else {
      maxCalories = max(maxCalories, tempCalories)
      tempCalories = 0
    }
  }
  println(maxCalories)
}