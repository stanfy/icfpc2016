package icfp16.state

import icfp16.data.Facet
import icfp16.data.Fraction
import icfp16.data.Vertex

class NastyaPublicStates {

    companion object {
      val states: Array<IState> = arrayOf(

        // triangle small
        State(
          arrayOf(
            Vertex(0, 0), // 0
            Vertex(0, 1), // 1
            Vertex(1, 1), // 2
            Vertex(1, 0), // 3
            Vertex(Fraction(1, 2), Fraction(1, 2)) // 4
          ),
          arrayOf(
            Facet(listOf(0, 4, 1)),
            Facet(listOf(1, 4, 2)),
            Facet(listOf(2, 4, 3)),
            Facet(listOf(3, 4, 0))
          ),
          arrayOf(
            Vertex(0, 0), // 0
            Vertex(1, 0), // 1
            Vertex(0, 0), // 2
            Vertex(1, 0), // 3
            Vertex(Fraction(1, 2), Fraction(1, 2)) // 4
          )
        ).setName("Triangle Twice"),

        // triangle 1/4 of square
        State(
          arrayOf(
            Vertex(0, 0), // 0
            Vertex(0, 1), // 1
            Vertex(1, 1), // 2
            Vertex(1, 0), // 3
            Vertex(Fraction(1, 2), Fraction(1, 2)), // 4 center
            Vertex(Fraction(0), Fraction(1, 2)), // 5
            Vertex(Fraction(1, 2), Fraction(1)), // 6
            Vertex(Fraction(1), Fraction(1, 2)), // 7
            Vertex(Fraction(1, 2), Fraction(0)) // 8
          ),
          arrayOf(
            Facet(listOf(0, 4, 5)),
            Facet(listOf(5, 4, 1)),
            Facet(listOf(1, 4, 6)),
            Facet(listOf(6, 4, 2)),
            Facet(listOf(2, 4, 7)),
            Facet(listOf(7, 4, 3)),
            Facet(listOf(3, 4, 8)),
            Facet(listOf(8, 4, 0))
          ),
          arrayOf(
            Vertex(0, 0), // 0
            Vertex(0, 0), // 1
            Vertex(0, 0), // 2
            Vertex(0, 0), // 3
            Vertex(Fraction(1, 2), Fraction(1, 2)), // 4 center
            Vertex(Fraction(1, 2), Fraction(0)), // 5
            Vertex(Fraction(1, 2), Fraction(0)), // 6
            Vertex(Fraction(1, 2), Fraction(0)), // 7
            Vertex(Fraction(1, 2), Fraction(0)) // 8
          )
        ).setName("Triangle Three times")

      )

    }
}