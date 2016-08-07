package icfp16.folder

import icfp16.data.Edge
import icfp16.data.Polygon
import icfp16.data.Vertex
//import icfp16.data.equals

fun Vertex.eq(that:Vertex) :Boolean{
  return this.x.equals(that.x) && this.y.equals(that.y)
}

fun Edge.eq(that:Edge) : Boolean{


  val sameOrder = this.a.eq(that.a) && this.b.eq(that.b)

  val differentOrder = this.a.eq(that.b) && this.b.eq(that.a)
  return sameOrder || differentOrder
}

fun LinkedEdge.eq(that:LinkedEdge) : Boolean{
  val sameOrder = this.startVertex.eq(that.startVertex) && this.endVertex.eq(that.endVertex)

  val differentOrder =this.startVertex.eq(that.endVertex) && this.endVertex.eq(that.startVertex)
  return sameOrder || differentOrder
}

fun LinkedEdge.hasVertex(vertex: Vertex) : Boolean{
  return startVertex.eq(vertex) || endVertex.eq(vertex)
}
fun Polygon.selfCrossing() :Boolean{
  this.edges().forEachIndexed { i, edge ->
    this.edges().forEachIndexed { j, that ->
      if(!edge.eq(that))
      {
        val cross = edge.cross(that)
        if(cross != null)
        {
          if(! (edge.a.eq(cross) ||  edge.b.eq(cross) || that.a.eq(cross)  || that.b.eq(cross)))
            return true
        }
      }


    }
  }
  return false
}

fun Polygon.commonEdge(that: Polygon) : Edge?{
  val linkedThis = this.toLindedEdges()
  val linkedThat = that.toLindedEdges()
  linkedThis.forEachIndexed { i, e1 ->
    linkedThat.forEachIndexed { j, e2 ->
      if(e1.eq(e2)){
        return Edge(e1.startVertex, e1.endVertex)
      }
    }
  }
  return null
}

fun Polygon.hasEdge(edge:Edge) : Boolean{
  return this.edges().any { it.eq(edge) }
}

fun Polygon.join(that:Polygon, edge:Edge) : Polygon? {

  if(!this.hasEdge(edge))
    return null
  if(!that.hasEdge(edge))
    return null

  val linkedThis = this.toLindedEdges()
  val linkedThat = that.toLindedEdges()
  var res : MutableList<Vertex> = arrayListOf()
  val commonEdge = LinkedEdge(edge.a, edge.b)
  var currentEdge = linkedThis.first()

  var usedVertex = currentEdge.endVertex
  // scan first poly until find common edge
  while (!currentEdge.eq(commonEdge as LinkedEdge))
  {

    currentEdge = currentEdge.Next
  }
  usedVertex = currentEdge.startVertex
  currentEdge = currentEdge.Next

  // while not returned
  // add current edge endpoint to res

  while (!currentEdge.eq(commonEdge as LinkedEdge))
  {

    res.add(currentEdge.endVertex)
    currentEdge = currentEdge.Next

  }


  // scan second poly until find common edge
  currentEdge = linkedThat.first()

  // scan second poly until find common edge
  while (!currentEdge.eq(commonEdge as LinkedEdge))
  {
    currentEdge = currentEdge.Next
  }

  currentEdge = currentEdge.Next
  // now we sould check if we're going in right direction
  // if current edge have used point, we should change our movement strategy
  var back = false
  if(!currentEdge.hasVertex(usedVertex)){
    back = true
    currentEdge = currentEdge.Prev.Prev
  }


  // while not returned
  // add current edge endpoint to res

  while (!currentEdge.eq(commonEdge as LinkedEdge))
  {

    if(back) {
      res.add(currentEdge.startVertex)
      currentEdge = currentEdge.Prev
    }else
    {
      res.add(currentEdge.endVertex)
      currentEdge = currentEdge.Next
    }

  }

  if(res.last().eq(usedVertex)) {
    res.removeAt(res.count() - 1)
    if(commonEdge.startVertex.eq(usedVertex)) {
      res.add(currentEdge.endVertex)
    }else
    {
      res.add(currentEdge.startVertex)
    }
  }

  return Polygon(res)
}

fun Polygon.join(that:Polygon) : Polygon? {
  val linkedThis = this.toLindedEdges()
  val linkedThat = that.toLindedEdges()
  var res : MutableList<Vertex> = arrayListOf()
  var commonEdge : LinkedEdge? = null

  linkedThis.forEachIndexed { i, e1 ->
    linkedThat.forEachIndexed { j, e2 ->
      if(e1.eq(e2)){
        commonEdge = LinkedEdge(e1.startVertex, e1.endVertex)
      }
    }
  }

  if(commonEdge != null)
  {
    return this.join(that, Edge(commonEdge!!.startVertex, commonEdge!!.endVertex))

  }

  return null
  //throw Throwable(" two polygons does not have common edge $this, $that")
}