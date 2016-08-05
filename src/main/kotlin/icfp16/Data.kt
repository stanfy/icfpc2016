package icfp16

import java.math.BigInteger

data class Problem(
    val poligons: List<Polygon>,
    val skeleton: List<Edge>,
    val rawString: String = "",
    var problemId: String = "",
    var problemHash: String = ""
)

data class Fraction(val a: BigInteger, val b: BigInteger = BigInteger.ONE) {
  constructor(a:Int, b: Int = 1):this(a = BigInteger("$a"), b = BigInteger("$b"))
  fun toDouble(): Double {
    return a.toDouble() / b.toDouble()
  }

  override fun toString(): String {
    return if (b == BigInteger.ONE) "$a" else "$a/$b"
  }
}


data class Vertex(val x: Fraction, val y: Fraction) {
  constructor(x: Int, y: Int = 1) : this(x = Fraction(x), y = Fraction(y))
  fun toPoint(): Pair<Double, Double> {
    return Pair(x.toDouble(), y.toDouble())
  }

  override fun toString(): String {
    return "$x,$y"
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

data class Facet(val indexes: List<Int>) {
  override fun toString(): String {
    return "${indexes.size} " + indexes.joinToString(separator = " ") { "$it" }
  }
}

enum class Direction {
  LEFT, RIGHT, TOP, BOTTOM
}

data class Edge(val a: Vertex, val b: Vertex)

