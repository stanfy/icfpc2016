package icfp16.folder

import icfp16.data.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.assertj.core.api.*;


class SimpleFolderTest {

  val vertices = arrayListOf(Vertex(0, 0), Vertex(0, 1), Vertex(1, 1), Vertex(1, 0))
  val poly = Polygon(vertices)

  @Test
  fun lineSideTest(){
    val edge = Edge(Vertex(0, 0), Vertex(0, 1))
    assert(Vertex(0,2).sideOf(edge) == LineSide.ON)
    assert(Vertex(-1,2).sideOf(edge) == LineSide.LEFT)
    assert(Vertex(1,2).sideOf(edge) == LineSide.RIGHT)

  }

  @Test
  fun edgeCrossTest(){
    val edge1 = Edge(Vertex(0, 0), Vertex(1, 1))
    val edge2 = Edge(Vertex(1, 0), Vertex(0, 1))
    assertThat(edge1.cross(edge2)).isEqualTo(Vertex(Fraction(1,2), Fraction(1,2)))

    val edge3 = Edge(Vertex(0, 0), Vertex(0, 1))
    val edge4 = Edge(Vertex(1, 0), Vertex(1, 1))
    assertThat(edge3.cross(edge4)).isEqualTo(null)

    val edge5 = Edge(Vertex(0, 0), Vertex(1, 1))
    val edge6 = Edge(Vertex(3, 1), Vertex(4, 0))
    assertThat(edge5.cross(edge6)).isEqualTo(null)

  }

  @Test
  fun polygonFoldWithoutCross() {


    val edge = Edge(Vertex(0, 2), Vertex(1, 3))
    val res = poly.fold(edge)
    assertThat(res.count() == 1)

    assert(res[0].vertices.count() == 4)
    assert(res[0].edges().count() == 4)
  }

  @Test
  fun polygonFoldWithHorizontalCross() {

    val edge = Edge(Vertex(Fraction(1,2), Fraction(0)), Vertex(Fraction(1,2), Fraction(1)))
    val res = poly.fold(edge)
    assert(res.count() == 2)

    //assertThat(res[0].vertices.count() == 4)
    //assertThat(res[0].edges().count() == 4)
  }
}