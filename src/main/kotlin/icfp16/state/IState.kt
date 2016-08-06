package icfp16.state

import icfp16.data.Facet
import icfp16.data.Polygon
import icfp16.data.Vertex

interface IState {

  //  fun solution(): String
  fun translate(vartex: Vertex) : IState
  fun rotate180(around: Vertex): IState
  fun rotate90(around: Vertex): IState
  fun rotate270(around: Vertex): IState
  fun rotate(around: Vertex, pihagorean: Triple<Int,Int,Int>): IState

  fun finalPositions(): Array<Vertex>
  fun vertexes(): Array<Vertex>
  fun facets(): Array<Facet>

  fun poligons(): Array<Polygon>
  fun initialPoligons(): Array<Polygon>

}

fun IState.solution(): String {
  var result = mutableListOf<String>()
  result.add("${this.vertexes().size}")
  if (this.vertexes().size > 0 ){
    result.addAll(this.vertexes().map { "$it" })
  }

  result.add("${this.facets().size}")
  if (this.facets().size > 0 ){
    result.addAll(this.facets().map { "$it" })
  }

  if (this.finalPositions().size > 0 ){
    result.addAll(this.finalPositions().map { "$it" })
  }

  return result.joinToString("\n")
}