package icfp16.state

import icfp16.Facet
import icfp16.Fraction
import icfp16.Vertex
import java.math.BigInteger

data class State(val vertexes: Array<Vertex> = emptyArray(),
                 val facets: Array<Facet> = emptyArray(),
                 val finalPositions: Array<Vertex> = vertexes) {

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

  companion object {
    fun initialSquare(): State {
      return State(vertexes = arrayOf(
          Vertex(Fraction(BigInteger("0")), Fraction(BigInteger("0"))),
          Vertex(Fraction(BigInteger("0")), Fraction(BigInteger("1"))),
          Vertex(Fraction(BigInteger("1")), Fraction(BigInteger("1"))),
          Vertex(Fraction(BigInteger("1")), Fraction(BigInteger("0")))
          ),
          facets = arrayOf(
              Facet(listOf(0,1,2,3))
          ))
    }
  }

}