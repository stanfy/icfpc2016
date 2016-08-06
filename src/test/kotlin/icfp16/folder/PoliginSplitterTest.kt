package icfp16.folder

import icfp16.data.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test


class PoliginSplitterTest {

  val vertices = arrayListOf(Vertex(0, 0), Vertex(0, 1), Vertex(1, 1), Vertex(1, 0))
  val poly = Polygon(vertices)


  @Test
  fun polygonToLinkedEdges(){
    val res = poly.toLindedEdges()
    assertThat(res.count()).isEqualTo(4)
    assertThat(res.first().Prev).isEqualTo(res.last())
    assertThat(res.last().Next).isEqualTo(res.first())
    assertThat(res[2].Prev).isEqualTo(res[1])
    assertThat(res[2].Next).isEqualTo(res[3])
  }

  @Test
  fun ploygonSplitSquareSimple() {
    val poly = Polygon(arrayListOf(Vertex(0, 0), Vertex(1, 0), Vertex(1, 1), Vertex(0, 1)))
    val edge = Edge(Vertex(Fraction(0), Fraction(1, 2)), Vertex(Fraction(1), Fraction(1, 2)))
    val res = poly.splitSimple(edge)
    assertThat(res.map { it.polygon })
        .containsExactly(
            Polygon(arrayListOf(Vertex(Fraction(1), Fraction(1, 2)), Vertex(1, 1), Vertex(0, 1),Vertex(Fraction(0), Fraction(1, 2)))),
            Polygon(arrayListOf(Vertex(Fraction(0), Fraction(1, 2)),Vertex(0, 0), Vertex(1, 0),Vertex(Fraction(1), Fraction(1, 2))))
        )
    assertThat(res.count()).isEqualTo(2)
    assertThat(res[0].splitted).isEqualTo(true)
    assertThat(res[1].splitted).isEqualTo(true)
    assertThat(res[0].crossVertex).isEqualTo(Vertex(Fraction(1), Fraction(1, 2)))
    assertThat(res[1].crossVertex).isEqualTo(Vertex(Fraction(0), Fraction(1, 2)))
    assertThat(res[0].crossEdge).isEqualTo(Edge(Vertex(1,0), Vertex(1, 1)))
    assertThat(res[1].crossEdge).isEqualTo(Edge(Vertex(0,1), Vertex(0, 0)))
    assertThat(res[0].edgeIndex).isEqualTo(1)
    assertThat(res[1].edgeIndex).isEqualTo(3)
    // DIstance is non euqlidian !! to get euqliidan one you should make sqrt, but sqrt doesn't work on fractions
    assertThat(res[0].xRatio).isEqualTo(Fraction(1))
    assertThat(res[0].yRatio).isEqualTo(Fraction(1,2))
    assertThat(res[1].xRatio).isEqualTo(Fraction(1))
    assertThat(res[1].yRatio).isEqualTo(Fraction(1,2))

  }


  @Test
  fun edgeSplitTest(){

    val ratioX = Fraction(3,4)
    val ratioY = Fraction(3,4)

    assertThat(Edge(Vertex(0,0), Vertex(8,8)).findSplitPoint(ratioX, ratioY)).isEqualTo(Vertex(6,6))
    assertThat(Edge(Vertex(0,0), Vertex(0,8)).findSplitPoint(ratioX, ratioY)).isEqualTo(Vertex(0,6))
    assertThat(Edge(Vertex(0,0), Vertex(8,4)).findSplitPoint(ratioX, ratioY)).isEqualTo(Vertex(6,3))
    assertThat(Edge(Vertex(1,1), Vertex(9,5)).findSplitPoint(ratioX, ratioY)).isEqualTo(Vertex(7,4))
    assertThat(Edge(Vertex(0,0), Vertex(-8,-8)).findSplitPoint(ratioX, ratioY)).isEqualTo(Vertex(-6,-6))

  }
  @Test
  fun ploygonSplitTriangleSimple() {
    val poly = Polygon(arrayListOf(Vertex(0, 0), Vertex(2, 2), Vertex(4, 0)))
    val edge = Edge(Vertex(0,1), Vertex(4,1))
    val res = poly.splitSimple(edge)
    assertThat(res.map { it.polygon })
        .containsExactly(
            Polygon(arrayListOf(Vertex(1,1), Vertex(2, 2), Vertex(3, 1))),
            Polygon(arrayListOf(Vertex(3,1), Vertex(4, 0), Vertex(0, 0), Vertex(1,1)))
        )
    assertThat(res.count()).isEqualTo(2)
    assertThat(res[0].splitted).isEqualTo(true)
    assertThat(res[1].splitted).isEqualTo(true)
    assertThat(res[0].crossVertex).isEqualTo(Vertex(1,1))
    assertThat(res[1].crossVertex).isEqualTo(Vertex(3,1))
    assertThat(res[0].crossEdge).isEqualTo(Edge(Vertex(0,0), Vertex(2, 2)))
    assertThat(res[1].crossEdge).isEqualTo(Edge(Vertex(2,2), Vertex(4, 0)))
    assertThat(res[0].xRatio).isEqualTo(Fraction(1,2))
    assertThat(res[0].yRatio).isEqualTo(Fraction(1,2))
    assertThat(res[1].xRatio).isEqualTo(Fraction(1,2))
    assertThat(res[1].yRatio).isEqualTo(Fraction(1,2))
  }

  @Test
  fun ploygonSplitWithoutCrossSimple() {
    val poly = Polygon(arrayListOf(Vertex(0, 0), Vertex(2, 2), Vertex(4, 0)))
    val edge = Edge(Vertex(0,5), Vertex(4,5))
    val res = poly.splitSimple(edge)
    assertThat(res.map { it.polygon })
        .containsExactly(
            Polygon(arrayListOf(Vertex(0, 0), Vertex(2, 2), Vertex(4, 0)))
        )
    assertThat(res.count()).isEqualTo(1)
    assertThat(res[0].splitted).isEqualTo(false)
    assertThat(res[0].crossVertex).isEqualTo(null)
    assertThat(res[0].crossEdge).isEqualTo(null)
    assertThat(res[0].xRatio).isEqualTo(null)
    assertThat(res[0].yRatio).isEqualTo(null)
  }


  @Test
  fun polygoSimpleFoldWithoutCross() {

    val edge = Edge(Vertex(0, 2), Vertex(1, 3))
    val res = poly.foldSimple(edge)
    assertThat(res.count() == 1)

    assert(res[0].vertices.count() == 4)
    assert(res[0].edges().count() == 4)
  }

  @Test
  @Ignore
  fun polygonSimpleFoldWithHorizontalCross() {

    val edge = Edge(Vertex(Fraction(1,2), Fraction(0)), Vertex(Fraction(1,2), Fraction(1)))
    val res = poly.foldSimple(edge)
    assert(res.count() == 2)

  }
}
