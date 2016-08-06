package icfp16.state


import icfp16.data.ComplexPolygon
import icfp16.data.Edge
import icfp16.data.Fraction
import icfp16.data.Vertex

class DimaPublicStates {
  companion object {
//    fun lineSolution(folds: Int): IState {
//      val polygons = (0..folds - 1).map { idx ->
//        ComplexPolygon(
//
//            initial = listOf(
//                v(f(idx, folds), f(0, 1)),
//                v(f(idx, folds), f(1, 1)),
//                v(f(idx + 1, folds), f(1, 1)),
//                v(f(idx + 1, folds), f(0, 1))
//            ),
//            final = listOf(
//                v(f(idx % 2, folds), f(0, 1)),
//                v(f(idx % 2, folds), f(1, 1)),
//                v(f((idx + 1) % 2, folds), f(1, 1)),
//                v(f((idx + 1) % 2, folds), f(0, 1))
//            )
//        )
//      }
//      return ComplexState(polygons.toTypedArray())
//          .appendName("Line(x$folds}")
//    }

    val states: Array<IState> = arrayOf(
        // problem 18
        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1), Fraction(1, 2)),
                Vertex(Fraction(0), Fraction(1, 2))
            ))
            .fold(Edge(
                Vertex(Fraction(1, 2), Fraction(1, 2)),
                Vertex(Fraction(0), Fraction(1, 4))
            )).apply { name = "problem_18" }
        )
  }
}