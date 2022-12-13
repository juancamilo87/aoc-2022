package day_13

import java.io.File
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_13/input.txt"

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private fun calculate() {
  val inputFile = File(FILE_PATH)
  val pairs = FileReader.read(inputFile)
  println(
    "Ordered pairs: ${
      pairs.mapIndexedNotNull { index, pair -> if (isRightOrder(pair)) index + 1 else null }.sum()
    }"
  )
}

private fun isRightOrder(pair: Pair<Signal, Signal>) : Boolean {
  return pair.first <= pair.second
}

