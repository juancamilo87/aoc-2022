package day_6

import java.io.File
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_6/input.txt"

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private fun calculate() {
  val inputFile = File(FILE_PATH)
  val inputStream = inputFile.inputStream()
  val codeSet = mutableSetOf<Char>()
  val currentCode = ArrayDeque<Char>()
  var index = 0
  while (inputStream.available() > 0) {
    index++
    val newChar = inputStream.read().toChar()
    currentCode.addLast(newChar)
    if (codeSet.contains(newChar)) {
      while (true) {
        val oldFirst = currentCode.removeFirst()
        if (oldFirst == newChar)
          break
        else
          codeSet.remove(oldFirst)
      }
    }
    if (currentCode.size == 4) {
      break
    } else {
      codeSet.add(newChar)
    }
  }
  println(index)
}
