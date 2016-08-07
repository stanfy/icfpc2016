package icfp16.folder

import icfp16.api.parseProblem
import icfp16.estimate.BitmapEstimator
import icfp16.io.ProblemContainersParser
import icfp16.solver.SequenceSolver
import icfp16.visualizer.Visualizer

import icfp16.data.*
import icfp16.state.ComplexState
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test

class PoligonJoinerTest{

  @Test
  fun vertexEqualTest(){
    val v1 = Vertex(1,2)
    val v2 = Vertex(1,2)
    val v3 = Vertex(3,3)

    assertThat(v1.eq(v1)).isTrue()
    assertThat(v1.eq(v2)).isTrue()
    assertThat(v1.eq(v3)).isFalse()

    assertThat(v2.eq(v1)).isTrue()
    assertThat(v2.eq(v2)).isTrue()
    assertThat(v2.eq(v3)).isFalse()

    assertThat(v3.eq(v1)).isFalse()
    assertThat(v3.eq(v2)).isFalse()
    assertThat(v3.eq(v3)).isTrue()

  }

  @Test
  fun edgesEqualTest(){
    val e1 = Edge(Vertex(1,2), Vertex(3,5))
    val e2 = Edge(Vertex(3,5),Vertex(1,2))
    val e3 = Edge(Vertex(3,3),Vertex(4,4))
    val e4 = Edge(Vertex(4,4),Vertex(3,3))

    assertThat(e1.eq(e1)).isTrue()
    assertThat(e1.eq(e2)).isTrue()
    assertThat(e1.eq(e3)).isFalse()
    assertThat(e1.eq(e4)).isFalse()

    assertThat(e2.eq(e1)).isTrue()
    assertThat(e2.eq(e2)).isTrue()
    assertThat(e2.eq(e3)).isFalse()
    assertThat(e2.eq(e4)).isFalse()

    assertThat(e3.eq(e1)).isFalse()
    assertThat(e3.eq(e2)).isFalse()
    assertThat(e3.eq(e3)).isTrue()
    assertThat(e3.eq(e4)).isTrue()

    assertThat(e4.eq(e1)).isFalse()
    assertThat(e4.eq(e2)).isFalse()
    assertThat(e4.eq(e3)).isTrue()
    assertThat(e4.eq(e4)).isTrue()

  }

  @Test
  fun linkedEdgesEqualTest(){
    val e1 = LinkedEdge(Vertex(1,2), Vertex(3,5))
    val e2 = LinkedEdge(Vertex(3,5),Vertex(1,2))
    val e3 = LinkedEdge(Vertex(3,3),Vertex(4,4))
    val e4 = LinkedEdge(Vertex(4,4),Vertex(3,3))

    assertThat(e1.eq(e1)).isTrue()
    assertThat(e1.eq(e2)).isTrue()
    assertThat(e1.eq(e3)).isFalse()
    assertThat(e1.eq(e4)).isFalse()

    assertThat(e2.eq(e1)).isTrue()
    assertThat(e2.eq(e2)).isTrue()
    assertThat(e2.eq(e3)).isFalse()
    assertThat(e2.eq(e4)).isFalse()

    assertThat(e3.eq(e1)).isFalse()
    assertThat(e3.eq(e2)).isFalse()
    assertThat(e3.eq(e3)).isTrue()
    assertThat(e3.eq(e4)).isTrue()

    assertThat(e4.eq(e1)).isFalse()
    assertThat(e4.eq(e2)).isFalse()
    assertThat(e4.eq(e3)).isTrue()
    assertThat(e4.eq(e4)).isTrue()

  }

  @Test
  fun triangleJoinerTest()
  {

    val a = Vertex(0, 0)
    val b = Vertex(2, 2)
    val c = Vertex(4, 0)
    val d = Vertex(1, -3)
    val clockwise =  Polygon(arrayListOf(b, c, d,a))
    val unclock = Polygon(arrayListOf(b, a, d,c))
  // second if fixed, first changes
   assertThat(Polygon(arrayListOf(a, b, c)).join(Polygon(arrayListOf(a, d, c))))
          .isEqualTo(clockwise)

    assertThat(Polygon(arrayListOf(a, c, b)).join(Polygon(arrayListOf(a, d, c))))
        .isEqualTo(unclock)

    assertThat(Polygon(arrayListOf(b, a, c)).join(Polygon(arrayListOf(a, d, c))))
        .isEqualTo(unclock)

    assertThat(Polygon(arrayListOf(b, c, a)).join(Polygon(arrayListOf(a, d, c))))
        .isEqualTo(clockwise)


    assertThat(Polygon(arrayListOf(c, a, b)).join(Polygon(arrayListOf(a, d, c))))
        .isEqualTo(clockwise)

    assertThat(Polygon(arrayListOf(c, b, a)).join(Polygon(arrayListOf(a, d, c))))
        .isEqualTo(unclock)

    /// first is fixed, second changes
    assertThat(Polygon(arrayListOf(a, b, c)).join(Polygon(arrayListOf(a, c, d))))
        .isEqualTo(clockwise)

    assertThat(Polygon(arrayListOf(a, b, c)).join(Polygon(arrayListOf(a, d, c))))
        .isEqualTo(clockwise)

    assertThat(Polygon(arrayListOf(a, b, c)).join(Polygon(arrayListOf(d, c, a))))
        .isEqualTo(clockwise)

    assertThat(Polygon(arrayListOf(a, b, c)).join(Polygon(arrayListOf(d, a, c))))
        .isEqualTo(clockwise)

    assertThat(Polygon(arrayListOf(a, b, c)).join(Polygon(arrayListOf(c, d, a))))
        .isEqualTo(clockwise)

    assertThat(Polygon(arrayListOf(a, b, c)).join(Polygon(arrayListOf(c, a, d))))
        .isEqualTo(clockwise)

  }


}