package icfp16

data class Problem(
    val poligons: List<Polygon>,
    val skeleton: List<Edge>
)

data class Fraction(val a: Int, val b: Int = 1) {
  fun toDouble(): Double {
    return a.toDouble() / b
  }
}
data class Vertex(val x: Fraction, val y: Fraction) {
  fun toPoint(): Pair<Double, Double> {
    return Pair(x.toDouble(), y.toDouble())
  }
}

data class Polygon(val vertices: List<Vertex>) {
  fun maxVertextIn(direction: Direction): Vertex? {
    when (direction) {
      Direction.LEFT -> return vertices.minBy { v -> v.x.toDouble() }
      Direction.RIGHT -> return vertices.maxBy { v -> v.x.toDouble() }
      Direction.TOP -> return vertices.maxBy { v -> v.y.toDouble() }
      Direction.BOTTOM -> return vertices.minBy { v -> v.y.toDouble() }
    }
  }
}

enum class Direction {
  LEFT, RIGHT, TOP, BOTTOM
}

data class Edge(val a: Vertex, val b: Vertex)

