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

    val translationSolve = ComplexState()
        .fold(Edge(
            Vertex(Fraction(0), Fraction(1, 3)),
            Vertex(Fraction(1), Fraction(2, 3))
        ))
        .fold(Edge(
            Vertex(Fraction(0), Fraction(1, 4)),
            Vertex(Fraction(1), Fraction(2, 4))
        ))
        .fold(Edge(
            Vertex(Fraction(0), Fraction(1, 5)),
            Vertex(Fraction(1), Fraction(3, 5))
        ))
        .fold(Edge(
            Vertex(Fraction(1,4), Fraction(0)),
            Vertex(Fraction(1,5), Fraction(1))
        ))
        .fold(Edge(
            Vertex(Fraction(3,4), Fraction(1)),
            Vertex(Fraction(5,5), Fraction(0))
        ))

    val translatorR = BitmapEstimator().resemblanceOf(problem, translationSolve)

    Visualizer().visualizedAndSaveImage(problem, translationSolve, filePath = "./translator_FOLD.png", showGrid = true)
  }

  @Test
  @Ignore
  fun starFoldingSolution() {

    val problemString =
        ProblemContainersParser().generateProblemContainerForProblemId("7")!!.problem.rawString

    val problem = parseProblem(problemString)

    val translationSolve = ComplexState()
        .foldStar(Edge(
            Vertex(Fraction(0), Fraction(1, 2)),
            Vertex(Fraction(1), Fraction(1, 2))
        ), Fraction(1,2), Fraction(1,2), Vertex(4,4))



    Visualizer().visualizedAndSaveImage(problem, translationSolve, filePath = "./translator_FOLD_Star.png")
  }

  @Test
  fun debugExTest4()
  {
    val edge1 = Edge(
        Vertex(Fraction(0,8), Fraction(1, 8)),
        Vertex(Fraction(1,8), Fraction(0, 8)))
    val edge2 = Edge(
        Vertex(Fraction(-1,4), Fraction(3, 8)),
        Vertex(Fraction(1,8), Fraction(3, 8)))
    val res = edge2.cross(edge1)
    assertThat(res).isEqualTo(null)
  }
  @Test
  fun debugExTest3()
  {
    //[7/8,3/8, 7/8,1, 1,1, 1,1/2] by Edge(a=7/8,1, b=1,1/2)
    val polygon = Polygon(
        arrayListOf( Vertex(Fraction(-1,4), Fraction(3,8)),
            Vertex(Fraction(1,8), Fraction(3,8)),
            Vertex(Fraction(0), Fraction(1,2)),
            Vertex(Fraction(-1,8), Fraction(1,2))))
    val edge = Edge(
        Vertex(Fraction(0,8), Fraction(1, 8)),
        Vertex(Fraction(1,8), Fraction(0, 8)))

    val res = polygon.splitSimple(edge)
    assertThat(res.count()).isEqualTo(1)

  }

  @Test
  fun debugExTest(){

    val step1 = ComplexState()
        .fold(Edge(
            Vertex(Fraction(1,2), Fraction(1, 1)),
            Vertex(Fraction(1,2), Fraction(0, 1))
        ))
     val step2 = step1.fold(Edge(
            Vertex(Fraction(1,4), Fraction(1, 1)),
            Vertex(Fraction(1,4), Fraction(0, 1))
        ))
      val step3 = step2.fold(Edge(
            Vertex(Fraction(1,8), Fraction(1, 1)),
            Vertex(Fraction(1,8), Fraction(0, 1))
        ))

     val step4 = step3.fold(Edge(
            Vertex(Fraction(1,8), Fraction(2, 8)),
            Vertex(Fraction(0,8), Fraction(1, 8))
        ))

    val step5 = step4.fold(Edge(
            Vertex(Fraction(0,8), Fraction(4, 8)),
            Vertex(Fraction(1,8), Fraction(3, 8))
        ))
    val step6 = step5.fold(Edge(
            Vertex(Fraction(0,8), Fraction(5, 8)),
            Vertex(Fraction(1,8), Fraction(6, 8))
        ))
     val step7 = step6.fold(Edge(
            Vertex(Fraction(0, 8), Fraction(1, 8)),
            Vertex(Fraction(1,8), Fraction(0, 8))
        ))
    assert(true ==true)
  }

}