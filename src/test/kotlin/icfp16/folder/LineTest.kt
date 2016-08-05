package icfp16.folder

import icfp16.data.Edge
import icfp16.data.Vertex
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class LineTest {
  @Test
  fun intersectionOfParallelLines() {
    val line = Line(Edge(Vertex(1,1), Vertex(2,2)))
    val line2 = Line(Edge(Vertex(1,1), Vertex(2,2)))
    assertThat(line.interection(line2)).isEqualTo(null)
  }

  @Test
  fun intersectionOfNonParallel() {
    val line = Line(Edge(Vertex(-1,1), Vertex(1,1)))
    val line2 = Line(Edge(Vertex(1,-1), Vertex(1,1)))
    assertThat(line.interection(line2)).isEqualTo(Vertex(1,1))
  }

}