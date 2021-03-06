package icfp16.solver

import icfp16.api.parseProblem
import icfp16.data.Edge
import icfp16.data.Fraction
import icfp16.data.Problem
import icfp16.data.Vertex
import icfp16.estimate.BitmapEstimator
import icfp16.io.ProblemContainersParser
import icfp16.state.State
import icfp16.state.solution
import icfp16.visualizer.Visualizer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test

class WrapperTest {

  val images = true

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

  private fun assertExactWrap(problem: Problem, prefix: String ="") {
    val state = Wrapper(true, prefix).solve(problem, "any")

    println(state!!.solution())
    if (images) {
      Visualizer().visualizedAndSaveImage(problem, state, 1, "test.png")
    }

    val estimator = BitmapEstimator()
    assertThat(estimator.resemblanceOf(problem, state, 4)).isEqualTo(1.0)
  }

  private fun assertWrap(problem: Problem, prefix: String ="") {
    val state = Wrapper(true, prefix).solve(problem, "any")

    println(state!!.solution())
    if (images) {
      Visualizer().visualizedAndSaveImage(problem, state, 1, "test.png")
    }

    val estimator = BitmapEstimator()
    assertThat(estimator.resemblanceOf(problem, state, 4)).isGreaterThan(0.3)
  }

  @Test
  fun wrapSimpleConvex() {
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

    assertExactWrap(parseProblem(problemString))
  }

  @Test
  fun wrapSquare() {
    val problemString =
        """1
          |4
          |0,0
          |1,0
          |1,1
          |0,1
          |4
          |0,0 1,0
          |0,0 0,1
          |1,0 1,1
          |0,1 1,1
        """.trimMargin()

    assertExactWrap(parseProblem(problemString))
  }

  @Test
  fun wrapSmallSquare() {
    val problemString =
        """1
          |4
          |1/4,3/4
          |3/4,3/4
          |3/4,1/4
          |1/4,1/4
          |1
          |0,0 1,0
        """.trimMargin()

    assertExactWrap(parseProblem(problemString))
  }

  @Test
  fun wrap12() {
    val problem = ProblemContainersParser().generateProblemContainerForProblemId("12")!!.problem
    assertExactWrap(problem, "12")
  }

  @Ignore
  @Test
  fun wrap101() {
    val problem = ProblemContainersParser().generateProblemContainerForProblemId("101")!!.problem
    assertExactWrap(problem, "101")
  }

  @Test
  fun wrap3585() {
    val problem = ProblemContainersParser().generateProblemContainerForProblemId("3585")!!.problem
    assertWrap(problem, "3585")
  }

  @Test
  fun wrap5796() {
    val problem = ProblemContainersParser().generateProblemContainerForProblemId("5796")!!.problem
    assertWrap(problem, "5796")
  }

}
