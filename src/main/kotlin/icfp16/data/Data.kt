package icfp16.data

import icfp16.state.IState
import java.math.BigInteger

data class Problem(
  val poligons: List<Polygon>,
  val skeleton: List<Edge>,
  val rawString: String = ""
)

data class ProblemContainer (
  val problem: Problem,
  var problemId: String = "",
  var problemHash: String = ""
)

data class SolutionContainer (
    val problemContainer: ProblemContainer,
    val state: IState,
    val realResemblance: Double,
    val estimatedResemblance: Double
)

data class Fraction(val a: BigInteger, val b: BigInteger = BigInteger.ONE) {
  constructor(a:Int, b: Int = 1):this(a = BigInteger("$a"), b = BigInteger("$b"))
  constructor(a:Long, b: Long = 1):this(a = BigInteger("$a"), b = BigInteger("$b"))
  fun toDouble(): Double {
    return a.toDouble() / b.toDouble()
  }

  fun isZero(): Boolean {
    return BigInteger.ZERO.equals(a)
  }

  override fun toString(): String {
    return if (b == BigInteger.ONE) "$a" else "$a/$b"
  }

  override fun equals(other: Any?): Boolean {
    if(other !is Fraction)
      return false
    else
      return this.scaledA(other.b) == other.scaledA(this.b)
    }
}



fun f(a: Int, b: Int): Fraction {
  return Fraction(a, b)
}

data class Vertex(val x: Fraction, val y: Fraction) {
  constructor(x: Int, y: Int = 1) : this(x = Fraction(x), y = Fraction(y))

  // Be careful! You should use relative values for this. x, and y are big!
  fun toPoint(): DPoint {
    return DPoint(x.toDouble(), y.toDouble())
  }

  fun relativeVector(v: Vertex): Vector {
    return Vector(x.sub(v.x).toDouble(), y.sub(v.y).toDouble())
  }

  override fun toString(): String {
    return "$x,$y"
  }

}

fun v(x: Fraction, y: Fraction): Vertex {
  return Vertex(x, y)
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

  fun edges() : List<Edge>{
    val pairs = vertices.subList(0, vertices.count()-1).zip(vertices.subList(1, vertices.count()))
    val closedPairs = pairs.plus(Pair(vertices.last(), vertices.first()))
    return closedPairs.map{Edge(it.first, it.second)}
  }

  fun area(): Double {
    var value = 0.0
    val count = vertices.count()
    var j = count - 1

    for (i in 0..count - 1) {
      value += vertices[j].x.add(vertices[i].x).mul(vertices[j].y.sub(vertices[i].y)).toDouble()
      j = i
    }
    return Math.abs(value) * 0.5
  }

  fun convexIn(v: Vertex, includeEdges: Boolean = true): Boolean {
    //http://stackoverflow.com/questions/1119627/how-to-test-if-a-point-is-inside-of-a-convex-polygon-in-2d-integer-coordinates
    // + modification to include points on edges
    var firstSide = VectorSide.NONE
    for (n in 0..vertices.size-1) {
      val a = vertices[n]
      val b = vertices[(n + 1) % vertices.size]
      val affineSegment = b.relativeVector(a)
      val affinePoint = v.relativeVector(a)

      val side = affineSegment.side(affinePoint)
      if (side == VectorSide.NONE) {
        if (!includeEdges) {
          return false
        }
        // Check if it's on the edge.
        return Edge(a, b).hasPoint(v)
      }
      if (firstSide == VectorSide.NONE) {
        firstSide = side
      } else if (firstSide != side) {
        return false
      }
    }
    return true
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

data class DPoint(val x: Double, val y: Double)

data class Edge(val a: Vertex, val b: Vertex){

  // Relative calculations!!! Yeah, no big integers!
  fun edgePoint(): DPoint = DPoint(b.x.sub(a.x).toDouble(), b.y.sub(a.y).toDouble())

  fun length(): Double = Math.sqrt(comparativeValue())

  // Sum of squares - no need of sqrt.
  fun comparativeValue(): Double {
    val data = edgePoint()
    return data.x * data.x + data.y * data.y
  }

  fun hasPoint(v: Vertex): Boolean {
    val edgePoint = edgePoint()
    val p = DPoint(v.x.sub(a.x).toDouble(), v.y.sub(a.y).toDouble())

    // Our equation:
    // p.y = edgePoint.y / edgePoint.x * p.x

    // Vertical line.
    if (edgePoint.x == 0.0) {
      return p.x == 0.0
          && (edgePoint.y >= 0 && p.y >= 0 || edgePoint.y < 0 && p.y < 0)
          && Math.abs(p.y) <= Math.abs(edgePoint.y)
    }

    return Math.abs(p.y - edgePoint.y / edgePoint.x * p.x) < 0.0000001 // TODO: Fuck, it's time to learn how to compare doubles finally.
  }
}

/** Use it after normalization only (vertex coordinates can be big). This is supposed to be in [0..1] interval. */
data class Vector(val a: Double, val b: Double) {
  fun scalarProduct(v2: Vector): Double {
    return a * v2.a + b * v2.b
  }
  fun crossProduct(v2: Vector): Double {
    return a * v2.b - b * v2.a
  }
  fun side(v2: Vector): VectorSide {
    val p = crossProduct(v2)
    if (p < 0) return VectorSide.LEFT
    if (p > 0) return VectorSide.RIGHT
    return VectorSide.NONE
  }
}

enum class VectorSide { LEFT, RIGHT, NONE }