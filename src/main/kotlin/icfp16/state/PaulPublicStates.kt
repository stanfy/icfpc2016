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
          .appendName("Line(x$folds}")
    }

    val states: Array<IState> = arrayOf(
        lineSolution(5),
        lineSolution(6),
        lineSolution(7),
        lineSolution(8),
        lineSolution(9),
        lineSolution(11),
        lineSolution(12),
        lineSolution(13),
        lineSolution(14),
        lineSolution(15),
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
    )
  }
}

