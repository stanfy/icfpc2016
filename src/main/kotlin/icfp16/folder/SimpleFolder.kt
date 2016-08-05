package icfp16.folder

import icfp16.data.*
import java.math.BigInteger

fun Polygon.fold(by: Edge): List<Polygon> {

  val edges = this.edges()
  var res: MutableList<Polygon> = arrayListOf()
  var currentPoly = Polygon(arrayListOf())
  val crossLine = Line(by)
  var newPoly = true
  edges.forEach {

    val crossPoint = Line(it).interection(crossLine)
    if(crossPoint != null) {

      val crossed = crossPoint.withinBoundary(it)

      if (crossed) {
        currentPoly = Polygon(currentPoly.vertices.plus(crossPoint))
        res.add(currentPoly)

        currentPoly = Polygon(arrayListOf(crossPoint, it.b))

      } else {

        currentPoly = Polygon(currentPoly.vertices.plus(it.a))

      }
    }else
    {
      currentPoly = Polygon(currentPoly.vertices.plus(it.a))
    }
  }

  res.add(currentPoly)

  return res

}

fun Edge.middle(): Vertex {
  val midx = a.x.sub(b.x).div(2).abs()
  val midy = a.y.sub(b.y).div(2).abs()
  return Vertex(midx, midy)
}

fun Edge.cross(that:Edge) : Vertex?{
  val crossPoint = Line(this).interection(Line(that))
  if(crossPoint != null) {
    val crossed = crossPoint.withinBoundary(this)

    if (crossed) {
      return crossPoint
    }
  }
  return null
}

enum class LineSide {
  LEFT, RIGHT, ON
}

data class PolyEdge (val vertex: Vertex, val lineSide: LineSide)
{
  var Next : PolyEdge = this       // next polygon in linked list
  var Prev : PolyEdge = this       // previous polygon in linked list
  var DistOnLine : Fraction = Fraction(1, 2*3*5*7*13*19)      // distance relative to first point on split line

  var Visited : Boolean = false    // for collecting split polygons
}

fun splitEdges(polygon: Polygon, cuttingEdge: Edge) : Pair<MutableList<PolyEdge>,MutableList<PolyEdge>>{

  val splitPoly:MutableList<PolyEdge> = arrayListOf()
  val edgesOnLine:MutableList<PolyEdge> = arrayListOf()


  val edges = polygon.edges()
  edges.forEach {
    val edgeStartSide = it.a.sideOf(cuttingEdge)
    val edgeEndSide = it.b.sideOf(cuttingEdge)
    splitPoly.add(PolyEdge(it.a, edgeStartSide))

    if(edgeStartSide == LineSide.ON){
      edgesOnLine.add(splitPoly.last())
    }
    else
      if (edgeStartSide != edgeEndSide && edgeEndSide != LineSide.ON)
      {
        cuttingEdge.cross(it)
      }

    //      QPointF ip;
    //      auto res = edge.intersect(line, &ip);
    //      assert(res != QLineF::NoIntersection);
    //      SplitPoly.push_back(PolyEdge{ip, LineSide::On});
    //      EdgesOnLine.push_back(&SplitPoly.back());
    //    }

   }
//  for (int i=0; i<poly.count(); i++)
//  {
//    const QLineF edge(poly[i], poly[(i+1)%poly.count()]);
//    const LineSide edgeStartSide = GetSideOfLine(line, edge.p1());
//    const LineSide edgeEndSide = GetSideOfLine(line, edge.p2());
//    SplitPoly.push_back(PolyEdge{poly[i], edgeStartSide});
//

//  }
//
//  // connect doubly linked list, except
//  // first->prev and last->next
//  for (auto iter=SplitPoly.begin(); iter!=std::prev(SplitPoly.end()); iter++)
//  {
//    auto nextIter = std::next(iter);
//    iter->Next = &(*nextIter);
//    nextIter->Prev = &(*iter);
//  }
//
//  // connect first->prev and last->next
//  SplitPoly.back().Next = &SplitPoly.front();
//  SplitPoly.front().Prev = &SplitPoly.back();
  return  Pair(splitPoly, edgesOnLine)
}

fun Vertex.sideOf(edge: Edge) : LineSide {

  val x1 = edge.a.x
  val x2 = edge.b.x
  val y1 = edge.a.y
  val y2 = edge.b.y

  val d = x.sub(x1).mul(y2.sub(y)).sub(y.sub(y1).mul(x2.sub(x)))

  if(d.geq(Fraction(1,13))){
    return LineSide.RIGHT
  } else
  {
    if(d.leq(Fraction(-1, 13))){
      return LineSide.LEFT
    }else
    {
      return LineSide.ON
    }
  }
}

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

