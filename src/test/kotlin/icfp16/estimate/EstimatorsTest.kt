package icfp16.estimate

import icfp16.api.parseProblem
import icfp16.data.SolutionContainer
import icfp16.io.ProblemContainersParser
import icfp16.solver.BestSolverEver
import icfp16.state.State
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.Ignore
import org.junit.Test

class EstimatorsTest {
  @Test
  fun solutionVertexes() {
    val problemString =
        """1
          |4
          |0,3/4
          |1/8,7/8
          |0,1
          |-1/8,7/8
          |5
          |0,3/4 0,1
          |0,3/4 1/8,7/8
          |0,3/4 -1/8,7/8
          |-1/8,7/8 0,1
          |1/8,7/8 0,1
        """.trimMargin()

    val problem = parseProblem(problemString)

    val estimator = BitmapEstimator()
    assertThat(estimator.resemblanceOf(problem, State.initialSquare(), quality = 4)).isCloseTo(0.015384, Offset.offset(0.00001))
  }

  @Test
  @Ignore
  fun compoundEstimatorShouldBeMorePreciseThatBitmap() {
    val map = mapOf(
        1 to 1.0,
        10 to 0.510204,
        100 to 0.328125,
        101 to 0.121076,
        11 to 0.777777,
        13 to 0.619631,
        14 to 0.501614,
        15 to 0.511203,
        16 to 0.512162,
        17 to 0.422085,
        18 to 0.427777,
        19 to 0.32653,
        2 to 1.0
      )

    for ((problemId, expectedResemblance) in map) {
      solveAndSubmitSolutionFor(problemId.toString(), expectedResemblance)
    }
  }

  fun solveAndSubmitSolutionFor(problemId: String, expectedResemblance: Double) {
    val problemContainer = ProblemContainersParser().generateProblemContainerForProblemId(problemId)
    if (problemContainer == null) {
      return
    }

    val solver = BestSolverEver()
    val state = solver.solve(problem = problemContainer.problem)
    
    val compoundEstimator = EstimatorFactory().bestEstimatorEver()

    val compoundResemblance = compoundEstimator.resemblanceOf(task = problemContainer.problem, state = state, quality = 4)

    assertThat(compoundResemblance).isCloseTo(expectedResemblance, Offset.offset(0.000001))
  }
}