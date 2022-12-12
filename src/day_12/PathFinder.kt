package day_12

fun findShortestPath(map: Array<IntArray>, start: Coordinate, end: Coordinate) : Int {
  val queue = ArrayDeque<Node>()
  val visitedNodes = mutableSetOf<Coordinate>()
  queue.addLast(Node(start, 0))
  visitedNodes.add(start)
  while (queue.isNotEmpty()) {
    val currentNode = queue.removeFirst()
    val currentCoordinate = currentNode.coordinate
    // println("Exploring: $currentCoordinate")
    if (currentCoordinate == end) {
      return currentNode.pathLength
    }
    currentCoordinate.moveUp()?.let {
      if (canMove(map, currentCoordinate, it) && !visitedNodes.contains(it)) {
        queue.addLast(Node(it, currentNode.pathLength + 1))
        visitedNodes.add(it)
      }
    }
    currentCoordinate.moveDown(map)?.let {
      if (canMove(map, currentCoordinate, it) && !visitedNodes.contains(it)) {
        queue.addLast(Node(it, currentNode.pathLength + 1))
        visitedNodes.add(it)
      }
    }
    currentCoordinate.moveLeft()?.let {
      if (canMove(map, currentCoordinate, it) && !visitedNodes.contains(it)) {
        queue.addLast(Node(it, currentNode.pathLength + 1))
        visitedNodes.add(it)
      }
    }
    currentCoordinate.moveRight(map)?.let {
      if (canMove(map, currentCoordinate, it) && !visitedNodes.contains(it)) {
        queue.addLast(Node(it, currentNode.pathLength + 1))
        visitedNodes.add(it)
      }
    }
  }
  throw RuntimeException("No path found")
}

fun canMove(map: Array<IntArray>, from: Coordinate, to: Coordinate) : Boolean {
  return getHeight(map, to) - getHeight(map, from) <= 1
}

fun getHeight(map: Array<IntArray>, coordinate: Coordinate): Int {
  return map[coordinate.y][coordinate.x]
}