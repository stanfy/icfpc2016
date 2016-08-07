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
  fun fractionAbs() {
    assertThat(Fraction(2,3).abs()).isEqualTo(Fraction(2,3))
    assertThat(Fraction(-2,3).abs()).isEqualTo(Fraction(2,3))
  }
  @Test
  fun fractionDivision() {
    assertThat(Fraction(4).divFrac(Fraction(2))).isEqualTo(Fraction(2))
    assertThat(Fraction(1, 3).divFrac(Fraction(2,3))).isEqualTo(Fraction(1,2))
  }

  @Test
  fun fractionDiv() {
    assertThat(Fraction(1, 2).div(3)).isEqualTo(Fraction(1,6))
    assertThat(Fraction(2, 3).div(2)).isEqualTo(Fraction(1,3))
    assertThat(Fraction(10, 15).div(3)).isEqualTo(Fraction(2,9))
  }

  @Test
  fun fractionLeq() {
    assertThat(Fraction(1, 2).leq(Fraction(1,2))).isEqualTo(true)
    assertThat(Fraction(1, 3).leq(Fraction(1,2))).isEqualTo(true)
    assertThat(Fraction(1, 2).leq(Fraction(1,3))).isEqualTo(false)

  }

  @Test
  fun fractionGeq() {
    assertThat(Fraction(1, 2).geq(Fraction(1,2))).isEqualTo(true)
    assertThat(Fraction(1, 3).geq(Fraction(1,2))).isEqualTo(false)
    assertThat(Fraction(1, 2).geq(Fraction(1,3))).isEqualTo(true)
  }

  @Test
  fun fractionMax() {
    assertThat(Fraction(1, 2).max(Fraction(1,2))).isEqualTo(Fraction(1,2))
    assertThat(Fraction(1, 3).max(Fraction(1,2))).isEqualTo(Fraction(1,2))
    assertThat(Fraction(1, 2).max(Fraction(1,3))).isEqualTo(Fraction(1,2))
  }

  @Test
  fun fractionMin() {
    assertThat(Fraction(1, 2).min(Fraction(1,2))).isEqualTo(Fraction(1,2))
    assertThat(Fraction(1, 3).min(Fraction(1,2))).isEqualTo(Fraction(1,3))
    assertThat(Fraction(1, 2).min(Fraction(1,3))).isEqualTo(Fraction(1,3))
  }

  @Test
  fun vertexAdding() {
    assertThat(Vertex(1,1).add(Vertex(2,2))).isEqualTo(Vertex(3,3))
    assertThat(Vertex(20,1).add(Vertex(2,30))).isEqualTo(Vertex(22,31))
    assertThat(Vertex(10,-10).add(Vertex(-11,11))).isEqualTo(Vertex(-1,1))
  }

  @Test
  fun vertexDiv() {
    assertThat(Vertex(1, 1).div(2)).isEqualTo(Vertex(Fraction(1, 2), Fraction(1, 2)))
    assertThat(Vertex(Fraction(1,7), Fraction(1,3)).div(5)).isEqualTo(Vertex(Fraction(1, 35), Fraction(1, 15)))
  }

  @Test
  fun vertexReflect(){
    assertThat(Vertex(0, 0).reflect(Edge(Vertex(0,1), Vertex(1,0)))).isEqualTo(Vertex(1,1))
    assertThat(Vertex(1, 1).reflect(Edge(Vertex(0,1), Vertex(1,0)))).isEqualTo(Vertex(0,0))
    assertThat(Vertex(1, 1).reflect(Edge(Vertex(1,0), Vertex(0,1)))).isEqualTo(Vertex(0,0))
    assertThat(Vertex(1, 1).reflect(Edge(Vertex(2,0), Vertex(0,2)))).isEqualTo(Vertex(1,1))
    assertThat(Vertex(2, 2).reflect(Edge(Vertex(0,0), Vertex(0,2)))).isEqualTo(Vertex(-2,2))
    assertThat(Vertex(2, 2).reflect(Edge(Vertex(0,0), Vertex(0,7)))).isEqualTo(Vertex(-2,2))
    assertThat(Vertex(1, 2).reflect(Edge(Vertex(0,4), Vertex(4,0)))).isEqualTo(Vertex(2,3))
    assertThat(Vertex(5, 2).reflect(Edge(Vertex(3,0), Vertex(3,3)))).isEqualTo(Vertex(1,2))
  }

  @Test
  fun relativeVector() {
    assertThat(Vertex(1, 1).relativeVector(Vertex(0, 0))).isEqualTo(Vector(1.0, 1.0))
    assertThat(Vertex(2, 2).relativeVector(Vertex(1, 1))).isEqualTo(Vector(1.0, 1.0))
    assertThat(Vertex(Fraction(1, 2), Fraction(1, 2)).relativeVector(Vertex(1, 1))).isEqualTo(Vector(-0.5, -0.5))
  }

  @Test
  fun hasPoint() {
    assertThat(Edge(Vertex(0, 0), Vertex(0, 1)).hasPoint(Vertex(Fraction(0), Fraction(1, 2)))).isTrue()
    assertThat(Edge(Vertex(0, 1), Vertex(0, 0)).hasPoint(Vertex(Fraction(0), Fraction(1, 2)))).isTrue()

    assertThat(Edge(Vertex(2, 0), Vertex(5, 0)).hasPoint(Vertex(Fraction(5, 2), Fraction(0)))).isTrue()
    assertThat(Edge(Vertex(5, 0), Vertex(2, 0)).hasPoint(Vertex(Fraction(5, 2), Fraction(0)))).isTrue()

    assertThat(Edge(Vertex(1, 1), Vertex(2, 2)).hasPoint(Vertex(Fraction(3, 2), Fraction(3, 2)))).isTrue()
    assertThat(Edge(Vertex(2, 2), Vertex(1, 1)).hasPoint(Vertex(Fraction(3, 2), Fraction(3, 2)))).isTrue()
    assertThat(Edge(Vertex(-1, -1), Vertex(2, 2)).hasPoint(Vertex(Fraction(-1, 2), Fraction(-1, 2)))).isTrue()
    assertThat(Edge(Vertex(-2, -2), Vertex(2, 2)).hasPoint(Vertex(Fraction(-1, 2), Fraction(-1, 2)))).isTrue()

    assertThat(Edge(Vertex(0, 0), Vertex(0, 1)).hasPoint(Vertex(0, 2))).isFalse()
    assertThat(Edge(Vertex(0, 1), Vertex(0, 0)).hasPoint(Vertex(0, 2))).isFalse()

    assertThat(Edge(Vertex(0, 0), Vertex(0, 1)).hasPoint(Vertex(1, 0))).isFalse()
    assertThat(Edge(Vertex(0, 0), Vertex(0, 1)).hasPoint(Vertex(Fraction(1, 2), Fraction(1, 2)))).isFalse()
    assertThat(Edge(Vertex(0, 1), Vertex(1, 1)).hasPoint(Vertex(0, 0))).isFalse()
    assertThat(Edge(Vertex(0, 1), Vertex(1, 1)).hasPoint(Vertex(1, 0))).isFalse()
  }

  @Test
  fun comparisons() {
    assertThat(Fraction(1, 56).geq(Fraction(0))).isTrue()
    assertThat(Fraction(1, 56).leq(Fraction(1, 8))).isTrue()
    assertThat(Fraction(1, 7).geq(Fraction(1, 8))).isTrue()
    assertThat(Fraction(1, 7).leq(Fraction(1, 4))).isTrue()
    assertThat(Fraction(1, 7).leq(Fraction(2, 8))).isTrue()

    assertThat(Fraction(2, 8).equals(Fraction(1, 4))).isTrue()
  }

  @Test
  fun convexIn() {
    val p = Polygon(arrayListOf(Vertex(0, 0), Vertex(0, 1), Vertex(1, 1), Vertex(1, 0)))

    assertThat(p.convexIn(Vertex(Fraction(1, 2), Fraction(1, 2)))).isTrue()
    assertThat(p.convexIn(Vertex(Fraction(0), Fraction(1, 2)))).isTrue()
    assertThat(p.convexIn(Vertex(0, 0))).isTrue()
    assertThat(p.convexIn(Vertex(1, 1))).isTrue()

    assertThat(p.convexIn(Vertex(Fraction(0), Fraction(1, 2)), false)).isFalse()
    assertThat(p.convexIn(Vertex(1, 1), false)).isFalse()
    assertThat(p.convexIn(Vertex(0, 0), false)).isFalse()

    assertThat(p.convexIn(Vertex(-1, -1))).isFalse()
    assertThat(p.convexIn(Vertex(2, 2))).isFalse()
    assertThat(p.convexIn(Vertex(Fraction(0), Fraction(3, 2)))).isFalse()
    assertThat(p.convexIn(Vertex(Fraction(1), Fraction(3, 2)))).isFalse()
    assertThat(p.convexIn(Vertex(Fraction(0), Fraction(-3, 2)))).isFalse()
    assertThat(p.convexIn(Vertex(Fraction(1), Fraction(-3, 2)))).isFalse()
  }

  @Test
  fun transformations() {
    val reflect = ReflectTransform(Edge(Vertex(0, 0), Vertex(1, 1)))
    val point = Vertex(Fraction(123, 124), Fraction(5, 4))
    assertThat(reflect.apply(reflect.apply(point)))
        .isEqualTo(point)
        .isEqualTo(reflect.reverse(reflect.apply(point)))

    val translate = TranslateTransform(Vertex(1, 1))
    assertThat(translate.reverse(translate.apply(point))).isEqualTo(point)

    val compose = TranslateTransform(Vertex(1, 1)).compose(reflect)
    assertThat(compose.reverse(compose.apply(point))).isEqualTo(point)
  }

}
