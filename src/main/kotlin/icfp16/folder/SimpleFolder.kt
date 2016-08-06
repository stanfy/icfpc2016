package icfp16.folder

import icfp16.data.*
import java.math.BigInteger
import java.util.*


data class Line(val edge: Edge) {

  fun interection(line: Line): Vertex? {
    // https://www.topcoder.com/community/data-science/data-science-tutorials/geometry-concepts-line-intersection-and-its-applications/
    val A1 = edge.a.y.sub(edge.b.y)
    val B1 = edge.b.x.sub(edge.a.x)
    val C1 = A1.mul(edge.a.x).add(B1.mul(edge.a.y))

    val A2 = line.edge.a.y.sub(line.edge.b.y)
    val B2 = line.edge.b.x.sub(line.edge.a.x)
    val C2 = A2.mul(line.edge.a.x).add(B2.mul(line.edge.a.y))

    val det = A1.mul(B2).sub(A2.mul(B1))
    if (det.a.equals(BigInteger.ZERO)) {
      return null
    }
    val x = (B2.mul(C1).sub(B1.mul(C2))).divFrac(det)
    val y = (A1.mul(C2).sub(A2.mul(C1))).divFrac(det)
    return Vertex(x, y)
  }

}

fun Edge.middle(): Vertex {
  val midx = a.x.sub(b.x).div(2).abs()
  val midy = a.y.sub(b.y).div(2).abs()
  return Vertex(midx, midy)
}

fun Edge.cross(that: Edge): Vertex? {
  val crossPoint = Line(this).interection(Line(that))
  if (crossPoint != null) {
    val crossed = crossPoint.withinBoundary(this)

    if (crossed) {
      return crossPoint
    }
  }
  return null
}

// distance is nonEuqlidian, but hukares
fun Vertex.distance(that: Vertex): Fraction {
  val dx = x.sub(that.x)
  val dy = y.sub(that.y)
  return dx.mul(dx).add(dy.mul(dy))
}

fun Vertex.signedDistance(edge: Edge): Fraction {

  val x1 = edge.a.x
  val x2 = edge.b.x
  val y1 = edge.a.y
  val y2 = edge.b.y

  val dx = x2.sub(x1)
  val dy = y2.sub(y1)
  // scalar projection on line. in case of co-linear
  // vectors this is equal to the signed distance.
  return x.sub(x1).mul(dx).add((y.sub(y1).mul(dy)))
}


fun Vertex.sideOf(edge: Edge): LineSide {

  val x1 = edge.a.x
  val x2 = edge.b.x
  val y1 = edge.a.y
  val y2 = edge.b.y

  val d = x.sub(x1).mul(y2.sub(y)).sub(y.sub(y1).mul(x2.sub(x)))

  if (d.geq(Fraction(1, 13))) {
    return LineSide.RIGHT
  } else {
    if (d.leq(Fraction(-1, 13))) {
      return LineSide.LEFT
    } else {
      return LineSide.ON
    }
  }
}

enum class LineSide {
  LEFT, RIGHT, ON
}

data class PolyEdge(val vertex: Vertex, val lineSide: LineSide) {
  var Next: PolyEdge = this       // next polygon in linked list
  var Prev: PolyEdge = this       // previous polygon in linked list
  var DistOnLine: Fraction = Fraction(1, 2 * 3 * 5 * 7 * 13 * 19)      // distance relative to first point on split line

  var Visited: Boolean = false    // for collecting split polygons
}

