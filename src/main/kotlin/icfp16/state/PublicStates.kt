package icfp16.state

import icfp16.data.Facet
import icfp16.data.Fraction
import icfp16.data.Vertex


class PublicStates {

  companion object {
    val localState: Array<IState> = arrayOf(

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
        ).appendName("Square"),

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
        ).appendName("Task 0"),

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
        ).appendName("Task 1"),

        // task 2
        State(
            arrayOf(
                Vertex(0, 0),
                Vertex(1, 0),
                Vertex(1, 1),
                Vertex(Fraction(2, 3), Fraction(1)),
                Vertex(0, 1),
                Vertex(Fraction(0), Fraction(1, 3))
            ),
            arrayOf(
                Facet(arrayListOf(5, 3, 4)),
                Facet(arrayListOf(0, 2, 3, 5)),
                Facet(arrayListOf(0, 1, 2))
            ),
            arrayOf(
                Vertex(0, 0),
                Vertex(1, 0),
                Vertex(1, 1),
                Vertex(Fraction(1), Fraction(2, 3)),
                Vertex(Fraction(1, 3), Fraction(2, 3)),
                Vertex(Fraction(1, 3), Fraction(0))
            )
        ).appendName("Task 2"),

        // task 3
        State(
            arrayOf(
                Vertex(Fraction(0), Fraction(0)),
                Vertex(Fraction(2, 3), Fraction(0)),
                Vertex(Fraction(1), Fraction(0)),
                Vertex(Fraction(1), Fraction(1, 3)),
                Vertex(Fraction(1), Fraction(1)),
                Vertex(Fraction(1, 3), Fraction(1)),
                Vertex(Fraction(0), Fraction(1)),
                Vertex(Fraction(0), Fraction(2, 3))
            ),
            arrayOf(
                Facet(arrayListOf(0, 1, 7)),
                Facet(arrayListOf(1, 2, 6, 7)),
                Facet(arrayListOf(2, 3, 5, 6)),
                Facet(arrayListOf(3, 4, 5))
            ),
            arrayOf(
                Vertex(Fraction(2, 3), Fraction(2, 3)),
                Vertex(Fraction(2, 3), Fraction(0)),
                Vertex(Fraction(1), Fraction(0)),
                Vertex(Fraction(2, 3), Fraction(0)),
                Vertex(Fraction(2, 3), Fraction(2, 3)),
                Vertex(Fraction(0), Fraction(2, 3)),
                Vertex(Fraction(0), Fraction(1)),
                Vertex(Fraction(0), Fraction(2, 3))
            )
        ).appendName("Task 3")

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
        ).appendName("problem 8, 567, 569, 583, 773, 1094, 1558, 1606, 1610, 1705, 1778, 1779, 1780, 1781, 1782, 1783, 1784, 2421, 2424, 2425, 2426, 2427, 2531, 2670, 2780, 2802"),

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
        ).appendName("Triangle"),

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
        ).appendName("3/4 square"),

        // two diagonals from 0.1 to center; 1.0 to center
        State(
            arrayOf(
                Vertex(0, 0), // 0
                Vertex(Fraction(0), Fraction(1, 2)), // 1
                Vertex(0, 1), // 2
                Vertex(Fraction(1, 2), Fraction(1)), // 3
                Vertex(1, 1), // 4
                Vertex(Fraction(1), Fraction(1, 2)), // 5
                Vertex(1, 0), // 6
                Vertex(Fraction(1, 2), Fraction(0))  // 7
            ),
            arrayOf(
                Facet(listOf(0, 1, 3, 4, 5, 7)),
                Facet(listOf(1, 2, 3)),
                Facet(listOf(7, 5, 6))
            ),
            arrayOf(
                Vertex(0, 0), // 0
                Vertex(Fraction(0), Fraction(1, 2)), // 1
                Vertex(Fraction(1, 2), Fraction(1, 2)), // 2
                Vertex(Fraction(1, 2), Fraction(1)), // 3
                Vertex(1, 1), // 4
                Vertex(Fraction(1), Fraction(1, 2)), // 5
                Vertex(Fraction(1, 2), Fraction(1, 2)), // 6
                Vertex(Fraction(1, 2), Fraction(0))  // 7
            )
        ).appendName("Two diagonals 0,1"),


        //        // very small square
        {
          val vertexes = arrayOf(
              Vertex(Fraction(0), Fraction(0)), // 0

              Vertex(Fraction(0), Fraction(1, 4)), // 1
              Vertex(Fraction(0), Fraction(1, 2)), // 2
              Vertex(Fraction(0), Fraction(3, 4)), // 3

              Vertex(Fraction(0), Fraction(1)), // 4

              Vertex(Fraction(1, 4), Fraction(1)), // 5
              Vertex(Fraction(1, 2), Fraction(1)), // 6
              Vertex(Fraction(3, 4), Fraction(1)), // 7

              Vertex(Fraction(1), Fraction(1)), // 8

              Vertex(Fraction(1), Fraction(3, 4)), // 9
              Vertex(Fraction(1), Fraction(1, 2)), // 10
              Vertex(Fraction(1), Fraction(1, 4)), // 11

              Vertex(Fraction(1), Fraction(0)), // 12

              Vertex(Fraction(3, 4), Fraction(0)), // 13
              Vertex(Fraction(1, 2), Fraction(0)), // 14
              Vertex(Fraction(1, 4), Fraction(0)), // 15

              Vertex(Fraction(1, 4), Fraction(1, 4)), // 16
              Vertex(Fraction(1, 4), Fraction(1, 2)), // 17
              Vertex(Fraction(1, 4), Fraction(3, 4)), // 18

              Vertex(Fraction(1, 2), Fraction(3, 4)), // 19
              Vertex(Fraction(3, 4), Fraction(3, 4)), // 20

              Vertex(Fraction(3, 4), Fraction(1, 2)), // 21
              Vertex(Fraction(3, 4), Fraction(1, 4)), // 22
              Vertex(Fraction(1, 2), Fraction(1, 4)), // 23

              Vertex(Fraction(1, 2), Fraction(1, 2)) // 24
          )
          State(vertexes = vertexes,
              facets = arrayOf(
                  Facet(arrayListOf(0, 1, 16, 15)),
                  Facet(arrayListOf(1, 2, 17, 16)),
                  Facet(arrayListOf(2, 3, 18, 17)),
                  Facet(arrayListOf(3, 4, 5, 18)),

                  Facet(arrayListOf(18, 5, 6, 19)),
                  Facet(arrayListOf(19, 6, 7, 20)),
                  Facet(arrayListOf(20, 7, 8, 9)),
                  Facet(arrayListOf(21, 20, 9, 10)),

                  Facet(arrayListOf(22, 21, 10, 11)),
                  Facet(arrayListOf(13, 22, 11, 12)),
                  Facet(arrayListOf(14, 23, 22, 13)),
                  Facet(arrayListOf(15, 16, 23, 14)),

                  Facet(arrayListOf(16, 17, 24, 23)),
                  Facet(arrayListOf(17, 18, 19, 24)),
                  Facet(arrayListOf(24, 19, 20, 21)),
                  Facet(arrayListOf(23, 24, 21, 22))
              ),

              finalPositions = arrayOf(
                  vertexes[0], // 0

                  vertexes[1], // 1
                  vertexes[0], // 2
                  vertexes[1], // 3
                  vertexes[0], // 4
                  vertexes[15], // 5
                  vertexes[0], // 6
                  vertexes[15], // 7
                  vertexes[0], // 8
                  vertexes[1], // 9
                  vertexes[0], // 10
                  vertexes[1], // 11
                  vertexes[0], // 12
                  vertexes[15], // 13
                  vertexes[0], // 14
                  vertexes[15], // 15
                  vertexes[16], // 16
                  vertexes[15], // 17
                  vertexes[16], // 18

                  vertexes[1], // 19
                  vertexes[16], // 20

                  vertexes[15], // 21
                  vertexes[16], // 22
                  vertexes[1], // 23

                  vertexes[0] // 24
              )
          )
        }().appendName("Very small square")

        ,
        // kolbaska-diagonal
        State(
            arrayOf(
                Vertex(0, 0), // 0
                Vertex(Fraction(0), Fraction(1, 2)), // 1
                Vertex(0, 1), // 2
                Vertex(Fraction(1, 2), Fraction(1)), // 3
                Vertex(1, 1), // 4
                Vertex(Fraction(1), Fraction(1, 2)), // 5
                Vertex(1, 0), // 6
                Vertex(Fraction(1, 2), Fraction(0))  // 7
            ),
            arrayOf(
                Facet(listOf(0, 1, 3, 4)),
                Facet(listOf(0, 4, 5, 7)),
                Facet(listOf(1, 2, 3)),
                Facet(listOf(7, 5, 6))
            ),
            arrayOf(
                Vertex(0, 0), // 0
                Vertex(Fraction(1, 2), Fraction(0)), // 1
                Vertex(Fraction(1, 2), Fraction(1, 2)), // 2
                Vertex(Fraction(1), Fraction(1, 2)), // 3
                Vertex(1, 1), // 4
                Vertex(Fraction(1), Fraction(1, 2)), // 5
                Vertex(Fraction(1, 2), Fraction(1, 2)), // 6
                Vertex(Fraction(1, 2), Fraction(0))  // 7
            )
        ).appendName("Kolbaska diagonal"),

        // cool last figure
        State(
            vertexes = arrayOf(
                Vertex(Fraction(0), Fraction(0)), // 0
                Vertex(Fraction(0), Fraction(3, 4)), // 1
                Vertex(Fraction(0), Fraction(1)), // 2
                Vertex(Fraction(1), Fraction(1)), //3
                Vertex(Fraction(1), Fraction(1, 4)), //4
                Vertex(Fraction(1), Fraction(0)) //5
            ),
            facets = arrayOf(
                Facet(listOf(0, 1, 4, 5)),
                Facet(listOf(1, 2, 3, 4))
            ),
            finalPositions = arrayOf(
                Vertex(Fraction(0), Fraction(0)), // 0
                Vertex(Fraction(0), Fraction(3, 4)), // 1
                Vertex(Fraction(-1, 5), Fraction(3, 5)), //2
                Vertex(Fraction(2, 5), Fraction(-1, 5)), // 3
                Vertex(Fraction(1), Fraction(1, 4)), //4
                Vertex(Fraction(1), Fraction(0)) //5
            )
        ).appendName("Cool last phigure")
    )

    val states: Array<IState> = localState.plus(PaulPublicStates.states).plus(DimaPublicStates.states)
//    val states: Array<IState> = DimaPublicStates.states
  }

}