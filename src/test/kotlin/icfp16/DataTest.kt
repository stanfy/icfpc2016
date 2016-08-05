package icfp16

import icfp16.data.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigInteger

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
  fun sumFraction() {
    val frac1 = Fraction(3,5)
    val frac2 = Fraction(7,13)
    val res = frac1.add(frac2)

    assertThat(res.a ==  BigInteger.valueOf(3*13 + 5*7))
    assertThat(res.b ==  BigInteger.valueOf(5*13))
    val res2 = frac2.add(frac1)
    assertThat(res2.a ==  BigInteger.valueOf(3*13 + 5*7))
    assertThat(res2.b ==  BigInteger.valueOf(5*13))


  }

  @Test
  fun fractionAdding() {
    assertThat(Fraction(1).add(Fraction(2))).isEqualTo(Fraction(3))
    assertThat(Fraction(1, 3).add(Fraction(2,3))).isEqualTo(Fraction(1))
    assertThat(Fraction(5, 3).add(Fraction(-2,3))).isEqualTo(Fraction(1))
    assertThat(Fraction(1, 12).add(Fraction(2,6))).isEqualTo(Fraction(5, 12))
  }

  @Test
  fun fractionNeg() {
    assertThat(Fraction(2).neg()).isEqualTo(Fraction(-2))
    assertThat(Fraction(-2).neg()).isEqualTo(Fraction(2))
  }

  @Test
  fun fractionSubstraction() {
    assertThat(Fraction(2).sub(Fraction(1))).isEqualTo(Fraction(1))
    assertThat(Fraction(2, 3).sub(Fraction(1,3))).isEqualTo(Fraction(1,3))
    assertThat(Fraction(5, 3).sub(Fraction(2,3))).isEqualTo(Fraction(1))
    assertThat(Fraction(2, 6).sub(Fraction(2,12))).isEqualTo(Fraction(1, 6))
  }

  @Test
  fun fractionMultiplication() {
    assertThat(Fraction(1).mul(Fraction(2))).isEqualTo(Fraction(2))
    assertThat(Fraction(1).mul(Fraction(0))).isEqualTo(Fraction(0))
    assertThat(Fraction(3, 3).mul(Fraction(3,3))).isEqualTo(Fraction(1))
    assertThat(Fraction(5, 3).mul(Fraction(2,3))).isEqualTo(Fraction(10,9))
  }

  @Test
  fun fractionInverse() {
    assertThat(Fraction(2,3).inverse()).isEqualTo(Fraction(3,2))
    assertThat(Fraction(3,2).inverse()).isEqualTo(Fraction(2,3))
  }
  @Test
  fun fractionDivision() {
    assertThat(Fraction(4).div(Fraction(2))).isEqualTo(Fraction(2))
    assertThat(Fraction(1, 3).div(Fraction(2,3))).isEqualTo(Fraction(1,2))
  }

  @Test
  fun vertexAdding() {
    assertThat(Vertex(1,1).add(Vertex(2,2))).isEqualTo(Vertex(3,3))
    assertThat(Vertex(20,1).add(Vertex(2,30))).isEqualTo(Vertex(22,31))
    assertThat(Vertex(10,-10).add(Vertex(-11,11))).isEqualTo(Vertex(-1,1))
  }


}