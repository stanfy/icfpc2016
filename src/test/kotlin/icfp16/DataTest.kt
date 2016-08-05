package icfp16

import icfp16.data.*
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

  @Test
  fun fractionAdding() {
    assertThat(Fraction(1).add(Fraction(2))).isEqualTo(Fraction(3))
    assertThat(Fraction(1, 3).add(Fraction(2,3))).isEqualTo(Fraction(1))
    assertThat(Fraction(5, 3).add(Fraction(-2,3))).isEqualTo(Fraction(1))
    assertThat(Fraction(1, 12).add(Fraction(2,6))).isEqualTo(Fraction(5, 12))
  }

  @Test
  fun vertexAdding() {
    assertThat(Vertex(1,1).add(Vertex(2,2))).isEqualTo(Vertex(3,3))
    assertThat(Vertex(20,1).add(Vertex(2,30))).isEqualTo(Vertex(22,31))
    assertThat(Vertex(10,-10).add(Vertex(-11,11))).isEqualTo(Vertex(-1,1))
  }


}