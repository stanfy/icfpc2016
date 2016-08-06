package icfp16

import icfp16.data.Fraction
import icfp16.data.Polygon
import icfp16.data.Problem
import icfp16.data.Vertex
import icfp16.farm.Farm
import icfp16.io.ProblemContainersGrabber
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

  if (args.size > 0 && "grab".equals(args[0])) {
    println("Grabbing data")
    ProblemContainersGrabber().grabProblemsAndSaveToFiles()
    return
  }

  println("Starting the farm")
  Farm().startSearchingBestSolutions()
}
