package day_17.part_one

import java.io.File
import kotlin.math.max
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_17/input.txt"

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private val rockTypes = listOf(
  RockType.LINE,
  RockType.CROSS,
  RockType.REVERSE_L,
  RockType.V_LINE,
  RockType.SQUARE
)
private const val totalRockTypes = 5

private fun calculate() {
  val inputFile = File(FILE_PATH)
  val jetStreams = inputFile.readLines().first().toList().mapNotNull { char ->
    when (char) {
      '<' -> Left
      '>' -> Right
      else -> null
    }
  }
  val totalJetStreamEvents = jetStreams.size
  var maxHeight = 0L
  var rocksFallen = 0L
  var rockIndex = 0
  var jetStreamIndex = 0
  val occupiedSpace = mutableSetOf<String>()
  while (rocksFallen < 2) {
    var currentRock: Rock? = Rock.createRock(rockTypes[rockIndex], maxHeight + 3L)
    // if (rocksFallen % 1000000 == 0L) {
    //   println("Start $rocksFallen: $currentRock")
    // }
    while (currentRock != null) {
      val jetStream = jetStreams[jetStreamIndex]
      currentRock = currentRock.move(jetStream, occupiedSpace)
      // println("Move $rocksFallen-$jetStreamIndex: $jetStream - $currentRock")
      val droppedRock = currentRock.drop(occupiedSpace)
      if (droppedRock == null) {
        occupiedSpace.addAll(currentRock.getOccupiedSpace())
        maxHeight = max(currentRock.maxY() + 1, maxHeight)
        // println("End of Rock $rocksFallen: $currentRock - maxRock: ${currentRock.maxY()} - maxHeight: $maxHeight")
      }
      // else {
      //   println("Drop $rocksFallen:$droppedRock")
      // }
      currentRock = droppedRock
      jetStreamIndex = (jetStreamIndex + 1) % totalJetStreamEvents
    }
    rockIndex = (rockIndex + 1) % totalRockTypes
    rocksFallen++
  }
  println("Max rock height: $maxHeight")
}

