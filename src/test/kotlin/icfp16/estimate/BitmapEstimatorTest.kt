package icfp16.estimate

import icfp16.api.parseProblem
import icfp16.state.State
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.Test

class BitmapEstimatorTest {
  @Test
  fun solutionVertexes() {

    val problemString =
        """1
          |4
          |0,3/4
          |1/8,7/8
          |0,1
          |-1/8,7/8
          |5
          |0,3/4 0,1
          |0,3/4 1/8,7/8
          |0,3/4 -1/8,7/8
          |-1/8,7/8 0,1
          |1/8,7/8 0,1
        """.trimMargin()

    val problem = parseProblem(problemString)

    val estimator = BitmapEstimator()
    assertThat(estimator.resemblanceOf(problem, State.initialSquare(), quality = 4)).isCloseTo(0.015384, Offset.offset(0.00001))
  }

}