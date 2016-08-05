package icfp16.folder

import icfp16.data.*
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class SimpleFolderTest {

  @Test
  fun polygonFoldWithoutCross() {

    val vertices = arrayListOf(Vertex(0, 0), Vertex(0, 1), Vertex(1, 1), Vertex(1, 0))
    val poly = Polygon(vertices)
    val edge = Edge(Vertex(0, 2), Vertex(1, 3))
    val res = poly.fold(edge)
    assertThat(res.count() == 1)

    assertThat(res[0].vertices.count() == 4)
    assertThat(res[0].edges().count() == 4)


   //Assertions.assertThat(problem.poligons[0].maxVertextIn(Direction.LEFT)).isEqualTo(Vertex(Fraction(0), Fraction(0)))
//    Assertions.assertThat(problem.poligons[0].maxVertextIn(Direction.RIGHT)).isEqualTo(Vertex(Fraction(1), Fraction(0)))
//    Assertions.assertThat(problem.poligons[0].maxVertextIn(Direction.TOP)).isEqualTo(Vertex(Fraction(1, 2), Fraction(1, 2)))
//    Assertions.assertThat(problem.poligons[0].maxVertextIn(Direction.BOTTOM)).isEqualTo(Vertex(Fraction(0), Fraction(0)))
  }
}