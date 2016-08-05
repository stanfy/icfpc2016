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
  fun rotate90(around: Vertex): State {
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
  fun rotate180(around: Vertex): State {
    return rotate90(around).rotate90(around)
  }


  /**
   *  Rotates around vertex
   */
  fun rotate270(around: Vertex): State {
    return rotate90(around).rotate90(around).rotate90(around)
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