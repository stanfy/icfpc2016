package icfp16.solver

import icfp16.api.parseProblem
import icfp16.data.Edge
import icfp16.data.Fraction
import icfp16.data.Vertex
import icfp16.estimate.BitmapEstimator
import icfp16.state.State
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class WrapperTest {

  @Test
  fun wrappingEdges() {
    val problemString =
        """1
          |4
          |0,0
          |1,0
          |1/2,1/2
          |0,1/2
          |5
          |0,0 1,0
          |1,0 1/2,1/2
          |1/2,1/2 0,1/2
          |0,1/2 0,0
          |0,0 1/2,1/2
        """.trimMargin()

    val problem = parseProblem(problemString)

    val res = wrappingEdges(State.initialSquare().poligons()[0], problem.poligons[0])
    assertThat(res).containsExactly(
        Edge(Vertex(1, 0), Vertex(Fraction(1, 2), Fraction(1, 2))),
        Edge(Vertex(Fraction(1, 2), Fraction(1, 2)), Vertex(Fraction(0), Fraction(1, 2)))
    )
  }

  @Test
  fun wrapConvex() {
    val problemString =
        """1
          |4
          |0,0
          |1,0
          |1/2,1/2
          |0,1/2
          |5
          |0,0 1,0
          |1,0 1/2,1/2
          |1/2,1/2 0,1/2
          |0,1/2 0,0
          |0,0 1/2,1/2
        """.trimMargin()

    val problem = parseProblem(problemString)

    val state = Wrapper().solveWithWrapping(problem, State.initialSquare())
    val anotherState = BetterTranslatorSolver().solve(problem, "")

    val estimator = BitmapEstimator()
    assertThat(estimator.resemblanceOf(problem, state, 4))
        .isGreaterThan(estimator.resemblanceOf(problem, anotherState, 4))
  }

}
