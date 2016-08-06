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
    val triangleCutter = Edge(Vertex(0,1), Vertex(4,1))
    val res = splitEdges(triangle, triangleCutter)
    val splitPoly = res.first
    val edgesOnLine = res.second
    // two edges are crossing the line
    assertThat(edgesOnLine.count()).isEqualTo(2)
    // we create two more points in poly, where cross thakes place
    assertThat(splitPoly.count()).isEqualTo(5)
  }

  @Test
  fun vertexDistanceTest(){
    assertThat(Vertex(2,2).distance(Vertex(5,5))).isEqualTo(Fraction(18))
  }

  @Test
  fun sortEdgesTest(){
    val triangle = Polygon(arrayListOf( Vertex(2,2),Vertex(0,0),Vertex(4,0)))
    val triangleCutter = Edge(Vertex(0,1), Vertex(4,1))
    val res = splitEdges(triangle, triangleCutter)
    val edgesOnLine = res.second
    val sorted = sortEdges(edgesOnLine, triangleCutter)

    assertThat(sorted.count()).isEqualTo(edgesOnLine.count())

  }

  @Test
  fun cycleValidationTest()
  {
    val p1 = PolyEdge(Vertex(1), LineSide.LEFT)
    p1.Next = p1
    p1.Prev = p1
    val list1 = arrayListOf(p1)
    assertThat(cyclesValid(list1)).isEqualTo(true)

    val p2 = PolyEdge(Vertex(2), LineSide.LEFT)
    val p3 = PolyEdge(Vertex(3), LineSide.LEFT)
    p2.Next =p3
    p2.Prev =p3
    p3.Next = p2
    p3.Prev = p2
    val list2 = arrayListOf(p2, p3)
    assertThat(cyclesValid(list2)).isEqualTo(true)

    val p4 = PolyEdge(Vertex(4), LineSide.LEFT)
    val p5 = PolyEdge(Vertex(5), LineSide.LEFT)
    val p6 = PolyEdge(Vertex(6), LineSide.LEFT)
    p4.Next =p5
    p4.Prev =p6
    p5.Next = p6
    p5.Prev = p4
    p6.Next = p4
    p6.Prev = p5
    val list3 = arrayListOf(p4, p5,p6)
    assertThat(cyclesValid(list3)).isEqualTo(true)

    val p7 = PolyEdge(Vertex(7), LineSide.LEFT)
    val p8 = PolyEdge(Vertex(8), LineSide.LEFT)
    val p9 = PolyEdge(Vertex(9), LineSide.LEFT)
    p7.Next =p8
    p7.Prev =p8
    p8.Next = p9
    p8.Prev = p7
    p9.Next = p5
    p9.Prev = p7
    val list4 = arrayListOf(p7, p8,p9)
    assertThat(cyclesValid(list4)).isEqualTo(false)

  }

  @Test
  fun bridgeCreationTest()
  {

  }

  @Test
  fun splitPoly() {

  }
}