fun splitEdges(polygon: Polygon, cuttingEdge: Edge): Pair<MutableList<PolyEdge>, MutableList<PolyEdge>> {

  val splitPoly: MutableList<PolyEdge> = arrayListOf()
  val edgesOnLine: MutableList<PolyEdge> = arrayListOf()


  val edges = polygon.edges()
  edges.forEach {
    val edgeStartSide = it.a.sideOf(cuttingEdge)
    val edgeEndSide = it.b.sideOf(cuttingEdge)
    splitPoly.add(PolyEdge(it.a, edgeStartSide))

    if (edgeStartSide == LineSide.ON) {
      edgesOnLine.add(splitPoly.last())
    } else
      if (edgeStartSide != edgeEndSide && edgeEndSide != LineSide.ON) {
        val crossPoint = cuttingEdge.cross(it)
        if (crossPoint != null) {
          splitPoly.add(PolyEdge(crossPoint, LineSide.ON))
          edgesOnLine.add(splitPoly.last())
        }
      }

  }
  val length = splitPoly.count() - 1

//  // connect doubly linked list, except
//  // first->prev and last->next
  splitPoly.forEachIndexed { i, polyEdge ->
    if (i < length) {
      splitPoly[i].Next = splitPoly[i + 1]
      splitPoly[i + 1].Prev = splitPoly[i]
    }
  }

//  // connect first->prev and last->next

  splitPoly.last().Next = splitPoly.first()
  splitPoly.first().Prev = splitPoly.last()

  return Pair(splitPoly, edgesOnLine)
}

fun sortEdges(edgesOnLine: MutableList<PolyEdge>, cuttingEdge: Edge): MutableList<PolyEdge> {

  edgesOnLine.sortWith(object : Comparator<PolyEdge> {
    override fun compare(x: PolyEdge?, y: PolyEdge?): Int {
      if (x != null && y != null) {
        // it's important to take the signed distance here,
        // because it can happen that the split line starts/ends
        // inside the polygon. in that case intersection points
        // can fall on both sides of the split line and taking
        // an unsigned distance metric will result in wrongly
        // ordered points in EdgesOnLine.

        val d1 = x.vertex.signedDistance(cuttingEdge)
        val d2 = y.vertex.signedDistance(cuttingEdge)
        if (d1.le(d2))
          return -1
        if (d1.ge(d2))
          return 1
        return 0
      } else {
        throw NullPointerException("x or y edges are null")
      }
    }
  })
//  for (size_t i=1; i<EdgesOnLine.size(); i++)
//  EdgesOnLine[i]->DistOnLine = PointDistance(EdgesOnLine[i]->StartPos, EdgesOnLine[0]->StartPos);
  edgesOnLine.forEachIndexed { i, polyEdge ->
    polyEdge.DistOnLine = polyEdge.vertex.distance(edgesOnLine.first().vertex)
  }
  return edgesOnLine
}

fun cyclesValid(list: MutableList<PolyEdge>): Boolean {
  val start = list.first()

  var current = start.Next
  var runningCount = 0
  val totalCount = list.count()
  while (current != start) {
    runningCount++
    if (runningCount > totalCount)
      return false
    current = current.Next
  }
  return true
}

fun createBridge(src: PolyEdge, dst: PolyEdge, splitPoly: MutableList<PolyEdge>): MutableList<PolyEdge> {

  splitPoly.add(src)
  val a = splitPoly.last()

  splitPoly.add(dst)
  val b = splitPoly.last()
  a.Next = dst
  a.Prev = src.Prev
  b.Next = src
  b.Prev = dst.Prev
  src.Prev.Next = a
  src.Prev = b
  dst.Prev.Next = b
  dst.Prev = a

  return splitPoly
}

// reference documentation https://geidav.wordpress.com/2015/03/21/splitting-an-arbitrary-polygon-by-a-line/
// reference implementation https://github.com/geidav/concave-poly-splitter/blob/master/polysplitter.cpp
// TODO: translate function void PolySplitter::SplitPolygon() from cpp to kotlin
// TODO: translate function std::vector<QPolygonF> PolySplitter::Split(const QPolygonF &poly, const QLineF &line) from cpp to kotlin
// TODO: use PolySplitter::Split in Polygon.fold

fun Polygon.fold(foldingEdge: Edge): List<Polygon> {

//  val edges = this.edges()
  var res: MutableList<Polygon> = arrayListOf()

  // !!! what should fold do
  // val splitted = splitPolygon(this)
  // foreach(polygon in splitted)
  //   foreach(edge in polygon)
  //       yield edge.reflect(foldingEdge)




  return res

}
