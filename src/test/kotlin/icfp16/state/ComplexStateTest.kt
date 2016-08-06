package icfp16.state

import icfp16.data.ComlexPolygon
import icfp16.data.Facet
import icfp16.data.Polygon
import icfp16.data.Vertex
import org.junit.Test
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore

class ComplexStateTest {

  fun testComplexPolygon(): ComlexPolygon {
    return ComlexPolygon(
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
    assertThat(state.poligons().toList()).isEqualTo(state.polys.map { it.initial })
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