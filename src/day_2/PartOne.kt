package day_2

import java.io.File

private const val FILE_PATH = "src/day_2/input.txt"

fun main() {
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

private fun score(matchEvent: String) : Int {
  val (opponent, me) = matchEvent.split(" ")
  var matchScore = 0
  when(me) {
    "X" -> { //Rock
      matchScore += 1
      when (opponent) {
        "A" -> matchScore += 3 //Rock
        "B" -> matchScore += 0 //Paper
        "C" -> matchScore += 6 //Scissors
      }
    }
    "Y" -> { // Paper
      matchScore += 2
      when (opponent) {
        "A" -> matchScore += 6 //Rock
        "B" -> matchScore += 3 //Paper
        "C" -> matchScore += 0 //Scissors
      }
    }
    "Z" -> { //Scissors
      matchScore += 3
      when (opponent) {
        "A" -> matchScore += 0 //Rock
        "B" -> matchScore += 6 //Paper
        "C" -> matchScore += 3 //Scissors
      }
    }
  }
  return matchScore
}