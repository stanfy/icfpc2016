package icfp16.state

import org.junit.Assert.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.junit.Test

class PaulPublicStatesTest {
  @Test
  fun solutionVertexes() {
    PaulPublicStates.lineSolution5.polys.zip(PaulPublicStates.lineSolution(5).polys).forEach {
      assertThat(it.first).isEqualTo(it.second)
    }
    assertThat(PaulPublicStates.lineSolution5).isEqualTo(PaulPublicStates.lineSolution(5))
  }

}