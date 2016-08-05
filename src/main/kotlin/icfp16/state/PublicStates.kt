package icfp16.state

import icfp16.data.Facet
import icfp16.data.Fraction
import icfp16.data.Vertex


class PublicStates {

  companion object {
    val states: Array<State> = arrayOf(

      // square
      State(vertexes = arrayOf(
        Vertex(Fraction(0), Fraction(0)),
        Vertex(Fraction(0), Fraction(1)),
        Vertex(Fraction(1), Fraction(1)),
        Vertex(Fraction(1), Fraction(0))
      ),
        facets = arrayOf(
          Facet(listOf(0, 1, 2, 3))
        )
      ),

      // task 0
      State(
        arrayOf(
          Vertex(0, 0),
          Vertex(1, 0),
          Vertex(Fraction(0), Fraction(1, 3)),
          Vertex(Fraction(1), Fraction(1, 3)),
          Vertex(Fraction(0), Fraction(2, 3)),
          Vertex(Fraction(1), Fraction(2, 3)),
          Vertex(0, 1),
          Vertex(1, 1)
        ),
        arrayOf(
          Facet(arrayListOf(0, 1, 3, 2)),
          Facet(arrayListOf(2, 3, 5, 4)),
          Facet(arrayListOf(4, 5, 7, 6))
        ),
        arrayOf(
          Vertex(Fraction(0), Fraction(2, 3)),
          Vertex(Fraction(1), Fraction(2, 3)),
          Vertex(Fraction(0), Fraction(1, 3)),
          Vertex(Fraction(1), Fraction(1, 3)),
          Vertex(Fraction(0), Fraction(2, 3)),
          Vertex(Fraction(1), Fraction(2, 3)),
          Vertex(Fraction(0), Fraction(2, 3)),
          Vertex(Fraction(1), Fraction(2, 3))
        )
      ),

      // task 1
      State(
        arrayOf(
          Vertex(0, 0),
          Vertex(Fraction(2, 3), Fraction(0)),
          Vertex(1, 0),
          Vertex(Fraction(0), Fraction(1, 3)),
          Vertex(Fraction(1, 3), Fraction(1, 3)),
          Vertex(Fraction(1), Fraction(1, 3)),
          Vertex(Fraction(0), Fraction(2, 3)),
          Vertex(Fraction(2, 3), Fraction(2, 3)),
          Vertex(Fraction(1), Fraction(2, 3)),
          Vertex(0, 1),
          Vertex(Fraction(1, 3), Fraction(1)),
          Vertex(1, 1)
        ),
        arrayOf(
          Facet(arrayListOf(0, 1, 4, 3)),
          Facet(arrayListOf(1, 2, 5, 4)),
          Facet(arrayListOf(3, 4, 7, 6)),
          Facet(arrayListOf(4, 5, 8, 7)),
          Facet(arrayListOf(6, 7, 10, 9)),
          Facet(arrayListOf(7, 8, 11, 10))
        ),
        arrayOf(
          Vertex(Fraction(0), Fraction(2, 3)),
          Vertex(Fraction(2, 3), Fraction(2, 3)),
          Vertex(Fraction(2, 3), Fraction(1)),
          Vertex(Fraction(0), Fraction(1, 3)),
          Vertex(Fraction(1, 3), Fraction(1, 3)),
          Vertex(Fraction(1, 3), Fraction(1)),
          Vertex(Fraction(0), Fraction(2, 3)),
          Vertex(Fraction(2, 3), Fraction(2, 3)),
          Vertex(Fraction(2, 3), Fraction(1)),
          Vertex(Fraction(0), Fraction(1, 3)),
          Vertex(Fraction(1, 3), Fraction(1, 3)),
          Vertex(Fraction(1, 3), Fraction(1))
        )
      )
    )
  }

}