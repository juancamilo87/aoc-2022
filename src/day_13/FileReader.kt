package day_13

import java.io.File

object FileReader {
  fun read(inputFile: File) : List<Pair<Signal, Signal>> {
    val pairs = mutableListOf<Pair<Signal, Signal>>()
    var first : Signal? = null
    inputFile.forEachLine { line ->
      if (line.isNotEmpty()) {
        first = if (first == null) {
          SignalList.parse(line)
        } else {
          pairs.add(Pair(first!!, SignalList.parse(line)))
          null
        }
      }
    }
    return pairs
  }
}