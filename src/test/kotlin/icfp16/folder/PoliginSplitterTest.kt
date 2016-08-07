package icfp16.folder

import icfp16.data.Edge
import icfp16.data.Fraction
import icfp16.data.Polygon
import icfp16.data.Vertex
import icfp16.state.ComplexState
import icfp16.state.appendName
import org.assertj.core.api.Assertions.assertThat
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
    assertThat(res[0].foldingEdge).isSameAs(res[1].foldingEdge).isSameAs(edge)
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
    assertThat(res[0].foldingEdge).isSameAs(res[1].foldingEdge).isSameAs(edge)
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
    assertThat(res[0].foldingEdge).isSameAs(edge)
  }


  @Test
  fun polygoSimpleFoldWithoutCross() {

    val edge = Edge(Vertex(0, 2), Vertex(1, 3))
    val e1 = Edge(Vertex(0, 0), Vertex(1, 0))
    val e2 = Edge(Vertex(1, 0), Vertex(1, 1))
    val e3 = Edge(Vertex(1, 1), Vertex(0, 1))
    val e4 = Edge(Vertex(0, 1), Vertex(0, 0))
    assertThat(e1.cross(edge)).isEqualTo(null)
    assertThat(e2.cross(edge)).isEqualTo(null)
    assertThat(e3.cross(edge)).isEqualTo(null)
    assertThat(e4.cross(edge)).isEqualTo(null)
    val res = poly.splitSimple(edge)
    assertThat(res.count()).isEqualTo(1)

    assert(res[0].splitted == false)

  }

  @Test
  fun polygonSimpleFoldWithHorizontalCross() {

    val edge = Edge(Vertex(Fraction(1,2), Fraction(0)), Vertex(Fraction(1,2), Fraction(1)))
    val res = poly.splitSimple(edge)
    assert(res.count() == 2)

  }

  @Test
  fun duplicatesProblem() {
    val polygon = Polygon(arrayListOf(
        Vertex(Fraction(7, 8), Fraction(3, 8)),
        Vertex(Fraction(7, 8), Fraction(1)),
        Vertex(Fraction(1), Fraction(1)),
        Vertex(Fraction(1), Fraction(1, 2))
    ))
    val res = polygon.splitSimple(Edge(Vertex(Fraction(7, 8), Fraction(1)), Vertex(Fraction(1), Fraction(1, 2))))
    res.forEach {
      assertThat(it.polygon.vertices.distinct()).isEqualTo(it.polygon.vertices)
    }
  }

  @Test
  fun noCross() {
    val polygon = Polygon(
        arrayListOf( Vertex(Fraction(-1,4), Fraction(3,8)),
            Vertex(Fraction(1,8), Fraction(3,8)),
            Vertex(Fraction(0), Fraction(1,2)),
            Vertex(Fraction(-1,8), Fraction(1,2))))
    val edge = Edge(
        Vertex(Fraction(0,8), Fraction(1, 8)),
        Vertex(Fraction(1,8), Fraction(0, 8)))

    val res = polygon.splitSimple(edge)
    assertThat(res).hasSize(1)
    assertThat(res[0].polygon).isEqualTo(polygon)
  }

  @Test
  fun crossProblem() {
    val le = LinkedEdge(Vertex(Fraction(-1, 4), Fraction(3, 8)), Vertex(Fraction(1, 8), Fraction(3, 8)))
    assertThat(le.crosses(Edge(Vertex(Fraction(0), Fraction(1, 8)), Vertex(Fraction(1, 8), Fraction(0))))).isNull()
  }

  @Test
  fun crossProblem2() {
    val e = Edge(Vertex(0, 0), Vertex(Fraction(1, 8), Fraction(1)))
    assertThat(e.cross(Edge(Vertex(Fraction(0), Fraction(1, 8)), Vertex(Fraction(1, 8), Fraction(2, 8)))))
      .isNotNull()
  }

  @Test
  fun badSplit() {
    val polygon = Polygon(
        arrayListOf( Vertex(Fraction(1,8), Fraction(8)),
            Vertex(Fraction(0), Fraction(1)),
            Vertex(Fraction(0), Fraction(0)),
            Vertex(Fraction(1,8), Fraction(1))))

    val edge = Edge(
        Vertex(Fraction(1,8), Fraction(2, 8)),
        Vertex(Fraction(0,8), Fraction(1, 8)))

    val res = polygon.splitSimple(edge)
    assertThat(res.distinct()).isEqualTo(res)
  }

  @Test
  fun badSplit2() {
    val state = ComplexState()
        .fold(Edge(
            Vertex(Fraction(1,2), Fraction(1, 1)),
            Vertex(Fraction(1,2), Fraction(0, 1))
        ))
        .fold(Edge(
            Vertex(Fraction(1,4), Fraction(1, 1)),
            Vertex(Fraction(1,4), Fraction(0, 1))
        ))
        .fold(Edge(
            Vertex(Fraction(1,8), Fraction(1, 1)),
            Vertex(Fraction(1,8), Fraction(0, 1))
        ))

        .fold(Edge(
            Vertex(Fraction(1,8), Fraction(2, 8)),
            Vertex(Fraction(0,8), Fraction(1, 8))
        ))

        .fold(Edge(
            Vertex(Fraction(0,8), Fraction(4, 8)),
            Vertex(Fraction(1,8), Fraction(3, 8))
        ))
        .appendName("Okolobubl")

    val edge = Edge(
        Vertex(Fraction(-1,4), Fraction(3, 8)),
        Vertex(Fraction(-1,8), Fraction(1, 2))
    )

    val cState = state as ComplexState
    val triangles = cState.polys.filter { it.splitSimple(edge).size == 2 }
        .filter { it.splitSimple(edge).any { it.initial().vertices.size == 3 } }
    triangles.forEach { // DEBUG them!
      it.splitSimple(edge, true)
    }
    assertThat(triangles).isEmpty()
  }

  @Test
  fun ratioUseBug() {
    val e = Edge(Vertex(Fraction(1, 4), Fraction(1, 2)), Vertex(Fraction(1, 4), Fraction(1)))
    val ratioX = Fraction(1, 4)
    val ratioY = Fraction(1)
    assertThat(e.findSplitPoint(ratioY, ratioX)).isEqualTo(Vertex(Fraction(1, 4), Fraction(5, 8)))
  }

}
