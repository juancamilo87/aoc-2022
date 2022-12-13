package day_7

import java.io.File
import kotlin.system.measureTimeMillis

private const val FILE_PATH = "src/day_7/input.txt"

private const val TOTAL_SPACE = 70000000
private const val MIN_SPACE = 30000000

private const val ROOT_DIR = "/"

fun main() {
  println("Time: ${measureTimeMillis { calculate() }}ms")
}

private fun calculate() {
  val inputFile = File(FILE_PATH)
  var currentDirectory = ROOT_DIR
  val directoryMap = mutableMapOf<String, Int>()
  val upperDirectories = mutableListOf<String>()
  inputFile.forEachLine { line ->
    if (isCommand(line)) {
      val command = getCommand(line)

      if (isChangeDirectoryCommand(command)) {
        val newDirectoryResult = getNewDirectory(currentDirectory, command)
        when(newDirectoryResult.second) {
          -1 -> upperDirectories.clear()
          0 -> upperDirectories.removeLast()
          1 -> upperDirectories.add(currentDirectory)
        }
        currentDirectory = newDirectoryResult.first
      } else {
        //list. Do nothing. Things will be handled afterwards.
      }
    } else {
      if (!isDirectory(line)) {
        val size = getFileSize(line)
        directoryMap[currentDirectory] = directoryMap.getOrDefault(currentDirectory, 0) + size
        upperDirectories.forEach { dir ->
          directoryMap[dir] = directoryMap.getOrDefault(dir, 0) + size
        }
      } else {
        // is a directory. It will be handled when it is entered.
      }
    }
  }

  val freeSpace = TOTAL_SPACE - directoryMap[ROOT_DIR]!! // Crash if not. Algorithm would be wrong.
  val neededSpace = MIN_SPACE - freeSpace

  val minDirSize = directoryMap
    .filterValues { it >= neededSpace }
    .minOf { it.value }
  println(minDirSize)
}

private fun isCommand(line: String) = line.startsWith("$")

private fun getCommand(line: String) = line.substring(2)

private fun isChangeDirectoryCommand(command: String) = command.startsWith("cd")

/**
 * Return int is -1 if back to root. 0 if back one step. 1 if one deeper.
 */
private fun getNewDirectory(currentDirectory: String, command: String): Pair<String, Int> {
  return when (val cdCommandParameter = command.substring(3)) {
    ROOT_DIR -> Pair(ROOT_DIR, -1)
    ".." -> Pair(currentDirectory.substring(0, currentDirectory.indexOfLast { it == '/' }), 0)
    else -> Pair("$currentDirectory/$cdCommandParameter", 1)
  }
}

private fun isDirectory(line: String) = line.startsWith("dir")

private fun getFileSize(line: String) = line.split(" ").first().toInt()
