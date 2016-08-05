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
      ),

        // это свёрнутый в 4 части квадрат. можно было проще, но я додумался поздно :(
      State(vertexes = arrayOf(
          Vertex(Fraction(0), Fraction(0)), // a
          Vertex(Fraction(1, 4), Fraction(0)), // b
          Vertex(Fraction(3, 4), Fraction(0)), // c
          Vertex(Fraction(1), Fraction(0)), // d
          Vertex(Fraction(1), Fraction(1, 4)), // e
          Vertex(Fraction(1), Fraction(3, 4)), // f
          Vertex(Fraction(1), Fraction(1)), // g
          Vertex(Fraction(3, 4), Fraction(1)), // j
          Vertex(Fraction(1, 4), Fraction(1)), // h
          Vertex(Fraction(0), Fraction(1)), // m
          Vertex(Fraction(0), Fraction(3, 4)), // n
          Vertex(Fraction(0), Fraction(1, 4)), // p
          Vertex(Fraction(1, 4), Fraction(1, 4)), // k
          Vertex(Fraction(3, 4), Fraction(1, 4)), // o
          Vertex(Fraction(3, 4), Fraction(3, 4)), // s
          Vertex(Fraction(1, 4), Fraction(3, 4)) // z
      ),
          facets = arrayOf(
              Facet(listOf(0, 1, 12, 11)),
              Facet(listOf(1, 2, 13, 12)),
              Facet(listOf(2, 3, 4, 13)),
              Facet(listOf(13, 4, 5, 14)),
              Facet(listOf(13, 4, 5, 14)),
              Facet(listOf(14, 5, 6, 7)),
              Facet(listOf(15, 14, 7, 8)),
              Facet(listOf(10, 15, 8, 9)),
              Facet(listOf(11, 12, 15, 10)),
              Facet(listOf(12, 13, 14, 15))
          ),
          finalPositions = arrayOf(
              Vertex(Fraction(1, 2), Fraction(1, 2)), // a //
              Vertex(Fraction(1, 4), Fraction(1, 2)), // b //
              Vertex(Fraction(3, 4), Fraction(1, 2)), // c //
              Vertex(Fraction(1, 2), Fraction(1, 2)), // d //
              Vertex(Fraction(1, 2), Fraction(1, 4)), // e  //
              Vertex(Fraction(1, 2), Fraction(3, 4)), // f //
              Vertex(Fraction(1, 2), Fraction(1, 2)), // g //
              Vertex(Fraction(3, 4), Fraction(1, 2)), // j //
              Vertex(Fraction(1, 4), Fraction(1, 2)), // h //
              Vertex(Fraction(1, 2), Fraction(1, 2)), // m //
              Vertex(Fraction(1, 2), Fraction(3, 4)), // n //
              Vertex(Fraction(1, 2), Fraction(1, 4)), // p //
              Vertex(Fraction(1, 4), Fraction(1, 4)), // k //
              Vertex(Fraction(3, 4), Fraction(1, 4)), // o //
              Vertex(Fraction(3, 4), Fraction(3, 4)), // s //
              Vertex(Fraction(1, 4), Fraction(3, 4)) // z //
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