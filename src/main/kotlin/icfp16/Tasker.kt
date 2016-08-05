package icfp16

import icfp16.data.*
import icfp16.state.State
import icfp16.visualizer.Visualizer
import java.io.File

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
                Vertex(Fraction(0), Fraction(2, 3)),
                Vertex(Fraction(1), Fraction(2, 3))
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
                        Vertex(Fraction(1, 2), Fraction(1, 2)),
                        Vertex(Fraction(22619537, 77227930), Fraction(54608393, 77227930)),
                        Vertex(Fraction(54608393, 77227930), Fraction(54608393, 77227930)),
                        Vertex(Fraction(1), Fraction(0))
                    )
                )
            ),
            emptyList()
        ),
        State(
            arrayOf(
                Vertex(0, 0),
                Vertex(0, 1),
                Vertex(Fraction(54608393, 77227930), Fraction(54608393, 77227930)),
                Vertex(1, 1),
                Vertex(1, 0)
            ),
            arrayOf(
                Facet(listOf(0, 2, 1)),
                Facet(listOf(0, 4, 2)),
                Facet(listOf(2, 4, 3)),
                Facet(listOf(2, 3, 1))
            ),
            arrayOf(
                Vertex(0, 0),
                Vertex(1, 0),
                Vertex(Fraction(54608393, 77227930), Fraction(54608393, 77227930)),
                Vertex(Fraction(22619537, 77227930), Fraction(54608393, 77227930)),
                Vertex(1, 0)
            )
        )
    )
)

fun main(args: Array<String>) {
  val dir = File("our_problems")
  dir.mkdirs()
  tasks.forEachIndexed { i, data ->
    Visualizer().visualizedAndSaveImage(data.first, data.second, 1, "our_problems/task$i.png")
    File(dir, "task$i.txt").writeText(data.second.solution())
  }
}
