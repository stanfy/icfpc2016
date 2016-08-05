package icfp16

import icfp16.api.ContestStatus
import icfp16.api.Snapshot
import icfp16.api.SolutionSpec
import icfp16.api.createApi
import icfp16.farm.ProblemsGrabber
import icfp16.submitter.Submitter
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import java.io.File

var problem = Problem(
    arrayListOf(Polygon(
        arrayListOf(
            Vertex(Fraction(0), Fraction(0)),
            Vertex(Fraction(1), Fraction(0)),
            Vertex(Fraction(1, 2), Fraction(1, 2)),
            Vertex(Fraction(0), Fraction(1, 2))
        )
    )),
    arrayListOf(),
        "", "", ""
)

fun main(args: Array<String>) {
  println("ICFP 2016")

//  val rightMost = problem.poligons[0].vertices.maxBy { v -> v.toPoint().first }
//  println(rightMost)

  val submitter = Submitter()

  // submit solution:
  val string = """
          4
          0,0
          0,1
          1,1
          1,0
          1
          4 0 1 2 3
          0,0
          0,1
          1,1
          1,0
          """.trim()

 // println(submitter.submitSolution(problemId = "1", solutionString = string))

  val problemsGrabber = ProblemsGrabber().grabProblemsAndSaveToFiles("parsed_problems")
}
