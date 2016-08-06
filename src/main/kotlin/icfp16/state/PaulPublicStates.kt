package icfp16.state

import icfp16.data.ComlexPolygon
import icfp16.data.Fraction
import icfp16.data.Vertex


class PaulPublicStates {
  companion object {
    fun lineSolution(folds: Int): IState {
      val polygons = (0..folds - 1).map { idx ->
        ComlexPolygon(

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


    val lineSolution5 =
        ComplexState(
            arrayOf(
                ComlexPolygon(
                    initial = listOf(
                        v(f(0, 5), f(0, 1)),
                        v(f(0, 5), f(1, 1)),
                        v(f(1, 5), f(1, 1)),
                        v(f(1, 5), f(0, 1))
                    ),
                    final = listOf(
                        v(f(0, 5), f(0, 1)),
                        v(f(0, 5), f(1, 1)),
                        v(f(1, 5), f(1, 1)),
                        v(f(1, 5), f(0, 1))
                    )
                ),

                ComlexPolygon(
                    initial = listOf(
                        v(f(1, 5), f(0, 1)),
                        v(f(1, 5), f(1, 1)),
                        v(f(2, 5), f(1, 1)),
                        v(f(2, 5), f(0, 1))
                    ),
                    final = listOf(
                        v(f(1, 5), f(0, 1)),
                        v(f(1, 5), f(1, 1)),
                        v(f(0, 5), f(1, 1)),
                        v(f(0, 5), f(0, 1))
                    )
                ),

                ComlexPolygon(
                    initial = listOf(
                        v(f(2, 5), f(0, 1)),
                        v(f(2, 5), f(1, 1)),
                        v(f(3, 5), f(1, 1)),
                        v(f(3, 5), f(0, 1))
                    ),
                    final = listOf(
                        v(f(0, 5), f(0, 1)),
                        v(f(0, 5), f(1, 1)),
                        v(f(1, 5), f(1, 1)),
                        v(f(1, 5), f(0, 1))
                    )
                ),

                ComlexPolygon(
                    initial = listOf(
                        v(f(3, 5), f(0, 1)),
                        v(f(3, 5), f(1, 1)),
                        v(f(4, 5), f(1, 1)),
                        v(f(4, 5), f(0, 1))
                    ),
                    final = listOf(
                        v(f(1, 5), f(0, 1)),
                        v(f(1, 5), f(1, 1)),
                        v(f(0, 5), f(1, 1)),
                        v(f(0, 5), f(0, 1))
                    )
                ),
                ComlexPolygon(
                    initial = listOf(
                        v(f(4, 5), f(0, 1)),
                        v(f(4, 5), f(1, 1)),
                        v(f(5, 5), f(1, 1)),
                        v(f(5, 5), f(0, 1))
                    ),
                    final = listOf(
                        v(f(0, 5), f(0, 1)),
                        v(f(0, 5), f(1, 1)),
                        v(f(1, 5), f(1, 1)),
                        v(f(1, 5), f(0, 1))
                    )
                )
            )
        )

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
        lineSolution(16)
    )
  }
}


fun f(a: Int, b: Int): Fraction {
  return Fraction(a, b)
}

fun v(x: Fraction, y: Fraction): Vertex {
  return Vertex(x, y)
}
