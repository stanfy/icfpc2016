package icfp16.solver

import icfp16.api.parseProblem
import icfp16.data.Edge
import icfp16.data.Fraction
import icfp16.data.Problem
import icfp16.data.Vertex
import icfp16.estimate.BitmapEstimator
import icfp16.io.ProblemContainersParser
import icfp16.state.State
import icfp16.state.solution
import icfp16.visualizer.Visualizer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test

class UglySolverTest {
  @Test
  fun testingOne(){
    val problemString =
        """1
          |4
          |0,0
          |1,0
          |1/2,1/2
          |0,1/2
          |5
          |0,0 1,0
          |1,0 1/2,1/2
          |1/2,1/2 0,1/2
          |0,1/2 0,0
          |0,0 1/2,1/2
        """.trimMargin()
    val problem = parseProblem(problemString)

    val solver = UglySolver()
    solver.solve(problem)

  }


}