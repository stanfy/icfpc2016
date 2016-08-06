package icfp16.state

import icfp16.data.*

data class State(val vertexes: Array<Vertex> = emptyArray(),
                 val facets: Array<Facet> = emptyArray(),
                 val finalPositions: Array<Vertex> = vertexes):IState {

  override fun finalPositions(): Array<Vertex> {
    return finalPositions
  }

  override fun vertexes(): Array<Vertex> {
    return vertexes
  }

  override fun facets(): Array<Facet> {
    return facets
  }

  override fun poligons(): Array<Polygon> {
    return facets.map { facet ->
      Polygon(facet.indexes.map { index ->
        finalPositions[index]
      })
    }.toTypedArray()
  }

  override fun initialPoligons(): Array<Polygon> {
    return facets.map { facet ->
      Polygon(facet.indexes.map { index ->
        vertexes[index]
      })
    }.toTypedArray()
  }


  /**
   * Translates state to another vertex
   */
  override fun translate(vartex: Vertex) : IState {
    val translatedPositions = this.finalPositions.map {
      it.add(vartex)
    }.toTypedArray()
    return State(vertexes = this.vertexes, facets = this.facets, finalPositions = translatedPositions)
  }

  /**
   *  Rotates around vertex
   */
  override fun rotate90(around: Vertex): IState {
    val translatedPositions = this.finalPositions.map {
      val relativePoint = it.sub(around)
      val related = Vertex(relativePoint.y.neg(), relativePoint.x)
      val final = related.add(around)
      final
    }.toTypedArray()
    return State(vertexes = this.vertexes, facets = this.facets, finalPositions = translatedPositions)
  }

  /**
   *  Rotates around vertex
   */
  override fun rotate180(around: Vertex): IState {
    return rotate90(around).rotate90(around)
  }

  /**
   *  Rotates around vertex
   */
  override fun rotate270(around: Vertex): IState {
    return rotate90(around).rotate90(around).rotate90(around)
  }


  override fun rotate(around: Vertex, pihagorean: Triple<Int,Int,Int>): State {
    val translatedPositions = this.finalPositions.map {
      it.rotate(around, pihagorean)
    }.toTypedArray()
    return State(vertexes = this.vertexes, facets = this.facets, finalPositions = translatedPositions)
  }

  override fun split(splitterEdge: Edge): IState {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  companion object {
    fun initialSquare(): State {
      return State(vertexes = arrayOf(
          Vertex(Fraction(0), Fraction(0)),
          Vertex(Fraction(0), Fraction(1)),
          Vertex(Fraction(1), Fraction(1)),
          Vertex(Fraction(1), Fraction(0))
          ),
          facets = arrayOf(
              Facet(listOf(0,1,2,3))
          ))
    }
  }
}