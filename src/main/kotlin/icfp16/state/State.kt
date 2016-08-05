package icfp16.state

import icfp16.Facet
import icfp16.Fraction
import icfp16.Polygon
import icfp16.Vertex
import java.math.BigInteger

data class State(val vertexes: Array<Vertex> = emptyArray(),
                 val facets: Array<Facet> = emptyArray(),
                 val finalPositions: Array<Vertex> = vertexes) {

  fun poligons(): Array<Polygon> {
    return facets.map { facet ->
      Polygon(facet.indexes.map { index ->
        vertexes[index]
      })
    }.toTypedArray()
  }

  fun solution(): String {
    var result = mutableListOf<String>()
    result.add("${vertexes.size}")
    if (vertexes.size > 0 ){
      result.addAll(vertexes.map { "$it" })
    }

    result.add("${facets.size}")
    if (facets.size > 0 ){
      result.addAll(facets.map { "$it" })
    }

    if (finalPositions.size > 0 ){
      result.addAll(finalPositions.map { "$it" })
    }

    return result.joinToString("\n")
  }

  /**
   * Translates state to another vertex
   */
  fun translate(vartex: Vertex) : State {
    val finalPositionsX = this.finalPositions.map {
      val translatedX = Fraction(it.x.a.add(it.x.b.multiply(vartex.x.a).divide(vartex.x.b)), it.x.b)
      val translatedY = Fraction(it.y.a.add(it.y.b.multiply(vartex.y.a).divide(vartex.y.b)), it.y.b)
      Vertex(translatedX, translatedY)
    }.toTypedArray()
    return State(vertexes = this.vertexes, facets = this.facets, finalPositions = finalPositionsX)
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