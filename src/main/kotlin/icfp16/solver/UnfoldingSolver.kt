package icfp16.solver

import icfp16.data.Problem
import icfp16.state.IState
import icfp16.state.State

class UnfoldingSolver : Solver {
  override fun solve(problem: Problem, problemId: String, thresholdResemblance: Double): IState? {
    val initialSquare = State.initialSquare()
    return initialSquare
  }
}