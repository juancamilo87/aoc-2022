package day_11

sealed interface Operator {
  fun calculate(old: Int): Int
  fun calculate(old: WorryLevel): WorryLevel
}

class Sum(private val num: Int) : Operator {
  override fun calculate(old: Int): Int = old + num

  override fun calculate(old: WorryLevel): WorryLevel {
    return WorryLevel(old.remainders.mapValues { pair ->
      (pair.value + num) % pair.key
    })
  }
}

class Mul(private val num: Int) : Operator {
  override fun calculate(old: Int) = old * num

  override fun calculate(old: WorryLevel): WorryLevel {
    return WorryLevel(old.remainders.mapValues { pair ->
      (pair.value * num) % pair.key
    })
  }
}

object Sqr : Operator {
  override fun calculate(old: Int): Int = old * old
  override fun calculate(old: WorryLevel): WorryLevel {
    return WorryLevel(old.remainders.mapValues { pair ->
      (pair.value * pair.value) % pair.key
    })
  }
}