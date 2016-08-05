package icfp16.solver

import icfp16.data.Problem
import icfp16.state.State

interface Solver {
  fun solve(problem: Problem): State
}

class StupidSolver: Solver {
  override fun solve(problem: Problem): State {
    return State.initialSquare()
  }
}

class TranslatorSolver: Solver {

  override fun solve(problem: Problem): State {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}