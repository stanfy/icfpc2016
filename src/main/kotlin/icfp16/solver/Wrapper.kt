package icfp16.solver

import icfp16.data.Edge
import icfp16.data.Polygon
import icfp16.data.Problem
import icfp16.state.State

fun wrappingEdges(envelop: Polygon, target: Polygon): List<Edge> {
  return target.fullEdges()
      // Check if the edge is inside.
      .filter { envelop.convexIn(it.a) && envelop.convexIn(it.b) }
      // Remove edges that match our boundaries.
      .filter { e -> !envelop.fullEdges().any { it.hasPoint(e.a) && it.hasPoint(e.b) } }
      // Sort by length.
      .sortedByDescending { it.comparativeValue() }
}

class Wrapper: Solver {

  fun solveWithWrapping(problem: Problem, startState: State): State {
    // We know that we have one polygon at the moment.
    val p = startState.poligons()[0]

    problem.poligons.forEach {
      println(wrappingEdges(p, it))
    }

    // TODO
    return State.initialSquare()
  }

  override fun solve(problem: Problem): State {
    // Position our square.
    val startState = BetterTranslatorSolver().solve(problem)
    return solveWithWrapping(problem, startState)
  }

}
