package icfp16.solver

import icfp16.api.parseProblem
import icfp16.data.centroid
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

  @Test
  fun task19Solution() {

    val problemString =
        """1
          |12
          |4/7,3/7
          |5/7,3/7
          |13/14,4/7
          |8/7,4/7
          |8/7,5/7
          |1,13/14
          |1,8/7
          |6/7,8/7
          |9/14,1
          |3/7,1
          |3/7,6/7
          |4/7,9/14
          |16
          |5/7,3/7 8/7,5/7
          |3/7,6/7 3/7,1
          |4/7,3/7 4/7,1
          |5/7,3/7 5/7,1
          |6/7,4/7 6/7,8/7
          |1,4/7 1,8/7
          |8/7,4/7 8/7,5/7
          |3/7,6/7 6/7,8/7
          |4/7,3/7 5/7,3/7
          |5/7,3/7 3/7,6/7
          |4/7,4/7 8/7,4/7
          |4/7,5/7 8/7,5/7
          |8/7,5/7 6/7,8/7
          |3/7,6/7 1,6/7
          |3/7,1 1,1
          |6/7,8/7 1,8/7
        """.trimMargin()

    val problem = parseProblem(problemString)
    val stupidSolve = StupidSolver().solve(problem)
    val translationSolve = TranslatorSolver().solve(problem)

    val solve1R = BitmapEstimator().resemblanceOf(problem, stupidSolve)
    val translatorR = BitmapEstimator().resemblanceOf(problem, translationSolve)

//    Visualizer().visualizedAndSaveImage(problem, stupidSolve,filePath = "./stupind_19.png")
//    Visualizer().visualizedAndSaveImage(problem, translationSolve,filePath = "./translator_19.png")
    assertThat(stupidSolve).isNotEqualTo(translationSolve)
    assertThat(translatorR).isGreaterThan(solve1R)
  }

}
