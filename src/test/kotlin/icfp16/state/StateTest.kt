package icfp16.state

import icfp16.Facet
import icfp16.Fraction
import icfp16.Vertex

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigInteger

class StateTest {
  @Test
  fun solutionVertexes() {
    val state = State(vertexes = arrayOf(
        Vertex(Fraction(1), Fraction(1)),
        Vertex(Fraction(1, 2), Fraction(2, 3))
    )
    )
    assertThat(state.solution()).isEqualTo(
"""
2
1,1
1/2,2/3
0
1,1
1/2,2/3
""".trim())
  }

  @Test
  fun solutionFacets() {
    val state = State(facets = arrayOf(
        Facet(listOf(1, 2, 3, 4)),
        Facet(listOf(5, 4, 3, 2, 1)))
    )
    assertThat(state.solution()).isEqualTo(
 """
0
2
4 1 2 3 4
5 5 4 3 2 1
""".trim())
  }

  @Test
  fun solutionEverything() {
    val state = State(
        vertexes = arrayOf(
            Vertex(Fraction(1), Fraction(1)),
            Vertex(Fraction(1, 2), Fraction(2, 3))
        ),

        facets = arrayOf(
            Facet(listOf(1, 2, 3, 4)),
            Facet(listOf(5, 4, 3, 2, 1))),

        finalPositions = arrayOf(
            Vertex(Fraction(5), Fraction(6)),
            Vertex(Fraction(12, 2), Fraction(13, 3))
        )

    )
    assertThat(state.solution()).isEqualTo(
        """
2
1,1
1/2,2/3
2
4 1 2 3 4
5 5 4 3 2 1
5,6
12/2,13/3
""".trim())
  }

  @Test
  fun solutionInitialSquare() {
    val state = State.initialSquare()
    assertThat(state.solution()).isEqualTo(
        """
4
0,0
0,1
1,1
1,0
1
4 0 1 2 3
0,0
0,1
1,1
1,0
""".trim())
  }

  @Test
  fun solutionTask2() {
    ///  This is a simple example of rectangle trasformed tvice
    val state = State(vertexes = arrayOf(
        Vertex(Fraction(0), Fraction(0)),
        Vertex(Fraction(0), Fraction(1)),
        Vertex(Fraction(1), Fraction(1)),
        Vertex(Fraction(1), Fraction(0))
    ),
        facets = arrayOf(
            Facet(listOf(0,1,2,3))
        ),
        finalPositions = arrayOf(
            Vertex(Fraction(2), Fraction(2)),
            Vertex(Fraction(1), Fraction(2)),
            Vertex(Fraction(2), Fraction(1)),
            Vertex(Fraction(1), Fraction(1))
        )
    )

    assertThat(state.solution()).isEqualTo(
        """4
0,0
0,1
1,1
1,0
1
4 0 1 2 3
2,2
1,2
2,1
1,1
""".trim())
  }


  @Test
  fun solutionTranslate() {
    ///  This is a simple example of rectangle transformed twice
    val originalState = State.initialSquare()

    val translatedState = originalState.translate(Vertex(23, 27))

    assertThat(originalState.vertexes).isEqualTo(translatedState.vertexes)
    assertThat(originalState.facets).isEqualTo(translatedState.facets)
    assertThat(originalState.finalPositions).isNotEqualTo(translatedState.finalPositions)
    val expectedFinalPositions = originalState.finalPositions.map {
      val translatedX = Fraction(it.x.a.add(it.x.b.multiply(BigInteger("23"))), it.x.b)
      val translatedY = Fraction(it.y.a.add(it.y.b.multiply(BigInteger("27"))), it.y.b)
      Vertex(translatedX, translatedY)
    }.toTypedArray()
    assertThat(expectedFinalPositions).isEqualTo(translatedState.finalPositions)

  }

}