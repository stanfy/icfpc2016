package icfp16.folder

import icfp16.api.parseProblem
import icfp16.estimate.BitmapEstimator
import icfp16.io.ProblemContainersParser
import icfp16.solver.SequenceSolver
import icfp16.visualizer.Visualizer

import icfp16.data.*
import icfp16.state.ComplexState
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test


class PoliginSplitterKtTest {

  @Test
  @Ignore
  fun task6Solution() {

    val problemString =
        ProblemContainersParser().generateProblemContainerForProblemId("7")!!.problem.rawString

    val problem = parseProblem(problemString)
    val foldingEdge = Edge(
        Vertex(Fraction(0), Fraction(1, 3)),
        Vertex(Fraction(1), Fraction(2, 3))
    )
    val foldingEdge2 = Edge(
        Vertex(Fraction(0), Fraction(1, 4)),
        Vertex(Fraction(1), Fraction(2, 4))
    )

    val foldingEdge3 = Edge(
        Vertex(Fraction(0), Fraction(1, 5)),
        Vertex(Fraction(1), Fraction(3, 5))
    )
    val foldingEdge4 = Edge(
        Vertex(Fraction(1,4), Fraction(0)),
        Vertex(Fraction(1,5), Fraction(1))
    )

    val foldingEdge5 = Edge(
        Vertex(Fraction(3,4), Fraction(1)),
        Vertex(Fraction(5,5), Fraction(0))
    )

    val translationSolve = ComplexState()
        .fold(foldingEdge)
        .fold(foldingEdge2)
        .fold(foldingEdge3)
        .fold(foldingEdge4)
        .fold(foldingEdge5)

    val translatorR = BitmapEstimator().resemblanceOf(problem, translationSolve)

    Visualizer().visualizedAndSaveImage(problem, translationSolve, filePath = "./translator_FOLD.png")
  }

}