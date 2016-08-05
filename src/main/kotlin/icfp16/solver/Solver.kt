package icfp16.solver

import icfp16.Problem
import icfp16.state.State

interface Solver {
  fun solve(problem: Problem): State
}

class StupidSolver: Solver {
  override fun solve(problem: Problem): State {
    return State.initialSquare()
  }
}