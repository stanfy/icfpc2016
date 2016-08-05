package icfp16.folder

import icfp16.data.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

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
fun lineCrossTest(){
  val edge1 = Edge(Vertex(0, 1), Vertex(4, 1))
  val edge2 = Edge(Vertex(2, 2), Vertex(0, 0))
  val res = Line(edge1).interection(Line(edge2))
  assertThat(res).isEqualTo(Vertex(1,1))
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

    val edge7 = Edge(Vertex(0, 1), Vertex(4, 1))
    val edge8 = Edge(Vertex(2, 2), Vertex(0, 0))
    val res = edge7.cross(edge8)
    assertThat(res).isEqualTo(Vertex(1,1))

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

  }

  @Test
  fun splitEdgesTest(){
    val triangle = Polygon(arrayListOf(Vertex(0,0), Vertex(4,0), Vertex(2,2)))
    var triangleCutter = Edge(Vertex(0,1), Vertex(4,1))
    val res = splitEdges(triangle, triangleCutter)
    val splitPoly = res.first
    val edgesOnLine = res.second
    // two edges are crossing the line
    assertThat(edgesOnLine.count()).isEqualTo(2)
    // we create two more points in poly, where cross thakes place
    assertThat(splitPoly.count()).isEqualTo(5)
  }
}