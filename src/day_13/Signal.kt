package day_13

sealed interface Signal : Comparable<Signal>

data class SignalItem(val packet: Int) : Signal {
  override fun compareTo(other: Signal): Int {
    return if (other is SignalItem) {
      packet.compareTo(other.packet)
    } else {
      SignalList(listOf(this)).compareTo(other)
    }
  }

  override fun toString(): String {
    return "$packet"
  }
}

class SignalList internal constructor (internal val packets: List<Signal>) : Signal {
  companion object {
    fun parse(inputPackets: String) : Signal {
      val tempPackets = mutableListOf<MutableList<Signal>>()
      var tempValue = ""
      inputPackets.forEach { char ->
        when(char) {
          '[' -> {
            tempPackets.add(mutableListOf())
          }
          ']' -> {
            if (tempValue.isNotBlank()) {
              tempPackets.last().add(SignalItem(tempValue.toInt()))
              tempValue = ""
            }
            if (tempPackets.size > 1) {
              tempPackets.removeLast().let {
                tempPackets.last().add(SignalList(it))
              }
            }
          }
          ',' -> {
            if (tempValue.isNotBlank()) {
              tempPackets.last().add(SignalItem(tempValue.toInt()))
              tempValue = ""
            }
          }
          else -> {
            tempValue += char
          }
        }
      }
      return SignalList(tempPackets.first())
    }
  }

  override fun compareTo(other: Signal): Int {
    return when(other) {
      is SignalItem -> compareTo(SignalList(listOf(other)))
      is SignalList -> {
        var result = 0
        var counter = 0
        while (result == 0) {
          val thisPacket = packets.getOrNull(counter)
          val otherPacket = other.packets.getOrNull(counter)
          if (thisPacket == null && otherPacket == null) return 0
          else if (thisPacket == null) return -1
          else if (otherPacket == null) return 1
          else {
            result = thisPacket.compareTo(otherPacket)
          }
          counter++
        }
        return result
      }
    }
  }

  override fun toString(): String {
    return "[${packets.joinToString(",")}]"
  }

}