package icfp16.solver

import icfp16.api.parseProblem
import icfp16.estimate.BitmapEstimator
import icfp16.visualizer.Visualizer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SolverTest {
  @Test
  fun differentSolvers() {

    val problemString =
        """1
          |4
          |1,1
          |2,1
          |2,2
          |1,2
          |4
          |1,1 1,2
          |2,1 2,2
          |1,1 2,1
          |1,2 2,2
        """.trimMargin()

    val problem = parseProblem(problemString)
    val stupidSolve = StupidSolver().solve(problem)
    val translationSolve = TranslatorSolver().solve(problem)

    val solve1R = BitmapEstimator().resemblanceOf(problem, stupidSolve)
    val translatorR = BitmapEstimator().resemblanceOf(problem, translationSolve)

//    Visualizer().visualizedAndSaveImage(problem, stupidSolve,filePath = "./stupind.png")
//    Visualizer().visualizedAndSaveImage(problem, translationSolve,filePath = "./translator.png")
    assertThat(stupidSolve).isNotEqualTo(translationSolve)
    assertThat(translatorR).isGreaterThan(solve1R)
  }
}
