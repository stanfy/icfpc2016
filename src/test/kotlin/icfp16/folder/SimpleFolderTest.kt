package icfp16.folder

import icfp16.data.Edge
import icfp16.data.Direction
import icfp16.data.Fraction
import icfp16.data.Vertex
import org.assertj.core.api.Assertions
import org.junit.Test


@Test
fun perimeterPoints() {

    val edge = Edge(Vertex(0,0), Vertex(1,1))
    val folded = edge.fold(Edge(Vertex(0,1),Vertex(1,0)))




//    Assertions.assertThat(problem.poligons[0].maxVertextIn(Direction.LEFT)).isEqualTo(Vertex(Fraction(0), Fraction(0)))
//    Assertions.assertThat(problem.poligons[0].maxVertextIn(Direction.RIGHT)).isEqualTo(Vertex(Fraction(1), Fraction(0)))
//    Assertions.assertThat(problem.poligons[0].maxVertextIn(Direction.TOP)).isEqualTo(Vertex(Fraction(1, 2), Fraction(1, 2)))
//    Assertions.assertThat(problem.poligons[0].maxVertextIn(Direction.BOTTOM)).isEqualTo(Vertex(Fraction(0), Fraction(0)))
}