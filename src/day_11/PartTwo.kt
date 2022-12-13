package day_11

import java.io.File
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_11/input.txt"

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private fun calculate() {
  val inputFile = File(FILE_PATH)
  val monkeys = fileToMonkeyList(inputFile)
  val divisors = monkeys.map { it.divisor }.toSet()
  monkeys.forEach { it.updateWorryLevels(divisors) }
  repeat(10000) {
    monkeys.forEach { monkey ->
      monkey.worryLevelsDivisibility.forEach { oldLvl ->
        val newLvl = monkey.calculateNewLevel(oldLvl)
        val destMonkeyIndex = monkey.calculateDestMonkeyIndex(newLvl)
        monkeys[destMonkeyIndex].addItem(newLvl)
        monkey.inspections++
      }
      monkey.worryLevelsDivisibility.clear()
    }
  }
  val sortedMonkeys = monkeys.sortedByDescending { it.inspections }
  println(sortedMonkeys[0].inspections * sortedMonkeys[1].inspections)
}
