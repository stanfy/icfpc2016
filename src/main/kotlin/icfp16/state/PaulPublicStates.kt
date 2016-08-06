package icfp16.state

import icfp16.data.ComlexPolygon
import icfp16.data.Fraction
import icfp16.data.Vertex


class PaulPublicStates {
  companion object {
    val states: Array<IState> = arrayOf(
        ComplexState(
            arrayOf(
                ComlexPolygon(
                    initial = listOf(
                        v(f(0, 1), f(0, 1)),
                        v(f(0, 1), f(1, 1)),
                        v(f(1, 5), f(1, 1)),
                        v(f(1, 5), f(0, 1))
                    ),
                    final = listOf(
                        v(f(0, 1), f(0, 1)),
                        v(f(0, 1), f(1, 1)),
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
    )
  }
}


fun f(a: Int, b: Int): Fraction {
  return Fraction(a, b)
}

fun v(x: Fraction, y: Fraction): Vertex {
  return Vertex(x, y)
}
