package icfp16.state

import icfp16.data.*
import icfp16.folder.splitSimple

data class ComplexState(val polys: Array<ComlexPolygon>) : IState {

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
    return polys.flatMap { it.initial.vertices }.distinct().toTypedArray()
  }

  override fun facets(): Array<Facet> {
    // TODO: This is bit comutational hard
    val vert = vertexes()
    return polys.map {
      Facet(it.initial.vertices.map { vert.indexOf(it) })
    }.toTypedArray()
  }

  override fun poligons(): Array<Polygon> {
    return polys.map { it.final }.toTypedArray()
  }

  override fun initialPoligons(): Array<Polygon> {
    return polys.map { it.initial }.toTypedArray()
  }

  override fun translate(vartex: Vertex): IState {
    return ComplexState(polys.map {
      ComlexPolygon(initial = it.initial,
          final = it.final.add(vartex)
      )
    }.toTypedArray())
  }

  override fun rotate90(around: Vertex): IState {
    return ComplexState(polys.map {
      ComlexPolygon(initial = it.initial,
          final = Polygon(it.final.vertices.map {
            val relativePoint = it.sub(around)
            val related = Vertex(relativePoint.y.neg(), relativePoint.x)
            val final = related.add(around)
            final
          })
      )
    }.toTypedArray())
  }

  override fun rotate180(around: Vertex): IState {
    return rotate90(around).rotate90(around)
  }


  override fun rotate270(around: Vertex): IState {
    return rotate90(around).rotate90(around).rotate90(around)
  }

  override fun split(splitterEdge: Edge): IState {
    val res : MutableList<ComlexPolygon> = arrayListOf()

    polys.forEach {
      val splitted = it.final.splitSimple(splitterEdge)
     // splitted.forEach { res.add(ComlexPolygon(it, it)) }
    }

    return ComplexState(polys.map {

//      ComlexPolygon(initial = it.initial,
//          final = Polygon(it.final.vertices.map {
//            val relativePoint = it.sub(around)
//            val related = Vertex(relativePoint.y.neg(), relativePoint.x)
//            val final = related.add(around)
//            final
//          })
//      )
      it
    }.toTypedArray())
  }

  /**
   * Rotates around pihagoreand triples :)
   * Piphagorean triple is a triple with wich we can make pryamougolniy triangle
   */
  override fun rotate(around: Vertex, pihagorean: Triple<Int, Int, Int>): IState {
    return ComplexState(polys.map {
      ComlexPolygon(initial = it.initial,
          final = Polygon(it.final.vertices.map {
            it.rotate(around, pihagorean)
          })
      )
    }.toTypedArray())
  }

}
