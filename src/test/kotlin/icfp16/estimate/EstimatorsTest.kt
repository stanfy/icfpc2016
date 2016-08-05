package icfp16.estimate

import icfp16.api.parseProblem
import icfp16.data.SolutionContainer
import icfp16.io.ProblemContainersParser
import icfp16.solver.BestSolverEver
import icfp16.state.State
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
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

//  @Test
  fun startSearchingBestSolutions() {

    for (problemId in 1..101) {
      println("---------------------------------")
      println("start searching best solution for problem: $problemId")
      solveAndSubmitSolutionFor(problemId.toString())

    }
  }

  fun solveAndSubmitSolutionFor(problemId: String) {
    val problemContainer = ProblemContainersParser().generateProblemContainerForProblemId(problemId)
    if (problemContainer == null) {
      return
    }

    val solver = BestSolverEver()
    val state = solver.solve(problem = problemContainer.problem)

//    val bitmapEstimator = BitmapEstimator()
//    val jstEstimator = JSTEstimator()

//    val bitmapResemblance = bitmapEstimator.resemblanceOf(task = problemContainer.problem, state = state, quality = 4)
//    val jstResemblance = jstEstimator.resemblanceOf(task = problemContainer.problem, state = state, quality = 4)

//    println("--------- task: $problemId, bitmap: $bitmapResemblance, jst: $jstResemblance ")

    val estimator = EstimatorFactory().bestEstimatorEver()
    val result = estimator.resemblanceOf(task = problemContainer.problem, state = state, quality = 4)
      println("--------- task: $problemId, esimated: $result")
  }
}