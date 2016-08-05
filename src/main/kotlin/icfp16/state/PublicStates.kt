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
          Vertex(Fraction(0), Fraction(1, 3)),
          Vertex(Fraction(1), Fraction(1, 3))
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
      ),

      State(vertexes = arrayOf(
          Vertex(Fraction(0), Fraction(0)),
          Vertex(Fraction(1), Fraction(0)),
          Vertex(Fraction(1), Fraction(1)),
          Vertex(Fraction(0), Fraction(1)),
          Vertex(Fraction(0), Fraction(1, 2)),
          Vertex(Fraction(1, 2), Fraction(1, 2)),
          Vertex(Fraction(1, 2), Fraction(1))
      ),
          facets = arrayOf(
              Facet(listOf(0, 1, 5, 4)),
              Facet(listOf(1, 2, 6, 5)),
              Facet(listOf(3, 4, 5, 3)),
              Facet(listOf(3, 5, 6, 3))
          ),
          finalPositions = arrayOf(
              Vertex(Fraction(0), Fraction(0)),
              Vertex(Fraction(1), Fraction(0)),
              Vertex(Fraction(0), Fraction(0)),
              Vertex(Fraction(0), Fraction(0)),
              Vertex(Fraction(0), Fraction(1, 2)),
              Vertex(Fraction(1, 2), Fraction(1, 2)),
              Vertex(Fraction(0), Fraction(1, 2))
          )
      )

      /*,

      // task 2
      State(
        arrayOf(
          Vertex(0, 0),
          Vertex(0, 1),
          Vertex(Fraction(54608393, 77227930), Fraction(54608393, 77227930)),
          Vertex(1, 1),
          Vertex(1, 0)
        ),
        arrayOf(
          Facet(listOf(0, 2, 1)),
          Facet(listOf(0, 4, 2)),
          Facet(listOf(2, 4, 3)),
          Facet(listOf(2, 3, 1))
        ),
        arrayOf(
          Vertex(0, 0),
          Vertex(1, 0),
          Vertex(Fraction(54608393, 77227930), Fraction(54608393, 77227930)),
          Vertex(Fraction(22619537, 77227930), Fraction(54608393, 77227930)),
          Vertex(1, 0)
        )
      ),

      // task 3
      State(
        arrayOf(
          Vertex(0, 0),
          Vertex(0, 1),
          Vertex(Fraction(54608393, 77227930), Fraction(54608393, 77227930)),
          Vertex(1, 1),
          Vertex(1, 0),
          Vertex(Fraction(1, 2), Fraction(1, 2)),
          Vertex(Fraction(1, 2), Fraction(0)),
          Vertex(Fraction(0), Fraction(1, 2))
        ),
        arrayOf(
          Facet(listOf(0, 5, 7)),
          Facet(listOf(7, 5, 1)),
          Facet(listOf(5, 2, 1)),
          Facet(listOf(2, 3, 1)),
          Facet(listOf(4, 3, 2)),
          Facet(listOf(4, 2, 5)),
          Facet(listOf(4, 5, 6)),
          Facet(listOf(6, 5, 0))
        ),
        arrayOf(
          Vertex(1, 0),
          Vertex(1, 0),
          Vertex(Fraction(54608393, 77227930), Fraction(54608393, 77227930)),
          Vertex(Fraction(22619537, 77227930), Fraction(54608393, 77227930)),
          Vertex(1, 0),
          Vertex(Fraction(1, 2), Fraction(1, 2)),
          Vertex(Fraction(1, 2), Fraction(0)),
          Vertex(Fraction(1, 2), Fraction(0))
        )
      )
    */
    )
  }

}