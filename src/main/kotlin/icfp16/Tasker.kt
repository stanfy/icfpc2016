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

val vertexes = arrayOf(
    Vertex(Fraction(0), Fraction(0)), // 0

    Vertex(Fraction(0), Fraction(1, 4)), // 1
    Vertex(Fraction(0), Fraction(1, 2)), // 2
    Vertex(Fraction(0), Fraction(3, 4)), // 3

    Vertex(Fraction(0), Fraction(1)), // 4

    Vertex(Fraction(1, 4), Fraction(1)), // 5
    Vertex(Fraction(1, 2), Fraction(1)), // 6
    Vertex(Fraction(3, 4), Fraction(1)), // 7

    Vertex(Fraction(1), Fraction(1)), // 8

    Vertex(Fraction(1), Fraction(3, 4)), // 9
    Vertex(Fraction(1), Fraction(1, 2)), // 10
    Vertex(Fraction(1), Fraction(1, 4)), // 11

    Vertex(Fraction(1), Fraction(0)), // 12

    Vertex(Fraction(3, 4), Fraction(0)), // 13
    Vertex(Fraction(1, 2), Fraction(0)), // 14
    Vertex(Fraction(1, 4), Fraction(0)), // 15

    Vertex(Fraction(1, 4), Fraction(1, 4)), // 16
    Vertex(Fraction(1, 4), Fraction(1, 2)), // 17
    Vertex(Fraction(1, 4), Fraction(3, 4)), // 18

    Vertex(Fraction(1, 2), Fraction(3, 4)), // 19
    Vertex(Fraction(3, 4), Fraction(3, 4)), // 20

    Vertex(Fraction(3, 4), Fraction(1, 2)), // 21
    Vertex(Fraction(3, 4), Fraction(1, 4)), // 22
    Vertex(Fraction(1, 2), Fraction(1, 4)), // 23

    Vertex(Fraction(1, 2), Fraction(1, 2)) // 24
)

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
    ),

    // Task 3
    Pair(
        Problem(
            listOf(
                Polygon(
                    listOf(
                        Vertex(Fraction(0), Fraction(1)),
                        Vertex(Fraction(0), Fraction(2,3)),
                        Vertex(Fraction(2,3), Fraction(0)),
                        Vertex(Fraction(1), Fraction(0)),
                        Vertex(Fraction(2, 3), Fraction(1, 3)),
                        Vertex(Fraction(2, 3), Fraction(2, 3)),
                        Vertex(Fraction(1, 3), Fraction(2, 3))
                    )
                )
            ),
            emptyList()
        ),
        State(
            arrayOf(
                Vertex(Fraction(0),Fraction(0)),
                Vertex(Fraction(2, 3),Fraction(0)),
                Vertex(Fraction(1),Fraction(0)),
                Vertex(Fraction(1),Fraction(1, 3)),
                Vertex(Fraction(1),Fraction(1)),
                Vertex(Fraction(1, 3),Fraction(1)),
                Vertex(Fraction(0),Fraction(1)),
                Vertex(Fraction(0),Fraction(2, 3))
            ),
            arrayOf(
                Facet(arrayListOf(0, 1, 7)),
                Facet(arrayListOf(1, 2, 6, 7)),
                Facet(arrayListOf(2, 3, 5, 6)),
                Facet(arrayListOf(3, 4, 5))
            ),
            arrayOf(
                Vertex(Fraction(2, 3), Fraction(2, 3)),
                Vertex(Fraction(2, 3), Fraction(0)),
                Vertex(Fraction(1), Fraction(0)),
                Vertex(Fraction(2, 3), Fraction(0)),
                Vertex(Fraction(2, 3), Fraction(2, 3)),
                Vertex(Fraction(0), Fraction(2, 3)),
                Vertex(Fraction(0), Fraction(1)),
                Vertex(Fraction(0), Fraction(2, 3))
            )
        )
    ),

    // cool last figure - task 4
    Pair(
        Problem(
            arrayListOf(),
            arrayListOf()
        ),
        State(
            vertexes = arrayOf(
                Vertex(Fraction(0), Fraction(0)), // 0
                Vertex(Fraction(0), Fraction(3, 4)), // 1
                Vertex(Fraction(0), Fraction(1)), // 2
                Vertex(Fraction(1), Fraction(1)), //3
                Vertex(Fraction(1), Fraction(1, 4)), //4
                Vertex(Fraction(1), Fraction(0)) //5
            ),
            facets = arrayOf(
                Facet(listOf(0, 1, 4, 5)),
                Facet(listOf(1, 2, 3, 4))
            ),
            finalPositions = arrayOf(
                Vertex(Fraction(0), Fraction(0)), // 0
                Vertex(Fraction(0), Fraction(3, 4)), // 1
                Vertex(Fraction(-1, 5), Fraction(3, 5)), //2
                Vertex(Fraction(2, 5), Fraction(-1, 5)), // 3
                Vertex(Fraction(1), Fraction(1, 4)), //4
                Vertex(Fraction(1), Fraction(0)) //5
            )
        )
    ),

    // Very small rectangle - task 5
    Pair(
        Problem(
            arrayListOf(),
            arrayListOf()
        ),
        State(vertexes = vertexes,
            facets = arrayOf(
                Facet(arrayListOf(0, 1, 16, 15)),
                Facet(arrayListOf(1, 2, 17, 16)),
                Facet(arrayListOf(2, 3, 18, 17)),
                Facet(arrayListOf(3, 4, 5, 18)),

                Facet(arrayListOf(18, 5, 6, 19)),
                Facet(arrayListOf(19, 6, 7, 20)),
                Facet(arrayListOf(20, 7, 8, 9)),
                Facet(arrayListOf(21, 20, 9, 10)),

                Facet(arrayListOf(22, 21, 10, 11)),
                Facet(arrayListOf(13, 22, 11, 12)),
                Facet(arrayListOf(14, 23, 22, 13)),
                Facet(arrayListOf(15, 16, 23, 14)),

                Facet(arrayListOf(16, 17, 24, 23)),
                Facet(arrayListOf(17, 18, 19, 24)),
                Facet(arrayListOf(24, 19, 20, 21)),
                Facet(arrayListOf(23, 24, 21, 22))
            ),

            finalPositions = arrayOf(
                vertexes[0], // 0

                vertexes[1], // 1
                vertexes[0], // 2
                vertexes[1], // 3
                vertexes[0], // 4
                vertexes[15], // 5
                vertexes[0], // 6
                vertexes[15], // 7
                vertexes[0], // 8
                vertexes[1], // 9
                vertexes[0], // 10
                vertexes[1], // 11
                vertexes[0], // 12
                vertexes[15], // 13
                vertexes[0], // 14
                vertexes[15], // 15
                vertexes[16], // 16
                vertexes[15], // 17
                vertexes[16], // 18

                vertexes[1], // 19
                vertexes[16], // 20

                vertexes[15], // 21
                vertexes[16], // 22
                vertexes[1], // 23

                vertexes[0] // 24
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
    val visualizer = Visualizer()
    visualizer.visualizedAndSaveImage(data.first, data.second, 1, "our_problems/task$i.png")
    visualizer.visualizedAndSaveFolds(data.second, 1, "our_problems/task${i}folds.png")
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
