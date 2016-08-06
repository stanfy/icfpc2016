package icfp16.solver

import icfp16.data.*
import icfp16.state.IState
import icfp16.state.State

fun wrappingEdges(envelop: Polygon, target: Polygon): List<Edge> {
  return target.edges()
      // Check if the edge is inside.
      .filter { envelop.convexIn(it.a) && envelop.convexIn(it.b) }
      // Remove edges that match our boundaries.
      .filter { e -> !envelop.edges().any { it.hasPoint(e.a) && it.hasPoint(e.b) } }
      // Sort by length.
      .sortedByDescending { it.comparativeValue() }
}

class Wrapper: Solver {

  fun solveWithWrapping(problem: Problem, startState: IState): IState {
    // We know that we have one polygon at the moment.
    val p = startState.poligons()[0]

    val we = wrappingEdges(p, problem.poligons[0])
    if (we.isEmpty()) {
      return startState
    }

    val affineSegment = we[0].b.relativeVector(we[0].a)
    var reflectedPoints = p.vertices
        .filter { affineSegment.side(it.relativeVector(we[0].a)) == VectorSide.LEFT }
    println(reflectedPoints)
    reflectedPoints = reflectedPoints
        .map { it.reflect(we[0]) }

    println(reflectedPoints)


    // TODO: Commented to avoid slowing down solutions.
//    problem.poligons.forEach {
//      println(wrappingEdges(p, it))
//    }

    // TODO
    return State.initialSquare()
  }

  override fun solve(problem: Problem, problemId: String): IState? {
    // Position our square.
    val startState = BetterTranslatorSolver().solve(problem, problemId)
    return solveWithWrapping(problem, startState!!)
  }

}
