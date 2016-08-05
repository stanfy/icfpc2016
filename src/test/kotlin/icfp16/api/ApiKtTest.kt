package icfp16.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ApiKtTest {
  fun f(a: Int, b: Int = 1) = Fraction(a, b)
  fun v(a: Fraction, b: Fraction) = Vertex(a, b)

  @Test
  fun parseProblem1() {
    val problem =
        """1
          |4
          |0,0
          |1,0
          |1,1
          |0,1
          |4
          |0,0 1,0
          |0,0 0,1
          |1,0 1,1
          |0,1 1,1
        """.trimMargin()

    assertThat(parseProblem(problem)).isEqualTo(
        Problem(
            listOf(
                Polygon(listOf(
                    v(f(0), f(0)),
                    v(f(1), f(0)),
                    v(f(1), f(1)),
                    v(f(0), f(1)))
                )),
            listOf(
                Edge(v(f(0), f(0)), v(f(1), f(0))),
                Edge(v(f(0), f(0)), v(f(0), f(1))),
                Edge(v(f(1), f(0)), v(f(1), f(1))),
                Edge(v(f(0), f(1)), v(f(1), f(1)))
            )
        )
    )

    parseProblem(problem)
  }

  @Test
  fun parseProblem2() {
    val problem =
        """1
          |4
          |0,0
          |1,0
          |1/2,1/2
          |0,1/2
          |5
          |0,0 1,0
          |1,0 1/2,1/2
          |1/2,1/2 0,1/2
          |0,1/2 0,0
          |0,0 1/2,1/2""".trimMargin()

    assertThat(parseProblem(problem)).isEqualTo(
        Problem(
            listOf(
                Polygon(listOf(
                    v(f(0), f(0)),
                    v(f(1), f(0)),
                    v(f(1, 2), f(1, 2)),
                    v(f(0), f(1, 2)))
                )),
            listOf(
                Edge(v(f(0), f(0)), v(f(1), f(0))),
                Edge(v(f(1), f(0)), v(f(1, 2), f(1, 2))),
                Edge(v(f(1, 2), f(1, 2)), v(f(0), f(1, 2))),
                Edge(v(f(0), f(1, 2)), v(f(0), f(0))),
                Edge(v(f(0), f(0)), v(f(1, 2), f(1, 2)))
            )
        )
    )

    parseProblem(problem)
  }

  @Test
  fun testParseVertex() {
    assertThat(parseVertex("1,1")).isEqualTo(v(f(1), f(1)))
    assertThat(parseVertex("1/2,1")).isEqualTo(v(f(1, 2), f(1)))
    assertThat(parseVertex("1,-1/5")).isEqualTo(v(f(1), f(-1, 5)))
    assertThat(parseVertex("1/5,1")).isEqualTo(v(f(1, 5), f(1)))
    assertThat(parseVertex("-1/3,-1/3")).isEqualTo(v(f(-1, 3), f(-1, 3)))
  }

  @Test
  fun testParseFraction() {
    assertThat(parseFraction("1")).isEqualTo(f(1))
    assertThat(parseFraction("-1")).isEqualTo(f(-1))
    assertThat(parseFraction("1/2")).isEqualTo(f(1, 2))
    assertThat(parseFraction("-1/2")).isEqualTo(f(-1, 2))
  }
}