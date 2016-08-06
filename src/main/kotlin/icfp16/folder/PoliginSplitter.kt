package icfp16.folder

import icfp16.data.*
import java.util.*


data class LinkedEdge(val startVertex: Vertex, val endVertex : Vertex) {
  var Next: LinkedEdge = this       // next polygon in linked list
  var Prev: LinkedEdge = this       // previous polygon in linked list
}

fun LinkedEdge.crosses(that:Edge) : Vertex?{
  val e1 = Edge(this.startVertex, this.endVertex)
  return e1.cross(that)
}

fun Polygon.toLindedEdges() : List<LinkedEdge> {
  val res : MutableList<LinkedEdge> = arrayListOf()
  vertices.forEachIndexed { i, vertex ->
    if (i < vertices.count()-1) {
      val linked = LinkedEdge(vertices[i], vertices[i+1])
      res.add(linked)
    }
    else
    {
      val linked = LinkedEdge(vertices[i], vertices[0])
      res.add(linked)
    }}

    res.forEachIndexed { i, linkedEdge ->
      if (i < res.count()-1) {

        if(i == 0)
        {
          linkedEdge.Prev = res.last()
          linkedEdge.Next = res[i+1]
        }else
        {
          linkedEdge.Prev = res[i-1]
          linkedEdge.Next = res[i+1]
        }
      }
      else
      {
        linkedEdge.Prev = res[i-1]
        linkedEdge.Next = res[0]
      }
    }
    return res
}

data class SplitResult(val polygon: Polygon,
                       val splitted: Boolean,
                       val crossVertex : Vertex?,
                       val crossEdge : Edge?,
                       val edgeIndex : Int = 0,
                       val xRatio : Fraction?,
                       val yRatio : Fraction?){
}

fun Polygon.splitSimple(foldingEdge: Edge): List<SplitResult> {
  val linked = this.toLindedEdges()
  val isCrossed = linked.any { edge -> edge.crosses(foldingEdge) != null}
  if(isCrossed)
  {
    // find first cross

    var currentEdge = linked.first()

    while (currentEdge.crosses(foldingEdge) == null)
    {
      currentEdge = currentEdge.Next

    }

    val firstCrossPoint = currentEdge.crosses(foldingEdge)
    val firstCrossEdge = currentEdge;
    if(firstCrossPoint != null) {

      val firstPolygonPoints: MutableList<Vertex> = arrayListOf()
      val secondPolygonPoints: MutableList<Vertex> = arrayListOf()

      firstPolygonPoints.add(firstCrossPoint)
      firstPolygonPoints.add(currentEdge.endVertex)


      currentEdge = currentEdge.Next
      while (currentEdge.crosses(foldingEdge) == null)
      {
        firstPolygonPoints.add(currentEdge.endVertex)
        currentEdge = currentEdge.Next

      }

      val secondCrossPoint = currentEdge.crosses(foldingEdge)
      val secondCrossEdge = currentEdge
      if(secondCrossPoint != null){
        firstPolygonPoints.add(secondCrossPoint)

        secondPolygonPoints.add(secondCrossPoint)
        secondPolygonPoints.add(currentEdge.endVertex)
        currentEdge = currentEdge.Next
        while (currentEdge.crosses(foldingEdge) == null)
        {
          secondPolygonPoints.add(currentEdge.endVertex)
          currentEdge = currentEdge.Next
        }
        secondPolygonPoints.add(firstCrossPoint)
        var firstEdge = Edge(firstCrossEdge.startVertex, firstCrossEdge.endVertex)


        val firstIndex = edges().indexOf(firstEdge)

        // (xc - x1)/ (x2 -x1)
        var firstRatioX = Fraction(1)
        val firstDx = firstEdge.b.x.sub(firstEdge.a.x)
        if(!firstDx.isZero()){
          firstRatioX = firstCrossPoint.x.sub(firstEdge.a.x).divFrac(firstDx)
        }

        // (yc - y1)/ (y2 -y1)
        var firstRatioY = Fraction(1)
        val firstDy = firstEdge.b.y.sub(firstEdge.a.y)
        if(!firstDy.isZero()){
          firstRatioY = firstCrossPoint.y.sub(firstEdge.a.y).divFrac(firstDy)
        }


        val firstSplitResult = SplitResult(Polygon(firstPolygonPoints), true, firstCrossPoint,
            firstEdge, firstIndex, firstRatioX, firstRatioY)

        var secondEdge = Edge(secondCrossEdge.startVertex, secondCrossEdge.endVertex)
        val secondIndex = edges().indexOf(secondEdge)

        var secondRatioX= Fraction(1)
        val secondDx = secondEdge.b.x.sub(secondEdge.a.x)
        if(!secondDx.isZero()){
          secondRatioX = secondCrossPoint.x.sub(secondEdge.a.x).divFrac(secondDx)
        }


        var secondRatioY= Fraction(1)
        val secondDy = secondEdge.b.y.sub(secondEdge.a.y)
        if(!secondDy.isZero()){
          secondRatioY = secondCrossPoint.y.sub(secondEdge.a.y).divFrac(secondDy)
        }

        val secondSplitResult = SplitResult(Polygon(secondPolygonPoints), true, secondCrossPoint,
            secondEdge, secondIndex, secondRatioX, secondRatioY)

        return arrayListOf(firstSplitResult, secondSplitResult)

      }


    }
  }

  return arrayListOf(SplitResult(this, false, null, null, 0, null, null))
}

fun Edge.findSplitPoint(ratioX : Fraction, ratioY:Fraction) : Vertex{
  val xc = ratioX.mul(b.x.sub(a.x)).add(a.x)
  val yc = ratioY.mul(b.y.sub(a.y)).add(a.y)
  return Vertex(xc, yc)
}

fun ComlexPolygon.splitSimple(foldingEdge: Edge): List<ComlexPolygon> {
  val splitted = final.splitSimple(foldingEdge)
  if(splitted.count() > 1)
  {
    var firstNewFinal = splitted.first()
    //now we need to find index of splitted edge
    if(firstNewFinal.crossEdge != null && firstNewFinal.crossVertex != null) {

      val firstInitialEdge = initial.edges()[firstNewFinal.edgeIndex]


    }


    return arrayListOf(this)
  }else
  {
    return arrayListOf(this)
  }
}


fun Polygon.foldSimple(foldingEdge: Edge): List<Polygon> {
  return arrayListOf(this)
}


