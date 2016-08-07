package icfp16.state

import icfp16.data.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test

class ComplexStateTest {

  fun testComplexPolygon(): ComplexPolygon {
    return ComplexPolygon(
        TRANSFORM_IDENTITY,
        Polygon(listOf(Vertex(1, 1), Vertex(1, 2), Vertex(2, 1)))
    )
  }
  @Test
  fun finalPositionsByDefault() {
    val state = ComplexState(arrayOf(testComplexPolygon()))
    assertThat(state.finalPositions()).isEqualTo(arrayOf(Vertex(1, 1), Vertex(1, 2), Vertex(2, 1)))
  }

  @Test
  fun vertexesByDefault() {
    val state = ComplexState(arrayOf(testComplexPolygon()))
    assertThat(state.vertexes()).isEqualTo(arrayOf(Vertex(1, 1), Vertex(1, 2), Vertex(2, 1)))
  }

  @Test
  fun facets() {
    val state = ComplexState(arrayOf(testComplexPolygon()))
    assertThat(state.facets()).isEqualTo(arrayOf(Facet(listOf(0,1,2))))
  }

  @Test
  fun poligons() {
    val state = ComplexState(arrayOf(testComplexPolygon()))
    assertThat(state.poligons().toList()).isEqualTo(state.polys.map { it.final })
  }

  @Test
  fun initialPoligons() {
    val state = ComplexState(arrayOf(testComplexPolygon()))
    assertThat(state.poligons().toList()).isEqualTo(state.polys.map { it.initial() })
  }

  @Test
  fun translate() {
    val state = ComplexState(arrayOf(testComplexPolygon()))
    val tranlsated = state.translate(Vertex(5,5))

    assertThat(state.initialPoligons()).isEqualTo(tranlsated.initialPoligons())
    assertThat(state.vertexes()).isEqualTo(tranlsated.vertexes())
    assertThat(state.facets()).isEqualTo(tranlsated.facets())
    assertThat(tranlsated.finalPositions()).isEqualTo(arrayOf(Vertex(6, 6), Vertex(6, 7), Vertex(7, 6)))
  }

  @Test
  fun triangle() {
    val state = ComplexState()
        .fold(Edge(
            Vertex(Fraction(1), Fraction(1)),
            Vertex(Fraction(0), Fraction(0))
        ))

    assertThat(state.isValid()).isEqualTo(true)

  }

  @Test
  fun triangle2() {
    val state = ComplexState()
        .fold(Edge(
            Vertex(Fraction(1), Fraction(1)),
            Vertex(Fraction(0), Fraction(0))
        ))
        .fold(Edge(Vertex(Fraction(0), Fraction(1)),
            Vertex(Fraction(0), Fraction(0))
        ))

    assertThat(state.isValid()).isEqualTo(true)

  }

  @Test
  fun triangle3() {
    val state = ComplexState()
        .fold(Edge(
            Vertex(Fraction(1), Fraction(1)),
            Vertex(Fraction(0), Fraction(0))
        ))
        .fold(Edge(Vertex(Fraction(1), Fraction(1)),
            Vertex(Fraction(-1), Fraction(-1))
        ))

    assertThat(state.isValid()).isEqualTo(true)
  }

  @Test
  fun triangle4() {
    val state = ComplexState()
        .fold(Edge(
            Vertex(Fraction(1), Fraction(1)),
            Vertex(Fraction(0), Fraction(0))
        ))
        .fold(Edge(Vertex(Fraction(2), Fraction(1)),
            Vertex(Fraction(-1), Fraction(-2))
        ))

    assertThat(state.isValid()).isEqualTo(true)
  }


  @Test
  fun bublo() {
    val state =
        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1, 2), Fraction(1, 1)),
                Vertex(Fraction(1, 2), Fraction(0, 1))
            ))
            .fold(Edge(
                Vertex(Fraction(1, 4), Fraction(1, 1)),
                Vertex(Fraction(1, 4), Fraction(0, 1))
            ))
            .fold(Edge(
                Vertex(Fraction(1, 8), Fraction(1, 1)),
                Vertex(Fraction(1, 8), Fraction(0, 1))
            ))

            .fold(Edge(
                Vertex(Fraction(1, 8), Fraction(2, 8)),
                Vertex(Fraction(0, 8), Fraction(1, 8))
            ))

            .fold(Edge(
                Vertex(Fraction(0, 8), Fraction(4, 8)),
                Vertex(Fraction(1, 8), Fraction(3, 8))
            ))
            .fold(Edge(
                Vertex(Fraction(0, 8), Fraction(5, 8)),
                Vertex(Fraction(1, 8), Fraction(6, 8))
            ))
            .appendName("Okolobubl")

    assertThat(state.isValid()).isEqualTo(true)
  }


  @Test
  @Ignore
  fun rotate90() {
    // TBD:
  }

  @Test
  @Ignore
  fun rotate180() {
    // TBD:
  }

  @Test
  @Ignore
  fun rotate270() {
    // TBD:
  }

  @Test
  @Ignore
  fun rotate() {
    // TBD:
  }

}