package icfp16.folder

import icfp16.data.Edge
import icfp16.folder.SimpleFolder
import icfp16.data.Direction
import icfp16.data.Fraction
import icfp16.data.Vertex
import org.assertj.core.api.Assertions
import org.junit.Test


@Test
fun perimeterPoints() {
    val a = Vertex(Fraction(0), Fraction(0));
    val b = Vertex(Fraction(1), Fraction(1));
    val e = Edge(a,b)
    val folder = SimpleFolder()
    val perpendicular = folder.perp(e);

//    Assertions.assertThat(problem.poligons[0].maxVertextIn(Direction.LEFT)).isEqualTo(Vertex(Fraction(0), Fraction(0)))
//    Assertions.assertThat(problem.poligons[0].maxVertextIn(Direction.RIGHT)).isEqualTo(Vertex(Fraction(1), Fraction(0)))
//    Assertions.assertThat(problem.poligons[0].maxVertextIn(Direction.TOP)).isEqualTo(Vertex(Fraction(1, 2), Fraction(1, 2)))
//    Assertions.assertThat(problem.poligons[0].maxVertextIn(Direction.BOTTOM)).isEqualTo(Vertex(Fraction(0), Fraction(0)))
}