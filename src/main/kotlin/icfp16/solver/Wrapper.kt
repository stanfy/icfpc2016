package icfp16.solver

import icfp16.data.Edge
import icfp16.data.Polygon
import icfp16.data.Problem
import icfp16.data.Vertex
import icfp16.estimate.BitmapEstimator
import icfp16.folder.Line
import icfp16.folder.isLeft
import icfp16.state.ComplexState
import icfp16.state.IState
import icfp16.state.solution
import icfp16.visualizer.Visualizer
import java.util.*

fun wrappingEdges(envelop: Polygon, target: Polygon): List<Edge> {
  return target.edges()
      // Check if the edge is inside.
      .filter { envelop.convexIn(it.a) && envelop.convexIn(it.b) }
      // Remove edges that match our boundaries.
      .filter { e -> !envelop.edges().any { it.hasPoint(e.a) && it.hasPoint(e.b) } }
      // Filter items that leaves us with all points on one side
      .filterNot { e -> envelop.vertices.all { isLeft(e.a, e.b, it) >=0 } }
      .filterNot { e -> envelop.vertices.all { isLeft(e.a, e.b, it) <= 0 } }
      // Sort by length. // This one is not actually the bes idea
      .sortedByDescending { it.comparativeValue() }
}

class Wrapper(private val debug: Boolean = false, val prefix : String = ""): Solver {

  fun solveWithWrapping(problem: Problem, startState: IState, depth: Int): IState {
    val we = startState.poligons().flatMap { wrappingEdges(it, problem.poligons[0]) }

    debugPrintCandidates(depth, problem, startState, we)

    if (we.isEmpty()) {
      // Nothing to do! We should be done.
      return startState
    }

    val state = startState as ComplexState

    val bestNextState = bestNextStateFromFoldingLines(problem, state, we.map { Line(it) })
    if (bestNextState != null) {
      // Fold successful. Let's continue our adventure.
      debugContinuesWithState(bestNextState)
      // Dumping visual state if needed
      debugDumpState(problem, bestNextState, depth, prefix)

      val res = solveWithWrapping(problem, bestNextState, depth + 1) as ComplexState
      if (res.solution().length < 4000) {
        return res
      }
      return startState
    } else {
      // We failed, return last valid state.
      debugMessage("We're done here")

      return startState
    }
  }

  private fun bestNextStateFromFoldingLines(problem: Problem, state: ComplexState, foldingLines: List<Line>): IState? {
    return foldingLines
        .map {
          bestNextStateFromFoldingSimpleLine(problem, state, it)
        }
        .filterNotNull()
        .sortedBy { it.second }
        .map { it.first }
        .firstOrNull()
  }

  private fun bestNextStateFromFoldingSimpleLine(problem: Problem, state: ComplexState, foldingLine: Line): Pair<IState, Double>? {
    val vertexes = state.poligons()
        .flatMap {
          it.edges()
              .map { it.to(Line(it).interection(foldingLine)) }
              .filter { it.second != null && it.first.hasPoint(it.second!!) }
              .map { it.second!! }
        }
        .sortedWith(boundaryComparator)
    if (vertexes.size < 2) {
      throw IllegalArgumentException("TODO: Looks like we cannot make it... Approximate?")
    }

    // This one now actually true because we need  direction hre so we could have two
    val foldingEdge = Edge(vertexes.first(), vertexes.last())
    val inversedFold = foldingEdge.copy(foldingEdge.b, foldingEdge.a)

    val normalState = state.fold(foldingEdge) as ComplexState
    val mirrorState = state.fold(inversedFold) as ComplexState

    // check which one is better
    // TODO: WE can check it faster, right? :)
    debugMessage("folding edge: $foldingEdge and $mirrorState")
    val startStateResemblance = BitmapEstimator().resemblanceOf(problem, state)
    val normalStateResembalnce = BitmapEstimator().resemblanceOf(problem, normalState)
    val mirrorStateResembalnce = BitmapEstimator().resemblanceOf(problem, mirrorState)

    if (normalStateResembalnce <= startStateResemblance && mirrorStateResembalnce <= startStateResemblance) {
      // We failed, return last valid state.
      debugMessage("Failure")

      return null
    }

    val newState = if (normalStateResembalnce > mirrorStateResembalnce) normalState else mirrorState
    val newResemblance = if (normalStateResembalnce > mirrorStateResembalnce) normalStateResembalnce else mirrorStateResembalnce

    // Now we have a correct edge to do the fold. Do it.
    if (newState.polys.size > state.polys.size) {
      return Pair(newState, newResemblance)
    } else {
      // We failed, return last valid state.
      debugMessage("Failure")

      return null
    }

  }


  private fun debugMessage(message: String) {
    if (debug) {
      println(message)
    }
  }

  private fun debugContinuesWithState(newState: IState) {
    if (debug) {
      println("continue")
      println(newState.solution())
    }
  }

  private fun debugDumpState(problem: Problem, state: IState, depth: Int, prefix: String) {
    if (!debug) {
      return
    }
    Visualizer().visualizedAndSaveImage(problem, state, 1, "wrapper/wrapper-$prefix-$depth.png")
  }


  private fun debugPrintCandidates(depth: Int, problem: Problem, startState: IState, we: List<Edge>) {
    if (debug) {
      println("$depth ------------------------")
      println("Candidates: $we")
      if (depth == 0) {
        debugDumpState(problem, startState, -1, prefix)
      }
    }
  }

  private val boundaryComparator: Comparator<Vertex> = object : Comparator<Vertex> {
    override fun compare(o1: Vertex, o2: Vertex): Int {
      val v = o1.relativeVector(o2)
      if (v.a > 0) {
        return 1
      } else if (v.a < 0) {
        return -1
      } else {
        return if (v.b > 0) {
          1
        } else if (v.b < 0) {
          -1
        } else {
          0
        }
      }
    }
  }

  override fun solve(problem: Problem, problemId: String, thresholdResemblance: Double): IState? {
    // Identify the initial state. If something is on the edge - wrap it.
    // If not, translate first.
    //

    // TODO

    // Wrap.
    val startState = ComplexState()
    return solveWithWrapping(problem, startState, 0)
  }

}
