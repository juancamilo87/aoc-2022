package day_2

import java.io.File
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_2/input.txt"

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private fun calculate() {
  var totalScore = 0
  val matchCombinationCount = mutableMapOf<String, Int>()
  File(FILE_PATH).forEachLine {
    matchCombinationCount[it] = matchCombinationCount.getOrDefault(it, 0) + 1
  }
  matchCombinationCount.forEach { (matchEvent, count) ->
    totalScore += score(matchEvent) * count
  }
  println(totalScore)
}

private fun score(matchEvent: String): Int {
  val (opponent, result) = matchEvent.split(" ")
  var matchScore = 0
  when (result) {
    "X" -> { // lose
      when (opponent) {
        "A" -> matchScore += 3 // Rock - Scissors
        "B" -> matchScore += 1 // Paper - Rock
        "C" -> matchScore += 2 // Scissors - Paper
      }
    }
    "Y" -> { // draw
      matchScore += 3
      when (opponent) {
        "A" -> matchScore += 1 // Rock - Rock
        "B" -> matchScore += 2 // Paper - Paper
        "C" -> matchScore += 3 // Scissors - Scissors
      }
    }
    "Z" -> { // win
      matchScore += 6
      when (opponent) {
        "A" -> matchScore += 2 // Rock - Paper
        "B" -> matchScore += 3 // Paper - Scissors
        "C" -> matchScore += 1 // Scissors - Rock
      }
    }
  }
  return matchScore
}