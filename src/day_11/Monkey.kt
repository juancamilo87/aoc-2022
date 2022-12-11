package day_11

import java.io.File
import java.math.BigInteger
import kotlin.properties.Delegates

class Monkey(id: Int) {
  private lateinit var operator: Operator
  var divisor by Delegates.notNull<Int>()
  private var trueDestMonkeyIndex by Delegates.notNull<Int>()
  private var falseDestMonkeyIndex by Delegates.notNull<Int>()

  var worryLevels = mutableListOf<Int>()
  var worryLevelsDivisibility = mutableListOf<WorryLevel>()
  var inspections: BigInteger = BigInteger.ZERO

  fun calculateNewLevel(oldLvl: Int) = operator.calculate(oldLvl)

  fun calculateDestMonkeyIndex(oldLvl: Int): Int {
    return if (oldLvl % divisor  == 0) trueDestMonkeyIndex else falseDestMonkeyIndex
  }

  fun addItem(item: Int) {
    this.worryLevels.add(item)
  }

  fun addItems(items: List<Int>) {
    this.worryLevels.addAll(items)
  }

  fun setOperator(operator: Operator) {
    this.operator = operator
  }

  fun setTestDivisor(div: Int) {
    this.divisor = div
  }

  @JvmName("setTrueDestMonkeyIndex1")
  fun setTrueDestMonkeyIndex(index: Int) {
    this.trueDestMonkeyIndex = index
  }

  @JvmName("setFalseDestMonkeyIndex1")
  fun setFalseDestMonkeyIndex(index: Int) {
    this.falseDestMonkeyIndex = index
  }

  fun updateWorryLevels(divisors: Set<Int>) {
    worryLevelsDivisibility = worryLevels.map { item ->
      val remainders = divisors.associateWith { item % it }.toMutableMap()
      WorryLevel(remainders)
    }.toMutableList()
  }

  fun calculateNewLevel(oldLvl: WorryLevel) = operator.calculate(oldLvl)

  fun calculateDestMonkeyIndex(worryLvl: WorryLevel): Int {
    return if (worryLvl.remainders[divisor] == 0) trueDestMonkeyIndex else falseDestMonkeyIndex
  }

  fun addItem(worryLevel: WorryLevel) {
    worryLevelsDivisibility.add(worryLevel)
  }
}

fun fileToMonkeyList(inputFile: File) : List<Monkey> {
  val monkeys = mutableListOf<Monkey>()
  var monkeyIndex = -1
  inputFile.forEachLine { line ->
    if (line.startsWith("Monkey")) {
      monkeyIndex++
      monkeys.add(Monkey(monkeyIndex))
      return@forEachLine
    }
    if (line.trim().startsWith("Starting")) {
      monkeys[monkeyIndex].addItems(
        line.split(":")[1]
          .trim()
          .split(",")
          .map { it.trim().toInt() }
      )
      return@forEachLine
    }
    if (line.trim().startsWith("Operation")) {
      val (operator, value) = line.split("=")[1]
        .trim()
        .removePrefix("old")
        .trim()
        .split(" ")
      val num = value.toIntOrNull()
      val ope = if (num == null) Sqr
      else if (operator == "+") Sum(num)
      else Mul(num)
      monkeys[monkeyIndex].setOperator(ope)
      return@forEachLine
    }
    if (line.trim().startsWith("Test")) {
      monkeys[monkeyIndex].setTestDivisor(line.trim().split("by")[1].trim().toInt())
      return@forEachLine
    }
    if (line.trim().startsWith("If true")) {
      monkeys[monkeyIndex].setTrueDestMonkeyIndex(line.trim().split("monkey")[1].trim().toInt())
      return@forEachLine
    }
    if (line.trim().startsWith("If false")) {
      monkeys[monkeyIndex].setFalseDestMonkeyIndex(line.trim().split("monkey")[1].trim().toInt())
      return@forEachLine
    }
  }
  return monkeys
}