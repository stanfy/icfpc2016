package icfp16.state


import icfp16.data.ComplexPolygon
import icfp16.data.Edge
import icfp16.data.Fraction
import icfp16.data.Vertex

class DimaPublicStates {
  companion object {
    val states: Array<IState> = arrayOf(
        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1), Fraction(1, 2)),
                Vertex(Fraction(0), Fraction(1, 2))
            ))
            .fold(Edge(
                Vertex(Fraction(1), Fraction(3, 4)),
                Vertex(Fraction(1, 2), Fraction(1, 2))
            )).appendName("problem_18"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(2, 3), Fraction(1)),
                Vertex(Fraction(1), Fraction(2, 3)))
            ).fold(Edge(
            Vertex(Fraction(0), Fraction(2, 3)),
            Vertex(Fraction(1, 3), Fraction(1)))
        ).appendName("problem_38, 994"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(0), Fraction(1, 2)),
                Vertex(Fraction(1, 2), Fraction(1)))
            ).fold(Edge(
            Vertex(Fraction(1, 2), Fraction(1)),
            Vertex(Fraction(1), Fraction(1, 2)))
        ).appendName("problem_39, 975 (0.9255216693418941)"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1, 2), Fraction(1)),
                Vertex(Fraction(1), Fraction(1, 2)))
            ).appendName("problem_158 (1.0)"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(0), Fraction(1, 2)),
                Vertex(Fraction(1, 2), Fraction(1))
            ))
            .appendName("problem_1075 (1.0)"),
        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1), Fraction(1)),
                Vertex(Fraction(0), Fraction(0))
            ))
            .appendName("problem_1759, 1776 (1.0)"), // !!!!!!!!!!
        ComplexState()
            .fold(Edge(
                Vertex(Fraction(3, 4), Fraction(0)),
                Vertex(Fraction(0), Fraction(3, 4))
            ))
            .appendName("problem_1848 (1.0)"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1, 8), Fraction(0)),
                Vertex(Fraction(0), Fraction(1, 8))
            ))
            .appendName("problem_2084 (1.0)"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1, 42), Fraction(0)),
                Vertex(Fraction(0), Fraction(1, 42))
            )).fold(Edge(
                Vertex(Fraction(41, 42), Fraction(1)),
                Vertex(Fraction(1), Fraction(41, 42))
            ))
            .appendName("problem_2235 (1.0)"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1, 3), Fraction(1)),
                Vertex(Fraction(1), Fraction(1, 3))
            ))
            .appendName("problem_2247 (1.0)"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(0), Fraction(1, 2)),
                Vertex(Fraction(1), Fraction(1, 2))
            ))
            .appendName("problem_3020, 3043, 779 (1.0)"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1), Fraction(1, 2)),
                Vertex(Fraction(1, 2), Fraction(0))
            )).fold(Edge(
              Vertex(Fraction(1, 2), Fraction(1)),
              Vertex(Fraction(1), Fraction(1, 2))
            )).fold(Edge(
                Vertex(Fraction(1), Fraction(1, 2)),
                Vertex(Fraction(0), Fraction(1, 2))
            ))
            .appendName("problem_1567, 175, 776, 845, 1068, 1071, 1074, 1076, 1085, 1118, 1132, 1231, 1371, 1372, 1373, 1374, 1375, 1377, 1383, 1384, 1385, 1386, 1387, 1389, 1391 (1.0)")
    )

  }
}