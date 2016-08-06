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
                Vertex(Fraction(1, 2), Fraction(1)),
                Vertex(Fraction(1), Fraction(1, 2)))
            ).appendName("problem_158")
        )

  }
}