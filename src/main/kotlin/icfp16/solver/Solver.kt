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
    // simple centroid  as  sum of all polygon coords
    val vertexes = problem.poligons.flatMap { it.vertices }
//    val cententroid = vertexes.reduce { original, next ->
////      Vertex(original.x.)
//    }
    return State()
  }
}