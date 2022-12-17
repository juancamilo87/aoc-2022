package day_17.part_one

sealed interface Jet
object Left : Jet {
  override fun toString(): String {
    return "Left"
  }
}

object Right : Jet {
  override fun toString(): String {
    return "Right"
  }
}