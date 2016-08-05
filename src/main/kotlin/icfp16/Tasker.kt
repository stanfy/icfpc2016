package icfp16

import icfp16.api.SolutionSpec
import icfp16.api.createApi
import icfp16.data.*
import icfp16.state.State
import icfp16.visualizer.Visualizer
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

val tasks = arrayOf(
    // Task 0.
    Pair(
        Problem(
            arrayListOf(
                Polygon(
                    arrayListOf(
                        Vertex(Fraction(0), Fraction(1, 3)),
                        Vertex(Fraction(0), Fraction(2, 3)),
                        Vertex(Fraction(1), Fraction(2, 3)),
                        Vertex(Fraction(1), Fraction(1, 3))
                    )
                )
            ),
            arrayListOf()
        ),
        State(
            arrayOf(
                Vertex(0, 0),
                Vertex(1, 0),
                Vertex(Fraction(0), Fraction(1, 3)),
                Vertex(Fraction(1), Fraction(1, 3)),
                Vertex(Fraction(0), Fraction(2, 3)),
                Vertex(Fraction(1), Fraction(2, 3)),
                Vertex(0, 1),
                Vertex(1, 1)
            ),
            arrayOf(
                Facet(arrayListOf(0, 1, 3, 2)),
                Facet(arrayListOf(2, 3, 5, 4)),
                Facet(arrayListOf(4, 5, 7, 6))
            ),
            arrayOf(
                Vertex(Fraction(0), Fraction(2, 3)),
                Vertex(Fraction(1), Fraction(2, 3)),
                Vertex(Fraction(0), Fraction(1, 3)),
                Vertex(Fraction(1), Fraction(1, 3)),
                Vertex(Fraction(0), Fraction(2, 3)),
                Vertex(Fraction(1), Fraction(2, 3)),
                Vertex(Fraction(0), Fraction(1, 3)),
                Vertex(Fraction(1), Fraction(1, 3))
            )
        )
    ),

    // Task 1.
    Pair(
        Problem(
            arrayListOf(
                Polygon(
                    arrayListOf(
                        Vertex(Fraction(0), Fraction(1, 3)),
                        Vertex(Fraction(1, 3), Fraction(1, 3)),
                        Vertex(Fraction(2, 3), Fraction(2, 3)),
                        Vertex(Fraction(2, 3), Fraction(1)),
                        Vertex(Fraction(1, 3), Fraction(1)),
                        Vertex(Fraction(1, 3), Fraction(2, 3)),
                        Vertex(Fraction(0), Fraction(2, 3))
                    )
                )
            ),
            arrayListOf()
        ),
        State(
            arrayOf(
                Vertex(0, 0),
                Vertex(Fraction(2, 3), Fraction(0)),
                Vertex(1, 0),
                Vertex(Fraction(0), Fraction(1, 3)),
                Vertex(Fraction(1, 3), Fraction(1, 3)),
                Vertex(Fraction(1), Fraction(1, 3)),
                Vertex(Fraction(0), Fraction(2, 3)),
                Vertex(Fraction(2, 3), Fraction(2, 3)),
                Vertex(Fraction(1), Fraction(2, 3)),
                Vertex(0, 1),
                Vertex(Fraction(1, 3), Fraction(1)),
                Vertex(1, 1)
            ),
            arrayOf(
                Facet(arrayListOf(0, 1, 4, 3)),
                Facet(arrayListOf(1, 2, 5, 4)),
                Facet(arrayListOf(3, 4, 7, 6)),
                Facet(arrayListOf(4, 5, 8, 7)),
                Facet(arrayListOf(6, 7, 10, 9)),
                Facet(arrayListOf(7, 8, 11, 10))
            ),
            arrayOf(
                Vertex(Fraction(0), Fraction(2, 3)),
                Vertex(Fraction(2, 3), Fraction(2, 3)),
                Vertex(Fraction(2, 3), Fraction(1)),
                Vertex(Fraction(0), Fraction(1, 3)),
                Vertex(Fraction(1, 3), Fraction(1, 3)),
                Vertex(Fraction(1, 3), Fraction(1)),
                Vertex(Fraction(0), Fraction(2, 3)),
                Vertex(Fraction(2, 3), Fraction(2, 3)),
                Vertex(Fraction(2, 3), Fraction(1)),
                Vertex(Fraction(0), Fraction(1, 3)),
                Vertex(Fraction(1, 3), Fraction(1, 3)),
                Vertex(Fraction(1, 3), Fraction(1))
            )
        )
    ),


    // Task 2
    Pair(
        Problem(
            listOf(
                Polygon(
                    listOf(
                        Vertex(Fraction(0), Fraction(0)),
                        Vertex(Fraction(1), Fraction(0)),
                        Vertex(Fraction(1), Fraction(1)),
                        Vertex(Fraction(2, 3), Fraction(2, 3)),
                        Vertex(Fraction(1, 3), Fraction(2, 3)),
                        Vertex(Fraction(1, 3), Fraction(1, 3))
                    )
                )
            ),
            emptyList()
        ),
        State(
            arrayOf(
                Vertex(0,0),
                Vertex(1,0),
                Vertex(1,1),
                Vertex(Fraction(2, 3), Fraction(1)),
                Vertex(0,1),
                Vertex(Fraction(0),Fraction(1, 3))
            ),
            arrayOf(
                Facet(arrayListOf(5, 3, 4)),
                Facet(arrayListOf(0, 2, 3, 5)),
                Facet(arrayListOf(0, 1, 2))
            ),
            arrayOf(
                Vertex(0, 0),
                Vertex(1, 0),
                Vertex(1, 1),
                Vertex(Fraction(1), Fraction(2, 3)),
                Vertex(Fraction(1, 3), Fraction(2, 3)),
                Vertex(Fraction(1, 3), Fraction(0))
            )
        )
    )
)

var submit = true

fun main(args: Array<String>) {
  if (args.size > 0) {
    submit = true
  }

  val dir = File("our_problems")
  dir.mkdirs()
  var ts = Instant.parse("2016-08-06T00:00:00Z")
  val api = createApi(HttpLoggingInterceptor.Level.NONE)
  tasks.forEachIndexed { i, data ->
    Visualizer().visualizedAndSaveImage(data.first, data.second, 1, "our_problems/task$i.png")
    val text = data.second.solution()
    File(dir, "task$i.txt").writeText(text)

    if (submit) {
      val request = api.submitProblem(SolutionSpec(text), TimeUnit.MILLISECONDS.toSeconds(ts.toEpochMilli())).execute()
      println("Problem submission #$i is successful: ${request.isSuccessful}")
      Thread.sleep(TimeUnit.SECONDS.toMillis(2))
    }

    ts = ts.plus(1, ChronoUnit.HOURS)
  }
}
