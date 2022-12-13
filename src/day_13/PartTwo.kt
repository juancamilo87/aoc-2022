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
  val divider1 = SignalList.parse("[[2]]")
  val divider2 = SignalList.parse("[[6]]")
  val signals = pairs.flatMap { listOf(it.first, it.second) }.plus(
    listOf(divider1, divider2)
  ).sorted()
  val divider1Index = signals.indexOf(divider1)
  val divider2Index = signals.indexOf(divider2)
  println("Decoder packets are in ${divider1Index + 1}, ${divider2Index + 1}.")
  println("Decoder key: ${(divider1Index + 1) * (divider2Index + 1)}")
}

