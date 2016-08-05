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
