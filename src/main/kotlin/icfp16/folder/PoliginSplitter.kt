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

data class SplitResult(val polygons: List<Polygon>,
                       val firstCrossVertex : Vertex?, val secondCrossVertex : Vertex?,
                       val firstCrossEdge : Edge?, val secondCrossEdge: Edge?){
  val splitted = polygons.count() == 2
}

fun Polygon.splitSimple(foldingEdge: Edge): SplitResult {
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
      }

      val resultList =arrayListOf(Polygon(firstPolygonPoints), Polygon(secondPolygonPoints))

      return SplitResult(resultList,firstCrossPoint, secondCrossPoint,
              Edge(firstCrossEdge.startVertex, firstCrossEdge.endVertex) ,Edge(secondCrossEdge.startVertex, secondCrossEdge.endVertex))
    }
  }

  return SplitResult(arrayListOf(this),null, null, null, null)
}

fun ComlexPolygon.splitSimple(foldingEdge: Edge): List<ComlexPolygon> {
  val splitted = final.splitSimple(foldingEdge)
  if(splitted.splitted)
  {
   return arrayListOf(this)
  }else
  {
    //
    return arrayListOf(this)
  }
}


fun Polygon.foldSimple(foldingEdge: Edge): List<Polygon> {
  return arrayListOf(this)
}


