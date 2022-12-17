package day_17.part_two

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
  val jetStreams = inputFile.readLines().first()
  val totalJetStreamEvents = jetStreams.length
  var maxHeight = 0L
  var rockIndex = 0
  var jetStreamIndex = 0
  val occupiedSpace = ArrayDeque<Int>(100)
  val possibleLoops = mutableMapOf<String, Pair<Long, Long>>()
  var count = 0L
  val totalIterations = 1000000000000
  while (count < totalIterations) {
    val key = "$rockIndex-$jetStreamIndex-${occupiedSpace.joinToString(",")}"
    val existingLoop = possibleLoops[key]
    if (existingLoop != null) { //Check for loops
      val diff = count - existingLoop.first
      if (diff > 0) {
        val heightDiff = maxHeight - existingLoop.second
        val missing = totalIterations - count
        val fits = missing / diff
        val newHeight = maxHeight + (heightDiff * fits)
        val newCount = count + (diff * fits)
        count = newCount
        maxHeight = newHeight
      }
    } else {
      possibleLoops[key] = Pair(count, maxHeight)
    }
    var currentRock: Rock? = Rock.createRock(rockTypes[rockIndex], maxHeight + 3L)

    // Move and drop through the free part
    val jetStreamList = mutableListOf<Char>()
    repeat(4) {
      jetStreamList.add(jetStreams[jetStreamIndex])
      jetStreamIndex = (jetStreamIndex + 1) % totalJetStreamEvents
    }
    currentRock = currentRock?.moveAndDropNoCheck(jetStreamList)

    // Drop and move through the occupied part
    while (currentRock != null) {
      val droppedRock = currentRock.drop(occupiedSpace)
      if (droppedRock == null) {
        var shifts = 0
        currentRock.getOccupiedSpace().forEach { (row, line) ->
          val existingLine = Rock.getLine(occupiedSpace, currentRock!!.getOriginalY(), row)
          val newValue = existingLine or line
          Rock.addLine(occupiedSpace, currentRock!!.getOriginalY(), row, newValue)
          shifts++
        }
        maxHeight = max(currentRock.maxY() + 1, maxHeight)
        currentRock = null
      } else {
        currentRock = droppedRock
        val jetStream = jetStreams[jetStreamIndex]
        currentRock = currentRock.move(jetStream, occupiedSpace)
        jetStreamIndex = (jetStreamIndex + 1) % totalJetStreamEvents
      }
    }
    rockIndex = (rockIndex + 1) % totalRockTypes
    count++
  }

  println("Max rock height: $maxHeight")
}

