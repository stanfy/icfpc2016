package icfp16.folder

import icfp16.data.*


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
                       val foldingEdge: Edge){
  fun validated(): SplitResult {
    if (polygon.vertices.distinct().count() == polygon.vertices.count()) {
      return this
    }
    return this
  }
}

fun Polygon.splitSimple(foldingEdge: Edge): List<SplitResult> {
  val linked = this.toLindedEdges()
  // check if we're trying to splith through one of edges
  val sameEdge = linked.any { link ->
           isLeft(link.startVertex, link.endVertex, foldingEdge.a) == 0
        && isLeft(link.startVertex, link.endVertex, foldingEdge.b) == 0
  }
  val isCrossed = !sameEdge && linked.any { edge -> edge.crosses(foldingEdge) != null}

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
// TODO :  THIS IS NOT WORKING :(
      if (currentEdge.endVertex == firstCrossPoint)  {
        currentEdge = currentEdge.Next
      }

      firstPolygonPoints.add(currentEdge.endVertex)

      if (firstPolygonPoints.distinct().count() != firstPolygonPoints.count()) {
        print(" Not cool :(")
      }

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

        // In case if we have it on the edge of polys - we would better fo to tnext poly
        if (currentEdge.endVertex == secondCrossPoint)  {
          currentEdge = currentEdge.Next
        }

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

        val firstSplitResult = SplitResult(Polygon(firstPolygonPoints), true, firstCrossPoint,
            firstEdge, firstIndex, foldingEdge)

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
            secondEdge, secondIndex, foldingEdge)

        return arrayListOf(firstSplitResult.validated(), secondSplitResult.validated())

      }


    }
  }
  return arrayListOf(SplitResult(this, false, null, null, 0, foldingEdge).validated())
}

fun Edge.findSplitPoint(ratioX : Fraction, ratioY:Fraction) : Vertex{
  val xc = ratioX.mul(b.x.sub(a.x)).add(a.x)
  val yc = ratioY.mul(b.y.sub(a.y)).add(a.y)
  return Vertex(xc, yc)
}

fun ComplexPolygon.splitSimple(foldingEdge: Edge, debug: Boolean = false): List<ComplexPolygon> {
  val splitted = final.splitSimple(foldingEdge)
  if(splitted.count() > 1)
  {
    val split1 = splitted.first()
    val split2= splitted.last()
    val final1 = split1.polygon
    val final2 = split2.polygon

    if (debug) {
      println("My initial size ${initial.vertices.size} ${final.vertices.size}")
      println("foldingEdge: ${split1.foldingEdge} and ${split2.foldingEdge}")
    }

    // now we need split initial poly
    if (split1.crossEdge != null) {
      val initEdge = Edge(transform.reverse(split1.foldingEdge.a), transform.reverse(split1.foldingEdge.b))
      val splittedInit = initial.splitSimple(initEdge)
      if (debug) {
        println("final: $final")
        println("edge: $foldingEdge")
        println("initial: $initial")
        println("edge: $initEdge")
        splittedInit.forEach {
          println(it.polygon.vertices)
        }
      }
      if(splittedInit.count() >1)
      {
        val initial1 = splittedInit.first().polygon
        val initial2 = splittedInit.last().polygon
        val res1 = ComplexPolygon(initial1, transform, final1)
        val res2 = ComplexPolygon(initial2, transform, final2)

        return arrayListOf(res1, res2)
      }
    }
    return arrayListOf(this)
  }else
  {
    return arrayListOf(this)
  }
}

fun isLeft(a: Vertex, b: Vertex, c: Vertex): Int {
  return (b.x.sub(a.x).mul(c.y.sub(a.y)))
  .sub(b.y.sub(a.y).mul(c.x.sub(a.x))).a.signum()
}

fun ComplexPolygon.foldSimple(foldingEdge: Edge): List<ComplexPolygon> {
  val splitted = this.splitSimple(foldingEdge)
  val result = splitted.map {
    if (it.final.vertices.any { point -> isLeft(foldingEdge.a, foldingEdge.b, point) > 0 }) {
      val t = ReflectTransform(foldingEdge)
      ComplexPolygon(it.initial, this.transform.compose(t), it.final.apply(t))
    } else {
      it
    }
  }
  return result
}

fun ComplexPolygon.foldMountainVAlley(foldingEdge: Edge, foldingEdge2: Edge): List<ComplexPolygon> {
  val splitted = this.splitSimple(foldingEdge)
  val result = splitted.flatMap {
    if (it.final.vertices.any { point -> isLeft(foldingEdge.a, foldingEdge.b, point) > 0 }) {
      val folded2 = it.foldSimple(foldingEdge2)
      folded2.map {
        val t = ReflectTransform(foldingEdge)
        ComplexPolygon(it.initial, this.transform.compose(t), it.final.apply(t))
      }
    } else {
      listOf(it)
    }
  }
  return result
}


fun ComplexPolygon.foldStar(foldingEdge: Edge, ratioX: Fraction, ratioY : Fraction, externalVertex: Vertex): List<ComplexPolygon> {
  val firstFold = this.foldSimple(foldingEdge)
  val crossPoint = foldingEdge.findSplitPoint(ratioX, ratioY)

  val secondFoldingEdge1 = Edge(externalVertex, crossPoint)
  val secondFoldingEdge2 = Edge(externalVertex.reflect(foldingEdge), crossPoint)
  val res : MutableList<ComplexPolygon> = arrayListOf()

  firstFold.forEach {
    val secondFold1 = it.foldSimple(secondFoldingEdge1)
    if(secondFold1.count() >1) {
      res.addAll(secondFold1)
    }
    val secondFold2 = it.foldSimple(secondFoldingEdge2)
    if(secondFold2.count() >1) {
      res.addAll(secondFold2)
    }
  }
  return res
}



