package icfp16.folder

import icfp16.api.parseProblem
import icfp16.estimate.BitmapEstimator
import icfp16.io.ProblemContainersParser
import icfp16.solver.SequenceSolver
import icfp16.visualizer.Visualizer

import icfp16.data.*
import icfp16.state.ComplexState
import icfp16.state.appendName
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
        ), Fraction(1,7), Fraction(1,2), Vertex(4,4))

    Visualizer().visualizedAndSaveImage(problem, translationSolve, filePath = "./translator_FOLD_Star.png")

  }

  @Test
  @Ignore
  fun trikutz() {
    val problemString =
        ProblemContainersParser().generateProblemContainerForProblemId("7")!!.problem.rawString

    val problem = parseProblem(problemString)

    val translationSolve = ComplexState()
        .fold(Edge(
            Vertex(Fraction(1, 2), Fraction(1, 1)),
            Vertex(Fraction(1, 2), Fraction(0, 4))
        ))
        .fold(Edge(
            Vertex(Fraction(0, 2), Fraction(1, 2)),
            Vertex(Fraction(1, 2), Fraction(1, 2))
        ))

        .fold(Edge(
            Vertex(Fraction(1, 2), Fraction(1, 8)),
            Vertex(Fraction(3, 8), Fraction(0, 8))
        ))
        .fold(Edge(
            Vertex(Fraction(3, 8), Fraction(1, 2)),
            Vertex(Fraction(1, 2), Fraction(3, 8))
        ))
        .fold(Edge(
            Vertex(Fraction(1, 8), Fraction(0, 2)),
            Vertex(Fraction(0, 2), Fraction(1, 8))
        ))
        .fold(Edge(
            Vertex(Fraction(0, 2), Fraction(3, 8)),
            Vertex(Fraction(1, 8), Fraction(1, 2))
        ))

    Visualizer().visualizedAndSaveImage(problem, translationSolve, filePath = "./translator_FOLD_Trikutz.png", showGrid = true)

  }

  @Test
  fun bubbloTest(){
    val problemString =
        ProblemContainersParser().generateProblemContainerForProblemId("7")!!.problem.rawString

    val problem = parseProblem(problemString)
    val b1 = ComplexState()
        .fold(Edge(
            Vertex(Fraction(1,2), Fraction(1, 1)),
            Vertex(Fraction(1,2), Fraction(0, 1))
        ))
    val b2 = b1
        .fold(Edge(
            Vertex(Fraction(1,4), Fraction(1, 1)),
            Vertex(Fraction(1,4), Fraction(0, 1))
        ))
    val b3 = b2
        .fold(Edge(
            Vertex(Fraction(1,8), Fraction(1, 1)),
            Vertex(Fraction(1,8), Fraction(0, 1))
        ))
    val b4 =b3
        .fold(Edge(
            Vertex(Fraction(1,8), Fraction(2, 8)),
            Vertex(Fraction(0,8), Fraction(1, 8))
        ))
    val b5 = b4
        .fold(Edge(
            Vertex(Fraction(0,8), Fraction(4, 8)),
            Vertex(Fraction(1,8), Fraction(3, 8))
        ))
    val b6 = b5
        .fold(Edge(
            Vertex(Fraction(-1,4), Fraction(3, 8)),
            Vertex(Fraction(-1,8), Fraction(1, 2))
        ))
        .appendName("Okolobubl")



    Visualizer().visualizedAndSaveImage(problem, b1, filePath = "./translator_FOLD_Bubl1.png")
    Visualizer().visualizedAndSaveImage(problem, b2, filePath = "./translator_FOLD_Bubl2.png")
    Visualizer().visualizedAndSaveImage(problem, b3, filePath = "./translator_FOLD_Bubl3.png")
    Visualizer().visualizedAndSaveImage(problem, b4, filePath = "./translator_FOLD_Bubl4.png")
    Visualizer().visualizedAndSaveImage(problem, b5, filePath = "./translator_FOLD_Bubl5.png")
    Visualizer().visualizedAndSaveImage(problem, b6, filePath = "./translator_FOLD_Bubl6.png")
  }

  @Test
  fun debugExTest7()
  {

    val polygon = Polygon(
        arrayListOf( Vertex(Fraction(1,8), Fraction(1)),
            Vertex(Fraction(0), Fraction(1)),
            Vertex(Fraction(0), Fraction(0)),
            Vertex(Fraction(1,8), Fraction(0))))

    val edge = Edge(
        Vertex(Fraction(1), Fraction(0, 8)),
        Vertex(Fraction(0,8), Fraction(0, 8)))

    val res = polygon.splitSimple(edge)

    assertThat(res.count()).isEqualTo(1)
    assertThat(res[0].polygon.vertices.count()).isEqualTo(4)
  }

  @Test
  fun debugExTest6()
  {

    val polygon = Polygon(
        arrayListOf( Vertex(Fraction(1,8), Fraction(1)),
            Vertex(Fraction(0), Fraction(1)),
            Vertex(Fraction(0), Fraction(0)),
            Vertex(Fraction(1,8), Fraction(0))))

    val edge = Edge(
        Vertex(Fraction(1,8), Fraction(2, 8)),
        Vertex(Fraction(0,8), Fraction(0, 8)))

    val res = polygon.splitSimple(edge)

    assertThat(res.count()).isEqualTo(2)
    assertThat(res[0].polygon.vertices.count()).isEqualTo(3)
    assertThat(res[1].polygon.vertices.count()).isEqualTo(4)
  }

  @Test
  fun debugExTest5_3()
  {

    val f1 = Fraction(1,4)
    val f2 = Fraction(2,8)


    assertThat(f1.leq(f2)).isEqualTo(true)
    assertThat(f1.geq(f2)).isEqualTo(true)
    assertThat(f1.equals(f2)).isEqualTo(true)
    assertThat(f2.leq(f1)).isEqualTo(true)
    assertThat(f2.geq(f1)).isEqualTo(true)
    assertThat(f2.equals(f1)).isEqualTo(true)

  }
  @Test
  fun debugExTest5_2()
  {

    val vertex = Vertex(Fraction(1,8), Fraction(1,4))
    val edge = Edge(
        Vertex(Fraction(1,8), Fraction(2, 8)),
        Vertex(Fraction(0,8), Fraction(1, 8)))
    val res = vertex.withinBoundary(edge)
    assertThat(res).isEqualTo(true)
  }

  @Test
  fun debugExTest5_1()
  {
    val edge1 = Edge(
        Vertex(Fraction(1,8), Fraction(0, 8)),
        Vertex(Fraction(1,8), Fraction(8, 8)))
    val edge2 = Edge(
        Vertex(Fraction(1,8), Fraction(2, 8)),
        Vertex(Fraction(0,8), Fraction(1, 8)))
    val res = edge2.cross(edge1)
    assertThat(res).isEqualTo(Vertex(Fraction(1,8), Fraction(2,8)))
  }
  @Test
  fun debugExTest5()
  {
    val polygon = Polygon(
        arrayListOf( Vertex(Fraction(1,8), Fraction(1)),
            Vertex(Fraction(0), Fraction(1)),
            Vertex(Fraction(0), Fraction(0)),
            Vertex(Fraction(1,8), Fraction(0))))

    val edge = Edge(
        Vertex(Fraction(1,8), Fraction(2, 8)),
        Vertex(Fraction(0,8), Fraction(1, 8)))

    val res = polygon.splitSimple(edge)

    assertThat(res.count()).isEqualTo(2)
    assertThat(res[0].polygon.vertices.count()).isEqualTo(4)
    assertThat(res[1].polygon.vertices.count()).isEqualTo(4)
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