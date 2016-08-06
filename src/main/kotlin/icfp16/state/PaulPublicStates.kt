package icfp16.state

import icfp16.data.ComplexPolygon
import icfp16.data.Fraction
import icfp16.data.Vertex


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
        lineSolution(32)
    )
  }
}


fun f(a: Int, b: Int): Fraction {
  return Fraction(a, b)
}

fun v(x: Fraction, y: Fraction): Vertex {
  return Vertex(x, y)
}
