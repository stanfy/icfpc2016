package icfp16.utils

import icfp16.api.parseProblem
import icfp16.data.ComplexPolygon
import icfp16.data.Fraction
import icfp16.data.TRANSFORM_IDENTITY
import icfp16.data.Vertex
import icfp16.state.ComplexState
import icfp16.visualizer.Visualizer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test

class PolygonsComposerTest {

  @Test
  fun itShouldFind1PolygonForSquare() {
    val rawProblem = """
1
4
0,0
1,0
1,1
0,1
4
0,0 1,0
0,0 0,1
1,0 1,1
0,1 1,1
    """

    val problem = parseProblem(rawProblem)

    val polygons = PolygonComposer().polygonsFromProblem(problem)

    assertThat(polygons.count()).isEqualTo(1)

    val p = polygons.first()
    assertThat(p.area()).isEqualTo(1.0)
    assertThat(p.vertices.count()).isEqualTo(4)

    assertThat(p.vertices.containsAll(listOf(
        Vertex(0, 0), Vertex(1, 0),
        Vertex(1, 0), Vertex(1, 1),
        Vertex(1, 1), Vertex(0, 1),
        Vertex(0, 1), Vertex(0, 0)
    )))
  }

  @Test
  fun itShouldFind3PolygonsForTask158() {
    val rawProblem = """
1
5
0,0
1,0
1,1/2
1/2,1
0,1
7
0,0 1,0
0,0 0,1
1/2,1/2 1/2,1
1,0 1,1/2
1/2,1/2 1,1/2
1,1/2 1/2,1
0,1 1/2,1
    """
    val problem = parseProblem(rawProblem)
    val polygons = PolygonComposer().polygonsFromProblem(problem)

    assertThat(polygons.count()).isEqualTo(3)

    val p0 = polygons[0]
    assertThat(p0.vertices.count()).isEqualTo(3)

    assertThat(listOf(
        Vertex(Fraction(1, 2), Fraction(1, 2)),
        Vertex(Fraction(1), Fraction(1, 2)),
        Vertex(Fraction(1, 2), Fraction(1))
    )).containsAll(p0.vertices)

    val p1 = polygons[1]
    assertThat(p1.vertices.count()).isEqualTo(6)

    assertThat(listOf(
        Vertex(Fraction(0), Fraction(0)),
        Vertex(Fraction(0), Fraction(1)),
        Vertex(Fraction(1, 2), Fraction(1)),
        Vertex(Fraction(1, 2), Fraction(1, 2)),
        Vertex(Fraction(1), Fraction(1, 2)),
        Vertex(Fraction(1), Fraction(0))

    )).containsAll(p1.vertices)

    val p2 = polygons[2]
    assertThat(p2.vertices.count()).isEqualTo(5)

    assertThat(listOf(
        Vertex(Fraction(0), Fraction(0)),
        Vertex(Fraction(0), Fraction(1)),
        Vertex(Fraction(1, 2), Fraction(1)),
        Vertex(Fraction(1), Fraction(1, 2)),
        Vertex(Fraction(1), Fraction(0))
    )).containsAll(p2.vertices)
  }

  @Test
  @Ignore
  fun itShouldFind15PolygonsForTask158() {
    val rawProblem = """
1
9
3/5,-1/5
3/4,0
1,0
1,1/3
0,2/3
-1/5,2/5
0,1/4
0,0
1/3,0
7
3/5,-1/5 1,1/3
0,0 1,0
0,0 0,2/3
1,0 1,1/3
3/5,-1/5 -1/5,2/5
-1/5,2/5 0,2/3
1,1/3 0,2/3

    """
    val problem = parseProblem(rawProblem)
    val polygons = PolygonComposer().polygonsFromProblem(problem)


    assertThat(polygons.count()).isEqualTo(3)

    // FIXME: It's nit identity transform, right?
    Visualizer().visualizedAndSaveFolds(ComplexState(arrayOf(ComplexPolygon(TRANSFORM_IDENTITY, polygons.flatMap { it.vertices }))), filePath = "tmp/folds0.png")
    Visualizer().visualizedAndSaveFolds(ComplexState(arrayOf(ComplexPolygon(TRANSFORM_IDENTITY, polygons.flatMap { it.vertices }))), filePath = "tmp/folds1.png")
    Visualizer().visualizedAndSaveFolds(ComplexState(arrayOf(ComplexPolygon(TRANSFORM_IDENTITY, polygons.flatMap { it.vertices }))), filePath = "tmp/folds2.png")

    val p0 = polygons[0]
    assertThat(p0.vertices.count()).isEqualTo(3)

    assertThat(listOf(
        Vertex(Fraction(1, 2), Fraction(1, 2)),
        Vertex(Fraction(1), Fraction(1, 2)),
        Vertex(Fraction(1, 2), Fraction(1))
    )).containsAll(p0.vertices)

    val p1 = polygons[1]
    assertThat(p1.vertices.count()).isEqualTo(6)

    assertThat(listOf(
        Vertex(Fraction(0), Fraction(0)),
        Vertex(Fraction(0), Fraction(1)),
        Vertex(Fraction(1, 2), Fraction(1)),
        Vertex(Fraction(1, 2), Fraction(1, 2)),
        Vertex(Fraction(1), Fraction(1, 2)),
        Vertex(Fraction(1), Fraction(0))

    )).containsAll(p1.vertices)

    val p2 = polygons[2]
    assertThat(p2.vertices.count()).isEqualTo(5)

    assertThat(listOf(
        Vertex(Fraction(0), Fraction(0)),
        Vertex(Fraction(0), Fraction(1)),
        Vertex(Fraction(1, 2), Fraction(1)),
        Vertex(Fraction(1), Fraction(1, 2)),
        Vertex(Fraction(1), Fraction(0))
    )).containsAll(p2.vertices)
  }

//  IDs: 12, 15, 158, 14

}
