package day_10

import java.io.File

private const val FILE_PATH = "src/day_10/input.txt"

fun main() {
  val inputFile = File(FILE_PATH)
  var currentCycle = 1
  var x = 2
  var crtLine = "#"
  inputFile.forEachLine { command ->
    currentCycle %= 40
    if (command == "noop") {
      currentCycle++
      crtLine = updateCrtAndPrint(crtLine, currentCycle, x)
    } else {
      val (_, amount) = command.split(" ")
      crtLine = updateCrtAndPrint(crtLine, currentCycle + 1, x)
      x += amount.toInt()
      currentCycle += 2
      currentCycle %= 40
      crtLine = updateCrtAndPrint(crtLine, currentCycle, x)
    }
  }
}

private fun updateCrtAndPrint(crtLine: String, currentCycle: Int, x: Int) : String {
  var newCrt = crtLine
  newCrt += if (currentCycle in x-1..x+1) {
    "#"
  } else {
    "."
  }
  return if (newCrt.length == 40) {
    println(newCrt)
    ""
  } else newCrt
}