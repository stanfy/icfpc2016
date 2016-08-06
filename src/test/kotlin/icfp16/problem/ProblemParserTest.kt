package icfp16.problem

import icfp16.data.Facet
import icfp16.data.Fraction
import icfp16.data.Vertex
import icfp16.state.State
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**   */
class ProblemParserTest {
  @Test
  fun parseState() {
    val input = """8
                  |0,0
                  |1,0
                  |0,1/3
                  |1,1/3
                  |0,2/3
                  |1,2/3
                  |0,1
                  |1,1
                  |3
                  |4 0 1 3 2
                  |4 2 3 5 4
                  |4 4 5 7 6
                  |0,2/3
                  |1,2/3
                  |0,1/3
                  |1,1/3
                  |0,2/3
                  |1,2/3
                  |0,1/3
                  |1,1/3
                  |""".trimMargin()

    val rez = ProblemParser().parseState(input)

    assertThat(rez.solution()).isEqualTo(
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
        ).solution()
    )
  }

}