package icfp16.state

import icfp16.data.*
import icfp16.folder.foldMountainVAlley
import icfp16.folder.foldSimple
import icfp16.folder.foldStar

data class ComplexState(val polys: Array<ComplexPolygon> = arrayOf(ComplexPolygon())) : IState {

  override var name: String = ""

  override fun finalPositions(): Array<Vertex> {
    // TODO: This is bit comutational hard
    return polys.flatMap { it.initial.vertices.zip(it.final.vertices) }
        .distinctBy { it.first }
        .map { it.second }
        .toTypedArray()
  }

  // It's actually original positions
  override fun vertexes(): Array<Vertex> {
    // TODO: This is a bit complex but
    return polys.flatMap { it.initial.vertices }.distinctBy{ it }.toTypedArray()
  }

  override fun facets(): Array<Facet> {
    // TODO: This is bit comutational hard
    val vert = vertexes()
    return polys.map {
      Facet(it.initial.vertices.map { vertx ->
        vert.indexOf(vertx)
      })
    }.toTypedArray()
  }

  override fun poligons(): Array<Polygon> {
    return polys.map { it.final }.toTypedArray()
  }

  override fun initialPoligons(): Array<Polygon> {
    return polys.map { it.initial }.toTypedArray()
  }

  fun apply(t: Transform, name: String): IState {
    val polys = polys.map { poly ->
      ComplexPolygon(
          poly.initial,
          poly.transform.compose(t),
          poly.final.apply(t)
      )
    }
    return ComplexState(polys.toTypedArray())
        .appendName(this.name)
        .appendName(name)
  }

  override fun translate(vartex: Vertex): IState {
    return apply(TranslateTransform(vartex), "Translate ($vartex)")
  }

  override fun rotate90(around: Vertex): IState {
//    return ComplexState(polys.map { poly ->
//      ComplexPolygon(initial = poly.initial,
//          final = Polygon(poly.final.vertices.map {
//            val relativePoint = it.sub(around)
//            val related = Vertex(relativePoint.y.neg(), relativePoint.x)
//            val final = related.add(around)
//            final
//          })
//      )
//    }.toTypedArray())
//        .appendName(this.name)
//        .appendName("Rotate 90")
    throw UnsupportedOperationException("TODO")
  }

  override fun rotate180(around: Vertex): IState {
    return rotate90(around).rotate90(around)
        .appendName("Rotate 180")
  }


  override fun rotate270(around: Vertex): IState {
    return rotate90(around).rotate90(around).rotate90(around)
        .appendName("Rotate 270")
  }

  override fun fold(foldingEdge: Edge): IState {
    return ComplexState(polys.flatMap { poly ->
      poly.foldSimple(foldingEdge)
    }.toTypedArray())
        .appendName(this.name)
        .appendName("Folded over $foldingEdge")
  }

  override fun foldMountainVAlley(foldingEdge: Edge, foldingEdge2: Edge): IState {
    return ComplexState(polys.flatMap { poly ->
      poly.foldMountainVAlley(foldingEdge, foldingEdge2)
    }.toTypedArray())
        .appendName(this.name)
        .appendName("Folded over $foldingEdge")
  }

  override fun foldStar(foldingEdge: Edge, ratioX: Fraction, ratioY : Fraction, externalVertex: Vertex): IState{
    return ComplexState(polys.flatMap { poly ->
      poly.foldStar(foldingEdge, ratioX, ratioY, externalVertex)
    }.toTypedArray())
        .appendName("Star folded over $foldingEdge, fraction $ratioX, $ratioY, externalVertes : $externalVertex")
  }

  /**
   * Rotates around pihagoreand triples :)
   * Piphagorean triple is a triple with wich we can make pryamougolniy triangle
   */
  override fun rotate(around: Vertex, pihagorean: Triple<Int, Int, Int>): IState {
    return apply(RotateTransform(around, pihagorean), "Rotate($around, <$pihagorean>)")
  }

}
