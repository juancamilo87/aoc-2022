package day_11

import java.io.File

private const val FILE_PATH = "src/day_11/input.txt"

fun main() {
  val inputFile = File(FILE_PATH)
  val monkeys = fileToMonkeyList(inputFile)
  repeat(20) {
    monkeys.forEach { monkey ->
      monkey.worryLevels.forEach { oldLvl ->
        val newLvl = monkey.calculateNewLevel(oldLvl).div(3)
        val destMonkeyIndex = monkey.calculateDestMonkeyIndex(newLvl)
        monkeys[destMonkeyIndex].addItem(newLvl)
        monkey.inspections++
      }
      monkey.worryLevels.clear()
    }
  }
  val sortedMonkeys = monkeys.sortedByDescending { it.inspections }
  println(sortedMonkeys[0].inspections * sortedMonkeys[1].inspections)
}
