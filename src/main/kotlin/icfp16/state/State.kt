package icfp16.state

import icfp16.data.*
import java.math.BigInteger

data class State(val vertexes: Array<Vertex> = emptyArray(),
                 val facets: Array<Facet> = emptyArray(),
                 val finalPositions: Array<Vertex> = vertexes) {

  fun poligons(): Array<Polygon> {
    return facets.map { facet ->
      Polygon(facet.indexes.map { index ->
        finalPositions[index]
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
    val translatedPositions = this.finalPositions.map {
      it.add(vartex)
    }.toTypedArray()
    return State(vertexes = this.vertexes, facets = this.facets, finalPositions = translatedPositions)
  }


  /**
   *  Rotates around vertex
   */
  fun rotate(around: Vertex, angle: Double): State {
    val translatedPositions = this.finalPositions.map {

      val center = around
      val point2 = it
      val x =
          center.x
          .add(point2.x.sub(center.x).mul(Math.cos(angle)))
          .sub(point2.y.sub(center.y).mul(Math.sin(angle)))

      val y =
          center.y
              .add(point2.x.sub(center.x).mul(Math.sin(angle)))
              .add(point2.y.sub(center.y).mul(Math.cos(angle)))

    Vertex(x,y)
    }.toTypedArray()
    return State(vertexes = this.vertexes, facets = this.facets, finalPositions = translatedPositions)
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