package icfp16.folder

import icfp16.io.ProblemContainersParser
import icfp16.state.NastyaPublicStates
import icfp16.state.solution
import icfp16.submitter.Submitter
import icfp16.visualizer.Visualizer
import org.junit.Ignore
import org.junit.Test


class FigureCheckTest {

  @Test
  @Ignore
  fun figureTest() {
    val problem = ProblemContainersParser().generateProblemContainerForProblemId("7")!!.problem

    val state = NastyaPublicStates.states.first()
    Visualizer().visualizedAndSaveImage(problem, state, filePath = "./figure_triangle.png")

    Submitter().submitSolution("2", state.solution().trim())
  }

}
