package icfp16

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DataTest {

  val problem = Problem(
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

  @Test
  fun perimeterPoints() {
    assertThat(problem.poligons[0].maxVertextIn(Direction.LEFT)).isEqualTo(Vertex(Fraction(0), Fraction(0)))
    assertThat(problem.poligons[0].maxVertextIn(Direction.RIGHT)).isEqualTo(Vertex(Fraction(1), Fraction(0)))
    assertThat(problem.poligons[0].maxVertextIn(Direction.TOP)).isEqualTo(Vertex(Fraction(1, 2), Fraction(1, 2)))
    assertThat(problem.poligons[0].maxVertextIn(Direction.BOTTOM)).isEqualTo(Vertex(Fraction(0), Fraction(0)))
  }

}