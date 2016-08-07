package icfp16.solver

import icfp16.api.parseProblem
import icfp16.data.Problem
import icfp16.estimate.BitmapEstimator
import icfp16.io.ProblemContainersParser
import icfp16.visualizer.Visualizer
import org.assertj.core.api.Assertions
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.concurrent.TimeUnit

class UnfoldingSolverTest {

  @Rule
  fun timeoutRule(): Timeout = Timeout(10, TimeUnit.SECONDS)

  fun problemForID(id: String): Problem {
    return ProblemContainersParser().generateProblemContainerForProblemId(id)!!.problem
  }

  fun testAgainstStupidSolver(id: String) {
    val problem = problemForID(id)

    val stupidSolve = StupidSolver().solve(problem, id)
    val unfoldingSolve = UnfoldingSolver().solve(problem, id)

    val stupidR = BitmapEstimator().resemblanceOf(problem, stupidSolve!!)
    val unfoldingR = BitmapEstimator().resemblanceOf(problem, unfoldingSolve!!)

    Visualizer().visualizedAndSaveImage(problem, unfoldingSolve, filePath = "./tmp/task_$id.png")

    Assertions.assertThat(stupidSolve).isNotEqualTo(unfoldingSolve)
    Assertions.assertThat(unfoldingR).isGreaterThan(stupidR)
  }

  @Test
  fun task12Solution() {
    val pid = "12"
    testAgainstStupidSolver(pid)
  }

//  IDs: 12, 15, 158, 14

}
