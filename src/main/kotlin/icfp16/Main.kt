package icfp16

import icfp16.api.createApi
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE

var problem = Problem(
    arrayListOf(Polygon(
        arrayListOf(
            Vertex(Fraction(0), Fraction(0)),
            Vertex(Fraction(1), Fraction(0)),
            Vertex(Fraction(1, 2), Fraction(1, 2)),
            Vertex(Fraction(0), Fraction(1, 2))
        )
    )),
    arrayListOf()
)

fun main(args: Array<String>) {
  println("ICFP 2016")

  val rightMost = problem.poligons[0].vertices.maxBy { v -> v.toPoint().first }
  println(rightMost)

  val api = createApi(NONE)
//  println(api.helloWorld().execute().body())
//  println(api.getProblemSpec("f4b1a8567108144bae331340a57c68b85df487e0").execute().body())
//  println(api.listSnapchots().execute().body())
//  println(api.getContestStatus("89c4c40226f4efcdb8fc58c9f74339d768ef4737").execute().body())
}


