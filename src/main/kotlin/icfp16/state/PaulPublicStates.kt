package icfp16.state

import icfp16.data.*

class PaulPublicStates {
  companion object {
    fun lineSolution(folds: Int): IState {
      val polygons = (0..folds - 1).map { idx ->
        ComplexPolygon(

            initial = listOf(
                v(f(idx, folds), f(0, 1)),
                v(f(idx, folds), f(1, 1)),
                v(f(idx + 1, folds), f(1, 1)),
                v(f(idx + 1, folds), f(0, 1))
            ),
            final = listOf(
                v(f(idx % 2, folds), f(0, 1)),
                v(f(idx % 2, folds), f(1, 1)),
                v(f((idx + 1) % 2, folds), f(1, 1)),
                v(f((idx + 1) % 2, folds), f(0, 1))
            )
        )
      }
      return ComplexState(polygons.toTypedArray())
          .setName("Line(x$folds}")
    }

    val states: Array<IState> = arrayOf(
        lineSolution(2),
        lineSolution(3),
        lineSolution(4),
        lineSolution(5),
        lineSolution(8),
        lineSolution(16),
        lineSolution(32),
        ComplexState()
            .fold(Edge(
                Vertex(Fraction(0), Fraction(1, 3)),
                Vertex(Fraction(1), Fraction(2, 3))
            ))
            .fold(Edge(
                Vertex(Fraction(0), Fraction(1, 4)),
                Vertex(Fraction(1), Fraction(2, 4))
            ))
            .fold(Edge(
                Vertex(Fraction(0), Fraction(1, 5)),
                Vertex(Fraction(1), Fraction(7, 10))
            ))
            .fold(Edge(
                Vertex(Fraction(1,4), Fraction(0)),
                Vertex(Fraction(1,5), Fraction(1))
            ))
            .setName("Pyaka"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1,2), Fraction(1, 1)),
                Vertex(Fraction(1,2), Fraction(0, 1))
            ))
            .fold(Edge(
                Vertex(Fraction(1,4), Fraction(1, 1)),
                Vertex(Fraction(1,4), Fraction(0, 1))
            ))
            .fold(Edge(
                Vertex(Fraction(1,8), Fraction(1, 1)),
                Vertex(Fraction(1,8), Fraction(0, 1))
            ))

            .fold(Edge(
                Vertex(Fraction(1,8), Fraction(2, 8)),
                Vertex(Fraction(0,8), Fraction(1, 8))
            ))

            .fold(Edge(
                Vertex(Fraction(0,8), Fraction(4, 8)),
                Vertex(Fraction(1,8), Fraction(3, 8))
            ))
            .fold(Edge(
                Vertex(Fraction(0,8), Fraction(5, 8)),
                Vertex(Fraction(1,8), Fraction(6, 8))
            ))
            .setName("Okolobubl"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1, 2), Fraction(1, 1)),
                Vertex(Fraction(1, 2), Fraction(0, 1))
            ))
            .fold(Edge(
                Vertex(Fraction(1, 4), Fraction(1, 1)),
                Vertex(Fraction(1, 4), Fraction(0, 1))
            ))
            .fold(Edge(
                Vertex(Fraction(1, 8), Fraction(1, 1)),
                Vertex(Fraction(1, 8), Fraction(0, 1))
            ))
            .fold(Edge(
                Vertex(Fraction(1, 8), Fraction(2, 8)),
                Vertex(Fraction(0, 8), Fraction(1, 8))
            ))
            .fold(Edge(
                Vertex(Fraction(0, 8), Fraction(4, 8)),
                Vertex(Fraction(1, 8), Fraction(3, 8))
            ))
            .fold(Edge(
                Vertex(Fraction(-1, 4), Fraction(3, 8)),
                Vertex(Fraction(-1, 8), Fraction(1, 2))
            ))
            .fold(Edge(
                Vertex(Fraction(-1, 4), Fraction(1, 4)),
                Vertex(Fraction(-1, 8), Fraction(1, 8))
            ))
            .setName("Bubloid"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(3, 4), Fraction(1, 1)),
                Vertex(Fraction(0, 4), Fraction(1, 4))
            ))
            .setName("Trikutz"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(3, 4), Fraction(1, 1)),
                Vertex(Fraction(0, 4), Fraction(1, 4))
            ))
            .fold(Edge(
                Vertex(Fraction(3, 4), Fraction(1, 1)),
                Vertex(Fraction(0, 4), Fraction(1, 4))
            ))
            .setName("TrikutzHalf4"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1, 1), Fraction(1, 4)),
                Vertex(Fraction(3, 4), Fraction(0, 4))
            ))
            .fold(Edge(
                Vertex(Fraction(3, 4), Fraction(1, 1)),
                Vertex(Fraction(1, 1), Fraction(3, 4))
            ))
            .fold(Edge(
                Vertex(Fraction(1, 4), Fraction(0, 1)),
                Vertex(Fraction(0, 1), Fraction(1, 4))
            ))
            .fold(Edge(
                Vertex(Fraction(0, 1), Fraction(3, 4)),
                Vertex(Fraction(1, 4), Fraction(1, 1))
            ))
            .setName("Quadruz"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1, 2), Fraction(1, 1)),
                Vertex(Fraction(1, 2), Fraction(0, 4))
            ))
            .fold(Edge(
                Vertex(Fraction(0, 2), Fraction(1, 2)),
                Vertex(Fraction(1, 2), Fraction(1, 2))
            ))

            .fold(Edge(
                Vertex(Fraction(1, 2), Fraction(1, 8)),
                Vertex(Fraction(3, 8), Fraction(0, 8))
            ))
            .fold(Edge(
                Vertex(Fraction(3, 8), Fraction(1, 2)),
                Vertex(Fraction(1, 2), Fraction(3, 8))
            ))
            .fold(Edge(
                Vertex(Fraction(1, 8), Fraction(0, 2)),
                Vertex(Fraction(0, 2), Fraction(1, 8))
            ))
            .fold(Edge(
                Vertex(Fraction(0, 2), Fraction(3, 8)),
                Vertex(Fraction(1, 8), Fraction(1, 2))
            ))
         .setName("Small Quadruz")
    )
  }
}

