package day_12

import common.Coordinate

fun findShortestPath(map: Array<IntArray>, start: Coordinate, end: Coordinate): Int {
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
    with(currentCoordinate.moveUp()) {
      if (inMap(this, map) && canMove(
          map,
          currentCoordinate,
          this
        ) && !visitedNodes.contains(this)
      ) {
        queue.addLast(Node(this, currentNode.pathLength + 1))
        visitedNodes.add(this)
      }
    }
    with(currentCoordinate.moveDown()) {
      if (inMap(this, map) && canMove(
          map,
          currentCoordinate,
          this
        ) && !visitedNodes.contains(this)
      ) {
        queue.addLast(Node(this, currentNode.pathLength + 1))
        visitedNodes.add(this)
      }
    }
    with(currentCoordinate.moveLeft()) {
      if (inMap(this, map) && canMove(
          map,
          currentCoordinate,
          this
        ) && !visitedNodes.contains(this)
      ) {
        queue.addLast(Node(this, currentNode.pathLength + 1))
        visitedNodes.add(this)
      }
    }
    with(currentCoordinate.moveRight()) {
      if (inMap(this, map) && canMove(
          map,
          currentCoordinate,
          this
        ) && !visitedNodes.contains(this)
      ) {
        queue.addLast(Node(this, currentNode.pathLength + 1))
        visitedNodes.add(this)
      }
    }
  }
  throw RuntimeException("No path found")
}

fun inMap(coordinate: Coordinate, map: Array<IntArray>): Boolean {
  return map.indices.contains(coordinate.y) && map[0].indices.contains(coordinate.x)
}

fun canMove(map: Array<IntArray>, from: Coordinate, to: Coordinate): Boolean {
  return getHeight(map, to) - getHeight(map, from) <= 1
}

fun getHeight(map: Array<IntArray>, coordinate: Coordinate): Int {
  return map[coordinate.y][coordinate.x]
}