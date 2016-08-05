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

      // square folded in 4 parts
      ,
      State(vertexes = arrayOf(
        Vertex(Fraction(0), Fraction(0)),
        Vertex(Fraction(0), Fraction(1, 2)),
        Vertex(Fraction(0), Fraction(1)),
        Vertex(Fraction(1, 2), Fraction(1)),
        Vertex(Fraction(1), Fraction(1)),
        Vertex(Fraction(1), Fraction(1, 2)),
        Vertex(Fraction(1), Fraction(0)),
        Vertex(Fraction(1, 2), Fraction(0)),
        Vertex(Fraction(1, 2), Fraction(1, 2))
      ),
        facets = arrayOf(
          Facet(arrayListOf(0, 1, 8, 7)),
          Facet(arrayListOf(1, 2, 3, 8)),
          Facet(arrayListOf(8, 3, 4, 5)),
          Facet(arrayListOf(7, 8, 5, 6))
        ),

        finalPositions = arrayOf(
          Vertex(Fraction(0), Fraction(0)), //
          Vertex(Fraction(0), Fraction(1, 2)), //
          Vertex(Fraction(0), Fraction(0)), //
          Vertex(Fraction(1, 2), Fraction(0)), //
          Vertex(Fraction(0), Fraction(0)), //
          Vertex(Fraction(0), Fraction(1, 2)),
          Vertex(Fraction(0), Fraction(0)),
          Vertex(Fraction(1, 2), Fraction(0)),
          Vertex(Fraction(1, 2), Fraction(1, 2)) //
        )
      ),

      // triangle
      State(
        arrayOf(
          Vertex(0, 0),
          Vertex(0, 1),
          Vertex(1, 1),
          Vertex(1, 0)
        ),
        arrayOf(
          Facet(listOf(0, 1, 2)),
          Facet(listOf(0, 2, 3))
        ),
        arrayOf(
          Vertex(0, 0),
          Vertex(1, 0),
          Vertex(1, 1),
          Vertex(1, 0)
        )
      ),

        // 3/4 of square

      State(vertexes = arrayOf(
          Vertex(Fraction(0), Fraction(0)),
          Vertex(Fraction(0), Fraction(3, 4)),
          Vertex(Fraction(0), Fraction(1)),
          Vertex(Fraction(3, 4), Fraction(1)),
          Vertex(Fraction(1), Fraction(1)),
          Vertex(Fraction(1), Fraction(3, 4)),
          Vertex(Fraction(1), Fraction(0)),
          Vertex(Fraction(3, 4), Fraction(0)),
          Vertex(Fraction(3, 4), Fraction(3, 4))
      ),
          facets = arrayOf(
              Facet(arrayListOf(0, 1, 8, 7)),
              Facet(arrayListOf(1, 2, 3, 8)),
              Facet(arrayListOf(8, 3, 4, 5)),
              Facet(arrayListOf(7, 8, 5, 6))
          ),

          finalPositions = arrayOf(
              Vertex(Fraction(0), Fraction(0)), //
              Vertex(Fraction(0), Fraction(3, 4)), //
              Vertex(Fraction(0), Fraction(1, 2)), //
              Vertex(Fraction(3, 4), Fraction(1, 2)), //
              Vertex(Fraction(1, 2), Fraction(1, 2)), //
              Vertex(Fraction(1, 2), Fraction(3, 4)), //
              Vertex(Fraction(1, 2), Fraction(0)), //
              Vertex(Fraction(3, 4), Fraction(0)), //
            Vertex(Fraction(3, 4), Fraction(3, 4)) //
          )
      ),


      // two diagonals from 0.1 to center; 1.0 to center
      State(
        arrayOf(
          Vertex(0, 0),   // 0
          Vertex(Fraction(0), Fraction(1, 2)), // 1
          Vertex(0, 1),   // 2
          Vertex(Fraction(1, 2), Fraction(1)), // 3
          Vertex(1, 1),   // 4
          Vertex(Fraction(1), Fraction(1, 2)), // 5
          Vertex(1, 0),   // 6
          Vertex(Fraction(1, 2), Fraction(0))  // 7
        ),
        arrayOf(
          Facet(listOf(0, 1, 3, 4, 5, 7)),
          Facet(listOf(1, 2, 3)),
          Facet(listOf(7, 5, 6))
        ),
        arrayOf(
          Vertex(0, 0),   // 0
          Vertex(Fraction(0), Fraction(1, 2)), // 1
          Vertex(Fraction(1, 2), Fraction(1, 2)),   // 2
          Vertex(Fraction(1, 2), Fraction(1)), // 3
          Vertex(1, 1),   // 4
          Vertex(Fraction(1), Fraction(1, 2)), // 5
          Vertex(Fraction(1, 2), Fraction(1, 2)),   // 6
          Vertex(Fraction(1, 2), Fraction(0))  // 7
        )
      )

    )
  }

}