package icfp16

import icfp16.api.ContestStatus
import icfp16.api.Snapshot
import icfp16.api.SolutionSpec
import icfp16.api.createApi
import icfp16.data.Fraction
import icfp16.data.Polygon
import icfp16.data.Problem
import icfp16.data.Vertex
import icfp16.farm.ProblemContainersGrabber
import icfp16.farm.ProblemContainersParser
import icfp16.submitter.Submitter
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import java.io.File
import java.math.BigInteger

var problem = Problem(
    arrayListOf(Polygon(
        arrayListOf(
            Vertex(Fraction(BigInteger("0")), Fraction(BigInteger("0"))),
            Vertex(Fraction(BigInteger("1")), Fraction(BigInteger("0"))),
            Vertex(Fraction(BigInteger("1"), BigInteger("2")), Fraction(BigInteger("1"), BigInteger("2"))),
            Vertex(Fraction(BigInteger("0")), Fraction(BigInteger("1"), BigInteger("2")))
        )
    )),
    arrayListOf(),
        ""
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

  // grab problems from server
  //val problemsGrabber = ProblemsGrabber().grabProblemsAndSaveToFiles("parsed_problems")
}
